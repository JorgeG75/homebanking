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


			Transaction credit1= new Transaction(account1, TransactionType.CREDIT, 1500.0, LocalDateTime.now(),"credit");
			Transaction debit1 = new Transaction(account1, TransactionType.DEBIT, 1000.0, LocalDateTime.now(), "debit");
			Transaction credit2= new Transaction(account2, TransactionType.CREDIT, 3000.0, LocalDateTime.now(),"prestamo");
			Transaction debit2 = new Transaction(account2, TransactionType.DEBIT, 600.0, LocalDateTime.now(), "pago chinos");
			Transaction credit3= new Transaction(account1, TransactionType.CREDIT, 2500.0, LocalDateTime.now(), "reembolso tarjeta");

			account1.addTransaction(credit1);
			account2.addTransaction(debit1);
			account1.addTransaction(credit2);
			account2.addTransaction(debit2);
			account1.addTransaction(credit3);
			transactionRepository.save(credit1);
			transactionRepository.save(debit1);
			transactionRepository.save(credit2);
			transactionRepository.save(debit2);
			transactionRepository.save(credit3);


//nuevo cliente
			Client client1 = new Client("Tomas", "Martinez", "newtmartinez@mindhub.com");
			System.out.println(client1);
			clientRepository.save(client1);

			Account newAccount = new Account(client1, "VIN003", 8000.0, LocalDate.now());

			client1.addAccount(newAccount);

			accountRepository.save(newAccount);

			Transaction newcredit1= new Transaction(newAccount, TransactionType.CREDIT, 1500.0, LocalDateTime.now(),"credit");
			Transaction newdebit1 = new Transaction(newAccount, TransactionType.DEBIT, 1000.0, LocalDateTime.now(), "debit");
			Transaction newcredit2= new Transaction(newAccount, TransactionType.CREDIT, 3000.0, LocalDateTime.now(),"prestamo");
			Transaction newdebit2 = new Transaction(newAccount, TransactionType.DEBIT, 600.0, LocalDateTime.now(), "pago chinos");
			Transaction newcredit3= new Transaction(newAccount, TransactionType.CREDIT, 2500.0, LocalDateTime.now(), "reembolso tarjeta");

			newAccount.addTransaction(newcredit1);
			newAccount.addTransaction(newdebit1);
			newAccount.addTransaction(newdebit2);
			newAccount.addTransaction(newdebit2);
			newAccount.addTransaction(newcredit3);
			transactionRepository.save(newcredit1);
			transactionRepository.save(newdebit1);
			transactionRepository.save(newcredit2);
			transactionRepository.save(newdebit2);
			transactionRepository.save(newcredit3);

		};
	}
}