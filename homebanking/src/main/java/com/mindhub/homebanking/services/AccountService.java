package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface AccountService {
    List<AccountDto> getAll();

    AccountDto getById(Long id, Authentication authentication);

    List<AccountDto> getCurrentAccounts( Authentication authentication);


    void createAccount(Authentication authentication);
}
