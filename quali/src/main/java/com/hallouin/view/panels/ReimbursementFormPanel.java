package com.hallouin.view.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.hallouin.model.bill.Bill;
import com.hallouin.model.ecologic.api.response_pojo.ResponseEcologic.ValidationError;
import com.hallouin.model.ecosystem.api.response_pojo.ClaimDetails;

public class ReimbursementFormPanel {

	private JTextField purchaseOrderByCustomer;
    private JTextField repairDate;
    private JTextField repairer;
    private JTextField repairPlace;
    private JLabel requestId;
    private JLabel claimId;
    private JLabel status;
    private JLabel comment;

    public JPanel addReimbursementFormPanel() {

    	purchaseOrderByCustomer = new JTextField();
    	JPanel panel_PurchaseOrderByCustomer = BasicFormJTextField("Ma ref.", purchaseOrderByCustomer);
    	panel_PurchaseOrderByCustomer.setPreferredSize(new Dimension(150,40));
    	panel_PurchaseOrderByCustomer.setMaximumSize(panel_PurchaseOrderByCustomer.getPreferredSize());

    	repairDate = new JTextField();
    	JPanel panel_repairDate = BasicFormJTextField("Date de réparation", repairDate);
    	panel_repairDate.setPreferredSize(new Dimension(120,40));
    	panel_repairDate.setMaximumSize(panel_repairDate.getPreferredSize());

    	repairer = new JTextField();
    	JPanel panel_Repairer = BasicFormJTextField("Dépanneur", repairer);
    	panel_Repairer.setPreferredSize(new Dimension(150,40));
    	panel_Repairer.setMaximumSize(panel_Repairer.getPreferredSize());

    	repairPlace = new JTextField();
    	JPanel panel_RepairPlace = BasicFormJTextField("Atelier", repairPlace);
    	panel_RepairPlace.setPreferredSize(new Dimension(180,40));
    	panel_RepairPlace.setMaximumSize(panel_RepairPlace.getPreferredSize());

    	requestId = new JLabel();
    	JPanel panel_requestId = BasicFormJLabel("Ref Ecosystem", requestId);
    	panel_requestId.setPreferredSize(new Dimension(100,40));
    	panel_requestId.setMaximumSize(panel_requestId.getPreferredSize());

    	claimId = new JLabel();
    	JPanel panel_claimId = BasicFormJLabel("ID de la demande", claimId);
    	panel_claimId.setPreferredSize(new Dimension(240,40));
    	panel_claimId.setMaximumSize(panel_claimId.getPreferredSize());

    	status = new JLabel();
    	JPanel panel_RequestStatus = BasicFormJLabel("Status de la demande", status);
    	panel_RequestStatus.setPreferredSize(new Dimension(200,40));
    	panel_RequestStatus.setMaximumSize(panel_RequestStatus.getPreferredSize());

    	comment = new JLabel();
    	JPanel panel_Comment = BasicFormJLabel("Commentaires", comment);
    	panel_Comment.setPreferredSize(new Dimension(300,40));
    	panel_Comment.setMaximumSize(panel_Comment.getPreferredSize());

        JPanel line1 = new JPanel();
        line1.setLayout(new BoxLayout(line1, BoxLayout.X_AXIS));
        line1.add(panel_repairDate);
        line1.add(panel_PurchaseOrderByCustomer);
        line1.add(Box.createHorizontalGlue());

        JPanel line2 = new JPanel();
        line2.setLayout(new BoxLayout(line2, BoxLayout.X_AXIS));
        line2.add(panel_Repairer);
        line2.add(panel_RepairPlace);
        line2.add(Box.createHorizontalGlue());

        JPanel line3 = new JPanel();
        line3.setLayout(new BoxLayout(line3, BoxLayout.X_AXIS));
        line3.add(panel_requestId);
        line3.add(panel_claimId);
        line3.add(Box.createHorizontalGlue());

        JPanel line4 = new JPanel();
        line4.setLayout(new BoxLayout(line4, BoxLayout.X_AXIS));
        line4.add(panel_RequestStatus);
        line4.add(Box.createHorizontalGlue());

        JPanel line5 = new JPanel();
        line5.setLayout(new BoxLayout(line5, BoxLayout.X_AXIS));
        line5.add(panel_Comment);
        line5.add(Box.createHorizontalGlue());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2, true), "Demande", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panel.setPreferredSize(new Dimension(320,200));
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

    private JPanel BasicFormJLabel(String label, JLabel jlabel) {
    	JPanel panel = new JPanel();
    	Border margin = new EmptyBorder(0, 5, 0, 5);

        // Application de la bordure au panel

        panel.setLayout(new GridLayout(2, 1, 0, 0));
        panel.setBorder(margin);

        panel.add(new JLabel(label));
        panel.add(jlabel);

        return panel;
    }

    public void updateReimbursement(ClaimDetails claim) {
    	clearFields();
    	try {
    		purchaseOrderByCustomer.setText(claim.getPurchaseOrderByCustomer());
        	repairDate.setText(claim.getRepairDate());
        	repairer.setText(claim.getRepairer().getCorporateName());
        	repairPlace.setText(claim.getRepairPlace().getCorporateName());
        	if (claim.getSapServiceOrder() != null)
        		requestId.setText(claim.getSapServiceOrder());
        	claimId.setText(claim.getReimbursementClaimID());
        	status.setText(claim.getRequestStatus());
        	comment.setText(claim.getRejectReason());
		} catch (Exception e) {
			// TODO: handle exception
		}

    }
    public void eclUpdateReimbursement(Bill bill) {
    	clearFields();

    	try {
    		purchaseOrderByCustomer.setText(bill.getEcologicDatas().getQuoteNumber());
        	repairDate.setText(bill.getBillInfos().getRepairDate());
        	repairer.setText(bill.getEcologicDatas().getRepairSite().getName());
        	repairPlace.setText(bill.getEcologicDatas().getRepairSite().getCommercialName());
        	requestId.setText(Integer.toString(bill.getEcologicDatas().getRequestId()));
        	claimId.setText(Integer.toString(bill.getEcologicDatas().getClaimId()));
        	status.setText(bill.getEcologicDatas().getLastStatus());
        	comment.setText(rejectRaison(bill.getEcologicDatas().getErrorsList()));
		} catch (Exception e) {
			// TODO: handle exception
		}

    }
    private String rejectRaison (List<ValidationError> errors) {
    	String errorString = "";
    	for (ValidationError validationError : errors) {
			errorString += validationError.getErrorMessage()+"\n";
		}
    	return errorString;
    }
    private void clearFields() {
    	purchaseOrderByCustomer.setText("");
    	repairDate.setText("");
    	repairer.setText("");
    	repairPlace.setText("");
    	requestId.setText("");
    	claimId.setText("");
    	status.setText("");
    	comment.setText("");
    }
}
