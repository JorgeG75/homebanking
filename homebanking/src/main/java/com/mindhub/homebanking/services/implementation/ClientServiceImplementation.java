package com.mindhub.homebanking.services.implementation;

import com.mindhub.homebanking.dtos.ClientDto;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ClientServiceImplementation implements ClientService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<ClientDto> getClientsDTO() {
        return clientRepository.findAll().stream()
                .map(client -> new ClientDto(client))
                .collect(toList());
    }

    @Override
    public void saveClient(Client newClient) {
        Account account= new Account("VIN"+String.format("%03d",accountRepository.count()+1) , 0.0, LocalDate.now());
        newClient.addAccount(account);
        clientRepository.save(newClient);
        accountRepository.save(account);
    }

    @Override
    public ClientDto getClientsDTOById(Long id) {
        return new ClientDto(clientRepository.findById(id).orElse(null));
    }
    @Override
    public ClientDto getCurrentClient(Authentication authentication) {
        return new ClientDto(clientRepository.findByMail(authentication.getName()));
    }
}
