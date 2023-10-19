
package com.hallouin.view.claimCreation.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.hallouin.controler.claim.ClaimController;
import com.hallouin.model.bill.FileInformations;

public class SelectFilesFormPanel{
	ClaimController claimController;

	JPanel buttonsPanel;

	JButton invoiceBtn;
	JLabel invoiceField;

    JButton serial_tagBtn;
    JLabel serial_tagField;

    JButton device_pictureBtn;
    JLabel device_pictureField;

    JButton certificate_clientBtn;
    JLabel certificate_clientField;

    JPanel panel;
    JPanel invoice;
    JPanel serial_tag;
    JPanel device_picture;
    JPanel certificate_client;

    public JPanel addSelectFilesFormPanel() {

    	panel = new JPanel();
    	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    	panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2, true), "Fichiers à transmettre", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
    	panel.setPreferredSize(new Dimension(220, 180));
        panel.setMaximumSize(panel.getPreferredSize());
        panel.setMinimumSize(panel.getPreferredSize());


    	invoiceBtn = new JButton("Facture");
    	invoiceField = new JLabel();
    	invoiceField.setName("invoiceField");
    	invoice = FormJButtonField (invoiceField, invoiceBtn);
    	invoiceBtn.addActionListener(new ButtonsListener());
    	invoiceBtn.setEnabled(false);

    	serial_tagBtn = new JButton("Plaque signalétique");
    	serial_tagField = new JLabel();
    	serial_tagField.setName("serial_tagField");
    	serial_tag = FormJButtonField (serial_tagField, serial_tagBtn);
    	serial_tagBtn.addActionListener(new ButtonsListener());
    	serial_tagBtn.setEnabled(false);

    	device_pictureBtn = new JButton("Photo appareil");
    	device_pictureField = new JLabel();
    	device_pictureField.setName("device_pictureField");
    	device_picture = FormJButtonField (device_pictureField, device_pictureBtn);
    	device_pictureBtn.addActionListener(new ButtonsListener());
    	device_picture.setVisible(false);

    	certificate_clientBtn = new JButton("Certificat client");
    	certificate_clientBtn.addActionListener(new ButtonsListener());
    	certificate_clientField = new JLabel();
    	certificate_clientField.setName("certificate_clientField");
    	certificate_client = FormJButtonField (certificate_clientField, certificate_clientBtn);
    	certificate_client.setVisible(false);

        // Ajout des boutons au JPanel centré
        panel.add(invoice);
        panel.add(serial_tag);
        panel.add(device_picture);
        panel.add(certificate_client);

        //panel.add(buttonsPanel);

        return panel;
	}

    public class ButtonsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        	String btnType = "";
        	if  (e.getSource() == invoiceBtn)
        		btnType= "invoice";
        	if  (e.getSource() == serial_tagBtn)
        		btnType= "serial_tag";
        	if  (e.getSource() == device_pictureBtn)
        		btnType= "device_picture";
        	if  (e.getSource() == certificate_clientBtn)
        		btnType= "certificate_client";
        	claimController.selectFileToSend(btnType);
        }
    }

    public JButton getInvoiceButton() {
        return invoiceBtn;
    }

    public JButton getserial_tagButton() {
        return serial_tagBtn;
    }

    public JButton getdevice_pictureButton() {
        return device_pictureBtn;
    }

    public JButton getCertificate_clientButton() {
        return certificate_clientBtn;
    }

    public void setController(ClaimController claimController) {
    	this.claimController = claimController;
    }

	private JPanel FormJButtonField (JLabel field, JButton button) {
		JPanel panel = new JPanel();
		field.setHorizontalAlignment(SwingConstants.CENTER);
    	Border margin = new EmptyBorder(0, 25, 0, 25);

        // Application de la bordure au panel

        panel.setLayout(new GridLayout(2, 1, 0, 0));
        panel.setBorder(margin);
        panel.add(field);
        panel.add(button);

        return panel;
	}
	public void setButtons(List<Boolean> buttonsStates) {
		invoiceBtn.setEnabled(buttonsStates.get(0));
		serial_tagBtn.setEnabled(buttonsStates.get(1));
		device_picture.setVisible(buttonsStates.get(2));
		certificate_client.setVisible(buttonsStates.get(3));
	}

	public void updateFilesNames(FileInformations fileInformations) {
		String fileName = fileInformations.getName() + "." + fileInformations.getExtension();

		switch (fileInformations.getType()) {
        case "invoice":
        	invoiceField.setText(fileName);
        	break;
        case "serial_tag":
        	serial_tagField.setText(fileName);
        	break;
        case "device_picture":
        	device_pictureField.setText(fileName);
        	break;
        case "certificate_client":
			certificate_clientField.setText(fileName);
	    	break;
		}
	}

	public List<String> getFilesList(){
		List<String> filesList = new ArrayList<>();
		JLabel[] fields = {invoiceField, serial_tagField, device_pictureField, certificate_clientField};

    	for (JLabel field : fields) {
    		if (!field.getText().isEmpty())
    			filesList.add(field.getName());
        }
    	return filesList;
	}

	public void clear() {
		invoiceField.setText("");
    	serial_tagField.setText("");
    	device_pictureField.setText("");
    	certificate_clientField.setText("");
    	List<Boolean> buttonsStatesBooleans = new ArrayList<>();
    	buttonsStatesBooleans.add(false);
		buttonsStatesBooleans.add(false);
		buttonsStatesBooleans.add(false);
		buttonsStatesBooleans.add(false);
		setButtons(buttonsStatesBooleans);
		panelSetcolor(0);
	}
	public void panelSetcolor(int color) {
		panel.setBorder(new TitledBorder(new LineBorder(new Color(color, 0, 0), 2, true), "Fichiers à transmettre", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
	}
}
