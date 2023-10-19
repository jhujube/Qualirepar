package com.hallouin.view.ecologic;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;

import com.hallouin.controler.ecologic.EcologicController;
import com.hallouin.model.bill.Bill;
import com.hallouin.model.ecologic.EcologicModel;
import com.hallouin.view.ecologic.panels.EclButtonsFormPanel;
import com.hallouin.view.ecologic.panels.EclClaimsListPanel;
import com.hallouin.view.ecologic.panels.EclConsultClaimPanel;
import com.hallouin.view.ecologic.panels.EclSearchPanel;

public class EcologicView {
	private EcologicModel ecologicModel;
	private EclSearchPanel searchPanel;
	private EclClaimsListPanel claimsListPanel;
	private EclConsultClaimPanel consultClaimPanel;
	private EclButtonsFormPanel buttonsFormPanel;

	public EcologicView(EcologicModel ecologicModel){
		super();
		this.ecologicModel = ecologicModel;
		setEclTabbedPane();

	}

	public JPanel setEclTabbedPane() {
		JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	    panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2, true), "Demandes", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
	    panel.setPreferredSize(new Dimension(900,230));
	    panel.setMaximumSize(panel.getPreferredSize());
	    panel.setMinimumSize(panel.getPreferredSize());

	    searchPanel = new EclSearchPanel();
	    JPanel search = searchPanel.setMainView();
	    search.setLayout(new  BoxLayout(search, BoxLayout.X_AXIS));
	    search.setAlignmentY(Component.TOP_ALIGNMENT);
	    search.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

	    panel.add(search);

	    JPanel listClaimsPanel = new JPanel();
	    listClaimsPanel.setLayout(new BoxLayout(listClaimsPanel, BoxLayout.Y_AXIS));
	    listClaimsPanel.setAlignmentY(Component.TOP_ALIGNMENT);
	    listClaimsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

	    claimsListPanel = new EclClaimsListPanel();
	    listClaimsPanel.add(claimsListPanel.addClaimsListPanel());

	    panel.add(listClaimsPanel);

	    JPanel consultClaim = new JPanel();
	    consultClaim.setLayout(new BoxLayout(consultClaim, BoxLayout.X_AXIS));
	    consultClaim.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

	    consultClaimPanel = new EclConsultClaimPanel(ecologicModel);
	    consultClaim.add(consultClaimPanel.setMainView());

	    panel.add(consultClaim);

	    JPanel buttonsPanel = new JPanel();
	    buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
	    buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

	    buttonsFormPanel = new EclButtonsFormPanel();
	    buttonsPanel.add(buttonsFormPanel.addButtonsFormPanel());

	    panel.add(buttonsPanel);

	    return panel;
	}

	public void setTableColumns(Object[] columnNames) {
		claimsListPanel.setTableColumns(columnNames);

	}

	public void setClaimsTableDatas (String[][] datas) {
		claimsListPanel.setClaimsTableDatas(datas);
	}

	@SuppressWarnings("serial")
	static class ButtonRenderer extends DefaultTableCellRenderer {
        private JButton button;

        public ButtonRenderer() {
            button = new JButton("Select");
            button.setPreferredSize(new Dimension(50, 25)); // Ajuster la taille du bouton
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

	public void setController(EcologicController ecologicController) {
		claimsListPanel.setController(ecologicController);
		buttonsFormPanel.setController(ecologicController);
		searchPanel.setController(ecologicController);
	}
	public void updateConsultClaim(Bill bill) {
		consultClaimPanel.fillClaimPanel(bill);
	}
}
