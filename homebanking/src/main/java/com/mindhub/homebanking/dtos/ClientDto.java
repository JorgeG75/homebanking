package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Client;

import java.util.Set;
import java.util.stream.Collectors;

public class ClientDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String mail;
    private Set<AccountDto> accounts;
    private Set<ClientLoanDto> loans;
    private Set<CardDto> cards;

    public ClientDto (Client client){
        id = client.getId();
        firstName= client.getFirstName();
        lastName= client.getLastName();
        mail= client.getMail();
        accounts= client.getAccounts().stream()
                .map(element -> new AccountDto(element))
                .collect(Collectors.toSet());
        loans=client.getClientLoans().stream()
                .map(clientLoan-> new ClientLoanDto(clientLoan))
                .collect(Collectors.toSet());
        cards= client.getCards().stream()
                .map(card -> new CardDto(card))
                .collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getMail() {
        return mail;
    }
    public Set<AccountDto> getAccounts() {
        return accounts;
    }
    public Set<ClientLoanDto> getLoans() {return loans;}
    public Set<CardDto> getCards() { return cards; }
}