package com.fredericboisguerin.insa.calculateurprix;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculateurPrixPresenter {
    private final CalculateurPrixView calculateurPrixView;

    public CalculateurPrixPresenter(CalculateurPrixView calculateurPrixView) {
        this.calculateurPrixView = calculateurPrixView;
    }

    public void onComputeButtonClicked(String montantArticleAsText) {
        BigDecimal articlePrice = new BigDecimal(montantArticleAsText);
        BigDecimal roundedValue = articlePrice.setScale(2, RoundingMode.HALF_EVEN);
        calculateurPrixView.setOrderAmountWithoutTax(roundedValue);
    }
}
