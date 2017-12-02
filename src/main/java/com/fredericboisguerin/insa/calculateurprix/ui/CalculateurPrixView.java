package com.fredericboisguerin.insa.calculateurprix.ui;

import static java.awt.BorderLayout.NORTH;
import static java.awt.BorderLayout.SOUTH;
import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.*;
import java.math.BigDecimal;
import java.text.NumberFormat;

import javax.swing.*;

import com.fredericboisguerin.insa.calculateurprix.CalculateurPrixPresenter;

public class CalculateurPrixView extends JFrame {

    private static final int DEFAULT_TEXT_FIELD_WIDTH = 10;
    private static final int DEFAULT_MARGIN = 20;
    private static final int DEFAULT_PADDING = 10;
    private static final int NUMBER_OF_COLUMNS_IN_FORM = 2;
    private static final int NUMBER_OF_ROWS_IN_FORM = 4;

    private final CalculateurPrixPresenter presenter;
    private final JFormattedTextField montantHTTextField, montantTTCTextField;
    private final JTextField quantityTextField = new JTextField(DEFAULT_TEXT_FIELD_WIDTH);

    public CalculateurPrixView() throws HeadlessException {
        super("Calculateur de prix");
        this.presenter = new CalculateurPrixPresenter(this);

        JLabel prixArticleLabel = new JLabel("Prix d'un article (€) : ");
        JTextField prixArticleTextField = new JTextField(DEFAULT_TEXT_FIELD_WIDTH);
        prixArticleLabel.setLabelFor(prixArticleTextField);
        prixArticleTextField.setToolTipText("Entrez ici le montant d'un article");

        JLabel quantityLabel = new JLabel("Quantité : ");
        quantityLabel.setLabelFor(quantityTextField);
        prixArticleTextField.setToolTipText("Entrez ici le nombre d'articles");

        JLabel montantHTLabel = new JLabel("Montant HT : ");
        montantHTTextField = buildTextFieldForCurrency();
        montantHTTextField.setEditable(false);
        montantHTLabel.setLabelFor(montantHTTextField);

        JLabel montantTTCLabel = new JLabel("Montant TTC (France) : ");
        montantTTCTextField = buildTextFieldForCurrency();
        montantTTCTextField.setEditable(false);
        montantTTCLabel.setLabelFor(montantTTCTextField);

        JButton computeButton = new JButton("Calculer");
        computeButton.addActionListener(e -> onComputeButtonClicked(prixArticleTextField));

        SpringPanel formPanel = new SpringPanel();
        addLabelAndFieldInPanel(prixArticleLabel, prixArticleTextField, formPanel);
        addLabelAndFieldInPanel(quantityLabel, quantityTextField, formPanel);
        addLabelAndFieldInPanel(montantHTLabel, montantHTTextField, formPanel);
        addLabelAndFieldInPanel(montantTTCLabel, montantTTCTextField, formPanel);
        formPanel.makeCompactGrid(NUMBER_OF_ROWS_IN_FORM, NUMBER_OF_COLUMNS_IN_FORM, DEFAULT_MARGIN, DEFAULT_PADDING);

        add(formPanel, NORTH);
        add(computeButton, SOUTH);
        prixArticleTextField.requestFocus();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void display() {
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public void setOrderAmountWithoutTax(BigDecimal orderAmountWithoutTax) {
        montantHTTextField.setValue(orderAmountWithoutTax);
    }

    public void setOrderAmountWithTax(BigDecimal orderAmountWithTax) {
        montantTTCTextField.setValue(orderAmountWithTax);
    }

    private void onComputeButtonClicked(JTextField prixArticleTextField) {
        String prixArticleAsText = prixArticleTextField.getText();
        String quantityAsText = quantityTextField.getText();
        presenter.onComputeButtonClicked(prixArticleAsText, quantityAsText);
    }

    private static JFormattedTextField buildTextFieldForCurrency() {
        return new JFormattedTextField(NumberFormat.getCurrencyInstance());
    }

    private static void addLabelAndFieldInPanel(JComponent label, JComponent field, JPanel p) {
        p.add(label);
        p.add(field);
    }
}
