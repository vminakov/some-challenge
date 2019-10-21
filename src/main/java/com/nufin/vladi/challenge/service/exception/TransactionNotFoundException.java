package com.nufin.vladi.challenge.service.exception;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(String message) { super(message); }
}
