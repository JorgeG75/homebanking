package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.dtos.ClientDto;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;
    @RequestMapping("/clients")
    public List<ClientDto> getAll(){
        return clientRepository.findAll().stream()
                .map(client -> new ClientDto(client))
                .collect(toList());
    }
    @RequestMapping("/clients/{id}")
    public ClientDto getById(@PathVariable Long id){
        return new ClientDto(clientRepository.findById(id).orElse(null));
    }

}