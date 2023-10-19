package com.hallouin.view.claimCreation.panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.hallouin.model.bill.BillInfos;
import com.hallouin.model.ecosystem.api.pojo.BrandEcosystem;
import com.hallouin.model.ecosystem.pojo.Id_Name;

public class BillFormPanel {

	private JTextField billNumber;
    private JTextField repairDate;
    private JComboBox<Id_Name> repairerComboBox;

    public JPanel addBillFormPanel() {

    	billNumber = new JTextField();
    	JPanel panel_billNumber = BasicFormJTextField("N° de Facture", billNumber);
    	panel_billNumber.setPreferredSize(new Dimension(100,40));
    	panel_billNumber.setMaximumSize(panel_billNumber.getPreferredSize());

    	repairDate = new JTextField();
    	JPanel panel_repairDate = BasicFormJTextField("Date de réparation", repairDate);
    	panel_repairDate.setPreferredSize(new Dimension(120,40));
    	panel_repairDate.setMaximumSize(panel_repairDate.getPreferredSize());

    	repairerComboBox = new JComboBox<>();
        repairerComboBox.setRenderer(new NameListCellRenderer());
    	JPanel panel_repairSite = BasicFormJComboBox("Réparateur", repairerComboBox);
    	panel_repairSite.setPreferredSize(new Dimension(150,40));
    	panel_repairSite.setMaximumSize(panel_repairDate.getPreferredSize());

    	setEnable(false);

        JPanel line1 = new JPanel();
        line1.setLayout(new BoxLayout(line1, BoxLayout.X_AXIS));
        line1.add(panel_billNumber);
        line1.add(Box.createHorizontalGlue());

        JPanel line2 = new JPanel();
        line2.setLayout(new BoxLayout(line2, BoxLayout.X_AXIS));
        line2.add(panel_repairDate);
        line2.add(Box.createHorizontalGlue());

        JPanel line3 = new JPanel();
        line3.setLayout(new BoxLayout(line3, BoxLayout.X_AXIS));
        line3.add(panel_repairSite);
        line3.add(Box.createHorizontalGlue());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2, true), "Facture", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panel.setPreferredSize(new Dimension(180,230));
        panel.setMaximumSize(panel.getPreferredSize());
        panel.setMinimumSize(panel.getPreferredSize());

        panel.add(line1);
        panel.add(line2);
        panel.add(line3);

    	return panel;
    }

    private JPanel BasicFormJTextField(String label, JTextField field) {
    	JPanel panel = new JPanel();
    	Border margin = new EmptyBorder(0, 5, 0, 5);

        // Application de la bordure au panel

        panel.setLayout(new GridLayout(2, 1, 0, 0));
        panel.setBorder(margin);

        panel.add(new JLabel(label));
        panel.add(field);

        return panel;
    }

    private JPanel BasicFormJComboBox(String label, JComboBox<Id_Name> comboBox) {

    	JPanel panel = new JPanel();
    	Border margin = new EmptyBorder(0, 5, 0, 5);

        // Application de la bordure au panel

        panel.setLayout(new GridLayout(2, 1, 0, 0));
        panel.setBorder(margin);

        panel.add(new JLabel(label));
	    panel.add(comboBox);


        return panel;
    }

    @SuppressWarnings("serial")
	public class NameListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value instanceof Id_Name) {
                value = ((Id_Name) value).getName();
            }
            if (value instanceof BrandEcosystem) {
                value = ((BrandEcosystem) value).getBrandName();
            }
            return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        }
    }


    public Id_Name getPartnerId(){
    	return (Id_Name) repairerComboBox.getSelectedItem();
    }

    public void setRepairersList(List<Id_Name> idRepairersList) {

		repairerComboBox.removeAllItems();
    	for (Id_Name id_Name: idRepairersList) {
    		repairerComboBox.addItem(id_Name);
    	}
    	repairerComboBox.repaint();
    }
    public void updateBillInfos(BillInfos billInfos) {
    	billNumber.setText(billInfos.getBillNumber());
    	repairDate.setText(billInfos.getRepairDate());
    }
    public BillInfos getBillInfos() {
        JTextField[] fields = {billNumber, repairDate};

        for (JTextField field : fields) {
            field.setBorder(new LineBorder(Color.GREEN));
        }

        // Vérification des champs non vides
        boolean allFieldsFilled = Arrays.stream(fields)
                .map(JTextField::getText)
                .allMatch(s -> !s.isEmpty());

        if (allFieldsFilled) {
            // Tous les champs sont valides, créer l'objet BillInfos
            BillInfos billInfos = new BillInfos(
                billNumber.getText(),
                repairDate.getText()
            );

            return billInfos;
            // Utiliser l'objet BillInfos comme souhaité
        } else {
            for (JTextField field : fields) {
                if (field.getText().isEmpty()) {
                    field.setBorder(new LineBorder(Color.RED));
                }
            }
            // Afficher un message d'erreur ou effectuer une action appropriée
            System.out.println("Tous les champs doivent être remplis.");
            return null;
        }
    }
    public void setEnable(Boolean isEnable) {
    	repairDate.setEnabled(isEnable);
    	billNumber.setEnabled(isEnable);
    	repairerComboBox.setEnabled(isEnable);
    }
    public void clear() {
    	JTextField[] fields = {billNumber, repairDate};

        for (JTextField field : fields) {
            field.setBorder(new EmptyBorder(0, 5, 0, 5));
        }

    	repairDate.setText("");
    	billNumber.setText("");
    	repairerComboBox.removeAllItems();
    	repairerComboBox.repaint();
    	setEnable(false);
    }
    public void test() {
    	JPanel panel = addBillFormPanel();
    	panel.repaint();
    }
}
