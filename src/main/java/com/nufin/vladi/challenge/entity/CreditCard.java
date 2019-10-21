package com.nufin.vladi.challenge.entity;

import com.nufin.vladi.challenge.dto.CreditCardWithBalance;
import com.nufin.vladi.challenge.validation.CreditCardLuhnCheckConstraint;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

// H2 does not properly support window/partition functions, so this ugly query is a workaround
@NamedNativeQuery(
        name="CreditCard.findAllWithBalance",
        query = "SELECT cc.id as id, cc.number as number, t.balance as balance FROM credit_cards cc " +
                "JOIN transactions t ON t.credit_card_id = cc.id " +
                "WHERE t.id IN ( " +
                " SELECT t_sub.id FROM transactions as t_sub WHERE t_sub.credit_card_id = cc.id ORDER BY t_sub.create_date_time DESC LIMIT 1 " +
                ") " +
                " ORDER BY cc.create_date_time DESC"
        ,
        resultSetMapping="creditCardsWithBalance"
)
@SqlResultSetMapping(name="creditCardsWithBalance",
        classes={
                @ConstructorResult(targetClass= CreditCardWithBalance.class, columns={
                        @ColumnResult(name="id", type=Long.class),
                        @ColumnResult(name="number", type=String.class),
                        @ColumnResult(name="balance", type=Double.class)
                })
        }
)
@Entity
@Table(name = "credit_cards")
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    @CreditCardLuhnCheckConstraint
    private String number;

    @CreationTimestamp
    private LocalDateTime createDateTime;

    protected CreditCard() {}

    public CreditCard(String number) {
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

}
