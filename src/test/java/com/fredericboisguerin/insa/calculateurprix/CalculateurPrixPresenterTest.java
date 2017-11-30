package com.fredericboisguerin.insa.calculateurprix;

import org.junit.Test;

import java.math.BigDecimal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CalculateurPrixPresenterTest {


    private final CalculateurPrixView view = mock(CalculateurPrixView.class);

    @Test
    public void should_set_order_amount_without_tax_on_calculate_asked() throws Exception {
        CalculateurPrixPresenter calculateurPrixPresenter = new CalculateurPrixPresenter(view);

        calculateurPrixPresenter.onComputeButtonClicked("123.456", "1");

        verify(view).setOrderAmountWithoutTax(new BigDecimal("123.46"));
    }

    @Test
    public void should_set_total_order_amount_without_tax_on_calculate_asked() throws Exception {
        CalculateurPrixPresenter calculateurPrixPresenter = new CalculateurPrixPresenter(view);

        calculateurPrixPresenter.onComputeButtonClicked("123.456", "2");

        verify(view).setOrderAmountWithoutTax(new BigDecimal("246.91"));
    }

    @Test
    public void should_set_order_amount_with_tax_on_calculate_asked() throws Exception {
        CalculateurPrixPresenter calculateurPrixPresenter = new CalculateurPrixPresenter(view);

        calculateurPrixPresenter.onComputeButtonClicked("123.456", "1");

        verify(view).setOrderAmountWithTax(new BigDecimal("148.15"));
    }

    @Test
    public void should_set_total_order_amount_with_tax_on_calculate_asked() throws Exception {
        CalculateurPrixPresenter calculateurPrixPresenter = new CalculateurPrixPresenter(view);

        calculateurPrixPresenter.onComputeButtonClicked("123.456", "2");

        verify(view).setOrderAmountWithTax(new BigDecimal("296.30"));
    }

}