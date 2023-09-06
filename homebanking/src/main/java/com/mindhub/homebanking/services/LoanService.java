package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.LoanApplicationDto;
import com.mindhub.homebanking.dtos.LoanDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface LoanService {
    List<LoanDto> getAll();

    void createLoan(LoanApplicationDto loanApplicationDTO, Authentication authentication);
}
