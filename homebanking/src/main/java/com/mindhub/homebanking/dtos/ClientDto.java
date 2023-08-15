package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Client;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class ClientDto  {

    private Long id;
    private String firstName;
    private String lastName;
    private String mail;

    private List<AccountDto> accounts;
    private List<ClientLoanDTO> loans;
    public ClientDto(Client client) {

        this.id = client.getId();

        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.mail = client.getMail();
        this.accounts= client.getAccounts().stream()
                .map(AccountDto::new)
                .collect(toList());
        this.loans = client.getClientLoans().stream()
                .map(ClientLoanDTO::new)
                .collect(toList());
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
    public List<AccountDto> getAccounts() {
        return accounts;
    }
    public List<ClientLoanDTO> getLoans() {
        return loans;
    }
}