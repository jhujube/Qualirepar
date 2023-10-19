package com.hallouin.controler.ecologic;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hallouin.controler.claim.ClaimController;
import com.hallouin.model.bill.Bill;
import com.hallouin.model.ecologic.EcologicModel;
import com.hallouin.model.ecologic.api.response_pojo.ResponseEcologic;
import com.hallouin.model.ecologic.api.response_pojo.ResponseEcologic.ValidationError;
import com.hallouin.view.DialogsView;
import com.hallouin.view.ecologic.EcologicView;

public class EcologicController implements PropertyChangeListener {
	private EcologicView ecologicView;
	private EcologicModel ecologicModel;
	private DialogsView dialogsView;
	private String[][] tableDatas;
	private Map<Integer	, Bill> billMap;

	public EcologicController(EcologicView ecologicView, DialogsView dialogsView, EcologicModel ecologicModel){
		this.ecologicView = ecologicView;
		this.ecologicModel  = ecologicModel;
		this.dialogsView = dialogsView;
		//ecologicModel.setController(this);
		ecologicView.setController(this);
		start();
	}

	private  void start() {

		String[] columnsName = {"id soutien", "id rembrsmt", "Ma ref.", "Client", "Crée le", "Réparateur", "Statut", "Date de réparation","Actions"};
		ecologicView.setTableColumns(columnsName);

		List<Bill> bills = ecologicModel.getAllClaims();
		tableDatas = computeClaimsDataToTable(bills, columnsName.length);

		ecologicView.setClaimsTableDatas(tableDatas);
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
	private String errorsMessages (Bill bill) {
		String errorString = "Ok";
		List<ValidationError> validationErrors = bill.getEcologicDatas().getErrorsList();
		if (validationErrors != null) {
			errorString = "";
			for (ValidationError validationError : validationErrors) {
				errorString += validationError.getErrorMessage()+"\n";
			}
		}
		return errorString;
	}
	public void selectClaim(String id) {
		int idInt = Integer.valueOf(id);
		ResponseEcologic responseEcologic = ecologicModel.consultClaim(idInt);

		System.out.println("Selected id :"+responseEcologic.getResponseData().getLastStatus());
		Bill testBill = billMap.get(responseEcologic.getResponseData().getClaimId());
		System.out.println("Selected Client :"+testBill.getClient().getName()	);
		ecologicView.updateConsultClaim(testBill);
	}
}
