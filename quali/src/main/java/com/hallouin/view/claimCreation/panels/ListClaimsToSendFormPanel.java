package com.hallouin.view.claimCreation.panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.hallouin.controler.claim.ClaimController;


public class ListClaimsToSendFormPanel {
	private ClaimController claimController;
	private String[] column = { "Nom de la demande", "Ecolabel", "Remboursement", "Fichiers joints" };
	private JTable table;
	private DefaultTableModel model;
	private JPanel panel;
	private final int nbLignesMax = 2;
	private JButton sendButton;
	private JLabel sendStateTitle;
	private JLabel sendStateLabel;

	public JPanel addListClaimsToSendFormPanel() {
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        //panel.setLayout(new FlowLayout());
        panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2, true), "Demandes à envoyer", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panel.setPreferredSize(new Dimension(800,200));
        panel.setMaximumSize(panel.getPreferredSize());
        panel.setMinimumSize(panel.getPreferredSize());

        // Créer le JTable
        String[][] data = {{"","","","",""}};
        model = new DefaultTableModel(data, column);
        table = new JTable(model);

        JScrollPane scrollPane = setTableDim(table);

        panel.add(scrollPane);

        sendButton = new JButton("Envoyer");
        sendButton.addActionListener(new ButtonsListener());
        sendButton.setEnabled(false);

        panel.add(sendButton);

        sendStateTitle = new JLabel("");
        panel.add(sendStateTitle);
        sendStateLabel = new JLabel("");
        panel.add(sendStateLabel);

        return panel;
	}
	private class ButtonsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Gérer les événements des boutons ici
            if (e.getSource() == sendButton) {
               claimController.sendInvoices();
            }
        }
    }


	public void setController(ClaimController claimController) {
    	this.claimController = claimController;
    }

	public void updateListClaims(String[][] claimsList) {

		while (model.getRowCount() > 0) {
		    model.removeRow(0);
		}
		// Ajouter les nouvelles lignes de données au modèle
		boolean flagBoolean = false;
		for (String[] row : claimsList) {
		    model.addRow(row);
		    if (claimsList[0][4].contains("Ko"))
		    		flagBoolean = true;
		}

	    if (flagBoolean)
	    	table.setDefaultRenderer(Object.class, new CustomTableCellRenderer());

		sendButton.setEnabled(true);
        model.fireTableDataChanged();
	}
	@SuppressWarnings("serial")
	private class CustomTableCellRenderer extends DefaultTableCellRenderer {
		@Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            cell.setBackground(Color.ORANGE);

            return cell;
        }
	}
	private int calculateTableHeight(JTable table) {
		int nbLignes = table.getRowCount();
		if (nbLignes >= nbLignesMax-1)
			nbLignes = nbLignesMax-1;
	    int totalHeight = 23 + nbLignes * table.getRowHeight(); // Calcul de la hauteur totale
	    System.out.println("hauteur"+totalHeight);
	    return totalHeight;
	}

	private JScrollPane setTableDim(JTable table) {
		JScrollPane scrollPane = new JScrollPane(table);
		
		int tableHeight = calculateTableHeight(table);
        System.out.println("hauteur table :"+tableHeight+"/"+this);
        TableColumnModel columnModel = table.getColumnModel();

	    TableColumn firstColumn = columnModel.getColumn(0);
	    firstColumn.setPreferredWidth(160);

	    TableColumn secondColumn = columnModel.getColumn(1);
	    secondColumn.setPreferredWidth(70);

	    TableColumn thirdColumn = columnModel.getColumn(2);
	    thirdColumn.setPreferredWidth(100);

	    TableColumn fourthColumn = columnModel.getColumn(3);
	    fourthColumn.setPreferredWidth(320);

        scrollPane.setPreferredSize(new Dimension(630, 23 + table.getRowHeight()*nbLignesMax));
        scrollPane.setMaximumSize(panel.getPreferredSize());
        scrollPane.setMinimumSize(panel.getPreferredSize());
        
        return scrollPane;
	}
	public void setSendStateTitle(String title) {
		System.out.println("sendstateTitle :"+title);
		sendStateTitle.setText(title);
	}
	public void setSendStateLabel(String title) {
		System.out.println("sendstatelabel :"+title);
		sendStateLabel.setText(title);
	}
}
