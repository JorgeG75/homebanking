package com.mindhub.homebanking.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

class BankingUtilsTest {

    @Test
    void generateRandomCardNumber() {
        String cardNumber = BankingUtils.generateRandomCardNumber();

        assertNotNull(cardNumber);

        assertTrue(cardNumber.matches("\\d{4}-\\d{4}-\\d{4}-\\d{4}"));
    }

    @Test
    void generateCvv() {
        String cardNumber = "1234-5678-9012-3456";
        Integer cvv = BankingUtils.generateCvv(cardNumber);

        assertNotNull(cvv);

        // Verificar que el CVV tenga el valor correcto para el numero de tarjeta dada
        assertEquals(Integer.valueOf(346), cvv);
    }

}
