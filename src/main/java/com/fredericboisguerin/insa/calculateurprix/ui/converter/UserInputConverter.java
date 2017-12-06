package com.fredericboisguerin.insa.calculateurprix.ui.converter;

import java.math.BigDecimal;

public class UserInputConverter {
    public BigDecimal convertToBigDecimal(String amountPerArticle) throws InvalidAmount {
        try {
            return new BigDecimal(amountPerArticle);
        } catch (NumberFormatException e) {
            throw new InvalidAmount();
        }
    }

    public int convertToQuantity(String quantityAsText) throws InvalidQuantity {
        try {
            return Integer.valueOf(quantityAsText);
        } catch (NumberFormatException e) {
            throw new InvalidQuantity();
        }
    }
}
