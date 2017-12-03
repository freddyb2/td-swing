package com.fredericboisguerin.insa.calculateurprix.core;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AmountCalculatorTest {

    private AmountCalculator amountCalculator;

    @BeforeEach
    void setUp() {
        amountCalculator = new AmountCalculator();
    }

    @ParameterizedTest
    @CsvSource(value = { "1 1 1.00", "1 2 2.00", "2 1 2.00", "1.5 2 3.00" }, delimiter = Character.SPACE_SEPARATOR)
    void should_compute_expected_result_without_tax(String amountPerArticle, int quantity, String expected) {
        BigDecimal articlePrice = new BigDecimal(amountPerArticle);

        BigDecimal calculated = amountCalculator.calculateTotal(articlePrice, quantity);

        assertThat(calculated).isEqualTo(expected);
    }
}