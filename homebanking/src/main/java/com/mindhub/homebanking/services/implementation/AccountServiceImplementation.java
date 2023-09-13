package com.mindhub.homebanking.services.implementation;

import com.mindhub.homebanking.dtos.AccountDto;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class AccountServiceImplementation implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<AccountDto> getAll() {
        return accountRepository.findAll().stream()
                .map(account -> new AccountDto(account))
                .collect(toList());
    }

    @Override
    public AccountDto getById(Long id, Authentication authentication) {
        return new AccountDto(accountRepository.findById(id).orElse(null));
    }

    @Override
    public List<AccountDto> getCurrentAccounts(Authentication authentication) {
        return accountRepository.findAll().stream()
                .filter(account -> account.getClient().getMail().equals(authentication.getName()))
                .map(account -> new AccountDto(account))
                .collect(toList());
    }

    @Override
    public void createAccount(Authentication authentication) {
        Account newAccount= new Account("VIN"+String.format("%03d",accountRepository.count()+1) , 0.0, LocalDate.now());
        Client AuthClient = clientRepository.findByMail(authentication.getName());
        AuthClient.addAccount(newAccount);
        accountRepository.save(newAccount);
    }
}
