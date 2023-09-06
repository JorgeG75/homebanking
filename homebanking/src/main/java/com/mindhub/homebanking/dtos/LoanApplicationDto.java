package com.mindhub.homebanking.dtos;

public class LoanApplicationDto {
    private static Long idLoan;
    private static Double amount;
    private static Integer payments;
    private static String toAccountNumber;
 public LoanApplicationDto(Long idLoan, Double amount, Integer payments, String toAccountNumber){
     this.idLoan = idLoan;
     this.amount = amount;
     this.payments = payments;
     this.toAccountNumber = toAccountNumber;
 }

    public static Long getIdLoan() {
        return idLoan;
    }

    public static void setIdLoan(Long idLoan) {
        LoanApplicationDto.idLoan = idLoan;
    }

    public static Double getAmount() {
        return amount;
    }

    public static void setAmount(Double amount) {
        LoanApplicationDto.amount = amount;
    }

    public static Integer getPayments() {
        return payments;
    }

    public static void setPayments(Integer payments) {
        LoanApplicationDto.payments = payments;
    }

    public static String getToAccountNumber() {
        return toAccountNumber;
    }

    public static void setToAccountNumber(String toAccountNumber) {
        LoanApplicationDto.toAccountNumber = toAccountNumber;
    }
}
