package com.hallouin.view.claimCreation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.hallouin.controler.claim.ClaimController;
import com.hallouin.model.bill.Bill;
import com.hallouin.model.bill.BillInfos;
import com.hallouin.model.bill.Client;
import com.hallouin.model.bill.Device;
import com.hallouin.model.bill.Displacement;
import com.hallouin.model.bill.FileInformations;
import com.hallouin.model.bill.Invoice;
import com.hallouin.model.bill.Labour;
import com.hallouin.model.bill.SparePart;
import com.hallouin.model.claim.ClaimModel;
import com.hallouin.model.ecosystem.api.pojo.BrandEcosystem;
import com.hallouin.model.ecosystem.api.pojo.Product;
import com.hallouin.model.ecosystem.pojo.Id_Name;
import com.hallouin.view.claimCreation.panels.BillFormPanel;
import com.hallouin.view.claimCreation.panels.ButtonsFormPanel;
import com.hallouin.view.claimCreation.panels.ClientFormPanel;
import com.hallouin.view.claimCreation.panels.DeviceFormPanel;
import com.hallouin.view.claimCreation.panels.ListClaimsToSendFormPanel;
import com.hallouin.view.claimCreation.panels.SelectFilesFormPanel;
import com.hallouin.view.claimCreation.panels.SpareFormPanel;

public class ClaimView implements PropertyChangeListener{
	ClaimModel claimModel;
	ClaimController claimController;

	BillFormPanel bfp;
	ClientFormPanel cfp;
	DeviceFormPanel dfp;
	SpareFormPanel sfp;
	ListClaimsToSendFormPanel lfp;
	SelectFilesFormPanel sffp;
	ButtonsFormPanel butFP;

	public ClaimView(ClaimModel claimModel) {
		super();
		this.claimModel = claimModel;
		claimModel.addPropertyChangeListener(this); // Enregistre la vue en tant qu'observateur des changements de propriétés du modèle
		setClaimTabbedPane();

	}
	public void setController(ClaimController claimController) {
		this.claimController = claimController;
		claimController.setView(this);
		dfp.setController(claimController);
		butFP.setController(claimController);
		sffp.setController(claimController);
		lfp.setController(claimController);
	}

	public JPanel setClaimTabbedPane() {
		
		JPanel panel = new JPanel();
	    panel.setLayout(new FlowLayout());
	    panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2, true), "Nouvelle demande ", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
	    panel.setPreferredSize(new Dimension(900,230));
	    panel.setMaximumSize(panel.getPreferredSize());
	    panel.setMinimumSize(panel.getPreferredSize());

	    JPanel panel_newClaim = new JPanel();
        panel_newClaim.setLayout(new BoxLayout(panel_newClaim, BoxLayout.Y_AXIS));
        panel_newClaim.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panel1_h = new JPanel();
        panel1_h.setLayout(new BoxLayout(panel1_h, BoxLayout.X_AXIS));
        panel1_h.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        JPanel panel2_h = new JPanel();
        panel2_h.setLayout(new FlowLayout());
        panel2_h.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        if (lfp == null) {
		    bfp = new BillFormPanel();
	        cfp = new ClientFormPanel();
	        dfp = new DeviceFormPanel();
	        sfp = new SpareFormPanel();
	        sffp = new SelectFilesFormPanel();
	        butFP = new ButtonsFormPanel();
	        lfp = new ListClaimsToSendFormPanel();
        }

        JPanel panel_client = cfp.addClientFormPanel();
        JPanel panel_bill = bfp.addBillFormPanel();
        JPanel panel_device = dfp.addDeviceFormPanel();
        JPanel panel_spare = sfp.addSpareFormPanel();
        JPanel panel_FilesButtons = sffp.addSelectFilesFormPanel();
        JPanel panel_buttons = butFP.addButtonsFormPanel();
        JPanel panel_listClaims = lfp.addListClaimsToSendFormPanel();

