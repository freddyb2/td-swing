package com.fredericboisguerin.insa.calculateurprix;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.fredericboisguerin.insa.calculateurprix.ui.CalculateurPrixView;

public class CalculateurPrixPresenter {
    private final CalculateurPrixView calculateurPrixView;

    public CalculateurPrixPresenter(CalculateurPrixView calculateurPrixView) {
        this.calculateurPrixView = calculateurPrixView;
    }

    public void onComputeButtonClicked(String montantArticleAsText, String quantityAsText) {
        BigDecimal articlePrice = new BigDecimal(montantArticleAsText);
        Integer quantity = Integer.valueOf(quantityAsText);

        BigDecimal totalWithoutTax = calculateTotalAmountWithoutTax(articlePrice, quantity);
        BigDecimal frenchTaxRate = new BigDecimal("1.20");
        BigDecimal totalWithTax = totalWithoutTax.multiply(frenchTaxRate);

        calculateurPrixView.setOrderAmountWithoutTax(roundValue(totalWithoutTax));
        calculateurPrixView.setOrderAmountWithTax(roundValue(totalWithTax));
    }

    private BigDecimal roundValue(BigDecimal totalWithoutTax) {
        return totalWithoutTax.setScale(2, RoundingMode.HALF_EVEN);
    }

    private static BigDecimal calculateTotalAmountWithoutTax(BigDecimal articlePrice, Integer quantity) {
        return articlePrice.multiply(new BigDecimal(quantity));
    }
}
