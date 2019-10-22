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
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CreditCardOperationsServiceImplTest {

    @Autowired
    private CreditCardOperationsServiceImpl creditCardOperationsService;

    @MockBean
    private CreditCardRepository creditCardRepository;

    @MockBean
    private TransactionRepository transactionRepository;


    @Test
    public void getAllCreditCards() {
        CreditCardWithBalance ccWithBalance1 =  new CreditCardWithBalance(1L, "4908333520439894", 100.0);
        Mockito.when(creditCardRepository.findAllWithBalance()).thenReturn(Arrays.asList(ccWithBalance1));

        List<CreditCardWithBalance> creditCards =  creditCardOperationsService.getAllCreditCards();
        assertEquals(1, creditCards.size());
        assertEquals(ccWithBalance1, creditCards.get(0));
    }

    @Test
    public void create() {
        String number = "4173835666345642";
        double balance = 200;

        Mockito.when(creditCardRepository.save(ArgumentMatchers.any(CreditCard.class))).thenReturn(new CreditCard(number));

        CreditCard cc = creditCardOperationsService.create(number, balance);
        assertEquals(number, cc.getNumber());

        ArgumentCaptor<CreditCard> argumentCaptorCreditCard = ArgumentCaptor.forClass(CreditCard.class);
        Mockito.verify(creditCardRepository).save(argumentCaptorCreditCard.capture());
        assertEquals(number, argumentCaptorCreditCard.getValue().getNumber());

        ArgumentCaptor<Transaction> argumentCaptorTransaction = ArgumentCaptor.forClass(Transaction.class);
        Mockito.verify(transactionRepository).save(argumentCaptorTransaction.capture());
        assertEquals(argumentCaptorCreditCard.getValue(), argumentCaptorTransaction.getValue().getCreditCard());
        assertEquals(Transaction.TransactionType.CREDIT, argumentCaptorTransaction.getValue().getType());
        assertEquals(balance, argumentCaptorTransaction.getValue().getAmount());
        assertEquals(balance, argumentCaptorTransaction.getValue().getBalance());

    }

    @Test
    public void whenCreditCardExists_thenThrowException() {
        String number = "4173835666345642";

        Mockito.when(creditCardRepository.existsByNumber(number)).thenReturn(true);

        assertThrows(CreditCardExistsException.class, () -> {
            creditCardOperationsService.create(number, 100);
        });
    }

    @Test
    public void executeTransactionCredit() {
        CreditCard cc = new CreditCard("4173835666345642");
        Transaction t = new Transaction(Transaction.TransactionType.CREDIT, 100, 100, cc);
        Long ccId = 1L;

        Mockito.when(creditCardRepository.findById(ccId)).thenReturn(Optional.of(cc));
        Mockito.when(transactionRepository.findLastTransaction(cc)).thenReturn(Optional.of(t));

        creditCardOperationsService.executeTransaction(ccId, Transaction.TransactionType.CREDIT, 50);

        ArgumentCaptor<Transaction> argumentCaptorTransaction = ArgumentCaptor.forClass(Transaction.class);
        Mockito.verify(transactionRepository).save(argumentCaptorTransaction.capture());
        assertEquals(Transaction.TransactionType.CREDIT, argumentCaptorTransaction.getValue().getType());
        assertEquals(50, argumentCaptorTransaction.getValue().getAmount());
        assertEquals(150, argumentCaptorTransaction.getValue().getBalance());
        assertEquals(cc, argumentCaptorTransaction.getValue().getCreditCard());
    }

    @Test
    public void executeTransactionCharge() {
        CreditCard cc = new CreditCard("4173835666345642");
        Transaction t = new Transaction(Transaction.TransactionType.CREDIT, 100, 100, cc);
        Long ccId = 1L;

        Mockito.when(creditCardRepository.findById(ccId)).thenReturn(Optional.of(cc));
        Mockito.when(transactionRepository.findLastTransaction(cc)).thenReturn(Optional.of(t));

        creditCardOperationsService.executeTransaction(ccId, Transaction.TransactionType.CHARGE, 100);

        ArgumentCaptor<Transaction> argumentCaptorTransaction = ArgumentCaptor.forClass(Transaction.class);
        Mockito.verify(transactionRepository).save(argumentCaptorTransaction.capture());
        assertEquals(Transaction.TransactionType.CHARGE, argumentCaptorTransaction.getValue().getType());
        assertEquals(100, argumentCaptorTransaction.getValue().getAmount());
        assertEquals(0, argumentCaptorTransaction.getValue().getBalance());
        assertEquals(cc, argumentCaptorTransaction.getValue().getCreditCard());
    }

    @Test
    public void whenNotEnoughBalance_thenChargeNotPossible() {
        CreditCard cc = new CreditCard("4173835666345642");
        Transaction t = new Transaction(Transaction.TransactionType.CREDIT, 100, 100, cc);
        Long ccId = 1L;

        Mockito.when(creditCardRepository.findById(ccId)).thenReturn(Optional.of(cc));
        Mockito.when(transactionRepository.findLastTransaction(cc)).thenReturn(Optional.of(t));

        assertThrows(NotEnoughBalanceException.class, () -> {
            creditCardOperationsService.executeTransaction(ccId, Transaction.TransactionType.CHARGE, 101);
        });
    }

    @Test
    public void whenNoPreviousTransaction_thenTransactionNotPossible() {
        CreditCard cc = new CreditCard("4173835666345642");
        Long ccId = 1L;

        Mockito.when(creditCardRepository.findById(ccId)).thenReturn(Optional.of(cc));
        Mockito.when(transactionRepository.findLastTransaction(cc)).thenReturn(Optional.empty());

        assertThrows(TransactionNotFoundException.class, () -> {
            creditCardOperationsService.executeTransaction(ccId, Transaction.TransactionType.CHARGE, 1);
        });
    }

    @Test
    public void getAllTranscationsForCreditCard() {
        CreditCard cc = new CreditCard("4173835666345642");
        Long ccId = 1L;

        Mockito.when(creditCardRepository.findById(ccId)).thenReturn(Optional.of(cc));

        creditCardOperationsService.getAllTransactionsForCreditCard(1L);

        Mockito.verify(creditCardRepository).findById(ccId);
        Mockito.verify(transactionRepository).findByCreditCardOrderByCreateDateTimeDesc(cc);

    }

    @Test
    public void whenNoCreditCardFound_thenExceptionIsThrown() {
        Long ccId = 1L;

        Mockito.when(creditCardRepository.findById(ccId)).thenReturn(Optional.empty());
        assertThrows(CreditCardNotFoundException.class, () -> {
           creditCardOperationsService.getAllTransactionsForCreditCard(1L);
        });
    }
}
