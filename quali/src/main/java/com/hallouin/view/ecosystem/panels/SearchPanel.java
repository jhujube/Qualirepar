package com.hallouin.view.ecosystem.panels;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.hallouin.controler.ecosystem.EcosystemController;

public class SearchPanel {
	private EcosystemController ecosystemController;
	private JTextField searchField;

	public SearchPanel() {
		super();
		setMainView();
	}
	public JPanel setMainView() {
		JPanel panel = new JPanel();

		searchField = new JTextField();

	    JPanel panel_Search = basicFormJTextField("Rechercher", searchField);
	    panel_Search.setPreferredSize(new Dimension(200,40));
	    panel_Search.setMaximumSize(panel_Search.getPreferredSize());
	    panel.add(panel_Search);

	    // Créez un groupe de boutons radio pour assurer la sélection exclusive
        ButtonGroup buttonGroup = new ButtonGroup();

        // Créez deux boutons radio pour les positions "Demandes" et "Remboursements"
        JRadioButton claimsRadioButton = new JRadioButton("Demandes");
        claimsRadioButton.setSelected(true);
        JRadioButton reimbursementRadioButton = new JRadioButton("Remboursements");

        // Ajoutez les boutons radio au groupe de boutons
        buttonGroup.add(claimsRadioButton);
        buttonGroup.add(reimbursementRadioButton);

        panel.add(claimsRadioButton);
        panel.add(reimbursementRadioButton);

        // Ajoutez un DocumentListener au JTextField
     		searchField.getDocument().addDocumentListener(new DocumentListener() {
                 @Override
                 public void insertUpdate(DocumentEvent e) {
                     ecosystemController.newSearch(searchField.getText());
                 }

                 @Override
                 public void removeUpdate(DocumentEvent e) {
                     ecosystemController.newSearch(searchField.getText());
                 }

                 @Override
                 public void changedUpdate(DocumentEvent e) {
                     // Cette méthode est appelée lorsqu'un attribut du texte (par exemple, style) change
                 }
             });

	    return panel;
	}

	public void setController(EcosystemController ecosystemController) {
		this.ecosystemController = ecosystemController;
	}
	private JPanel basicFormJTextField(String label, JTextField field) {
    	JPanel panel = new JPanel();
    	Border margin = new EmptyBorder(0, 5, 0, 5);

        // Application de la bordure au panel

        panel.setLayout(new GridLayout(2, 1, 0, 0));
        panel.setBorder(margin);

        panel.add(new JLabel(label));
        panel.add(field);

        return panel;

    }
}
