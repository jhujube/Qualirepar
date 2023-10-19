package com.hallouin.controler.ecosystem;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hallouin.controler.claim.ClaimController;
import com.hallouin.model.ecosystem.EcosystemModel;
import com.hallouin.model.ecosystem.api.response_pojo.ClaimDetails;
import com.hallouin.model.ecosystem.api.response_pojo.ClaimSentInfos;
import com.hallouin.model.ecosystem.api.response_pojo.GetAllClaims;
import com.hallouin.view.DialogsView;
import com.hallouin.view.ecosystem.EcosystemView;

public class EcosystemController implements PropertyChangeListener {
	private EcosystemView ecosystemView;
	private EcosystemModel ecosystemModel;
	private DialogsView dialogsView;
	private String selectedClaim = "";
	private String[][] tableDatas;

	public EcosystemController(EcosystemView ecosystemView, DialogsView dialogsView, EcosystemModel ecosystemModel){
		this.ecosystemView = ecosystemView;
		this.ecosystemModel  = ecosystemModel;
		this.dialogsView = dialogsView;
		ecosystemModel.setController(this);
		ecosystemView.setController(this);
		start();
	}

	private void start() {
		List<ClaimSentInfos> claimsList = new ArrayList<>();

		String[] columnsName = {"id", "Ref. Ecosystem", "Ma ref.", "Crée le", "Réparateur", "Statut", "Date de réparation","Actions"};
		ecosystemView.setTableColumns(columnsName);

		GetAllClaims createdClaims = ecosystemModel.getAllClaims();
		claimsList = createdClaims.getClaimsSentList();

		System.out.println("Ecosystem 1ere lecture effectuée, mise à jour des demandes...");
		updateCreatedClaims(claimsList);

		createdClaims = ecosystemModel.getAllClaims();
		claimsList = createdClaims.getClaimsSentList();

		System.out.println("Ecosystem 2nde lecture effectuée, affichage résultats...");
		tableDatas = computeClaimsDataToTable(claimsList, columnsName.length);
		ecosystemView.setClaimsTableDatas(tableDatas);

	}

	public void setClaimController(ClaimController claimController) {
		claimController.addPropertyChangeListener(this); // Enregistre la vue en tant qu'observateur des changements de propriétés du modèle
	}

	// Méthode appelée lorsque le modèle envoie un événement de changement de propriété
    @Override
	public void propertyChange(PropertyChangeEvent evt) {
    	String propertyName = evt.getPropertyName();

    	switch (propertyName) {
		case "newClaimEcosystem":
        	start();
			break;

		default:
			break;
    	}
    }

	public void deleteClaim() {
		if (!selectedClaim.isEmpty() || !selectedClaim.isBlank()) {
			ecosystemModel.deleteClaim(selectedClaim);
			start();
		}
	}

	private String[][] computeClaimsDataToTable(List<ClaimSentInfos> createdClaimsList, int columnsNumber){
		String[][] datas = new String[createdClaimsList.size()][columnsNumber];
		int i = 0;
		for(ClaimSentInfos claimSentInfo: createdClaimsList) {
			datas[i][0] = claimSentInfo.getReimbursementClaimID();
			datas[i][1] = claimSentInfo.getSapServiceOrder();
			datas[i][2] = claimSentInfo.getPurchaseOrderByCustomer();

			Date date = claimSentInfo.getCreationDate();
			SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MM-yyyy");
			String formattedDate = outputDateFormat.format(date);

			datas[i][3] = formattedDate;
			try {
				datas[i][4] = claimSentInfo.getRepairer().getCorporateName();
			} catch (NullPointerException e) {
				datas[i][4] = "";
			}
			datas[i][5] = claimSentInfo.getRequestStatus();
			datas[i][6] = claimSentInfo.getRepairDate();
			i++;
		}
		return datas;
	}

	public void selectClaim(String id) {
		ClaimDetails claimDetails = ecosystemModel.consultClaim(id, true);
		selectedClaim = id;
		if (claimDetails != null) {
			if (!claimDetails.getRequestStatus().contains("En cours de création")) {
				ecosystemView.enableDeleteButton(false);
			}
			else {
				ecosystemView.enableDeleteButton(true);
			}
		}
	}
	private void updateCreatedClaims(List<ClaimSentInfos> claimsSendList) {

		//  Récuprération des demandes "en cours"
		List<ClaimSentInfos> claimsCreatedList = new ArrayList<>();
		for (ClaimSentInfos claimSentInfos : claimsSendList) {
			if (claimSentInfos.getRequestStatus().contains("en cours"))
				claimsCreatedList.add(claimSentInfos);
		}

		// Mise à jour de l'état de chaque demande "en cours"
		for (ClaimSentInfos claimSentInfos : claimsCreatedList) {
			ecosystemModel.consultClaim(claimSentInfos.getSapServiceOrder(), false);
		}
	}

	public void newSearch(String text) {
		List<String[]> filteredDataList = new ArrayList<>();

		for (String[] strings : tableDatas) {
			boolean isStringPresent = false;
			for (int i=0; i<7; i++) {
				if (strings[i] != null && strings[i].toLowerCase().contains(text.toLowerCase())) {
					isStringPresent = true;
					break;
				}
			}
			if (isStringPresent)
				filteredDataList.add(strings);
		}

		// Convertissez la liste en un tableau 2D
	    String[][] filteredTableDatas = new String[filteredDataList.size()][];
	    filteredDataList.toArray(filteredTableDatas);

		ecosystemView.setClaimsTableDatas(filteredTableDatas);
	}

	public void showSimpleMessage(String message, String title) {
		dialogsView.simpleMessage(message, title);
	}
}
