package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanApplicationDto;
import com.mindhub.homebanking.dtos.LoanDto;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.LoanRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api")
public class LoanController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private LoanService loanService;
    @RequestMapping(value="/loans",method = RequestMethod.GET)
    public List<LoanDto> getAll(){
        return loanService.getAll();
    }

    @Transactional
    @RequestMapping(value="/loans",method = RequestMethod.POST)
    public ResponseEntity<Object> createLoan(
            @RequestBody LoanApplicationDto loanApplicationDto,
            Authentication authentication){

        Long idLoan = loanApplicationDto.getIdLoan();
        Double amount = LoanApplicationDto.getAmount();
        Integer payments = LoanApplicationDto.getPayments();
        String toAccountNumber = LoanApplicationDto.getToAccountNumber();

        if (idLoan==null || amount == null || payments == null || toAccountNumber == null) {
            return new ResponseEntity<>("Debe completar todos campos", HttpStatus.FORBIDDEN);
        }
        if (amount < 0) {
            return new ResponseEntity<>("Ingrese un monto mayor que 0", HttpStatus.FORBIDDEN);
        }
        if (payments < 0) {
            return new ResponseEntity<>("El monto del pago debe ser mayor que cero", HttpStatus.FORBIDDEN);
        }
        if (!loanRepository.findById(idLoan).isPresent() ) {
            return new ResponseEntity<>("El tipo prestamo no existe", HttpStatus.FORBIDDEN);
        }
        if (accountRepository.findByNumber(toAccountNumber) == null) {
            return new ResponseEntity<>("La cuenta de destino no existe", HttpStatus.FORBIDDEN);
        }
        if (!accountRepository.findByNumber(toAccountNumber).getClient().getMail().equals(authentication.getName())) {
            return new ResponseEntity<>("Esta cuenta no le pertenece ingrese una valida", HttpStatus.FORBIDDEN);
        }

        if(amount>loanRepository.findById(idLoan).get().getMaxAmount()){
            return new ResponseEntity<>("El monto supera la cantidad permitida", HttpStatus.FORBIDDEN);
        }

        loanService.createLoan(loanApplicationDto,authentication);

        return  new ResponseEntity<>(HttpStatus.CREATED);
    }

}
