package com.mindhub.homebanking.dtos;

public class LoanApplicationDto {
    private Long idLoan;
    private Double amount;
    private Integer payments;
    private String toAccountNumber;

    public LoanApplicationDto(){

    }

    public Long getIdLoan() {
        return idLoan;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public String getToAccountNumber() {
        return toAccountNumber;
    }
}
