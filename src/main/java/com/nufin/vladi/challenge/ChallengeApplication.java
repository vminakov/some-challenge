package com.nufin.vladi.challenge;

import com.nufin.vladi.challenge.entity.CreditCard;
import com.nufin.vladi.challenge.entity.Transaction;
import com.nufin.vladi.challenge.repository.CreditCardRepository;
import com.nufin.vladi.challenge.repository.TransactionRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ChallengeApplication {

	@Autowired
	private CreditCardRepository creditCardRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	public static void main(String[] args) {
		SpringApplication.run(ChallengeApplication.class, args);
	}

	@Bean
	InitializingBean initDummyData() {
		return () -> {
			CreditCard cc1 = new CreditCard("4111111111111111");
			Transaction t1 = new Transaction(Transaction.TransactionType.CREDIT, 100.0, 100.0, cc1);

			creditCardRepository.save(cc1);
			transactionRepository.save(t1);
		};
	}
}
