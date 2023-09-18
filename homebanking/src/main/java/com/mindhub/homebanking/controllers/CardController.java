package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDto;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CardService cardService;

    @GetMapping("/cards")
    public List<CardDto> getAll(){
        return cardService.getAllCardsDto();
    }

    @GetMapping("/cards/{id}")
    public CardDto getById(@PathVariable Long id){
        return cardService.getById(id);
    }


    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard(@RequestParam  CardType cardType, @RequestParam CardColor cardColor, Authentication authentication) {

        Client AuthClient = clientRepository.findByMail(authentication.getName());


        if (cardRepository.findByClient(AuthClient).stream()
                .anyMatch(card -> card.getType().equals(cardType) && card.getColor().equals(cardColor))) {
            return new ResponseEntity<>("Already have a "+cardType+" card "+cardColor+".", HttpStatus.FORBIDDEN);
        }

        cardService.createCard(cardType, cardColor, AuthClient);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}

