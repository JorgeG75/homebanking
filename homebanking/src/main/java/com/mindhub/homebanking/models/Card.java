package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

import static com.mindhub.homebanking.utils.BankingUtils.generateCvv;
import static com.mindhub.homebanking.utils.BankingUtils.generateRandomCardNumber;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "client_id")
    private Client client;
    private String cardHolder;
    private CardType type;
    private CardColor color;
    private String number;
    private Integer cvv;
    private LocalDate fromDate;
    private LocalDate thruDate;


    public Card(){};

    public Card(CardType type, CardColor color, LocalDate fromDate) {
        this.type = type;
        this.color = color;
        this.fromDate = fromDate;
        setThruDate(fromDate);
        setNumber(generateRandomCardNumber());
        setCvv(generateCvv(this.number));
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public CardType getType() {
        return type;
    }
    public void setType(CardType type) {
        this.type = type;
    }
    public CardColor getColor() {
        return color;
    }
    public void setColor(CardColor color) {
        this.color = color;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public Integer getCvv() {
        return cvv;
    }
    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }
    public LocalDate getFromDate() {
        return fromDate;
    }
    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }
    public LocalDate getThruDate() {
        return thruDate;
    }
    public void setThruDate(LocalDate thruDate) {
        this.thruDate = this.fromDate.plusYears(5);
    }
    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public void addCardHolder(Client client) {
        this.cardHolder = client.getFirstName() + " " + client.getLastName();
        this.client = client;
    }
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

}
