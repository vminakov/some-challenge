package com.nufin.vladi.challenge.api;

import com.nufin.vladi.challenge.api.request.CreateNewTransactionRequest;
import com.nufin.vladi.challenge.entity.Transaction;
import com.nufin.vladi.challenge.service.CreditCardOperationsService;
import com.nufin.vladi.challenge.service.exception.CreditCardNotFoundException;
import com.nufin.vladi.challenge.service.exception.NotEnoughBalanceException;
import com.nufin.vladi.challenge.service.exception.TransactionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin
public class TransactionsController {

    @Autowired
    private CreditCardOperationsService creditCardsOperationService;


    @GetMapping("/cards/{id}/transactions")
    public List<Transaction> all(@PathVariable Long id) {
        try {
            return creditCardsOperationService.getAllTransactionsForCreditCard(id);
        } catch (CreditCardNotFoundException  e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }

    @PostMapping("/cards/{id}/transactions")
    public Transaction create(@PathVariable Long id, @RequestBody CreateNewTransactionRequest request) {
        try {
            return creditCardsOperationService.executeTransaction(
                    id,
                    request.getType(),
                    request.getAmount()
            );
        } catch (NotEnoughBalanceException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage(), e);
        } catch (CreditCardNotFoundException| TransactionNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }
}
