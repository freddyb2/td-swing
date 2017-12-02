package com.fredericboisguerin.insa.calculateurprix;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static com.fredericboisguerin.insa.calculateurprix.ui.Country.FRANCE;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.fredericboisguerin.insa.calculateurprix.ui.CalculateurPrixView;
import com.fredericboisguerin.insa.calculateurprix.ui.Country;

public class CalculateurPrixPresenterTest {

    private final CalculateurPrixView view = mock(CalculateurPrixView.class);
    private CalculateurPrixPresenter calculateurPrixPresenter;

    @Before
    public void setUp() throws Exception {
        calculateurPrixPresenter = new CalculateurPrixPresenter(view);
    }

    @Test
    public void should_set_order_amount_without_tax_on_calculate_asked() throws Exception {
        calculateurPrixPresenter.onComputeButtonClicked("123.456", "1", FRANCE);

        verify(view).setOrderAmountWithoutTax(new BigDecimal("123.46"));
    }

    @Test
    public void should_set_total_order_amount_without_tax_on_calculate_asked() throws Exception {
        calculateurPrixPresenter.onComputeButtonClicked("123.456", "2", FRANCE);

        verify(view).setOrderAmountWithoutTax(new BigDecimal("246.91"));
    }

    @Test
    public void should_set_order_amount_with_tax_on_calculate_asked() throws Exception {
        calculateurPrixPresenter.onComputeButtonClicked("123.456", "1", FRANCE);

        verify(view).setOrderAmountWithTax(new BigDecimal("148.15"));
    }

    @Test
    public void should_set_total_order_amount_with_tax_on_calculate_asked() throws Exception {
        calculateurPrixPresenter.onComputeButtonClicked("123.456", "2", FRANCE);

        verify(view).setOrderAmountWithTax(new BigDecimal("296.29"));
    }
}