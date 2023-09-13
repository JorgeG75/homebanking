package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.CardDto;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;

import java.util.List;

public interface CardService {
    List<CardDto> getAllCardsDto();

    CardDto getById(Long id);

    void createCard(CardType cardType, CardColor cardColor, Client AuthClient);
}
