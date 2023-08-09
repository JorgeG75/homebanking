package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository) {
		return args -> {

			Client client = new Client("Melba", "Morel","melba@mindhub.com");
			System.out.println(client);
			clientRepository.save(client);

			Account account1 = new Account(client, "VIN001", 5000.0, LocalDate.now());
			Account account2 = new Account(client, "VIN002", 7500.0, LocalDate.now());

			client.addAccount(account1);
			client.addAccount(account2);

			accountRepository.save(account1);
			accountRepository.save(account2);


			Transaction creditTransactionClient = new Transaction(account1, TransactionType.CREDIT, 1500.0, LocalDateTime.now(),"credit");
			account1.addTransaction(creditTransactionClient);
			transactionRepository.save(creditTransactionClient);

			Transaction debitTransacttioClient = new Transaction(account2, TransactionType.DEBIT, 1000.0, LocalDateTime.now(), "debit");
			account2.addTransaction(debitTransacttioClient);
			transactionRepository.save(debitTransacttioClient);

			Client client1 = new Client("Tomas", "Martinez", "newtmartinez@mindhub.com");
			System.out.println(client1);
			clientRepository.save(client1);

			Account newAccount = new Account(client1, "VIN001", 8000.0, LocalDate.now());

			client1.addAccount(newAccount);

			accountRepository.save(newAccount);


		};
	}
}