package com.nufin.vladi.challenge.api;

import com.nufin.vladi.challenge.api.request.CreateNewCreditCardRequest;
import com.nufin.vladi.challenge.dto.CreditCardWithBalance;
import com.nufin.vladi.challenge.entity.CreditCard;
import com.nufin.vladi.challenge.service.CreditCardOperationsService;
import com.nufin.vladi.challenge.service.exception.CreditCardExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
public class CreditCardController {

    @Autowired
    private CreditCardOperationsService creditCardService;

    @GetMapping("/cards")
    public List<CreditCardWithBalance> index() {
        return creditCardService.getAllCreditCards();
    }

    @PostMapping("/cards")
    public CreditCard create(@Valid @RequestBody CreateNewCreditCardRequest request) {
        try {
            return creditCardService.create(request.getNumber(), request.getInitialBalance());
        } catch (CreditCardExistsException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage(), e);
        }

    }
}
