package com.hallouin.view.ecologic.panels;

import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.hallouin.model.bill.Bill;
import com.hallouin.model.ecologic.EcologicModel;
import com.hallouin.view.panels.BillFormPanel;
import com.hallouin.view.panels.ClientFormPanel;
import com.hallouin.view.panels.DeviceFormPanel;
import com.hallouin.view.panels.FilesFormPanel;
import com.hallouin.view.panels.ReimbursementFormPanel;
import com.hallouin.view.panels.SpareFormPanel;

public class EclConsultClaimPanel {

	private ReimbursementFormPanel rfp;
	private ClientFormPanel cfp;
	private DeviceFormPanel dfp;
	private SpareFormPanel sfp;
	private BillFormPanel bfp;
	private FilesFormPanel ffp;
	private JPanel panel;

	public EclConsultClaimPanel (EcologicModel ecologicModel) {
		super();
		//ecologicModel.addPropertyChangeListener(this); // Enregistre la vue en tant qu'observateur des changements de propriétés du modèle
	}

	public JPanel setMainView() {
		panel = new JPanel();

	    JPanel panel_consultClaim = new JPanel();
        panel_consultClaim.setLayout(new BoxLayout(panel_consultClaim, BoxLayout.Y_AXIS));
        panel_consultClaim.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panel1_h = new JPanel();
        panel1_h.setLayout(new BoxLayout(panel1_h, BoxLayout.X_AXIS));
        panel1_h.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        JPanel panel2_v = new JPanel();
        panel2_v.setLayout(new BoxLayout(panel2_v, BoxLayout.Y_AXIS));
        panel2_v.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        JPanel panel2_h = new JPanel();
        panel2_h.setLayout(new FlowLayout());
        panel2_h.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        cfp = new ClientFormPanel();
        dfp = new DeviceFormPanel();
        rfp = new ReimbursementFormPanel();
        sfp = new SpareFormPanel();
        bfp = new BillFormPanel();
        ffp = new FilesFormPanel();

        JPanel panel_Reimbursement = rfp.addReimbursementFormPanel();
        JPanel panel_client = cfp.addClientFormPanel();
        JPanel panel_device = dfp.addDeviceFormPanel();
        JPanel panel_spares = sfp.addSpareFormPanel();
        JPanel panel_bill = bfp.addBillFormPanel();
        JPanel panel_files = ffp.addFilesFormPanel();


        panel1_h.add(panel_Reimbursement);
        panel1_h.add(panel_client);
        panel1_h.add(panel_device);

        panel2_h.add(panel_bill);
        panel2_h.add(panel_spares);
        panel2_h.add(panel_files);

        panel_consultClaim.add(panel1_h);
        panel_consultClaim.add(panel2_h);

        panel.add(panel_consultClaim);

	    panel.setVisible(false);

	    return panel;
	}

	// Méthode appelée lorsque le modèle envoie un événement de changement de propriété
    public void fillClaimPanel(Bill bill) {

        // Mettre à jour l'interface graphique avec la nouvelle valeur

    	cfp.updateClient(bill.getClient());
    	dfp.eclUpdateDevice(bill.getDevice());
    	rfp.eclUpdateReimbursement(bill);
    	sfp.eclUpdateSpares(bill.getSparesList());
    	bfp.eclUpdateBill(bill);
    	ffp.eclUpdateFiles(bill.getFilesInformationsList());
    	panel.setVisible(true);
    }
}
