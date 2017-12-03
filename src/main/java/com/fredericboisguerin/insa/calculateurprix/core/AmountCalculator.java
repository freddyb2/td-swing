package com.fredericboisguerin.insa.calculateurprix.core;

import static java.math.BigDecimal.ONE;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.fredericboisguerin.insa.calculateurprix.model.Country;

public class AmountCalculator {

    public BigDecimal calculateTotal(BigDecimal articlePrice, int quantity) {
        return roundValueOf(totalAmountWithoutTaxFor(articlePrice, quantity));
    }

    public BigDecimal calculateTotalWithTax(BigDecimal articlePrice, int quantity, Country country) {
        return roundValueOf(valueWithTavOf(totalAmountWithoutTaxFor(articlePrice, quantity), country.getTaxRate()));
    }

    private static BigDecimal totalAmountWithoutTaxFor(BigDecimal articlePrice, Integer quantity) {
        return articlePrice.multiply(new BigDecimal(quantity));
    }

    private static BigDecimal roundValueOf(BigDecimal amount) {
        return amount.setScale(2, RoundingMode.HALF_EVEN);
    }

    private static BigDecimal valueWithTavOf(BigDecimal amountWithoutTax, double taxRate) {
        BigDecimal bigDecimalTaxRate = BigDecimal.valueOf(taxRate);
        return amountWithoutTax.multiply(ONE.add(bigDecimalTaxRate));
    }
}
