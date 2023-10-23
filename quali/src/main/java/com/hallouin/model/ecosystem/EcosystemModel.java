package com.hallouin.model.ecosystem;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.gson.Gson;
import com.hallouin.controler.ecosystem.EcosystemController;
import com.hallouin.model.ecosystem.api.EcosystemApi;
import com.hallouin.model.ecosystem.api.response_pojo.ClaimDetails;
import com.hallouin.model.ecosystem.api.response_pojo.ClaimSentInfos;
import com.hallouin.model.ecosystem.api.response_pojo.ClaimsDetailsList;
import com.hallouin.model.ecosystem.api.response_pojo.GetAllClaims;
import com.hallouin.model.ecosystem.api.response_pojo.RqError;

public class EcosystemModel {
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	private EcosystemController ecosystemController;
	private EcosystemApi ecosystemApi;

	ClaimDetails claimDetails;

	public EcosystemModel(EcosystemApi ecosystemApi) {
		super();
		this.ecosystemApi = ecosystemApi;
	}

	// Méthode pour ajouter un PropertyChangeListener au modèle
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    // Méthode pour supprimer un PropertyChangeListener du modèle
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

	public void setController(EcosystemController ecosystemController) {
		this.ecosystemController = ecosystemController;
	}
	
	public GetAllClaims getAllClaims() {
		String jsonResponse = "";
		jsonResponse = ecosystemApi.getDatas("/reimbursement-claims");

		checkForError(jsonResponse);

		Gson gson = new Gson();
    	GetAllClaims createdClaims = gson.fromJson(jsonResponse, GetAllClaims.class);

		return sortClaimsByDate(createdClaims);
	}

	public Boolean deleteClaim(String id) {
		boolean isDeleted = ecosystemApi.deleteClaim(id);
		return isDeleted;
	}

	public ClaimDetails consultClaim(String id, Boolean showClaimContent) {
		String jsonResponse = "";
		jsonResponse = ecosystemApi.getDatas("/reimbursement-claims/"+id);

		Boolean isError = checkForError(jsonResponse);

		if (!isError) {
			Gson gson = new Gson();
			ClaimsDetailsList claimDetailsList = gson.fromJson(jsonResponse, ClaimsDetailsList.class);

			ClaimDetails newClaimDetails = claimDetailsList.getClaimsDetailsList().get(0);

			if (showClaimContent) {
				pcs.firePropertyChange("claimDetails", claimDetails, newClaimDetails); // Notifie les observateurs qu'il y a eu une mise à jour
			}

			this.claimDetails = newClaimDetails;

			return claimDetails;
		} else {
			return null;
		}
	}
	private GetAllClaims sortClaimsByDate (GetAllClaims allClaims) {

		List<ClaimSentInfos> claimsList= new ArrayList<>();
		claimsList = allClaims.getClaimsSentList();
		if (claimsList != null) {
			Collections.sort(claimsList, new Comparator<ClaimSentInfos>() {
	            @Override
	            public int compare(ClaimSentInfos obj1, ClaimSentInfos obj2) {
	                return obj1.getCreationDate().compareTo(obj2.getCreationDate());
	            }
	        });

			Collections.reverse(claimsList);
			allClaims.setClaimsSentList(claimsList);
		}
		return allClaims;
	}

	private Boolean checkForError(String jsonResponse){
		boolean isError = false;
		if (jsonResponse.contains("error")) {
			isError = true;
			Gson gson = new Gson();
			RqError rqError = gson.fromJson(jsonResponse, RqError.class);
			ecosystemController.showSimpleMessage(rqError.getError().getMessage(), rqError.getError().getStatus());
		}
		if (jsonResponse.contains("Anonymize")){
			isError = true;
		}
		return isError;
	}
}
