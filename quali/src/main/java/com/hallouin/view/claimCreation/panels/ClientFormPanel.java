package com.hallouin.view.claimCreation.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Arrays;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.hallouin.model.bill.Client;

@SuppressWarnings("serial")
public class ClientFormPanel extends JPanel{
	private JComboBox<String> gender;
    private JTextField name;
    private JTextField firstName;
    private JTextField streetNumber;
    private JTextField street;
    private JTextField zipCode;
    private JTextField town;
    private JTextField phone;
    private JTextField mail;

    public JPanel addClientFormPanel() {

        // Création des panels enfants

    	DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
    	comboBoxModel.addElement("M.");
    	comboBoxModel.addElement("Mme");
    	comboBoxModel.addElement("Autre");
    	gender = new JComboBox<>(comboBoxModel);
        JPanel panel_civilite = BasicFormJComboBox("Civilité",gender);
        panel_civilite.setPreferredSize(new Dimension(70,40));
        panel_civilite.setMaximumSize(panel_civilite.getPreferredSize());

        name = new JTextField();
        JPanel panel_name = BasicFormJTextField("Nom", name);
        panel_name.setPreferredSize(new Dimension(120,40));
        panel_name.setMaximumSize(panel_name.getPreferredSize());

        firstName = new JTextField();
        JPanel panel_firstName = BasicFormJTextField("Prénom",firstName);
        panel_firstName.setPreferredSize(new Dimension(120,40));
        panel_firstName.setMaximumSize(panel_firstName.getPreferredSize());

        streetNumber = new JTextField();
        JPanel panel_streetNumber = BasicFormJTextField("N°",streetNumber);
        panel_streetNumber.setPreferredSize(new Dimension(50,40));
        panel_streetNumber.setMaximumSize(panel_streetNumber.getPreferredSize());

        street = new JTextField();
        JPanel panel_streetName = BasicFormJTextField("Rue",street);
        panel_streetName.setPreferredSize(new Dimension(180,40));
        panel_streetName.setMaximumSize(panel_streetName.getPreferredSize());

        zipCode = new JTextField();
        JPanel panel_zip = BasicFormJTextField("CP",zipCode);
        panel_zip.setPreferredSize(new Dimension(60,40));
        panel_zip.setMaximumSize(panel_zip.getPreferredSize());

        town = new JTextField();
        JPanel panel_town = BasicFormJTextField("Ville",town);
        panel_town.setPreferredSize(new Dimension(180,40));
        panel_town.setMaximumSize(panel_town.getPreferredSize());

        phone = new JTextField();
        JPanel panel_phone = BasicFormJTextField("Téléphone",phone);
        panel_phone.setPreferredSize(new Dimension(100,40));
        panel_phone.setMaximumSize(panel_phone.getPreferredSize());

        mail = new JTextField();
        JPanel panel_mail = BasicFormJTextField("Email",mail);
        panel_mail.setPreferredSize(new Dimension(180,40));
        panel_mail.setMaximumSize(panel_mail.getPreferredSize());

        setEnable(false);

        JPanel line1 = new JPanel();
        line1.setLayout(new BoxLayout(line1, BoxLayout.X_AXIS));
        line1.add(panel_civilite);
        line1.add(panel_name);
        line1.add(panel_firstName);
        line1.add(Box.createHorizontalGlue());

        JPanel line2 = new JPanel();
        line2.setLayout(new BoxLayout(line2, BoxLayout.X_AXIS));
        line2.add(panel_streetNumber);
        line2.add(panel_streetName);
        line2.add(Box.createHorizontalGlue());

        JPanel line3 = new JPanel();
        line3.setLayout(new BoxLayout(line3, BoxLayout.X_AXIS));
        line3.add(panel_zip);
        line3.add(panel_town);
        line3.add(Box.createHorizontalGlue());

        JPanel line4 = new JPanel();
        line4.setLayout(new BoxLayout(line4, BoxLayout.X_AXIS));
        line4.add(panel_phone);
        line4.add(Box.createHorizontalGlue());

        JPanel line5 = new JPanel();
        line5.setLayout(new BoxLayout(line5, BoxLayout.X_AXIS));
        line5.add(panel_mail);
        line5.add(Box.createHorizontalGlue());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2, true), "Client", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
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

    private JPanel BasicFormJComboBox(String label, JComboBox<String> comboBox) {
    	JPanel panel = new JPanel();
    	Border margin = new EmptyBorder(0, 5, 0, 5);

        // Application de la bordure au panel

        panel.setLayout(new GridLayout(2, 1, 0, 0));
        panel.setBorder(margin);

        panel.add(new JLabel(label));
        panel.add(comboBox);

        return panel;
    }

    public void updateClient(Client client) {

    	gender.setSelectedItem(client.getGender());
    	name.setText(client.getName());
    	firstName.setText(client.getFirstName());
    	streetNumber.setText(client.getStreetNumber());
    	street.setText(client.getStreet());
    	zipCode.setText(client.getZipCode());
    	town.setText(client.getTown());
    	phone.setText(client.getPhone());
    	mail.setText(client.getMail());
    }

    public void clear() {
    	JTextField[] fields = {name, firstName, streetNumber, street, zipCode, town, phone, mail};
    	for (JTextField field : fields) {
    		field.setBorder(null);
            field.setBorder(new EmptyBorder(0, 5, 0, 5));
        }
    	gender.setSelectedIndex(0);
    	name.setText("");
    	firstName.setText("");
    	streetNumber.setText("");
    	street.setText("");
    	zipCode.setText("");
    	town.setText("");
    	phone.setText("");
    	mail.setText("");
    	setEnable(false);
    }

    public void setEnable(Boolean isEnable) {
    	gender.setEnabled(isEnable);
    	name.setEnabled(isEnable);
    	firstName.setEnabled(isEnable);
    	streetNumber.setEnabled(isEnable);
    	street.setEnabled(isEnable);
    	zipCode.setEnabled(isEnable);
    	town.setEnabled(isEnable);
    	phone.setEnabled(isEnable);
    	mail.setEnabled(isEnable);
    }

    public Client getClient() {
    	//JTextField[] fields = {name, firstName, streetNumber, street, zipCode, town, phone};
    	JTextField[] fields = {name, firstName, streetNumber, street, zipCode, town, phone, mail};

    	for (JTextField field : fields) {
    		field.setBorder(null);
            field.setBorder(new LineBorder(Color.GREEN));
        }

    	// Vérification des champs non vides
        boolean allFieldsFilled = Arrays.stream(fields)
                .map(JTextField::getText)
                .allMatch(s -> !s.isEmpty());

        if (allFieldsFilled) {
        	Client client = new Client(
        			(String) gender.getSelectedItem(),
        		    name.getText(),
        		    firstName.getText(),
        		    streetNumber.getText(),
        		    street.getText(),
        		    zipCode.getText(),
        		    town.getText(),
        		    phone.getText());
        	if (!mail.getText().isEmpty()) {
    	    	client.setMail(mail.getText());
    	    }

        	return client;
        } else {
        	 for (JTextField field : fields) {
                 if (field.getText().isEmpty()) {
             		field.setBorder(null);
                     field.setBorder(new LineBorder(Color.RED));
          		}
             }
		    // Afficher un message d'erreur ou effectuer une action appropriée
		    System.out.println("Tous les champs doivent être remplis. /Client/");
		    return null;
        }
    }
}
