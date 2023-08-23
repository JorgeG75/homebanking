package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository,
									  TransactionRepository transactionRepository, LoanRepository loanRepository,
									  ClientLoanRepository clientLoanRepository,CardRepository cardRepository){
		return args -> {

			Client client = new Client("Melba", "Morel","melba@mindhub.com", passwordEncoder.encode("123456"));
			clientRepository.save(client);

			Account account1 = new Account(client,"VIN001", 5000.0, LocalDate.now());
			client.addAccount(account1);
			accountRepository.save(account1);

			Account account2 = new Account(client, "VIN002", 7500.0, LocalDate.now());
			client.addAccount(account2);
			accountRepository.save(account2);

			Transaction credit1= new Transaction(account1, TransactionType.CREDIT, 3000.0,"debit");
			Transaction debit1 = new Transaction(account1, TransactionType.DEBIT, 1000.0, "debit");
			Transaction credit2= new Transaction(account2, TransactionType.CREDIT, 3000.0,"prestamo");
			Transaction debit2 = new Transaction(account2, TransactionType.DEBIT, 600.0, "pago chinos");
			Transaction credit3= new Transaction(account1, TransactionType.CREDIT, 2500.0, "reembolso tarjeta");

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
			Client client1 = new Client("Tomas", "Martinez", "newtmartinez@mindhub.com", passwordEncoder.encode("654321"));
			clientRepository.save(client1);

			Account newAccount = new Account(client1, "VIN003", 8000.0, LocalDate.now());
			client1.addAccount(newAccount);
			accountRepository.save(newAccount);

			Transaction newcredit1= new Transaction(newAccount, TransactionType.CREDIT, 1500.0,"credit");
			Transaction newdebit1 = new Transaction(newAccount, TransactionType.DEBIT, 1000.0, "debit");
			Transaction newcredit2= new Transaction(newAccount, TransactionType.CREDIT, 3000.0,"prestamo");
			Transaction newdebit2 = new Transaction(newAccount, TransactionType.DEBIT, 600.0, "pago chinos");
			Transaction newcredit3= new Transaction(newAccount, TransactionType.CREDIT, 2500.0, "reembolso tarjeta");

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

			Loan loan1 = new Loan("hipotecario", 500000.0, List.of(12,24,36,48,60));
			loanRepository.save(loan1);
			ClientLoan clientLoan = new ClientLoan(loan1, 400000.0,60);
			client.addClientLoan(clientLoan);
			clientLoanRepository.save(clientLoan);

			Loan loan2 = new Loan("personal", 100000.0, List.of(6,12,24));
			loanRepository.save(loan2);
			ClientLoan clientLoan1 = new ClientLoan(loan2,  40000.0, 12);
			client.addClientLoan(clientLoan1);
			clientLoanRepository.save(clientLoan1);

			Loan loan3 = new Loan("personal", 100000.0, List.of(6,12,24));
			loanRepository.save(loan3);
			ClientLoan clientLoan2 = new ClientLoan(loan3,  100000.0, 24);
			client1.addClientLoan(clientLoan2);
			clientLoanRepository.save(clientLoan2);

			Loan loan4 = new Loan("automotriz", 300000.0, List.of(6,12,24,36));
			loanRepository.save(loan4);
			ClientLoan clientLoan3 = new ClientLoan(loan4, 200000.0, 36);
			client1.addClientLoan(clientLoan3);
			clientLoanRepository.save(clientLoan3);


			Card card1= new Card();
			card1.setCardHolder(client.getFirstName().toUpperCase() +" "+ client.getLastName().toUpperCase());
			card1.setType(CardType.DEBIT);
			card1.setColor(CardColor.GOLD);
			card1.setNumber("5034 5674 8763 1022");
			card1.setCvv(750);
			card1.setFromDate(LocalDate.now());
			card1.setThruDate(LocalDate.now().plusYears(5));
			client.addCard(card1);
			cardRepository.save(card1);

			Card card2= new Card();
			card2.setCardHolder(client.getFirstName().toUpperCase() +" "+ client.getLastName().toUpperCase());
			card2.setType(CardType.CREDIT);
			card2.setColor(CardColor.TITANIUM);
			card2.setNumber("5567 0032 6784 1123");
			card2.setCvv(990);
			card2.setFromDate(LocalDate.now());
			card2.setThruDate(LocalDate.now().plusYears(5));
			client.addCard(card2);
			cardRepository.save(card2);

			Card card3 = new Card();
			card3.setCardHolder(client1.getFirstName().toUpperCase() +" "+ client1.getLastName().toUpperCase());
			card3.setType(CardType.CREDIT);
			card3.setColor(CardColor.SILVER);
			card3.setNumber("5784 9843 7654 1222");
			card3.setCvv(987);
			card3.setFromDate(LocalDate.now());
			card3.setThruDate(LocalDate.now().plusYears(5));
			client1.addCard(card3);
			cardRepository.save(card3);

		};
	}
}