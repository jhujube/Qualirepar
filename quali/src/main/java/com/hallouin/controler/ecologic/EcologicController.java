package com.hallouin.controler.ecologic;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.hallouin.controler.claim.ClaimController;
import com.hallouin.model.bill.Bill;
import com.hallouin.model.ecologic.EcologicModel;
import com.hallouin.model.ecologic.api.response_pojo.ResponseEcologic.ValidationError;
import com.hallouin.view.DialogsView;
import com.hallouin.view.ecologic.EcologicView;

public class EcologicController implements PropertyChangeListener {
	private EcologicView ecologicView;
	private EcologicModel ecologicModel;
	private DialogsView dialogsView;
	private Map<Integer	, Bill> billMap;
	private String[][] tableDatas;

	public EcologicController(EcologicView ecologicView, DialogsView dialogsView, EcologicModel ecologicModel){
		this.ecologicView = ecologicView;
		this.ecologicModel  = ecologicModel;
		this.dialogsView = dialogsView;
		//ecologicModel.setController(this);
		ecologicView.setController(this);
		start();
	}

	private  void start() {

		//String[] columnsName = {"id soutien", "id rembrsmt", "Ma ref.", "Client", "Crée le", "Réparateur", "Statut", "Date de réparation","Actions"};
		String[] columnsName = {"id rembrsmt", "Ma ref.", "Client", "Crée le", "Réparateur", "Statut", "Date de réparation","Actions"};
				
		ecologicView.setTableColumns(columnsName);
		
		tableDatas = ecologicModel.loadClaims();
		ecologicView.setClaimsTableDatas(tableDatas);
		
		// Creation d'un completableFurture ds un autre Thread pour ne pas bloquer le programme
		CompletableFuture<String[][]> updateClaims = new CompletableFuture<>();
		
		updateClaims.whenComplete((value,error) -> {
			tableDatas = value;
			ecologicModel.saveClaims(tableDatas);
	
			ecologicView.setClaimsTableDatas(tableDatas);
		});
		CompletableFuture.runAsync(() -> {
			updateClaims.complete(ecologicModel.updateClaimsStatus(tableDatas));
		});
	
	}

	public void setClaimController(ClaimController claimController) {
		claimController.addPropertyChangeListener(this); // Enregistre la vue en tant qu'observateur des changements de propriétés du modèle
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();

    	switch (propertyName) {
		case "newClaimEcologic":
        	start();
			break;

		default:
			break;
    	}
	}

	private String[][] computeClaimsDataToTable(List<Bill> createdClaimsList, int columnsNumber){
		billMap = new HashMap<>();

		String[][] datas = new String[createdClaimsList.size()][columnsNumber];
		int i = 0;
		for(Bill bill: createdClaimsList) {
			datas[i][0] = ""+bill.getEcologicDatas().getRequestId();
			datas[i][1] = ""+bill.getEcologicDatas().getClaimId();
			datas[i][2] = bill.getEcologicDatas().getQuoteNumber();
			datas[i][3] = bill.getClient().getName()+" "+bill.getClient().getFirstName();
			datas[i][4] = bill.getBillInfos().getRepairDate();
			datas[i][5] = bill.getEcologicDatas().getRepairSite().getCommercialName();
			datas[i][6] = bill.getEcologicDatas().getLastStatus();
			try {
				String creationDate = convertDate(bill.getEcologicDatas().getCreationDate());
				datas[i][7] = creationDate;
			} catch (NullPointerException e) {
				datas[i][7] = "";
			}

			i++;

			if (bill.getEcologicDatas().getClaimId() > 0) {
				billMap.put(bill.getEcologicDatas().getClaimId(),bill );
			}else {
				billMap.put(bill.getEcologicDatas().getRequestId(),bill );
			}
		}
		return datas;
	}

	private String convertDate (LocalDateTime date) {
		LocalDateTime creationDate = date;

        // Définissez le format personnalisé pour "DD/MM/YYYY"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Formatez la date en chaîne avec le format personnalisé
        return creationDate.format(formatter);

	}

	public void selectClaim(String id) {
		int idInt = Integer.valueOf(id);
		String[][] claimsInfoStrings = ecologicModel.getValidationErrors(idInt);
		ecologicView.updateConsultClaim(claimsInfoStrings);
	}
	
	public void newSearch(String text) {
		List<String[]> filteredDataList = new ArrayList<>();

		for (String[] strings : tableDatas) {
			boolean isStringPresent = false;
			for (int i=0; i<strings.length; i++) {
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
	    
	    ecologicView.setClaimsTableDatas(filteredTableDatas);
	}
}
