package com.fredericboisguerin.insa.calculateurprix.ui;

import static com.fredericboisguerin.insa.calculateurprix.model.Country.FRANCE;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;

import com.fredericboisguerin.insa.calculateurprix.core.AmountCalculator;
import com.fredericboisguerin.insa.calculateurprix.model.Country;
import com.fredericboisguerin.insa.calculateurprix.ui.CalculateurPrixPresenter;
import com.fredericboisguerin.insa.calculateurprix.ui.CalculateurPrixView;

public class CalculateurPrixPresenterTest {

    private static final BigDecimal CALCULATED_AMOUNT_WITHOUT_TAX = ONE;
    private static final BigDecimal CALCULATED_AMOUNT_WITH_TAX = TEN;
    private static final String SOME_AMOUNT_PER_ARTICLE = "123";
    private static final String SOME_QUANTITY = "456";
    private static final Country SOME_COUNTRY = FRANCE;

    @Mock private CalculateurPrixView view;
    @Mock private AmountCalculator calculator;

    private CalculateurPrixPresenter calculateurPrixPresenter;

    @BeforeEach
    void setUp() {
        initMocks(this);
        calculateurPrixPresenter = new CalculateurPrixPresenter(view, calculator);
    }

    @Test
    void should_transform_article_price_to_big_decimal() {
        String someArticlePriceAsString = "123.456";
        BigDecimal expectedPrice = new BigDecimal(someArticlePriceAsString);

        calculateurPrixPresenter.onComputeButtonClicked(someArticlePriceAsString, SOME_QUANTITY, SOME_COUNTRY);

        verify(calculator).calculateTotal(eq(expectedPrice), anyInt());
        verify(calculator).calculateTotalWithTax(eq(expectedPrice), anyInt(), any());
    }

    @Test
    void should_transform_quantity_to_int() {
        String someQuantityAsString = "123";
        int expectedQuantity = Integer.valueOf(someQuantityAsString);

        calculateurPrixPresenter.onComputeButtonClicked(SOME_AMOUNT_PER_ARTICLE, someQuantityAsString, SOME_COUNTRY);

        verify(calculator).calculateTotal(any(), eq(expectedQuantity));
        verify(calculator).calculateTotalWithTax(any(), eq(expectedQuantity), any());
    }

    @ParameterizedTest
    @EnumSource(Country.class)
    void should_pass_country_to_calculator_to_compute_amount_with_TAV(Country country) {
        calculateurPrixPresenter.onComputeButtonClicked(SOME_AMOUNT_PER_ARTICLE, SOME_QUANTITY, country);

        verify(calculator).calculateTotalWithTax(any(), anyInt(), eq(country));
    }

    @Test
    void should_set_amount_without_tax_to_its_view_when_computation_asked() {
        when(calculator.calculateTotal(any(), anyInt())).thenReturn(CALCULATED_AMOUNT_WITHOUT_TAX);

        calculateurPrixPresenter.onComputeButtonClicked(SOME_AMOUNT_PER_ARTICLE, SOME_QUANTITY, SOME_COUNTRY);

        verify(view).setOrderAmountWithoutTax(CALCULATED_AMOUNT_WITHOUT_TAX);
    }

    @Test
    void should_set_amount_with_tax_to_its_view_when_computation_asked() {
        when(calculator.calculateTotalWithTax(any(), anyInt(), any())).thenReturn(CALCULATED_AMOUNT_WITH_TAX);

        calculateurPrixPresenter.onComputeButtonClicked(SOME_AMOUNT_PER_ARTICLE, SOME_QUANTITY, SOME_COUNTRY);

        verify(view).setOrderAmountWithTax(CALCULATED_AMOUNT_WITH_TAX);
    }
}