package com.nufin.vladi.challenge.repository;

import com.nufin.vladi.challenge.dto.CreditCardWithBalance;
import com.nufin.vladi.challenge.entity.CreditCard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CreditCardRepository extends CrudRepository<CreditCard, Long> {

    Boolean existsByNumber(String number);

    @Query(nativeQuery = true)
    List<CreditCardWithBalance> findAllWithBalance();
}
