package com.hallouin.view.panels;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DecimalFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.hallouin.model.bill.Bill;
import com.hallouin.model.ecosystem.api.request_pojo.AmountCurency;
import com.hallouin.model.ecosystem.api.request_pojo.EcosystemBill;

public class BillFormPanel {
	JPanel panel;
	GridBagConstraints pos;

	public BillFormPanel() {
		super();
	}
	public JPanel addBillFormPanel() {
		EcosystemBill billContent = null;
		panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2, true), "Facture", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

        createPanel(billContent);

        return panel;
	}

	private void createPanel(EcosystemBill billContent) {
		panel.removeAll();
		DecimalFormat decimalFormat = new DecimalFormat("0.00");

		// Créez un objet GridBagConstraints pour configurer les propriétés du composant
		GridBagConstraints posEast = new GridBagConstraints();
		posEast.insets = new Insets(0, 0, 0, 10);
        posEast.anchor = GridBagConstraints.EAST;

        GridBagConstraints posWest = new GridBagConstraints();
        posWest.insets = new Insets(0, 10, 0, 0);
        posWest.anchor = GridBagConstraints.WEST;


		if (billContent != null) {
			int numLine = 0;

			//montant pieces
			Double spareDouble = billContent.getSparePartsCost();
			if (spareDouble > 0.0) {
				posWest.gridx = 0;
	        	posWest.gridy = numLine;
	        	JLabel label = new JLabel("Pièces détachées ");
	            panel.add(label, posWest);

	            posEast.gridx = 1;
	            posEast.gridy = numLine;
	        	JLabel labelVal = new JLabel(decimalFormat.format(spareDouble));
	            panel.add(labelVal, posEast);
	            numLine++;
			}

            //montant Mo
            Double moDouble = billContent.getLaborCost();
            if (moDouble > 0.0) {
				posWest.gridx = 0;
	        	posWest.gridy = numLine;
	        	JLabel label1 = new JLabel("Main d'oeuvre  ");
	        	label1.setName("mo");
	            panel.add(label1, posWest);

	            posEast.gridx = 1;
	            posEast.gridy = numLine;
	        	JLabel label1Val = new JLabel(decimalFormat.format(moDouble));
	            panel.add(label1Val, posEast);
	            numLine++;
            }

            //montant Déplacement
            Double displacmtDouble = billContent.getTravelExpenses();
            if (displacmtDouble > 0.0) {
				posWest.gridx = 0;
	        	posWest.gridy = numLine;
	        	JLabel label2 = new JLabel("Déplacement  ");
	        	label2.setName("displacement");
	            panel.add(label2, posWest);

	            posEast.gridx = 1;
	            posEast.gridy = numLine;
	        	JLabel label2Val = new JLabel(decimalFormat.format(displacmtDouble));
	            panel.add(label2Val, posEast);
	            numLine++;
            }

            posEast.gridx = 1;
            posEast.gridy = numLine;
        	JLabel label3Val = new JLabel("--------");
            panel.add(label3Val, posEast);
            numLine++;


          //montant Total ht
            Double htDouble = billContent.getAmountBeforeTax();
            if (htDouble > 0.0) {
				posWest.gridx = 0;
	        	posWest.gridy = numLine;
	        	JLabel label4 = new JLabel("Total HT  ");
	        	label4.setName("ht");
	            panel.add(label4, posWest);

	            posEast.gridx = 1;
	            posEast.gridy = numLine;
	        	JLabel label4Val = new JLabel(decimalFormat.format(htDouble));
	            panel.add(label4Val, posEast);
	            numLine++;
            }

          //montant Total Ttc
            Double amountttcDouble = billContent.getTotalAmountInclVAT();
            if (amountttcDouble > 0.0) {
				posWest.gridx = 0;
	        	posWest.gridy = numLine;
	        	JLabel label5 = new JLabel("Total TTC  ");
	            panel.add(label5, posWest);

	            posEast.gridx = 1;
	            posEast.gridy = numLine;
	        	JLabel label5Val = new JLabel(decimalFormat.format(amountttcDouble));
	            panel.add(label5Val, posEast);
	            numLine++;
            }

            posEast.gridx = 1;
            posEast.gridy = numLine;
        	JLabel label6Val = new JLabel("--------");
            panel.add(label6Val, posEast);
            numLine++;

          //montant aide
			posWest.gridx = 0;
        	posWest.gridy = numLine;
        	JLabel label7 = new JLabel("Bonus réparation  ");
            panel.add(label7, posWest);

            posEast.gridx = 1;
            posEast.gridy = numLine;
        	JLabel label7Val = new JLabel(decimalFormat.format(billContent.getAmountCovered()));
            panel.add(label7Val, posEast);
		}
	}

	public void updateBill(EcosystemBill ecosystemBill) {
    	createPanel(ecosystemBill);
    }
	public void eclUpdateBill(Bill bill) {
		// cionversion d'un objet bill en un objet ecosystembill
		AmountCurency amountBeforeTax = new AmountCurency(bill.getInvoice().getTotalExclVat());
		AmountCurency laborCost = new AmountCurency(bill.getLabour().getTotalExclVat());
		AmountCurency totalAmountInclVAT = new AmountCurency(bill.getInvoice().getTotalVat());
		AmountCurency amountCovered = new AmountCurency(bill.getInvoice().getSupportAmount());
		AmountCurency setSparePartsCost = new AmountCurency(bill.getInvoice().getTotalSparesExclVat());
		AmountCurency setTravelExpenses = new AmountCurency(bill.getDisplacement().getTotalExclVat());
		EcosystemBill ecosystemBill = new EcosystemBill(amountBeforeTax, laborCost, totalAmountInclVAT, amountCovered);
    	ecosystemBill.setSparePartsCost(setSparePartsCost);
    	ecosystemBill.setTravelExpenses(setTravelExpenses);
		createPanel(ecosystemBill);
    }
}
