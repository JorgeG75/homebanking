package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.TransactionDto;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface TransactionService {
    List<TransactionDto> getAll();

    TransactionDto getById(@PathVariable Long id);

    void createdTransaction(
            @RequestParam Double amount, @RequestParam String description ,
            @RequestParam(value = "fromAccountNumber") String accountFromNumber, @RequestParam String toAccountNumber,
            Authentication authentication);
}
