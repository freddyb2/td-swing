package com.fredericboisguerin.insa.calculateurprix.ui;

import static com.fredericboisguerin.insa.calculateurprix.model.Country.FRANCE;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.mockito.ArgumentMatchers.anyString;
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
import com.fredericboisguerin.insa.calculateurprix.ui.converter.InvalidAmount;
import com.fredericboisguerin.insa.calculateurprix.ui.converter.InvalidQuantity;
import com.fredericboisguerin.insa.calculateurprix.ui.converter.UserInputConverter;

public class CalculateurPrixPresenterTest {

    private static final BigDecimal CALCULATED_AMOUNT_WITHOUT_TAX = ONE;
    private static final BigDecimal CALCULATED_AMOUNT_WITH_TAX = TEN;
    private static final String SOME_AMOUNT_PER_ARTICLE = "1";
    private static final String SOME_QUANTITY = "2";
    private static final Country SOME_COUNTRY = FRANCE;
    private static final String INVALID_AMOUNT_PER_ARTICLE = "A";
    private static final String INVALID_QUANTITY = "B";

    @Mock private CalculateurPrixView view;
    @Mock private AmountCalculator calculator;
    @Mock private UserInputConverter converter;

    private CalculateurPrixPresenter calculateurPrixPresenter;

    @BeforeEach
    void setUp() {
        initMocks(this);
        calculateurPrixPresenter = new CalculateurPrixPresenter(view, calculator, converter);
    }

    @Test
    void should_convert_article_price_and_pass_it_to_calculator() throws Exception {
        BigDecimal expectedPrice = new BigDecimal("123.456");
        when(converter.convertToBigDecimal(SOME_AMOUNT_PER_ARTICLE)).thenReturn(expectedPrice);

        calculateurPrixPresenter.onComputeButtonClicked(SOME_AMOUNT_PER_ARTICLE, SOME_QUANTITY, SOME_COUNTRY);

        verify(calculator).calculateTotal(eq(expectedPrice), anyInt());
        verify(calculator).calculateTotalWithTax(eq(expectedPrice), anyInt(), any());
    }

    @Test
    void should_convert_quantity_and_pass_it_to_calculator() throws Exception {
        int expectedQuantity = Integer.valueOf("123");
        when(converter.convertToQuantity(SOME_QUANTITY)).thenReturn(expectedQuantity);

        calculateurPrixPresenter.onComputeButtonClicked(SOME_AMOUNT_PER_ARTICLE, SOME_QUANTITY, SOME_COUNTRY);

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

    @Test
    void should_display_an_error_if_amount_is_not_a_decimal_number() throws Exception {
        when(converter.convertToBigDecimal(anyString())).thenThrow(InvalidAmount.class);

        calculateurPrixPresenter.onComputeButtonClicked(INVALID_AMOUNT_PER_ARTICLE, SOME_QUANTITY, SOME_COUNTRY);

        verify(view).displayError("Invalid decimal format for amount: " + INVALID_AMOUNT_PER_ARTICLE);
    }

    @Test
    void should_display_an_error_if_quantity_is_not_an_integer() throws Exception {
        when(converter.convertToQuantity(anyString())).thenThrow(InvalidQuantity.class);

        calculateurPrixPresenter.onComputeButtonClicked(SOME_AMOUNT_PER_ARTICLE, INVALID_QUANTITY, SOME_COUNTRY);

        verify(view).displayError("Invalid integer format for quantity: " + INVALID_QUANTITY);
    }
}