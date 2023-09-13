package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.ClientDto;
import com.mindhub.homebanking.models.Client;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ClientService {
    List<ClientDto> getClientsDTO();

    void saveClient(Client client);

    ClientDto getClientsDTOById(Long id);

    ClientDto getCurrentClient(Authentication authentication);
}
