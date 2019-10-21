package com.nufin.vladi.challenge.util;

public class CreditCardLuhnChecker {

    public static boolean check(String creditCardNumber) {
        return checkCreditCardNumberLength(creditCardNumber) && checkLuhn(creditCardNumber);
    }

    private static boolean checkLuhn(String creditCardNumber) {
        int sum = 0;
        boolean toBeDoubled = false;
        for (int i = creditCardNumber.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(creditCardNumber.charAt(i));
            if (toBeDoubled) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
            toBeDoubled = !toBeDoubled;
        }

        return (sum % 10 == 0);
    }

    private static boolean checkCreditCardNumberLength(String creditCardNumber) {
        return creditCardNumber != null
                && creditCardNumber.length() >= 13
                && creditCardNumber.length() <= 19;
    }
}
