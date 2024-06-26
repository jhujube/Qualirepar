package com.hallouin.view.ecologic.panels;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

public class EclErrorsPanel {
	private JPanel panel;
	private MyTableModel tableModel;
	private JTable table;

	
	public EclErrorsPanel(int panelHeight, int panelWidth) {
		// Initialisation du modèle de table
        tableModel = new MyTableModel();

        // Création de la JTable avec le modèle personnalisé
        table = new JTable(tableModel);

        // Utiliser un renderer personnalisé pour les cellules multi-lignes
        table.setDefaultRenderer(Object.class, new MultiLineCellRenderer());
        
        // Ajout de la JTable à un JScrollPane pour le rendre défilable
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(panelHeight, panelWidth));

        // Création du JPanel et ajout du JScrollPane
        panel = new JPanel();
        panel.add(scrollPane, BorderLayout.CENTER);
	}

	// Retourne le JPanel contenant le tableau
    public JPanel getPanel() {
        return panel;
    }

    // Définit la visibilité du JPanel
    public void setVisible(boolean visible) {
        panel.setVisible(visible);
    }

    // Met à jour les données du tableau avec un tableau de String[]
    public void updateTableData(String[][] data) {
        tableModel.setData(data);
    }

    // Modèle personnalisé pour la JTable
    private class MyTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;
		private final String[] columnNames = {"Domaine", "Erreur"};
        private String[][] data = new String[0][0];

        @Override
        public int getRowCount() {
            return data.length;
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data[rowIndex][columnIndex];
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return String.class;
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        // Méthode pour mettre à jour les données du tableau
        public void setData(String[][] data) {
            this.data = data;
            fireTableDataChanged();
        }
    }
    static class MultiLineCellRenderer extends JTextArea implements TableCellRenderer {
		private static final long serialVersionUID = 1L;

		public MultiLineCellRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value.toString());
            setSize(table.getColumnModel().getColumn(column).getWidth(), getPreferredSize().height);
            if (table.getRowHeight(row) != getPreferredSize().height) {
                table.setRowHeight(row, getPreferredSize().height);
            }
            return this;
        }
    }
}
