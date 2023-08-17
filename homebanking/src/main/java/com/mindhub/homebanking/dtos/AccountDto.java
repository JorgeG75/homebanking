package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDto {
    private Long id;
    private String number;
    private LocalDate date;
    private Double balance;
    private Set<TransactionDto> transactions;


    public AccountDto (Account account){
        id= account.getId();
        number= account.getNumber();
        balance= account.getBalance();
        date= account.getCreationDate();
        transactions = account.getTransactions().stream()
                .map(element -> new TransactionDto(element))
                .collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public Double getBalance() {
        return balance;
    }

    public LocalDate getDate() {
        return date;
    }

    public Set<TransactionDto> getTransactions() {
        return transactions;
    }
}