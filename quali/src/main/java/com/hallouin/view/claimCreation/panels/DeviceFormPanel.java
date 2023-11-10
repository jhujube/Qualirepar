package com.hallouin.view.claimCreation.panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.hallouin.controler.claim.ClaimController;
import com.hallouin.model.bill.Device;
import com.hallouin.model.ecosystem.api.pojo.BrandEcosystem;
import com.hallouin.model.ecosystem.api.pojo.Product;
import com.hallouin.model.ecosystem.pojo.Id_Name;

public class DeviceFormPanel {
	private static final String UNKNOWN_BRAND = "MARQUE INCONNUE";

	private ClaimController claimController;
	private JPanel panel_repair;
	private JPanel panel_Brand;
	private JPanel panel_symptom;
	private JPanel panel_ecoOrganism;
	private JPanel panel_amount;
	private JPanel panel_manualBrand;
	private JLabel organismLabel;
	private JLabel amountLabel;
	private JComboBox<Object> jcbProduct;
	private JComboBox<Object> jcbIrisSymptom;
	private JComboBox<Object> jcbIrisSection;
	private JComboBox<Object> jcbBrandName;
    private JTextField serial;
    private JTextField reference;
    private JTextField manualBrandField;
    private Double totalAmount = 0.0;

    public DeviceFormPanel() {
    	super();
    }

    public void setController(ClaimController claimController) {
    	this.claimController = claimController;
    }

    public JPanel addDeviceFormPanel() {

    	jcbBrandName = new JComboBox<>();
    	jcbBrandName.addActionListener(new ComboBoxListener());
    	jcbBrandName.setRenderer(new NameListCellRenderer());
    	jcbBrandName.setName("brand");
        panel_Brand = BasicFormJComboBox("Marque", jcbBrandName);
        panel_Brand.setPreferredSize(new Dimension(150,40));
        panel_Brand.setMaximumSize(panel_Brand.getPreferredSize());

        reference = new JTextField();
        JPanel panel_Reference = BasicFormJTextField("Référence", reference);
        panel_Reference.setPreferredSize(new Dimension(100,40));
        panel_Reference.setMaximumSize(panel_Reference.getPreferredSize());
        CreatePopupMenu(reference);

        serial = new JTextField();
        JPanel panel_Serial = BasicFormJTextField("N° de série", serial);
        panel_Serial.setPreferredSize(new Dimension(100,40));
        panel_Serial.setMaximumSize(panel_Serial.getPreferredSize());
        CreatePopupMenu(serial);

        manualBrandField = new JTextField();
        panel_manualBrand = BasicFormJTextField("Entrez la marque manuellement :", manualBrandField);
        panel_manualBrand.setPreferredSize(new Dimension(220,40));
        panel_manualBrand.setMaximumSize(panel_manualBrand.getPreferredSize());
        panel_manualBrand.setVisible(false);

    	jcbIrisSymptom = new JComboBox<>();
      	jcbIrisSymptom.setRenderer(new NameListCellRenderer());
      	jcbIrisSymptom.setName("jcbIrisSymptom");
    	panel_symptom = BasicFormJComboBox("Symptôme", jcbIrisSymptom);
    	panel_symptom.setPreferredSize(new Dimension(200,40));
    	panel_symptom.setMaximumSize(panel_symptom.getPreferredSize());

    	jcbIrisSection = new JComboBox<>();
    	jcbIrisSection.setRenderer(new NameListCellRenderer());
    	jcbIrisSection.setName("jcbIrisSection");
    	panel_repair = BasicFormJComboBox("Panne", jcbIrisSection);
    	panel_repair.setPreferredSize(new Dimension(200,40));
    	panel_repair.setMaximumSize(panel_repair.getPreferredSize());
    	//panel_repair.setVisible(false);

        organismLabel = new JLabel();
        panel_ecoOrganism = BasicFormJLabel("Eco-organisme :", organismLabel);
        panel_ecoOrganism.setPreferredSize(new Dimension(200,20));
        panel_ecoOrganism.setMaximumSize(panel_ecoOrganism.getPreferredSize());

        amountLabel = new JLabel();
        panel_amount = BasicFormJLabel("Remboursement : ", amountLabel);
        panel_amount.setPreferredSize(new Dimension(220,20));
        panel_amount.setMaximumSize(panel_amount.getPreferredSize());

        setEnable(false);

    	jcbProduct = new JComboBox<>();
        jcbProduct.addActionListener(new ComboBoxListener());
        jcbProduct.setRenderer(new NameListCellRenderer());
        jcbProduct.setName("product");
        jcbProduct.setBorder(new LineBorder(Color.RED));
    	JPanel panel_product = BasicFormJComboBox("Type d'appareil", jcbProduct);
    	panel_product.setPreferredSize(new Dimension(150,40));
    	panel_product.setMaximumSize(panel_product.getPreferredSize());

        JPanel line1 = new JPanel();
        line1.setLayout(new BoxLayout(line1, BoxLayout.X_AXIS));
        line1.add(panel_product);
        line1.add(panel_Brand);

        JPanel line2 = new JPanel();
        line2.setLayout(new BoxLayout(line2, BoxLayout.X_AXIS));
        line2.add(panel_Reference);
        line2.add(panel_Serial);

        JPanel line3 = new JPanel();
        line3.setLayout(new BoxLayout(line3, BoxLayout.Y_AXIS));
        line3.add(panel_symptom);
        line3.add(panel_repair);

        JPanel line4 = new JPanel();
        line4.setLayout(new BoxLayout(line4, BoxLayout.Y_AXIS));
        line4.add(panel_ecoOrganism);
        line4.add(panel_amount);

        JPanel line5 = new JPanel();
        line5.setLayout(new BoxLayout(line5, BoxLayout.Y_AXIS));
        line5.add(panel_manualBrand);

        JPanel panel = new JPanel();
    	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    	panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2, true), "Appareil", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panel.setPreferredSize(new Dimension(350,230));
        panel.setMaximumSize(panel.getPreferredSize());
        panel.setMinimumSize(panel.getPreferredSize());

