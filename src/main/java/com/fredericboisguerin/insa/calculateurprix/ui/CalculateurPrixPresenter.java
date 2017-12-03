package com.fredericboisguerin.insa.calculateurprix.ui;

import java.math.BigDecimal;

import com.fredericboisguerin.insa.calculateurprix.model.Country;
import com.fredericboisguerin.insa.calculateurprix.core.AmountCalculator;

public class CalculateurPrixPresenter {
    private final CalculateurPrixView calculateurPrixView;
    private final AmountCalculator amountCalculator;

    public CalculateurPrixPresenter(CalculateurPrixView calculateurPrixView, AmountCalculator amountCalculator) {
        this.calculateurPrixView = calculateurPrixView;
        this.amountCalculator = amountCalculator;
    }

    public void onComputeButtonClicked(String amountPerArticle, String quantityAsText, Country country) {
        BigDecimal articlePrice = new BigDecimal(amountPerArticle);
        Integer quantity = Integer.valueOf(quantityAsText);

        setTotalAmountWithoutTax(articlePrice, quantity);
        setTotalAmountWithTax(articlePrice, quantity, country);
    }

    private void setTotalAmountWithoutTax(BigDecimal articlePrice, int quantity) {
        BigDecimal totalWithoutTax = amountCalculator.calculateTotal(articlePrice, quantity);
        calculateurPrixView.setOrderAmountWithoutTax(totalWithoutTax);
    }

    private void setTotalAmountWithTax(BigDecimal articlePrice, int quantity, Country country) {
        BigDecimal totalWithTax = amountCalculator.calculateTotalWithTax(articlePrice, quantity, country);
        calculateurPrixView.setOrderAmountWithTax(totalWithTax);
    }
}
