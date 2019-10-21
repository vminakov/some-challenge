package com.nufin.vladi.challenge.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CreditCardLuhnCheckerTest {

    @Test
    public void luhnCheckerValidNumber() {
        assertTrue(CreditCardLuhnChecker.check("378282246310005"));
        assertTrue(CreditCardLuhnChecker.check("5610591081018250"));
        assertTrue(CreditCardLuhnChecker.check("4111111111111111"));
        assertTrue(CreditCardLuhnChecker.check("5105105105105100"));
    }

    @Test
    public void luhnCheckerInvalidNumber() {
        assertFalse(CreditCardLuhnChecker.check("378282246310004"));
        assertFalse(CreditCardLuhnChecker.check("4610591081018250"));
        assertFalse(CreditCardLuhnChecker.check("4111111121111111"));
        assertFalse(CreditCardLuhnChecker.check("51051051051051000"));
    }

    @Test
    public void luhnCheckerInvalidCardNumberFormat() {
        assertFalse(CreditCardLuhnChecker.check(null));
        assertFalse(CreditCardLuhnChecker.check(""));
        assertFalse(CreditCardLuhnChecker.check("123"));
        assertFalse(CreditCardLuhnChecker.check("12345678901234567890"));
        assertFalse(CreditCardLuhnChecker.check("somerubbishstring"));
    }
}
