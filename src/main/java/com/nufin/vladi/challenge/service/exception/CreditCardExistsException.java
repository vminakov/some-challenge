package com.nufin.vladi.challenge.service.exception;

public class CreditCardExistsException extends RuntimeException {
    public CreditCardExistsException(String message) { super(message); }
}
