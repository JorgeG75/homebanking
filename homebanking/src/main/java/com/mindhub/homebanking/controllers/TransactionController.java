package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.TransactionDto;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
    public class TransactionController {
        @Autowired
        private TransactionRepository transactionRepository;
        @Autowired
        private AccountRepository accountRepository;

        @RequestMapping(value = "/transactions",method = RequestMethod.GET)
        public List<TransactionDto> getAll(){
            return transactionRepository.findAll().stream()
                    .map(transaction -> new TransactionDto(transaction))
                    .collect(toList());
        }
        @RequestMapping(value = "/transactions/{id}",method = RequestMethod.GET)
        public TransactionDto getById(@PathVariable Long id){
            return new TransactionDto(transactionRepository.findById(id).orElse(null));
        }
    @Transactional
    @RequestMapping(value = "/transactions",method = RequestMethod.POST)
    public ResponseEntity makeTransaction(
            @RequestParam Double amount, @RequestParam String description ,
            @RequestParam(value = "fromAccountNumber") String accountFromNumber, @RequestParam String toAccountNumber,
            Authentication authentication){


        if (amount == null || description == null || accountFromNumber == null || toAccountNumber == null) {
            return new ResponseEntity<>("Debe completar todos campos ", HttpStatus.FORBIDDEN);
        }

        if (amount < 0) {
            return new ResponseEntity<>("Ingrese un monto mayor que 0", HttpStatus.FORBIDDEN);
        }

        if (accountFromNumber.equals(toAccountNumber)) {
            return new ResponseEntity<>("Las cuentas son iguales, ingrese otro numero de cuenta ", HttpStatus.FORBIDDEN);
        }

        if (accountRepository.findByNumber(accountFromNumber) == null) {
            return new ResponseEntity<>("No existe el numero de cuenta ingresado", HttpStatus.FORBIDDEN);
        }

        if (accountRepository.findByNumber(toAccountNumber) == null) {
            return new ResponseEntity<>("La cuenta no existe", HttpStatus.FORBIDDEN);
        }

        if (!accountRepository.findByNumber(accountFromNumber).getClient().getMail().equals(authentication.getName())) {
            return new ResponseEntity<>("La cuenta no pertenece al cliente", HttpStatus.FORBIDDEN);
        }

        if (accountRepository.findByNumber(accountFromNumber).getBalance() < amount) {
            return new ResponseEntity<>("Fondos insuficientes", HttpStatus.FORBIDDEN);
        }

        Transaction transactionDebit = new Transaction(TransactionType.DEBIT,amount, description);
        accountRepository.findByNumber(accountFromNumber).setBalance(accountRepository.findByNumber(accountFromNumber).getBalance() - amount);
        accountRepository.findByNumber(accountFromNumber).addTransaction(transactionDebit);
        transactionRepository.save(transactionDebit);
        accountRepository.save(accountRepository.findByNumber(accountFromNumber));

        Transaction transactionCredit = new Transaction(TransactionType.CREDIT,amount, description);
        accountRepository.findByNumber(toAccountNumber).setBalance(accountRepository.findByNumber(toAccountNumber).getBalance() + amount);
        accountRepository.findByNumber(toAccountNumber).addTransaction(transactionCredit);
        transactionRepository.save(transactionCredit);
        accountRepository.save(accountRepository.findByNumber(toAccountNumber));

        return new ResponseEntity<>(HttpStatus.CREATED);

    }



}
