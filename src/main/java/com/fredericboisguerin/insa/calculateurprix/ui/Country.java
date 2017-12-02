package com.fredericboisguerin.insa.calculateurprix.ui;

import static java.math.BigDecimal.ONE;

import java.math.BigDecimal;

public enum Country {

    BELGIQUE("Belgique", 0.21),
    DANEMARK("Danemark", 0.25),
    ALLEMAGNE("Allemagne", 0.19),
    GRECE("Grèce", 0.23),
    ESPAGNE("Espagne", 0.21),
    FRANCE("France", 0.20),
    IRLANDE("Irlande", 0.23),
    ITALIE("Italie", 0.22),
    CHYPRE("Chypre", 0.19),
    LUXEMBOURG("Luxembourg", 0.15),
    PAYS_BAS("Pays Bas", 0.21),
    AUTRICHE("Autriche", 0.20),
    PORTUGAL("Portugal", 0.23),
    SUEDE("Suède", 0.25);

    private final String asText;
    private final BigDecimal taxRate;

    Country(String asText, double taxRate) {
        this.asText = asText;
        this.taxRate = BigDecimal.valueOf(taxRate);
    }

    public BigDecimal addTAV(BigDecimal amountWithoutTax) {
        return amountWithoutTax.multiply(ONE.add(taxRate));
    }

    @Override
    public String toString() {
        return asText;
    }

}
