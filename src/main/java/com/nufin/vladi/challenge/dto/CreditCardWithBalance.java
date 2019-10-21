package com.nufin.vladi.challenge.dto;

public class CreditCardWithBalance {
    private Long id;
    private String number;
    private double balance;

    public CreditCardWithBalance(Long id, String number, double balance) {
        this.id = id;
        this.number = number;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public double getBalance() {
        return balance;
    }
}
