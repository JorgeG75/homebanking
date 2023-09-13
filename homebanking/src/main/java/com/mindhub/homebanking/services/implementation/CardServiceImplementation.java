package com.mindhub.homebanking.services.implementation;

import com.mindhub.homebanking.dtos.CardDto;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.mindhub.homebanking.utils.BankingUtils.generateCvv;
import static com.mindhub.homebanking.utils.BankingUtils.generateRandomCardNumber;
import static java.util.stream.Collectors.toList;

@Service
public class CardServiceImplementation implements CardService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CardRepository cardRepository;


    @Override
    public List<CardDto> getAllCardsDto() {
        return cardRepository.findAll().stream()
                .map(card -> new CardDto(card))
                .collect(toList());
    }

    @Override
    public CardDto getById(Long id) {
        return new CardDto(cardRepository.findById(id).orElse(null));
    }


    @Override
    public void createCard(CardType cardType, CardColor cardColor, Client AuthClient) {
        Card newCard = new Card(cardType, cardColor, LocalDate.now());

        do {
            newCard.setNumber(generateRandomCardNumber());
        } while (cardRepository.findByNumber(newCard.getNumber()) != null);

        newCard.setCvv(generateCvv(newCard.getNumber()));

        AuthClient.addCard(newCard);
        cardRepository.save(newCard);

    }

}
