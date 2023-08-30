package com.mindhub.homebanking.utils;

import com.mindhub.homebanking.repositories.AccountRepository;

import java.util.Random;

public class BankingUtils {

    public static String generateRandomCardNumber() {
        Random random = new Random();
        int cardNumber = random.nextInt(10000);

        StringBuilder cardNumberString = new StringBuilder(String.format("%04d", cardNumber));

        for (int i = 0; i < 3; i++) {
            cardNumberString.append("-").append(String.format("%04d", random.nextInt(10000)));
        }

        return cardNumberString.toString();
    }

    public static Integer generateCvv(String cardNumber) {
        int sum = 0;

        for (String block : cardNumber.split("-")) {
            int aux = 0;

            for (int i = 0; i < 3; i++) {
                aux += Character.getNumericValue(block.charAt(i));
            }

            sum += aux;
        }

        return (300 + (sum % 1000));
    }

    public static String genAccountId(AccountRepository accountRepository) {
        return "VIN" + String.format("%03d", accountRepository.count() + 1);
    }
}
