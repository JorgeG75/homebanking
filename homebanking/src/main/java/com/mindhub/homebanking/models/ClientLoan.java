package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

public class ClientLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private Double amount;
    private Integer payments;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Client> clients = new HashSet<>();
    @ManyToOne(fetch = FetchType.EAGER)
    private Loan Loan;

    public ClientLoan(){}

    public ClientLoan(Loan loan, Double amount, Integer payments){
        this.amount = amount;
        this.payments = payments;
        this.Loan = loan;
    }

    public Long getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public Set<Client> getClients() {
        return clients;
    }

    public com.mindhub.homebanking.models.Loan getLoan() {
        return Loan;
    }
}
