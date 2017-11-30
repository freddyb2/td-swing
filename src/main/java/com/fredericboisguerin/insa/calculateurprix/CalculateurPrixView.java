package com.fredericboisguerin.insa.calculateurprix;

import static java.awt.BorderLayout.EAST;
import static java.awt.BorderLayout.SOUTH;
import static java.awt.BorderLayout.WEST;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.*;
import java.math.BigDecimal;
import java.text.NumberFormat;

import javax.swing.*;

public class CalculateurPrixView extends JFrame {

    private static final int DEFAULT_TEXT_FIELD_WIDTH = 10;

    private final CalculateurPrixPresenter presenter;
    private final JFormattedTextField montantHTTextField;
    private final JFormattedTextField montantTTCTextField;
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
        montantHTTextField.setValue(15);
        montantHTTextField.setEditable(false);
        montantHTLabel.setLabelFor(montantHTTextField);

        JLabel montantTTCLabel = new JLabel("Montant TTC (France) : ");
        montantTTCTextField = buildTextFieldForCurrency();
        montantTTCTextField.setValue(15);
        montantTTCTextField.setEditable(false);
        montantTTCLabel.setLabelFor(montantTTCTextField);

        JButton computeButton = new JButton("Calculer");
        computeButton.addActionListener(e -> onComputeButtonClicked(prixArticleTextField));

        JPanel contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.add(prixArticleTextField);

        JPanel labelPane = new JPanel(new GridLayout(0, 1));
        labelPane.add(prixArticleLabel);
        labelPane.add(quantityLabel);
        labelPane.add(montantHTLabel);
        labelPane.add(montantTTCLabel);

        JPanel fieldPane = new JPanel(new GridLayout(0, 1));
        fieldPane.add(prixArticleTextField);
        fieldPane.add(quantityTextField);
        fieldPane.add(montantHTTextField);
        fieldPane.add(montantTTCTextField);

        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(labelPane, WEST);
        add(fieldPane, EAST);
        add(computeButton, SOUTH);

        prixArticleTextField.requestFocus();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void afficherErreur(String message) {
        showMessageDialog(this, message, "Erreur", ERROR_MESSAGE);
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
}
