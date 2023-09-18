package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.dtos.ClientDto;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.RolType;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientService clientService;
    @GetMapping("/clients")
    public List<ClientDto> getAll(){
        return clientService.getClientsDTO();
    }

    @PostMapping("/clients")

    public ResponseEntity<Object> saveClient(

            @RequestParam String firstName, @RequestParam String lastName,

            @RequestParam String email, @RequestParam String password) {

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (clientRepository.findByMail(email) != null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

        clientService.saveClient(new Client(firstName, lastName, email, passwordEncoder.encode(password), RolType.CLIENT));

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @GetMapping("/clients/{id}")
    public ClientDto getById(@PathVariable Long id){
        return clientService.getClientsDTOById(id);
    }

    @GetMapping("/clients/current")
    public ClientDto getCurrentClient(Authentication authentication) {
        return clientService.getCurrentClient(authentication);
    }
}