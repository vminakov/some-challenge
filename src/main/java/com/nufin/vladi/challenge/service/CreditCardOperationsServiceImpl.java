package com.nufin.vladi.challenge.service;

import com.nufin.vladi.challenge.dto.CreditCardWithBalance;
import com.nufin.vladi.challenge.entity.CreditCard;
import com.nufin.vladi.challenge.entity.Transaction;
import com.nufin.vladi.challenge.repository.CreditCardRepository;
import com.nufin.vladi.challenge.repository.TransactionRepository;
import com.nufin.vladi.challenge.service.exception.CreditCardExistsException;
import com.nufin.vladi.challenge.service.exception.CreditCardNotFoundException;
import com.nufin.vladi.challenge.service.exception.NotEnoughBalanceException;
import com.nufin.vladi.challenge.service.exception.TransactionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreditCardOperationsServiceImpl implements CreditCardOperationsService {
    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public List<CreditCardWithBalance> getAllCreditCards() {
        return creditCardRepository.findAllWithBalance();
    }


    @Override
    public CreditCard create(String number, double initialBalance) {
        if (creditCardRepository.existsByNumber(number)) {
            throw new CreditCardExistsException("Credit card with the given number already exists");
        }

        CreditCard cc = new CreditCard(number);
        Transaction transaction = new Transaction(Transaction.TransactionType.CREDIT, initialBalance, initialBalance, cc);

        cc = creditCardRepository.save(cc);
        transactionRepository.save(transaction);

        return cc;
    }

    @Override
    public Transaction executeTransaction(Long creditCardId, Transaction.TransactionType type, double amount) {
        CreditCard cc = creditCardRepository
                .findById(creditCardId)
                .orElseThrow(() -> new CreditCardNotFoundException("Credit card " + creditCardId.toString() + " not found"));

        return executeTransaction(cc, type, amount);
    }

    @Override
    public Transaction executeTransaction(CreditCard creditCard,Transaction.TransactionType type, double amount) {
        Transaction previousTransaction = transactionRepository
                .findLastTransaction(creditCard)
                .orElseThrow(() -> new TransactionNotFoundException("No previous transaction found"));

        double newBalance = calculateNewBalance(previousTransaction.getBalance(), type, amount);
        Transaction trans = new Transaction(type, amount, newBalance, creditCard);
        return transactionRepository.save(trans);
    }

    @Override
    public List<Transaction> getAllTransactionsForCreditCard(Long creditCardId) {
        return getAllTransactionsForCreditCard(
                creditCardRepository
                        .findById(creditCardId)
                        .orElseThrow(() -> new CreditCardNotFoundException("Credit card " + creditCardId.toString() + " not found"))
        );
    }

    @Override
    public List<Transaction> getAllTransactionsForCreditCard(CreditCard creditCard) {
        return new ArrayList<>(transactionRepository.findByCreditCardOrderByCreateDateTimeDesc(creditCard));
    }

    private static double calculateNewBalance(double currentBalance, Transaction.TransactionType type, double amount) {
        if (type == Transaction.TransactionType.CHARGE) {
            if (currentBalance - amount < 0) {
                throw new NotEnoughBalanceException("Not enough balance on credit card");
            }
            return currentBalance - amount;
        }

        return currentBalance + amount;
    }
}
