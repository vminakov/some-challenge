package com.nufin.vladi.challenge.api.request;

import com.nufin.vladi.challenge.entity.Transaction;

public class CreateNewTransactionRequest {
    private Transaction.TransactionType type;
    private double amount;

    public CreateNewTransactionRequest(Transaction.TransactionType type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public Transaction.TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}
