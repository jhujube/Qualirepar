package com.hallouin.view.ecosystem.panels;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.hallouin.controler.ecosystem.EcosystemController;


public class ButtonsFormPanel {
	EcosystemController ecosystemController;

    JButton delete;

	public JPanel addButtonsFormPanel() {

        delete = new JButton("Supprimer");
        delete.addActionListener(new ButtonsListener());
        delete.setVisible(false);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Ajout des boutons au JPanel centré
        buttonsPanel.add(delete);

        return buttonsPanel;
	}

	public class ButtonsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Gérer les événements des boutons ici
            if (e.getSource() == delete) {
                ecosystemController.deleteClaim();
            }
        }
    }

	public void setController(EcosystemController ecosystemController) {
    	this.ecosystemController = ecosystemController;
    }

    public JButton getDeleteButton() {
        return delete;
    }

    public void setVisibleDeleteButton(boolean state) {
    	delete.setVisible(state);
    }

    public void enableDeleteButton(boolean state) {
    	if (state)
    		setVisibleDeleteButton(true);
    	delete.setEnabled(state);
    }
}
