package com.nufin.vladi.challenge.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nufin.vladi.challenge.entity.CreditCard;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name="transactions")
public class Transaction {
    public enum TransactionType { CHARGE, CREDIT }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "credit_card_id", nullable = false)
    @JsonIgnore
    private CreditCard creditCard;

    private TransactionType type;

    @NotNull
    private double amount;

    @NotNull
    private double balance;

    @CreationTimestamp
    private LocalDateTime createDateTime;

    protected Transaction() {}

    public Transaction(TransactionType type, double amount, double balance) {
        this.type = type;
        this.amount = amount;
        this.balance = balance;
    }

    public Transaction(TransactionType type, double amount, double balance, CreditCard creditCard) {
        this(type, amount, balance);
        this.creditCard = creditCard;
    }

    public Long getId() {
        return id;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public double getBalance() {
        return balance;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }
}
