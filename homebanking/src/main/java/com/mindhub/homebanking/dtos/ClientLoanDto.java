package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ClientLoan;

public class ClientLoanDto {
    private Long id;
    private String name;
    private Double amount;
    private Integer payments;
    private Long loanId;

    public ClientLoanDto (ClientLoan clientLoan){
        this.id = clientLoan.getId();
        this.name= clientLoan.getLoan().getName();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
        this.loanId = clientLoan.getLoan().getId();
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
    public String getName() {
        return name;
    }
    public Long getLoanId() {
        return loanId;
    }
}