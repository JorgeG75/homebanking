package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDto;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping("/cards")
    public List<CardDto> getAll() {
        return cardRepository.findAll().stream()
                .map(card -> new CardDto(card))
                .collect(toList());
    }

    @RequestMapping("/cards/{id}")
    public CardDto getById(@PathVariable Long id) {
        return new CardDto(cardRepository.findById(id).orElse(null));
    }

    @RequestMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard(
            @RequestParam CardType cardType,
            @RequestParam CardColor cardColor,
            Authentication authentication) {

        long count = clientRepository.findByMail(authentication.getName())
                .getCards()
                .stream()
                .filter(card -> card.getType().equals(cardType))
                .count();

        if (count == 3) {
            return new ResponseEntity<>("Already have 3 cards of " + cardType, HttpStatus.FORBIDDEN);
        }

        Card newCard = new Card(cardType, cardColor, LocalDate.now());
        Client AuthClient = clientRepository.findByMail(authentication.getName());
        AuthClient.addCard(newCard);
        cardRepository.save(newCard);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

