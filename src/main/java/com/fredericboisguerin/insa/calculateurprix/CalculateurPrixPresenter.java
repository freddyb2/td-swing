package com.fredericboisguerin.insa.calculateurprix;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculateurPrixPresenter {
    private final CalculateurPrixView calculateurPrixView;

    public CalculateurPrixPresenter(CalculateurPrixView calculateurPrixView) {
        this.calculateurPrixView = calculateurPrixView;
    }

    public void onComputeButtonClicked(String montantArticleAsText, String quantityAsText) {
        BigDecimal articlePrice = new BigDecimal(montantArticleAsText);
        Integer quantity = Integer.valueOf(quantityAsText);

        BigDecimal totalWithoutTax = calculateTotalAmountWithoutTax(articlePrice, quantity);

        BigDecimal roundedValue = totalWithoutTax.setScale(2, RoundingMode.HALF_EVEN);
        calculateurPrixView.setOrderAmountWithoutTax(roundedValue);
    }

    private static BigDecimal calculateTotalAmountWithoutTax(BigDecimal articlePrice, Integer quantity) {
        return articlePrice.multiply(new BigDecimal(quantity));
    }
}
