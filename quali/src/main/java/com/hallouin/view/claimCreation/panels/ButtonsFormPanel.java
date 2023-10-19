package com.hallouin.view.claimCreation.panels;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.hallouin.controler.claim.ClaimController;


public class ButtonsFormPanel {
	ClaimController claimController;

	JButton loadBill;
    JButton add;
    JButton clear;

	public JPanel addButtonsFormPanel() {

		loadBill = new JButton("Importer facture");
		loadBill.setEnabled(false);
		loadBill.addActionListener(new ButtonsListener());

        add = new JButton("Ajouter aux envois");
        add.addActionListener(new ButtonsListener());

        clear = new JButton("Effacer");
        clear.addActionListener(new ButtonsListener());

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Ajout des boutons au JPanel centré
        buttonsPanel.add(loadBill);
        buttonsPanel.add(add);
        buttonsPanel.add(clear);

        return buttonsPanel;
	}

	public class ButtonsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Gérer les événements des boutons ici
            if (e.getSource() == loadBill) {
                claimController.importInvoice();
            }
            if (e.getSource() == add) {
                claimController.addInvoice();
            }
            if (e.getSource() == clear) {
                claimController.clearInvoice();
            }
        }
    }

	public void setController(ClaimController claimController) {
    	this.claimController = claimController;
    }

	public JButton getLoadBillButton() {
        return loadBill;
    }

    public JButton getAddButton() {
        return add;
    }

    public JButton getQuitButton() {
        return clear;
    }

    public void setVisible(JButton button, Boolean state) {
    	button.setVisible(state);
    }

    public void enableSendBillButton(boolean state) {
    	loadBill.setEnabled(state);
    }
}
