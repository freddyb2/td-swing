package com.fredericboisguerin.insa.calculateurprix;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.math.BigDecimal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CalculateurPrixPresenterTest {


    private final CalculateurPrixView view = mock(CalculateurPrixView.class);

    @Test
    public void should_set_order_amount_without_tax_on_calculate_asked() throws Exception {
        CalculateurPrixPresenter calculateurPrixPresenter = new CalculateurPrixPresenter(view);

        calculateurPrixPresenter.onComputeButtonClicked("123.456");

        verify(view).setOrderAmountWithoutTax(new BigDecimal("123.46"));
    }
}