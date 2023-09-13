package com.mindhub.homebanking.services.implementation;

import com.mindhub.homebanking.dtos.LoanApplicationDto;
import com.mindhub.homebanking.dtos.LoanDto;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;
@Service
public class LoanServiceImplementation implements LoanService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @Override
    public List<LoanDto> getAll() {
        return loanRepository.findAll().stream()
                .map(loan -> new LoanDto(loan))
                .collect(toList());
    }

    @Override
    public void createLoan(LoanApplicationDto loanApplicationDto, Authentication authentication) {
        Long loanId = loanApplicationDto.getIdLoan();
        Double amount = loanApplicationDto.getAmount();
        Integer payments = loanApplicationDto.getPayments();
        String toAccountNumber = loanApplicationDto.getToAccountNumber();

        clientRepository.findByMail(authentication.getName());

        //loan mas el 20%
        ClientLoan clientLoan =new ClientLoan(loanApplicationDto.getAmount() + amount*0.2, loanApplicationDto.getPayments());

        clientRepository.findByMail(authentication.getName()).addClientLoan(clientLoan);
        loanRepository.findById(loanId).get().addClientLoan(clientLoan);

        clientLoanRepository.save(clientLoan);

        Transaction transactionLoan = new Transaction(TransactionType.CREDIT,amount, "loan approved");
        accountRepository.findByNumber(toAccountNumber).setBalance(accountRepository.findByNumber(toAccountNumber).getBalance() + amount);
        accountRepository.findByNumber(toAccountNumber).addTransaction(transactionLoan);
        transactionRepository.save(transactionLoan);
        accountRepository.save(accountRepository.findByNumber(toAccountNumber));
    }
}