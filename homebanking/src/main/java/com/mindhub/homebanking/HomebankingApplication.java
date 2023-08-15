package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository) {
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

			/*List<String> credits = List.of("hipotecario", "personal","automotriz");
			List<Integer> payments = List.of(2, 4,6,12,24,32,48,60);

			for (String i: credits){
				loanRepository.save(new Loan(i, 40000.0, payments));
			}*/




			Loan loan1 = new Loan("hipotecario", 500000.0, List.of(12,24,36,48,60));
			loanRepository.save(loan1);
			ClientLoan clientLoan = new ClientLoan(loan1, client, 500000.0, 60);
			client.addClientLoan(clientLoan);
			clientLoanRepository.save(clientLoan);

			Loan loan2 = new Loan("personal", 100000.0, List.of(6,12,24));
			loanRepository.save(loan2);
			ClientLoan clientLoan1 = new ClientLoan(loan2, client, 40000.0, 12);
			client.addClientLoan(clientLoan1);
			clientLoanRepository.save(clientLoan1);

			Loan loan3 = new Loan("personal", 100000.0, List.of(6,12,24));
			loanRepository.save(loan3);
			ClientLoan clientLoan2 = new ClientLoan(loan3, client1, 100000.0, 24);
			client1.addClientLoan(clientLoan2);
			clientLoanRepository.save(clientLoan2);

			Loan loan4 = new Loan("automotriz", 300000.0, List.of(6,12,24,36));
			loanRepository.save(loan4);
			ClientLoan clientLoan3 = new ClientLoan(loan4, client1, 200000.0, 36);
			client1.addClientLoan(clientLoan3);
			clientLoanRepository.save(clientLoan3);


		};
	}
}