        panel.add(line1);
        panel.add(line2);
        panel.add(line3);
        panel.add(line4);
        panel.add(line5);

    	return panel;
    }
    private void CreatePopupMenu(JTextField textField) {
   	 // Créez le menu contextuel
       JPopupMenu popupMenu = new JPopupMenu();
       JMenuItem pasteItem = new JMenuItem("Coller");

       // Ajoutez des écouteurs d'événements aux éléments de menu
       pasteItem.addActionListener(e -> textField.paste());

       popupMenu.add(pasteItem);

       // Ajoutez un écouteur de souris au champ JTextField
       textField.addMouseListener(new MouseAdapter() {
           @Override
           public void mouseClicked(MouseEvent e) {
               // Vérifiez si le clic est un clic droit
               if (SwingUtilities.isRightMouseButton(e)) {
                   // Affichez le menu contextuel au clic droit
                   popupMenu.show(textField, e.getX(), e.getY());
               }
           }
       });

   }
    private class ComboBoxListener implements ActionListener {

		@SuppressWarnings("unchecked")
		@Override
		public void actionPerformed(ActionEvent e) {
			JComboBox<Object> comboBox = (JComboBox<Object>) e.getSource();
	        Object selectedItem = comboBox.getSelectedItem();
	        if (selectedItem != null) {
		        if (comboBox.getName() != null) {

			        if (comboBox.getName().equals("product")) {
			        	Product product = (((Product) selectedItem));
			        	if (product.getProductID() != null) {
			        		claimController.setProduct(product);
			        	}
			        }
			        if (comboBox.getName().equals("brand")) {
			        	String brandId = (((BrandEcosystem) selectedItem).getBrandId());
			        	if (brandId != null) {
			        		claimController.setBrandEcosystem((BrandEcosystem) selectedItem);
			        	}
			        	String brandName = (((BrandEcosystem) selectedItem).getBrandName());
			        	panel_manualBrand.setVisible(false);
			        	if (brandName.contentEquals(UNKNOWN_BRAND))
			        		panel_manualBrand.setVisible(true);
			        }
		        }
	        }
		}
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

    private JPanel BasicFormJComboBox(String label, JComboBox<Object> comboBox) {

    	JPanel panel = new JPanel();
    	Border margin = new EmptyBorder(0, 5, 0, 5);

        // Application de la bordure au panel

        panel.setLayout(new GridLayout(2, 1, 0, 0));
        panel.setBorder(margin);

        panel.add(new JLabel(label));
	    panel.add(comboBox);


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

    @SuppressWarnings("serial")
	public class NameListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value instanceof Id_Name) {
                value = ((Id_Name) value).getName();
            }
            if (value instanceof Product) {
                value = ((Product) value).getProductName();
            }
            if (value instanceof BrandEcosystem) {
                value = ((BrandEcosystem) value).getBrandName();
            }
            return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        }
    }

    public void setProductsList(List<Product> productsList) {

    	jcbProduct.removeAllItems();
    	jcbProduct.addItem(new Product("Choisir un produit"));
    	for (Product product: productsList) {
    		jcbProduct.addItem(product);
    	}
    	jcbProduct.repaint();
    }

    public void setBrandsList(List<BrandEcosystem> ecsBrandsList) {
    	jcbBrandName.removeAllItems();
    	jcbBrandName.addItem(new BrandEcosystem(null,"Choisir une marque",null));
    	for (BrandEcosystem brandEcosystem: ecsBrandsList) {
    		jcbBrandName.addItem(brandEcosystem);
    	}
    	jcbBrandName.repaint();

    	jcbProduct.setBorder(null);
    	jcbProduct.repaint();
    }

    public void setIrisSection(List<Id_Name> irisSectionsList) {
    	jcbIrisSection.removeAllItems();

    	for (Id_Name id_Name: irisSectionsList) {
    		jcbIrisSection.addItem(id_Name);
    	}
    	jcbIrisSection.repaint();
    }

    public void setIrisSymptom(List<Id_Name> irisSymptomsList) {
    	jcbIrisSymptom.removeAllItems();

    	for (Id_Name id_Name: irisSymptomsList) {
    		jcbIrisSymptom.addItem(id_Name);
    	}
    	jcbIrisSymptom.repaint();
    }

    public void setAmount(String amount) {
    	totalAmount = Double.parseDouble(amount);
    	amountLabel.setText(amount+"€");
    }

    public void setEcoorganism(String ecoorganism) {
    	organismLabel.setText(ecoorganism);
    	if (ecoorganism.contentEquals("Ecosystem")) {
    		panel_symptom.setVisible(false);
    		jcbIrisSymptom.setEnabled(false);
    	}else {
    		panel_symptom.setVisible(true);
    		jcbIrisSymptom.setEnabled(true);
    	}
    }

    public void updateDeviceInfos(Device device) {
    	jcbBrandName.setSelectedItem(device.getBrand());
    	reference.setText(device.getReference());
    	serial.setText(device.getSerial());

    }

    public void setEnable(Boolean isEnable) {
    	jcbBrandName.setEnabled(isEnable);
    	reference.setEnabled(isEnable);
    	serial.setEnabled(isEnable);
    	jcbIrisSymptom.setEnabled(isEnable);
    	jcbIrisSection.setEnabled(isEnable);
    }

    public Double getAmount() {
    	return totalAmount;
    }
    public Device getDevice() {
    	BrandEcosystem brandName = (BrandEcosystem) jcbBrandName.getSelectedItem();

    	JTextField[] fieldsText = {reference, serial};
    	if (brandName.getBrandName().contentEquals(UNKNOWN_BRAND)) {
    		// Créez un nouveau tableau avec une taille plus grande
    	    JTextField[] newFieldsText = new JTextField[fieldsText.length + 1];

    	    // Copiez les éléments de l'ancien tableau dans le nouveau
    	    for (int i = 0; i < fieldsText.length; i++) {
    	        newFieldsText[i] = fieldsText[i];
    	    }

    	    // Ajoutez manualBrandField au nouveau tableau
    	    newFieldsText[newFieldsText.length - 1] = manualBrandField;

    	    // Remplacez l'ancien tableau par le nouveau
    	    fieldsText = newFieldsText;
    	}

    	for (JTextField field : fieldsText) {
    		field.setBackground(null);
            field.setBorder(new LineBorder(Color.GREEN));
        }

        // Vérification des champs non vides
        boolean allFieldsFilled = Arrays.stream(fieldsText)
                .map(JTextField::getText)
                .allMatch(s -> !s.isEmpty());

        JComboBox[] comboBoxes = {jcbProduct,jcbBrandName,jcbIrisSection,jcbIrisSymptom};

        for (JComboBox comboBox : comboBoxes) {
        	comboBox.setBackground(null);
        	comboBox.setBorder(new LineBorder(Color.GREEN));
        }

     // Vérification des JComboBox non vides
        boolean allComboBoxesFilled = Arrays.stream(comboBoxes)
        		.filter(c -> c.isEnabled()) // Filtrez les JComboBox actifs seulement
                .map(c -> c.getSelectedItem())
                .allMatch(s -> s != null);

        if (allFieldsFilled && allComboBoxesFilled) {
        	Device device = new Device(
        			reference.getText(),
        		    serial.getText());

        	//Product product = (Product) jcbProduct.getSelectedItem();
        	//device.setProductId(product.getProductID());

        	BrandEcosystem brand = (BrandEcosystem) jcbBrandName.getSelectedItem();
        	//device.setBrandId(brand.getBrandId());

        	if (brand.getBrandName().contentEquals(UNKNOWN_BRAND))
        		device.setManualBrandName(manualBrandField.getText().toUpperCase());

        	Id_Name selectedItem = (Id_Name) jcbIrisSection.getSelectedItem();
        	device.setIrisSectionCode(selectedItem.getId());

        	if (jcbIrisSymptom.getSelectedItem() != null) {
	        	selectedItem = (Id_Name) jcbIrisSymptom.getSelectedItem();
	        	device.setIrisSymptomCode(selectedItem.getId());
        	}

        	device.setIsEcosystem(true);
        	if (organismLabel.getText().contains("Ecologic"))
        		device.setIsEcosystem(false);

        	return device;
        } else {
			for (JTextField field : fieldsText) {
                if (field.getText().isEmpty()) {
                	field.setBackground(null);
                    field.setBorder(new LineBorder(Color.RED));
         		}
             }
			// Afficher un message d'erreur ou effectuer une action appropriée
		    System.out.println("Tous les champs doivent être remplis.");
		    return null;
		}
    }

    public void clear() {
    	// Réinitialisez les champs du formulaire à leur état initial
        jcbProduct.setSelectedIndex(0); // Sélectionnez "Choisir un produit" dans la JComboBox des produits
        jcbBrandName.removeAllItems();
        jcbBrandName.repaint();
        reference.setText(""); // Réinitialisez le champ de référence
        serial.setText(""); // Réinitialisez le champ du numéro de série
        jcbIrisSymptom.setSelectedIndex(-1); // Déselectionnez tout dans la JComboBox des symptômes Iris
        jcbIrisSection.setSelectedIndex(-1); // Déselectionnez tout dans la JComboBox des sections Iris

        JTextField[] fieldsText = {reference, serial};
    	for (JTextField field : fieldsText) {
            field.setBorder(new EmptyBorder(0, 5, 0, 5));
        }

    	jcbProduct.setBorder(new LineBorder(Color.RED));
    	JComboBox[] comboBoxes = {jcbBrandName,jcbIrisSection,jcbIrisSymptom};
        for (JComboBox comboBox : comboBoxes) {
        	comboBox.setBackground(null);
        	comboBox.setBorder(new EmptyBorder(0, 5, 0, 5));
        }

        // Réinitialisez les labels éco-organisme et remboursement
        setEcoorganism("");
        setAmount("0.0");

        // Désactivez tous les champs du formulaire
        setEnable(false);
    }
}
