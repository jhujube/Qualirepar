package com.hallouin.view.ecosystem;

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

import com.hallouin.controler.ecosystem.EcosystemController;
import com.hallouin.model.ecosystem.EcosystemModel;
import com.hallouin.view.ecosystem.panels.ButtonsFormPanel;
import com.hallouin.view.ecosystem.panels.ClaimsListPanel;
import com.hallouin.view.ecosystem.panels.ConsultClaimPanel;
import com.hallouin.view.ecosystem.panels.SearchPanel;

@SuppressWarnings("serial")
public class EcosystemView extends JPanel {
	private EcosystemModel ecosystemModel;

	private ClaimsListPanel claimsListPanel;
	private ConsultClaimPanel consultClaimPanel;
	private ButtonsFormPanel buttonsFormPanel;
	private SearchPanel searchPanel;

	public EcosystemView(EcosystemModel ecosystemModel) {
		super();
		this.ecosystemModel = ecosystemModel;
		setEcsTabbedPane();
	}

	public JPanel setEcsTabbedPane() {
		JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	    panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2, true), "Demandes", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
	    panel.setPreferredSize(new Dimension(900,230));
	    panel.setMaximumSize(panel.getPreferredSize());
	    panel.setMinimumSize(panel.getPreferredSize());

	    searchPanel = new SearchPanel();
	    JPanel search = searchPanel.setMainView();
	    search.setLayout(new  BoxLayout(search, BoxLayout.X_AXIS));
	    search.setAlignmentY(Component.TOP_ALIGNMENT);
	    search.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

	    panel.add(search);

	    JPanel listClaimsPanel = new JPanel();
	    listClaimsPanel.setLayout(new BoxLayout(listClaimsPanel, BoxLayout.Y_AXIS));
	    listClaimsPanel.setAlignmentY(Component.TOP_ALIGNMENT);
	    listClaimsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

	    claimsListPanel = new ClaimsListPanel();
	    listClaimsPanel.add(claimsListPanel.addClaimsListPanel());

	    panel.add(listClaimsPanel);

	    JPanel consultClaim = new JPanel();
	    consultClaim.setLayout(new BoxLayout(consultClaim, BoxLayout.X_AXIS));
	    consultClaim.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

	    consultClaimPanel = new ConsultClaimPanel(ecosystemModel);
	    consultClaim.add(consultClaimPanel.setMainView());

	    panel.add(consultClaim);

	    JPanel buttonsPanel = new JPanel();
	    buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
	    buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

	    buttonsFormPanel = new ButtonsFormPanel();
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

	public void setController(EcosystemController ecosystemController) {
		claimsListPanel.setController(ecosystemController);
		buttonsFormPanel.setController(ecosystemController);
		searchPanel.setController(ecosystemController);
	}

    public void enableDeleteButton(boolean state) {
    	buttonsFormPanel.enableDeleteButton(state);
    }
}
