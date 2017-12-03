package com.fredericboisguerin.insa;

import com.fredericboisguerin.insa.calculateurprix.core.AmountCalculator;
import com.fredericboisguerin.insa.calculateurprix.ui.CalculateurPrixPresenter;
import com.fredericboisguerin.insa.calculateurprix.ui.CalculateurPrixView;

/**
 * Hello world!
 */
public class Main {
    public static void main(String[] args) {
        CalculateurPrixView view = new CalculateurPrixView();
        CalculateurPrixPresenter presenter = new CalculateurPrixPresenter(view, new AmountCalculator());
        view.setListener(presenter);
        view.display();
    }

}
