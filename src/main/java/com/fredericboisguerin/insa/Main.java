package com.fredericboisguerin.insa;

import com.fredericboisguerin.insa.calculateurprix.core.AmountCalculator;
import com.fredericboisguerin.insa.calculateurprix.ui.CalculateurPrixPresenter;
import com.fredericboisguerin.insa.calculateurprix.ui.CalculateurPrixView;
import com.fredericboisguerin.insa.calculateurprix.ui.converter.UserInputConverter;

public class Main {
    public static void main(String[] args) {
        CalculateurPrixView view = new CalculateurPrixView();
        CalculateurPrixPresenter presenter = new CalculateurPrixPresenter(view, new AmountCalculator(), new UserInputConverter());
        view.setListener(presenter);
        view.display();
    }
}
