package com.nufin.vladi.challenge.api.request;

import com.nufin.vladi.challenge.validation.CreditCardLuhnCheckConstraint;

import javax.validation.constraints.Min;

public class CreateNewCreditCardRequest {

    @CreditCardLuhnCheckConstraint
    private String number;

    @Min(value = 0, message = "Balance cannot be negative")
    private double initialBalance;

    public CreateNewCreditCardRequest(String number, double initialBalance) {
        this.number = number;
        this.initialBalance = initialBalance;
    }

    public String getNumber() {
        return number;
    }

    public double getInitialBalance() {
        return initialBalance;
    }
}
