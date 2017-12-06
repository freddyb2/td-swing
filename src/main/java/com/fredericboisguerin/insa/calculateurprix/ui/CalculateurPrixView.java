package com.fredericboisguerin.insa.calculateurprix.ui;

import static java.awt.BorderLayout.NORTH;
import static java.awt.BorderLayout.SOUTH;
import static javax.swing.JOptionPane.ERROR_MESSAGE;

import java.awt.*;
import java.math.BigDecimal;
import java.text.NumberFormat;

import javax.swing.*;

import com.fredericboisguerin.insa.calculateurprix.model.Country;

public class CalculateurPrixView extends JFrame {

    private static final int DEFAULT_TEXT_FIELD_WIDTH = 10;
    private static final int DEFAULT_MARGIN = 10;
    private static final int DEFAULT_PADDING = 10;
    private static final int NUMBER_OF_COLUMNS_IN_FORM = 2;
    private static final int NUMBER_OF_ROWS_IN_FORM = 5;

    private final JTextField quantityTextField = buildInputTextField();
    private final JTextField prixArticleTextField = buildInputTextField();
    private final JFormattedTextField montantHTTextField = buildAmountTextField();
    private final JFormattedTextField montantTTCTextField = buildAmountTextField();

    private final JComboBox<Country> countryComboBox = new JComboBox<>(Country.values());

    private CalculateurPrixPresenter listener;

    public CalculateurPrixView() throws HeadlessException {
        super("Calculateur de prix");

        JLabel prixArticleLabel = new JLabel("Prix d'un article (€) : ");
        prixArticleLabel.setLabelFor(prixArticleTextField);

        JLabel quantityLabel = new JLabel("Quantité : ");
        quantityLabel.setLabelFor(quantityTextField);

        JLabel countryLabel = new JLabel("Pays :");
        countryLabel.setLabelFor(countryComboBox);

        JLabel montantHTLabel = new JLabel("Montant HT : ");
        montantHTLabel.setLabelFor(montantHTTextField);

        JLabel montantTTCLabel = new JLabel("Montant TTC : ");
        montantTTCLabel.setLabelFor(montantTTCTextField);

        JButton computeButton = new JButton("Calculer");
        computeButton.addActionListener(e -> onComputeButtonClicked());

        SpringPanel formPanel = new SpringPanel();
        addLabelAndFieldInPanel(prixArticleLabel, prixArticleTextField, formPanel);
        addLabelAndFieldInPanel(quantityLabel, quantityTextField, formPanel);
        addLabelAndFieldInPanel(countryLabel, countryComboBox, formPanel);
        addLabelAndFieldInPanel(montantHTLabel, montantHTTextField, formPanel);
        addLabelAndFieldInPanel(montantTTCLabel, montantTTCTextField, formPanel);
        formPanel.makeCompactGrid(NUMBER_OF_ROWS_IN_FORM, NUMBER_OF_COLUMNS_IN_FORM, DEFAULT_MARGIN, DEFAULT_PADDING);

        add(formPanel, NORTH);
        add(computeButton, SOUTH);

        getRootPane().setDefaultButton(computeButton);
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

    private void onComputeButtonClicked() {
        String prixArticleAsText = prixArticleTextField.getText();
        String quantityAsText = quantityTextField.getText();
        listener.onComputeButtonClicked(prixArticleAsText, quantityAsText, (Country) countryComboBox.getSelectedItem());
    }

    private static void addLabelAndFieldInPanel(JComponent label, JComponent field, JPanel p) {
        p.add(label);
        p.add(field);
    }

    private static JTextField buildInputTextField() {
        return new JTextField(DEFAULT_TEXT_FIELD_WIDTH);
    }

    private static JFormattedTextField buildAmountTextField() {
        JFormattedTextField jFormattedTextField = new JFormattedTextField(NumberFormat.getCurrencyInstance());
        jFormattedTextField.setEditable(false);
        return jFormattedTextField;
    }

    public void setListener(CalculateurPrixPresenter listener) {
        this.listener = listener;
    }

    public void displayError(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage, "Error", ERROR_MESSAGE);
    }
}
