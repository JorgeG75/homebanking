package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.dtos.ClientDto;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ClientRepository clientRepository;
    @RequestMapping("/clients")
    public List<ClientDto> getAll(){
        return clientRepository.findAll().stream()
                .map(client -> new ClientDto(client))
                .collect(toList());
    }
    @RequestMapping(path = "/clients", method = RequestMethod.POST)

    public ResponseEntity<Object> register(

            @RequestParam String firstName, @RequestParam String lastName,

            @RequestParam String mail, @RequestParam String password) {

        if (firstName.isEmpty() || lastName.isEmpty() || mail.isEmpty() || password.isEmpty()) {

            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);

        }

        if (clientRepository.findByMail(mail) != null) {

            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);

        }

        clientRepository.save(new Client(firstName, lastName, mail, passwordEncoder.encode(password)));

        return new ResponseEntity<>(HttpStatus.CREATED);

    }
    @RequestMapping("/clients/current")
    public ClientDto getCurrentClient(Authentication authentication) {
        return new ClientDto(clientRepository.findByMail(authentication.getName()));
    }
    @RequestMapping("/clients/{id}")
    public ClientDto getById(@PathVariable Long id){
        return new ClientDto(clientRepository.findById(id).orElse(null));
    }

}