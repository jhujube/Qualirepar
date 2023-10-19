package com.hallouin.view.claimCreation.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.hallouin.model.bill.Bill;
import com.hallouin.model.bill.Displacement;
import com.hallouin.model.bill.Invoice;
import com.hallouin.model.bill.Labour;
import com.hallouin.model.bill.SparePart;


@SuppressWarnings("serial")
public class SpareFormPanel  extends JPanel{

	GridBagConstraints pos;
	JPanel panel;
	FocusListener focusListener;
	private final static double VAT = 20.00;
	private final double[] cell_weightx = {0.2, 0.4, 0.1, 0.15, 0.15}; // Définissez les proportions de largeur pour chaque cellule;
	private static Double moUnitPrice = 0.0;
	private static Double deplUnitPrice = 0.0;
	private static Double spareUnitPrice = 0.0;
	private static Double moQty = 0.0;
	private static Double deplQty = 0.0;
	private static Double spareQty = 0.0;
	private static Double totalExclVat = 0.0;
	private static Double totalVat = 0.0;
	private static Double totalSpareExclVat = 0.0;
	private static Double totalSparesExclVat = 0.0;
	private static Double totalMoExclVat = 0.0;
	private static Double totalDeplExclVat = 0.0;
	private static JTextField totalMPExclVatField;
	private static JTextField totalDPExclVatField;
	private static JTextField totalPExclVatField;
	private static JLabel labelTotalExclVat;
	private static JLabel labelTotalVat;
	private static JTextField refField;
	private static JTextField designationField;
	private static JTextField qtyField;
	private static JTextField unitPVatField;
	private static JButton addButton;
	private static JTextField qtyMField;
	private static JTextField unitMPVatField;
	private static JTextField qtyDField;
	private static JTextField unitDPVatField;
	private List<SparePart> sparePartsList;

	public SpareFormPanel() {
		super();
		sparePartsList = new ArrayList<>();
	}

	public JPanel addSpareFormPanel() {

		panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2, true), "Contenu de la facture", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

        pos = new GridBagConstraints();
        pos.fill = GridBagConstraints.HORIZONTAL;
        pos.insets = new Insets(0, 0, 0, 10);

        focusListener = new CustomFocusListener();

        createPanel();
        setEnable(false);

