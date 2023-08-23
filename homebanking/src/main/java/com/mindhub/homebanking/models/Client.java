package com.mindhub.homebanking.models;

import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    private Long id;
    private String firstName;
    private String lastName;
    private String mail;
    private String password;
    private RolType rol;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "client")
    private Set<Account> accounts = new HashSet<>();
    @OneToMany (fetch = FetchType.EAGER, mappedBy = "client")
    private Set<ClientLoan> loans = new HashSet<>();
    @OneToMany (fetch = FetchType.EAGER, mappedBy = "client")
    private Set<Card> cards = new HashSet<>();

    public Client(){ }

    public Client(String firstName, String lastName, String mail, String password){
        this.firstName= firstName;
        this.lastName= lastName;
        this.mail= mail;
        this.password= password;
        this.rol= RolType.USER;
    }

    public Long getId() {
        return id;
    }
    public Set<Account> getAccounts() {
        return accounts;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }
    public Set<ClientLoan> getClientLoans() {
        return loans;
    }
    public void setLoans(Set<ClientLoan> loans) {
        this.loans = loans;
    }
    public Set<Card> getCards() { return cards;}
    public void setCards(Set<Card> cards) { this.cards = cards;}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public RolType getRol() {
        return rol;
    }
    public void setRol(RolType rol) {
        this.rol = rol;
    }

    public void addAccount(Account account){
        account.setClient(this);
        accounts.add(account);
    }
    public void addClientLoan (ClientLoan clientLoan){
        clientLoan.setClient(this);
        loans.add(clientLoan);
    }
    public void addCard (Card card){
        card.setClient(this);
        cards.add(card);
    }
    @JsonIgnore
    public List<Loan> getLoans (){
        return loans.stream().map(ClientLoan::getLoan).collect(Collectors.toList());
    }
}