        panel1_h.add(panel_bill);
        panel1_h.add(panel_client);
        panel1_h.add(panel_device);

        panel2_h.add(panel_FilesButtons);
        panel2_h.add(panel_spare);

        panel_newClaim.add(panel1_h);
        panel_newClaim.add(panel2_h);
        panel_newClaim.add(panel_buttons);
        panel_newClaim.add(panel_listClaims);
        
        panel.add(panel_newClaim);
        
	    return panel;
	}

	// Méthode appelée lorsque le modèle envoie un événement de changement de propriété
    @SuppressWarnings("unchecked")
	@Override
    public void propertyChange(PropertyChangeEvent evt) {
    	String propertyName = evt.getPropertyName();

    	switch (propertyName) {
		case "productsList":
        	dfp.setProductsList((List<Product>) evt.getNewValue());
			break;

		case "ecsBrandsList":
	    	dfp.setBrandsList((List<BrandEcosystem>) evt.getNewValue());
	    	break;

		case "irisSectionsList":
        	dfp.setIrisSection((List<Id_Name>) evt.getNewValue());
			break;

		case "irisSymptomsList":
        	dfp.setIrisSymptom((List<Id_Name>) evt.getNewValue());
        	break;

		case "amount":
         	dfp.setAmount((String) evt.getNewValue());
         	break;

		case "ecoorganism":
         	dfp.setEcoorganism((String) evt.getNewValue());
			break;

		case "partnersList":
        	bfp.setRepairersList((List<Id_Name>) evt.getNewValue());
			break;

		case "filesButtonsStates":
        	sffp.setButtons((List<Boolean>) evt.getNewValue());
        	break;

		case "infosClient":
        	cfp.updateClient((Client) evt.getNewValue());
        	break;

		case "infosBill":
        	bfp.updateBillInfos((BillInfos) evt.getNewValue());
        	break;

		case "infosDevice":
        	dfp.updateDeviceInfos((Device) evt.getNewValue());
        	break;

		case "infosSpares":
        	sfp.updateSpareParts((Bill) evt.getNewValue());
        	break;

		case "fileToUpload":
			sffp.updateFilesNames((FileInformations) evt.getNewValue());
        	break;

		case "claimsToSend":
			lfp.updateListClaims((String[][]) evt.getNewValue());
			break;

		case "sendStateTitle":
			lfp.setSendStateTitle((String) evt.getNewValue());
			break;

		case "sendStateLabel":
			lfp.setSendStateLabel((String) evt.getNewValue());
        	break;

		default:
			break;
    	}
    }

    public void enableFields(Boolean isEnable) {
    	cfp.setEnable(isEnable);
    	bfp.setEnable(isEnable);
    	butFP.enableSendBillButton(isEnable);
    	dfp.setEnable(isEnable);
    	sfp.setEnable(isEnable);
    }

    public BillInfos getBillInfos() {
    	return bfp.getBillInfos();
    }
    public Id_Name getPartnerId() {
    	return bfp.getPartnerId();
    }
    public Client getClient() {
    	return cfp.getClient();
    }
    public Device getDevice() {
    	return dfp.getDevice();
    }
    public Double getAmount() {
    	return dfp.getAmount();
    }
    public Labour getLabour() {
    	return sfp.getLabour();
	}
    public Displacement getDisplacement() {
    	return sfp.getDisplacement();
    }
    public Invoice getInvoice() {
    	return sfp.getInvoice();
    }
    public List<SparePart> getSpareParts(){
    	return sfp.getSparePartsList();
    }
    public List<String> getFilesList(){
    	return sffp.getFilesList();
    }
    public void panelFilesSetColor(int color) {
    	sffp.panelSetcolor(color);
    }

    public void clearInvoice() {
    	bfp.clear();
    	cfp.clear();
    	sffp.clear();
    	sfp.clear();
    	dfp.clear();
    }
}
