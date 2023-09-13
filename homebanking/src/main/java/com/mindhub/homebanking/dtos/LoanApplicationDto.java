package com.mindhub.homebanking.dtos;

public class LoanApplicationDto {
    private  Long idLoan;
    private Double amount;
    private Integer payments;
    private String toAccountNumber;
 public LoanApplicationDto(Long idLoan, Double amount, Integer payments, String toAccountNumber){
     this.idLoan = idLoan;
     this.amount = amount;
     this.payments = payments;
     this.toAccountNumber = toAccountNumber;
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
