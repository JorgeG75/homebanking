package com.mindhub.homebanking.services.implementation;

import com.mindhub.homebanking.dtos.TransactionDto;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class TransactionServiceImplementation implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<TransactionDto> getAll() {
        return transactionRepository.findAll().stream()
                .map(transaction -> new TransactionDto(transaction))
                .collect(toList());
    }

    @Override
    public TransactionDto getById(Long id) {
        return new TransactionDto(transactionRepository.findById(id).orElse(null));
    }

    @Override
    public void makeTransaction(Double amount, String description, String accountFromNumber, String toAccountNumber, Authentication authentication) {

        //creamos la transaccion
        Transaction transactionDebit = new Transaction(TransactionType.DEBIT,amount, description);
        Transaction transactionCredit = new Transaction(TransactionType.CREDIT,amount, description);

        //actualizamos el balance de la cuenta origen
        accountRepository.findByNumber(accountFromNumber).setBalance(accountRepository.findByNumber(accountFromNumber).getBalance() - amount);
        accountRepository.findByNumber(accountFromNumber).addTransaction(transactionDebit);
        //actualizamos el balance de la cuenta destino
        accountRepository.findByNumber(toAccountNumber).setBalance(accountRepository.findByNumber(toAccountNumber).getBalance() + amount);
        accountRepository.findByNumber(toAccountNumber).addTransaction(transactionCredit);
        //guardamos la transaccion
        transactionRepository.save(transactionDebit);
        transactionRepository.save(transactionCredit);
        //guardamos los cambios en las cuentas
        accountRepository.save(accountRepository.findByNumber(accountFromNumber));
        accountRepository.save(accountRepository.findByNumber(toAccountNumber));
    }
}
