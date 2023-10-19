package com.hallouin.view.ecosystem.panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.hallouin.controler.ecosystem.EcosystemController;


public class ClaimsListPanel {
	private JPanel panel;
	private JScrollPane scrollPane;
	private JTable table;
	private DefaultTableModel tableModel;

	private EcosystemController ecosystemController;

	public ClaimsListPanel() {
		super();
	}

	public JPanel addClaimsListPanel() {
		panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setPreferredSize(new Dimension(1400,310));
        panel.setMaximumSize(panel.getPreferredSize());
        panel.setMinimumSize(panel.getPreferredSize());
        //panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2, true), "Liste des demandes", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

        panel = createPanel();

        return panel;
	}

	private JPanel createPanel() {
		// Créer le tableau et le JScrollPane après avoir initialisé les colonnes
	    String[] columnsName = {""};
        setTableColumns(columnsName);

        scrollPane = new JScrollPane(table);

        scrollPane.setPreferredSize(new Dimension(1400, 300));


	    panel.add(scrollPane);

	    return panel;
	}

	public void setController(EcosystemController ecosystemController) {
    	this.ecosystemController = ecosystemController;
    }

	@SuppressWarnings("serial")
	public void setTableColumns(Object[] columnNames) {

		if (tableModel == null) {
			tableModel = new DefaultTableModel(null, columnNames){

	            @Override
	            public Class<?> getColumnClass(int columnIndex) {
	                return (columnIndex == table.getColumnCount()-1) ? Object.class : Object.class;
	            }

	            @Override
	            public boolean isCellEditable(int row, int column) {
	                // Rendre toutes les cellules non éditables (pour éviter la modification accidentelle de l'ID)
	                return false;
	            }
	        };

		table = new JTable(tableModel);

        // Ajouter un bouton de sélection à la dernière colonne du tableau
        table.getColumnModel().getColumn(table.getColumnCount()-1).setCellRenderer(new ButtonRenderer());

        // Ajouter un MouseListener pour détecter les clics de bouton
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
	                int column = table.getColumnModel().getColumnIndexAtX(e.getX());
	                int row = e.getY() / table.getRowHeight();

	                // Vérifier si le clic est dans la dernière colonne et que le bouton est cliqué
	                if (column == table.getColumnCount() - 1 && row < table.getRowCount()) {
	                    //ecosystemController.deleteClaim((String) table.getValueAt(row, 0));
	                	ecosystemController.selectClaim((String) table.getValueAt(row, 0));
	                }
	            }
	        });
		}
		else {
			tableModel.setColumnIdentifiers(columnNames);
			// Ajouter un bouton de sélection à la dernière colonne du tableau
	        table.getColumnModel().getColumn(table.getColumnCount()-1).setCellRenderer(new ButtonRenderer());

		}
	}
	public void setClaimsTableDatas (String[][] datas) {
		clearTableData();
		for (String[] data: datas) {
			tableModel.addRow(data);
		}

		// Créez une instance du rendu personnalisé avec le texte cible et la couleur de fond souhaitée
	    CustomTableCellRenderer cellRenderer = new CustomTableCellRenderer();

	    // Définissez le rendu personnalisé pour la colonne spécifique (par exemple, la colonne 0)
	    table.getColumnModel().getColumn(5).setCellRenderer(cellRenderer);
/*
		//int totalHeight = table.getPreferredSize().height + table.getTableHeader().getPreferredSize().height+3;
		//scrollPane.setPreferredSize(new Dimension(scrollPane.getPreferredSize().width, totalHeight));
		JPanel parentPanel = (JPanel) scrollPane.getParent();
	    parentPanel.invalidate();
	    parentPanel.revalidate();
	    parentPanel.repaint();
*/
	    panel.invalidate();
	    panel.revalidate();
	    panel.repaint();
	}
	private void clearTableData() {
	    tableModel.setRowCount(0);
	}

	@SuppressWarnings("serial")
	private class ButtonRenderer extends DefaultTableCellRenderer {
        private JButton button;

        public ButtonRenderer() {
            button = new JButton("Select");
            button.setPreferredSize(new Dimension(80, 25)); // Ajuster la taille du bouton
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        	if (column == table.getColumnCount()-1) {
                button.setActionCommand(String.valueOf(row));
                return button;
            } else {
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        }
    }

	@SuppressWarnings("serial")
	private class CustomTableCellRenderer extends DefaultTableCellRenderer {
		private Map<String, Color> conditionMap; // Map associant des textes à des couleurs de fond

	    public CustomTableCellRenderer() {
	    	conditionMap = new HashMap<>();
	        // Définissez vos conditions et couleurs ici
	        conditionMap.put("Approuvé, en cours rembourst", Color.GREEN);
	        conditionMap.put("Envoyé, en cours vérification", Color.ORANGE);
	        conditionMap.put("Remboursement effectué", Color.YELLOW);
	        conditionMap.put("Rejeté", Color.GRAY);
	        conditionMap.put("En cours de création", Color.CYAN);
	    }

	    @Override
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

	        // Vérifiez si la valeur dans la cellule correspond au texte cible
	        if (value != null && conditionMap.containsKey(value.toString())) {
	            cellComponent.setBackground(conditionMap.get(value.toString()));
	        } else {
	            // Réinitialisez la couleur de fond à la couleur par défaut
	            cellComponent.setBackground(table.getBackground());
	        }

	        return cellComponent;
	    }
	}
}
