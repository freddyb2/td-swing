package com.fredericboisguerin.insa.calculateurprix.ui;

import java.math.BigDecimal;

import com.fredericboisguerin.insa.calculateurprix.core.AmountCalculator;
import com.fredericboisguerin.insa.calculateurprix.model.Country;
import com.fredericboisguerin.insa.calculateurprix.ui.converter.InvalidAmount;
import com.fredericboisguerin.insa.calculateurprix.ui.converter.InvalidQuantity;
import com.fredericboisguerin.insa.calculateurprix.ui.converter.UserInputConverter;

public class CalculateurPrixPresenter {
    private final CalculateurPrixView view;
    private final AmountCalculator amountCalculator;
    private final UserInputConverter converter;

    public CalculateurPrixPresenter(CalculateurPrixView view, AmountCalculator amountCalculator, UserInputConverter converter) {
        this.view = view;
        this.amountCalculator = amountCalculator;
        this.converter = converter;
    }

    public void onComputeButtonClicked(String amountPerArticle, String quantityAsText, Country country) {
        try {
            BigDecimal articlePrice = converter.convertToBigDecimal(amountPerArticle);
            Integer quantity = converter.convertToQuantity(quantityAsText);
            setAmountsInView(country, articlePrice, quantity);
        } catch (InvalidAmount invalidAmount) {
            view.displayError("Invalid decimal format for amount: " + amountPerArticle);
        } catch (InvalidQuantity invalidQuantity) {
            view.displayError("Invalid integer format for quantity: " + quantityAsText);
        }
    }

    private void setAmountsInView(Country country, BigDecimal articlePrice, Integer quantity) {
        setTotalAmountWithoutTax(articlePrice, quantity);
        setTotalAmountWithTax(articlePrice, quantity, country);
    }

    private void setTotalAmountWithoutTax(BigDecimal articlePrice, int quantity) {
        BigDecimal totalWithoutTax = amountCalculator.calculateTotal(articlePrice, quantity);
        view.setOrderAmountWithoutTax(totalWithoutTax);
    }

    private void setTotalAmountWithTax(BigDecimal articlePrice, int quantity, Country country) {
        BigDecimal totalWithTax = amountCalculator.calculateTotalWithTax(articlePrice, quantity, country);
        view.setOrderAmountWithTax(totalWithTax);
    }
}
