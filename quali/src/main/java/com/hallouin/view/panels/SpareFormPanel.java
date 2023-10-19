package com.hallouin.view.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.hallouin.model.bill.SparePart;
import com.hallouin.model.ecosystem.api.response_pojo.Spare;


@SuppressWarnings("serial")
public class SpareFormPanel extends JPanel{
	GridBagConstraints pos;
	JPanel panel;

	private final double[] cell_weightx = {0.5, 0.1, 0.4}; // Définissez les proportions de largeur pour chaque cellule;
	private final String[] cell_name = {"Référence","Qte","Etat"};

	public SpareFormPanel() {
		super();
	}

	public JPanel addSpareFormPanel() {
		List<Spare> sparesList = null;

		panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2, true), "Pièces détachées", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

        pos = new GridBagConstraints();
        pos.fill = GridBagConstraints.HORIZONTAL;
        pos.insets = new Insets(0, 0, 0, 10);

        createPanel(sparesList);

        return panel;
	}

	private void createPanel(List<Spare> sparesList) {
		panel.removeAll();

		int lineNumber = 0;
        lineNumber = showCreateSpareLine(lineNumber);
        lineNumber = showSpareLine(lineNumber, sparesList);

        int panelHeight = calculatePanelHeight(lineNumber);
        panel.setPreferredSize(new Dimension(300, panelHeight));
        panel.setMaximumSize(panel.getPreferredSize());
        panel.setMinimumSize(panel.getPreferredSize());

        panel.revalidate();
        panel.repaint();
	}
	private int calculatePanelHeight(int lineNumber) {
	    int lineHeight = 20; // Hauteur d'une ligne du panel
	    int padding = 20; // Espacement supplémentaire entre les lignes
	    int totalHeight = lineHeight + padding * lineNumber; // Calcul de la hauteur totale

	    return totalHeight;
	}

	private int showCreateSpareLine(int lineNumber) {

		for (int i=0; i<cell_name.length; i++) {
        	pos.gridx = i;
        	pos.gridy = lineNumber;
        	pos.weightx = cell_weightx[i];
        	//
            panel.add(new JLabel(cell_name[i]),pos);
        }
		lineNumber++;

        return lineNumber;
	}

	private int showSpareLine(int lineNumber, List<Spare> sparesList) {

		if (sparesList != null) {
	    	for (Spare sparePart : sparesList) {
	    		pos.gridx = 0;
	        	pos.gridy = lineNumber;
	        	pos.weightx = cell_weightx[0];
	        	JLabel label = new JLabel(sparePart.getIdentificationNumber());
	            panel.add(label, pos);

	            pos.gridx = 1;
	        	pos.gridy = lineNumber;
	        	pos.weightx = cell_weightx[1];
	        	JLabel label1 = new JLabel(""+sparePart.getQuantity());
	            panel.add(label1, pos);

	            pos.gridx = 2;
	        	pos.gridy = lineNumber;
	        	pos.weightx = cell_weightx[2];
	        	JLabel label2 = new JLabel(sparePart.getStatus());
	            panel.add(label2, pos);

	            lineNumber++;
	    	}
		}

    	return lineNumber;
	}

	public void updateSpares(List<Spare> sparesList) {
    	createPanel(sparesList);
    }
	public void eclUpdateSpares(List<SparePart> spares) {
		List<Spare> sparesList = new ArrayList<>();

		for (SparePart sparePart : spares) {
			Spare spare = new Spare("Neuve", sparePart.getDesignation(), sparePart.getQty());
			sparesList.add(spare);
		}
    	createPanel(sparesList);
    }
}
