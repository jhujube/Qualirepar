package com.hallouin.model.ecologic;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.hallouin.controler.ecologic.EcologicController;
import com.hallouin.model.bill.Bill;
import com.hallouin.model.datas.AppDatas;
import com.hallouin.model.ecologic.api.EcologicApi;
import com.hallouin.model.ecologic.api.response_pojo.ResponseEcologic;

public class EcologicModel {
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	private EcologicController ecologicController;
	private EcologicApi ecologicApi;
	private AppDatas appDatas;

	public EcologicModel(AppDatas appDatas) {
		super();
		this.ecologicApi = new EcologicApi(appDatas);
		this.appDatas = appDatas;
	}


	// Méthode pour ajouter un PropertyChangeListener au modèle
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    // Méthode pour supprimer un PropertyChangeListener du modèle
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

	public void setController(EcologicController ecologicController) {
		this.ecologicController = ecologicController;
	}

	public List<Bill> getAllClaims(){
		File directory = new File(appDatas.getDatasPath()+"/ecologic");

		List<Bill> claimsList = new ArrayList<>();

		// Parcourez les fichiers du répertoire
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".bill")) {
                    try (FileInputStream fileInputStream = new FileInputStream(file);
                         ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
                    	try {
                        Bill bill = (Bill) objectInputStream.readObject();
                        claimsList.add(bill);
                    	} catch (ObjectStreamException e) {
                    		System.out.println("Erreur de lecture du fichier :"+file.getName());
                    		e.printStackTrace();
                    	}

                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
		return claimsList;
	}

	public ResponseEcologic consultClaim(int id) {
		String jsonResponse = "";
		Gson gson = new Gson();

		jsonResponse = ecologicApi.checkClaimStatus(id);
		ResponseEcologic response = gson.fromJson(jsonResponse, ResponseEcologic.class);

		if (!response.getIsValid()) {
			jsonResponse = ecologicApi.CheckSupportStatus(id);
		}
		response = gson.fromJson(jsonResponse, ResponseEcologic.class);

		return response;
	}
	private Boolean checkForError(String jsonResponse){
		Gson gson = new Gson();
		ResponseEcologic response = gson.fromJson(jsonResponse, ResponseEcologic.class);

		return response.getIsValid();
	}
}
