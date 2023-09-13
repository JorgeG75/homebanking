package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.TransactionDto;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
    public class TransactionController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionService transactionService;
    @RequestMapping(value = "/transactions",method = RequestMethod.GET)
    public List<TransactionDto> getAll(){
        return transactionService.getAll();
    }
    @RequestMapping(value = "/transactions/{id}",method = RequestMethod.GET)
    public TransactionDto getById(@PathVariable Long id){
        return transactionService.getById(id);
    }
    @Transactional
    @RequestMapping(value = "/transactions",method = RequestMethod.POST)
    public ResponseEntity createdTransaction(
            @RequestParam Double amount, @RequestParam String description ,
            @RequestParam(value = "fromAccountNumber") String accountFromNumber, @RequestParam String toAccountNumber,
            Authentication authentication){

        if (amount == null || description == null || accountFromNumber == null || toAccountNumber == null) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (accountFromNumber.equals(toAccountNumber)) {
            return new ResponseEntity<>("The origin and destination accounts are the same", HttpStatus.FORBIDDEN);
        }
        if (accountRepository.findByNumber(accountFromNumber) == null) {
            return new ResponseEntity<>("The origin account does not exist", HttpStatus.FORBIDDEN);
        }
        if (accountRepository.findByNumber(toAccountNumber) == null) {
            return new ResponseEntity<>("The destination account does not exist", HttpStatus.FORBIDDEN);
        }
        if (!accountRepository.findByNumber(accountFromNumber).getClient().getMail().equals(authentication.getName())) {
            return new ResponseEntity<>("The origin account does not belong to the logged in client", HttpStatus.FORBIDDEN);
        }
        if (accountRepository.findByNumber(accountFromNumber).getBalance() < amount) {
            return new ResponseEntity<>("Insufficient funds", HttpStatus.FORBIDDEN);
        }
        if (amount < 0) {
            return new ResponseEntity<>("The amount must be greater than 0", HttpStatus.FORBIDDEN);
        }

        transactionService.createdTransaction(amount,description,accountFromNumber,toAccountNumber,authentication);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

}
