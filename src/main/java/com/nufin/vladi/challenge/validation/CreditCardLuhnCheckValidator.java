package com.nufin.vladi.challenge.validation;

import com.nufin.vladi.challenge.util.CreditCardLuhnChecker;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CreditCardLuhnCheckValidator implements ConstraintValidator<CreditCardLuhnCheckConstraint, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return CreditCardLuhnChecker.check(value);
    }

    @Override
    public void initialize(CreditCardLuhnCheckConstraint constraintAnnotation) {}
}
