package com.nufin.vladi.challenge.repository;

import com.nufin.vladi.challenge.entity.CreditCard;
import com.nufin.vladi.challenge.entity.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    List<Transaction> findByCreditCard(CreditCard creditCard);
    List<Transaction> findByCreditCardOrderByCreateDateTimeDesc(CreditCard creditCard);

    default Optional<Transaction> findLastTransaction(CreditCard creditCard) {
        return findFirstByCreditCardOrderByCreateDateTimeDesc(creditCard);
    }

    Optional<Transaction> findFirstByCreditCardOrderByCreateDateTimeDesc(CreditCard creditCard);
}
