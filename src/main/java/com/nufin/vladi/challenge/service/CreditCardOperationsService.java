package com.nufin.vladi.challenge.service;

import com.nufin.vladi.challenge.dto.CreditCardWithBalance;
import com.nufin.vladi.challenge.entity.CreditCard;
import com.nufin.vladi.challenge.entity.Transaction;

import java.util.List;

public interface CreditCardOperationsService {
    List<CreditCardWithBalance> getAllCreditCards();
    CreditCard create(String number, double initialBalance);

    Transaction executeTransaction(Long creditCardId, Transaction.TransactionType type, double amount);
    Transaction executeTransaction(CreditCard creditCard, Transaction.TransactionType type, double amount);

    List<Transaction> getAllTransactionsForCreditCard(Long creditCardId);
    List<Transaction> getAllTransactionsForCreditCard(CreditCard creditCard);
}
