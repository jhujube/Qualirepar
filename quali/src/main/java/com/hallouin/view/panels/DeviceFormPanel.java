package com.hallouin.view.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.hallouin.model.bill.Device;
import com.hallouin.model.ecosystem.api.response_pojo.ProductDetails;

public class DeviceFormPanel {

	private JTextField commercialReference;
	private JTextField repairDescription;
	private JTextField productName;
    private JTextField productIdentificationNumber;
    private JTextField productSubCategory;
    private JTextField brandName;
    private JTextField manualBrandName;

    private JPanel line1b;

    public DeviceFormPanel() {
    	super();
    }

    public JPanel addDeviceFormPanel() {

        brandName = new JTextField();
        JPanel panel_Brand = BasicFormJTextField("Marque", brandName);
        panel_Brand.setPreferredSize(new Dimension(140,40));
        panel_Brand.setMaximumSize(panel_Brand.getPreferredSize());

        productSubCategory = new JTextField();
        JPanel panel_ProductSubCategory = BasicFormJTextField("Catégorie", productSubCategory);
        panel_ProductSubCategory.setPreferredSize(new Dimension(200,40));
        panel_ProductSubCategory.setMaximumSize(panel_ProductSubCategory.getPreferredSize());

        manualBrandName = new JTextField();
        JPanel panel_ManualBrand = BasicFormJTextField("Marque proposée", manualBrandName);
        panel_ManualBrand.setPreferredSize(new Dimension(140,40));
        panel_ManualBrand.setMaximumSize(panel_ManualBrand.getPreferredSize());

        commercialReference = new JTextField();
        JPanel panel_CommercialReference = BasicFormJTextField("Référence", commercialReference);
        panel_CommercialReference.setPreferredSize(new Dimension(200,40));
        panel_CommercialReference.setMaximumSize(panel_CommercialReference.getPreferredSize());

        productIdentificationNumber = new JTextField();
        JPanel panel_ProductIdentificationNumber = BasicFormJTextField("N° de série", productIdentificationNumber);
        panel_ProductIdentificationNumber.setPreferredSize(new Dimension(200,40));
        panel_ProductIdentificationNumber.setMaximumSize(panel_ProductIdentificationNumber.getPreferredSize());

        productName = new JTextField();
        JPanel panel_ProductName = BasicFormJTextField("Nom de produit", productName);
        panel_ProductName.setPreferredSize(new Dimension(150,40));
        panel_ProductName.setMaximumSize(panel_ProductName.getPreferredSize());

        repairDescription = new JTextField();
        JPanel panel_RepairDescription = BasicFormJTextField("Panne", repairDescription);
        panel_RepairDescription.setPreferredSize(new Dimension(100,40));
        panel_RepairDescription.setMaximumSize(panel_RepairDescription.getPreferredSize());

        JPanel line1 = new JPanel();
        line1.setLayout(new BoxLayout(line1, BoxLayout.X_AXIS));
        line1.add(panel_Brand);
        line1.add(panel_ProductSubCategory);
        line1.add(Box.createHorizontalGlue());

        line1b = new JPanel();
        line1b.setLayout(new BoxLayout(line1b, BoxLayout.X_AXIS));
        line1b.add(panel_ManualBrand);
        line1b.add(Box.createHorizontalGlue());
        line1b.setVisible(false);


        JPanel line2 = new JPanel();
        line2.setLayout(new BoxLayout(line2, BoxLayout.X_AXIS));
        line2.add(panel_CommercialReference);
        line2.add(panel_ProductIdentificationNumber);
        line2.add(Box.createHorizontalGlue());

        JPanel line3 = new JPanel();
        line3.setLayout(new BoxLayout(line3, BoxLayout.X_AXIS));
        line3.add(panel_ProductName);
        line3.add(panel_RepairDescription);
        line3.add(Box.createHorizontalGlue());

        JPanel panel = new JPanel();
    	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    	panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2, true), "Appareil", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panel.setPreferredSize(new Dimension(350,200));
        panel.setMaximumSize(panel.getPreferredSize());
        panel.setMinimumSize(panel.getPreferredSize());

        panel.add(line1);
        panel.add(line1b);
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

    private JPanel BasicFormJLabel(String label, JLabel field) {
    	JPanel panel = new JPanel();
    	Border margin = new EmptyBorder(0, 0, 0, 0);

        // Application de la bordure au panel

        panel.setLayout(new GridLayout(1, 2, 0, 0));
        panel.setBorder(margin);

        panel.add(new JLabel(label));
        panel.add(field);

        return panel;
    }
//  mise à jour du panel par ecosystem
    public void updateDevice(ProductDetails product) {
    	clearFields();
    	try {
	    	brandName.setText(product.getBrandName());
	    	productSubCategory.setText(product.getProductSubCategory());
	    	commercialReference.setText(product.getCommercialReference());
	    	productIdentificationNumber.setText(product.getProductIdentificationNumber());
	    	productName.setText(product.getProductName());
	    	repairDescription.setText(product.getRepairDescription());
	    	if (product.getManualBrandName() != null) {
	    		manualBrandName.setText(product.getManualBrandName());
	    		line1b.setVisible(true);
	    	}else {
	    		line1b.setVisible(false);
	    	}
    	} catch (NullPointerException e) {

    	}

    }
    //   mise à jour du panel par ecologic
    public void eclUpdateDevice(Device device) {
    	clearFields();
    	try {
	    	brandName.setText(device.getBrandName());
	    	productSubCategory.setText(device.getProductName());
	    	commercialReference.setText(device.getReference());
	    	productIdentificationNumber.setText(device.getSerial());
	    	productName.setText(device.getProductName());
	    	repairDescription.setText(device.getSymptom());
	    	if (device.getManualBrandName() != null) {
	    		manualBrandName.setText(device.getManualBrandName());
	    		line1b.setVisible(true);
	    	}else {
	    		line1b.setVisible(false);
	    	}
    	} catch (NullPointerException e) {

    	}

    }
    private void clearFields() {
    	brandName.setText("");
    	productSubCategory.setText("");
    	commercialReference.setText("");
    	productIdentificationNumber.setText("");
    	productName.setText("");
    	repairDescription.setText("");
    }
}
