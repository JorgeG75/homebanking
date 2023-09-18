package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Loan;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertTrue;
@DataJpaTest
//@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LoanRepositoryTest {
    @Autowired
    LoanRepository loanRepository;

    @Test
    void existsById() {
       //probando otra forma
        Loan loan = new Loan();
        loanRepository.save(loan);

        boolean exists = loanRepository.existsById(loan.getId());

        assertTrue(exists);

    }


}