        return panel;
	}
	private void createPanel() {
		panel.removeAll();

		int lineNumber = 0;
        lineNumber = showCreateSpareLine(lineNumber);
        lineNumber = showSpareLine(lineNumber);
        lineNumber = showBlankLine(lineNumber);
        lineNumber = showMoLine(lineNumber);
        lineNumber = showDepLine(lineNumber);
        lineNumber = showTHTLine(lineNumber);
        lineNumber = showTTTCLine(lineNumber);

        int panelHeight = calculatePanelHeight(lineNumber);
        panel.setPreferredSize(new Dimension(655, panelHeight));
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

		String[] cell_name = {"Référence","Désignation","Qte","P. Unit. HT","P. Tot. HT"};
		for (int i=0; i<cell_name.length; i++) {
        	pos.gridx = i;
        	pos.gridy = lineNumber;
        	pos.weightx = cell_weightx[i];
        	//
            panel.add(new JLabel(cell_name[i]),pos);
        }
		lineNumber++;

    	pos.gridx = 0;
    	pos.gridy = lineNumber;
    	pos.weightx = cell_weightx[0];
    	refField = new JTextField("");
        panel.add(refField, pos);

        pos.gridx = 1;
    	pos.gridy = lineNumber;
    	pos.weightx = cell_weightx[1];
    	designationField = new JTextField("");
        panel.add(designationField, pos);

        pos.gridx = 2;
    	pos.gridy = lineNumber;
    	pos.weightx = cell_weightx[2];
    	qtyField = new JTextField("");
    	qtyField.setName("spareQty");
    	qtyField.addFocusListener(focusListener);
        panel.add(qtyField, pos);

        pos.gridx = 3;
    	pos.gridy = lineNumber;
    	pos.weightx = cell_weightx[3];
    	unitPVatField = new JTextField("");
    	unitPVatField.setName("spareUnitPrice");
    	unitPVatField.addFocusListener(focusListener);
        panel.add(unitPVatField, pos);

        pos.gridx = 4;
    	pos.gridy = lineNumber;
    	pos.weightx = cell_weightx[4];
    	totalPExclVatField = new JTextField("");
        panel.add(totalPExclVatField, pos);

		addButton = new JButton("Add");

        // Ajouter le bouton "Add" à la fin de la ligne
        addButton = new JButton("Add");
        pos.gridx = cell_name.length+1; // Définir la position x pour le bouton
        pos.gridy = lineNumber++;
        pos.weightx = 0.0; // Définir weightx à 0 pour que le bouton ne prenne pas de largeur supplémentaire
        panel.add(addButton, pos);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	boolean valide_spare = true;
            	String qty = qtyField.getText();
            	Double qty_dble=0.0;
            	qty.replaceAll(",",".");
            	try {
            		qty_dble = Double.valueOf(qty);
            	}catch (NumberFormatException err) {
            		valide_spare = false;
            	}

            	String unitPVat = unitPVatField.getText();
            	Double unitPVat_dble=0.0;
            	unitPVat.replaceAll(",",".");
            	try {
            		unitPVat_dble = Double.valueOf(unitPVat);
            	}catch (NumberFormatException err) {
            		valide_spare = false;
            	}

            	String totalPVat = totalPExclVatField.getText();
            	Double totalPVat_dble=0.0;
            	totalPVat.replaceAll(",",".");
            	try {
            		totalPVat_dble = Double.valueOf(totalPVat);
            	}catch (NumberFormatException err) {
            		valide_spare = false;
            	}

            	if (valide_spare) {
            		SparePart spare = new SparePart(refField.getText(),qty_dble,unitPVat_dble,totalPVat_dble);
            		if (!designationField.getText().isBlank())
            			spare.setDesignation(designationField.getText());
            		sparePartsList.add(spare);
            		refField.setText("");
                    qtyField.setText("");
                    designationField.setText("");
                    unitPVatField.setText("");
                    totalPExclVatField.setText("");

                    totalSparesExclVat += totalPVat_dble;
                    totalExclVat = totalDeplExclVat + totalMoExclVat + totalSparesExclVat;
                    totalVat = totalExclVat*(1+VAT/100);
                    totalVat = Math.round(totalVat*100.0)/100.0;


                    createPanel();

            	}
            }
        });

        return lineNumber;
	}

	private int showSpareLine(int lineNumber) {
		totalSparesExclVat = 0.0;
    	if (sparePartsList != null) {
	    	for (SparePart sparePart : sparePartsList) {
	    		pos.gridx = 0;
	        	pos.gridy = lineNumber;
	        	pos.weightx = cell_weightx[0];
	        	JLabel label = new JLabel(sparePart.getRef());
	            panel.add(label, pos);

	            pos.gridx = 1;
	        	pos.gridy = lineNumber;
	        	pos.weightx = cell_weightx[1];
	        	JLabel label1 = new JLabel(sparePart.getDesignation());
	            panel.add(label1, pos);

	            pos.gridx = 2;
	        	pos.gridy = lineNumber;
	        	pos.weightx = cell_weightx[2];
	        	JLabel label2 = new JLabel(sparePart.getQty().toString());
	            panel.add(label2, pos);

	            pos.gridx = 3;
	        	pos.gridy = lineNumber;
	        	pos.weightx = cell_weightx[3];
	        	JLabel label3 = new JLabel(sparePart.getUnitPExclVat().toString());
	            panel.add(label3, pos);

	            pos.gridx = 4;
	        	pos.gridy = lineNumber;
	        	pos.weightx = cell_weightx[4];
	        	JLabel label4 = new JLabel(sparePart.getTotalPExclVat().toString());
	            panel.add(label4, pos);

	            totalSparesExclVat += sparePart.getTotalPExclVat();
	            lineNumber++;
	    	}
    	}
    	pos.gridx = 3;
    	pos.gridy = lineNumber;
    	pos.weightx = cell_weightx[3];
    	JLabel label3 = new JLabel("Total pièce(s) HT :");
        panel.add(label3, pos);

        pos.gridx = 4;
    	pos.gridy = lineNumber++;
    	pos.weightx = cell_weightx[4];
    	JLabel label4 = new JLabel(totalSparesExclVat.toString());
        panel.add(label4, pos);

    	return lineNumber;
	}

	private int showMoLine(int lineNumber) {

        pos.gridx = 0;
    	pos.gridy = lineNumber;
    	pos.weightx = cell_weightx[0];
        panel.add(new JLabel(""), pos);

        pos.gridx = 1;
    	pos.gridy = lineNumber;
    	pos.weightx = cell_weightx[1];
    	JLabel moField = new JLabel("Main d'oeuvre :");
        panel.add(moField, pos);

        pos.gridx = 2;
    	pos.gridy = lineNumber;
    	pos.weightx = cell_weightx[2];
    	qtyMField = new JTextField("");
    	qtyMField.setName("qtyMo");
    	qtyMField.setText(""+moQty);
    	qtyMField.addFocusListener(focusListener);
        panel.add(qtyMField, pos);

        pos.gridx = 3;
    	pos.gridy = lineNumber;
    	pos.weightx = cell_weightx[3];
    	unitMPVatField = new JTextField("");
    	unitMPVatField.setName("moUnitPrice");
    	unitMPVatField.setText(""+moUnitPrice);
    	unitMPVatField.addFocusListener(focusListener);
        panel.add(unitMPVatField, pos);

        pos.gridx = 4;
    	pos.gridy = lineNumber++;
    	pos.weightx = cell_weightx[4];
    	totalMPExclVatField = new JTextField("");
    	totalMPExclVatField.setText(""+totalMoExclVat);
        panel.add(totalMPExclVatField, pos);
        return lineNumber;
	}
	private int showDepLine(int lineNumber) {

        pos.gridx = 0;
    	pos.gridy = lineNumber;
    	pos.weightx = cell_weightx[0];
        panel.add(new JLabel(""), pos);

        pos.gridx = 1;
    	pos.gridy = lineNumber;
    	pos.weightx = cell_weightx[1];
    	JLabel depField = new JLabel("Déplacement :");
        panel.add(depField, pos);

        pos.gridx = 2;
    	pos.gridy = lineNumber;
    	pos.weightx = cell_weightx[2];
    	qtyDField = new JTextField("");
    	qtyDField.setName("qtyDepl");
    	qtyDField.setText(""+deplQty);
    	qtyDField.addFocusListener(focusListener);
        panel.add(qtyDField, pos);

        pos.gridx = 3;
    	pos.gridy = lineNumber;
    	pos.weightx = cell_weightx[3];
    	unitDPVatField = new JTextField("");
    	unitDPVatField.setName("deplUnitPrice");
    	unitDPVatField.setText(""+deplUnitPrice);
    	unitDPVatField.addFocusListener(focusListener);
        panel.add(unitDPVatField, pos);

        pos.gridx = 4;
    	pos.gridy = lineNumber++;
    	pos.weightx = cell_weightx[4];
    	totalDPExclVatField = new JTextField("");
    	totalDPExclVatField.setText(""+totalDeplExclVat);
        panel.add(totalDPExclVatField, pos);
        return lineNumber;
	}
	private int showTHTLine(int lineNumber) {
		pos.gridx = 3;
    	pos.gridy = lineNumber;
    	pos.weightx = cell_weightx[3];
    	JLabel label3 = new JLabel("Total HT :");
        panel.add(label3, pos);

        pos.gridx = 4;
    	pos.gridy = lineNumber++;
    	pos.weightx = cell_weightx[4];
    	labelTotalExclVat = new JLabel(""+totalExclVat);
        panel.add(labelTotalExclVat, pos);
		return lineNumber;
	}
	private int showTTTCLine(int lineNumber) {
		pos.gridx = 3;
    	pos.gridy = lineNumber;
    	pos.weightx = cell_weightx[3];
    	JLabel label3 = new JLabel("Total TTC :");
        panel.add(label3, pos);

        pos.gridx = 4;
    	pos.gridy = lineNumber++;
    	pos.weightx = cell_weightx[4];
    	labelTotalVat = new JLabel(""+totalVat);
        panel.add(labelTotalVat, pos);
		return lineNumber;
	}
	private int showBlankLine(int lineNumber) {
		pos.gridx = 0;
    	pos.gridy = lineNumber++;
    	pos.weightx = cell_weightx[0];
        panel.add(new JLabel(" "), pos);

        return lineNumber;
	}

	// Classe FocusListener personnalisée
    private static class CustomFocusListener implements FocusListener {
        @Override
        public void focusGained(FocusEvent e) {
            // L'action à effectuer lorsque le JTextField obtient le focus
        	JTextField textField = (JTextField) e.getSource();
            if (textField.getName().equals("qtyMo") || textField.getName().equals("qtyDepl") || textField.getName().equals("moUnitPrice") || textField.getName().equals("deplUnitPrice")){
            	textField.setText("");
        	}

        }

        @Override
        public void focusLost(FocusEvent e) {
            // L'action à effectuer lorsque le JTextField perd le focus
            JTextField textField = (JTextField) e.getSource();

            if (textField.getName().equals("spareQty")){
            	String value = textField.getText();
            	value.replaceAll(",",".");
            	try {
            		spareQty = Double.valueOf(value);
            		totalSpareExclVat = spareQty*spareUnitPrice;
            		totalPExclVatField.setText(""+totalSpareExclVat);
            	}catch (NumberFormatException err) {
            		textField.setText("");
            	}
            }
            if (textField.getName().equals("qtyMo")){
            	String value = textField.getText();
            	value.replaceAll(",",".");
            	try {
            		moQty = Double.valueOf(value);
            		totalMoExclVat = moQty*moUnitPrice;
            		totalMPExclVatField.setText(""+totalMoExclVat);
            	}catch (NumberFormatException err) {
            		textField.setText("");
            	}
            }
            if (textField.getName().equals("qtyDepl")){
            	String value = textField.getText();
            	value.replaceAll(",",".");
            	try {
            		deplQty = Double.valueOf(value);
            		totalDeplExclVat = deplQty*deplUnitPrice;
            		totalDPExclVatField.setText(""+totalDeplExclVat);
            	}catch (NumberFormatException err) {
            		textField.setText("");
            	}
            }
            if (textField.getName().equals("spareUnitPrice")){
            	String value = textField.getText();
            	value.replaceAll(",",".");
            	try {
            		spareUnitPrice = Double.valueOf(value);
            		totalSpareExclVat = spareQty*spareUnitPrice;
            		totalPExclVatField.setText(""+totalSpareExclVat);
            	}catch (NumberFormatException err) {
            		textField.setText("");
            	}
            }
            if (textField.getName().equals("moUnitPrice")){
            	String value = textField.getText();
            	value.replaceAll(",",".");
            	try {
            		moUnitPrice = Double.valueOf(value);
            		totalMoExclVat = moQty*moUnitPrice;
            		totalMPExclVatField.setText(""+totalMoExclVat);
            	}catch (NumberFormatException err) {
            		textField.setText("");
            	}
            }
            if (textField.getName().equals("deplUnitPrice")){
            	String value = textField.getText();
            	value.replaceAll(",",".");
            	try {
            		deplUnitPrice = Double.valueOf(value);
            		totalDeplExclVat = deplQty*deplUnitPrice;
            		totalDPExclVatField.setText(""+totalDeplExclVat);
            	}catch (NumberFormatException err) {
            		textField.setText("");
            	}
            }
            totalExclVat = totalDeplExclVat + totalMoExclVat + totalSparesExclVat;
            labelTotalExclVat.setText(""+totalExclVat);

            totalVat = totalExclVat*(1+VAT/100);
            totalVat = Math.round(totalVat*100.0)/100.0;

            labelTotalVat.setText(""+totalVat);
        }
    }

    public void updateSpareParts(Bill bill) {
		this.sparePartsList = bill.getSparesList();
		moQty = bill.getLabour().getQty();
		moUnitPrice = bill.getLabour().getUnitPExclVat();
		totalMoExclVat = bill.getLabour().getTotalExclVat();
		try {
			deplQty = bill.getDisplacement().getQty();
			deplUnitPrice = bill.getDisplacement().getUnitPExclVat();
			totalDeplExclVat = bill.getDisplacement().getTotalExclVat();
		}catch (NullPointerException err) {
			System.out.println("Dépannage en atelier");
		}
		totalExclVat = bill.getInvoice().getTotalExclVat();
		totalVat = bill.getInvoice().getTotalVat();

        createPanel();

    }
	public void setEnable(Boolean isEnable) {
		refField.setEnabled(isEnable);
		designationField.setEnabled(isEnable);
		qtyField.setEnabled(isEnable);
		unitPVatField.setEnabled(isEnable);
		totalPExclVatField.setEnabled(isEnable);
		addButton.setEnabled(isEnable);

		qtyMField.setEnabled(isEnable);
		unitMPVatField.setEnabled(isEnable);
		totalMPExclVatField.setEnabled(isEnable);
		qtyDField.setEnabled(isEnable);
		unitDPVatField.setEnabled(isEnable);
		totalDPExclVatField.setEnabled(isEnable);

		panel.revalidate();
		panel.repaint();
    }

	public Labour getLabour() {
		JTextField[] fieldsText = {qtyMField, unitMPVatField, totalMPExclVatField };

    	for (JTextField field : fieldsText) {
    		field.setBackground(null);
            field.setBorder(new LineBorder(Color.GREEN));
        }

        // Vérification des champs non vides
        boolean allFieldsFilled = Arrays.stream(fieldsText)
                .map(JTextField::getText)
                .allMatch(s -> !s.isEmpty());


		if (moQty != 0.0 && moUnitPrice != 0.0 && totalMoExclVat != 0.0 && allFieldsFilled) {
			Labour labour = new Labour(moQty, moUnitPrice, totalMoExclVat);
			return labour;
		} else {
			for (JTextField field : fieldsText) {
                if (field.getText().isEmpty() || Double.parseDouble(field.getText())==0.0 ) {
                	field.setBackground(null);
                    field.setBorder(new LineBorder(Color.RED));
         		}
             }
			// Afficher un message d'erreur ou effectuer une action appropriée
		    System.out.println("Tous les champs doivent être remplis.");
		    return null;
		}
	}
	public Displacement getDisplacement() {
		Displacement displacement = new Displacement(deplQty, deplUnitPrice, totalDeplExclVat);
		return displacement;
	}
	public Invoice getInvoice() {
		System.out.println("totalExclVat="+totalExclVat+" totalVat="+totalVat);
		if (totalExclVat != 0.0 && totalVat != 0.0) {
			Invoice invoice = new Invoice(totalVat, totalExclVat, totalSparesExclVat);
			return invoice;
		}
		return null;
	}
	public List<SparePart> getSparePartsList(){
		return sparePartsList;
	}

	public void clear() {
		// Réinitialiser les champs texte
	    refField.setText("");
	    designationField.setText("");
	    qtyField.setText("");
	    unitPVatField.setText("");
	    totalPExclVatField.setText("");
	    qtyMField.setText("");
	    unitMPVatField.setText("");
	    totalMPExclVatField.setText("");
	    qtyDField.setText("");
	    unitDPVatField.setText("");
	    totalDPExclVatField.setText("");
	    labelTotalExclVat.setText("");
	    labelTotalVat.setText("");

	    // Réinitialiser la liste des pièces de rechange
	    sparePartsList.clear();

	    // Réinitialiser les variables
	    totalVat = 0.0;
	    totalExclVat = 0.0;
	    moQty = 0.0;
	    moUnitPrice = 0.0;
	    totalMoExclVat = 0.0;
	    deplQty = 0.0;
	    deplUnitPrice = 0.0;
	    totalDeplExclVat = 0.0;

	    // Réafficher le panneau vide
	    createPanel();

	    // Désactiver les champs
	    setEnable(false);
	}
}
