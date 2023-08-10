package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    private TransactionType type;

    private Double amount;

    private String description;

    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.EAGER)
    private Account account;

    public Transaction(){}

    public Transaction(Account account, TransactionType type, Double amount, LocalDateTime date, String description){
        this.type = type;
        setAmount( amount, type);
        this.account = account;
        this.description = description;
        this.date = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public LocalDateTime getDate() {
        return date;
    }
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount, TransactionType type) {

        //muestra el saldo negativo para debit
        this.amount = this.type.equals(TransactionType.CREDIT)? amount : -amount;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
