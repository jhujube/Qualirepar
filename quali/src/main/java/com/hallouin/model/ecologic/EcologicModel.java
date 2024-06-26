package com.hallouin.model.ecologic;

import java.awt.image.DataBufferShort;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.gson.Gson;
import com.hallouin.controler.ecologic.EcologicController;
import com.hallouin.model.bill.Bill;
import com.hallouin.model.datas.AppDatas;
import com.hallouin.model.ecologic.api.EcologicApi;
import com.hallouin.model.ecologic.api.response_pojo.ResponseEcologic;
import com.hallouin.model.ecologic.api.response_pojo.ResponseEcologic.ValidationError;

public class EcologicModel {
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	private EcologicController ecologicController;
	private EcologicApi ecologicApi;
	private AppDatas appDatas;
	private List<ResponseEcologic> claimsStateEcologic;

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
	public String[][] loadClaims(){
		
		List<String[]> dataList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader((appDatas.getDatasPath()+"/ecologic/"+"ecologicClaims.txt")))) {
        	String line;
        	while ((line = reader.readLine()) != null) {
                String[] elements = line.split(",");
                dataList.add(elements);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // classement des éléments suivant l'ordre décroissant des id
        Collections.sort(dataList, new Comparator<String[]>() {
            @Override
            public int compare(String[] o1, String[] o2) {
                return o2[0].compareTo(o1[0]);
            }
        });
        
        // Convert the list to a 2D array
        String[][] dataArray = new String[dataList.size()][];
        dataList.toArray(dataArray);
        
     // Print the 2D array to verify the result
        for (int i = 0; i < dataArray.length; i++) {
            for (int j = 0; j < dataArray[i].length; j++) {
                System.out.print(dataArray[i][j] + " ");
            }
            System.out.println();
        }

    
		return dataArray;
	}
	
	public Boolean saveClaims(String[][] claims) {	// Définissez le nom du fichier dans lequel vous souhaitez enregistrer l'objet
		Boolean state = true;
		String claimsToWriteString = "";
		
		for (int i=0 ; i < claims.length ; i++) {
			for (int j=0 ; j < claims[i].length ; j++) {
				claimsToWriteString += claims[i][j];
				if ( j < claims[i].length - 1)
					claimsToWriteString += ",";
				else 
					claimsToWriteString += "\n";
			}
		}
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(appDatas.getDatasPath()+"/ecologic/"+"ecologicClaims.txt"))) {
			writer.write(claimsToWriteString);
        } catch (IOException e) {
            e.printStackTrace();
            state = false;
        }
		return state;
	}
	
	public String[][] updateClaimsStatus(String[][] tableDatas){
		claimsStateEcologic = new ArrayList<>();
		
		int claimsCount = tableDatas.length;
		for (int i = 0; i < claimsCount ; i++) {
			Integer claimIdInteger = Integer.parseInt(tableDatas[i][0]);
			if (!tableDatas[i][5].contains("Clôturée")) {
				ResponseEcologic claimInfosEcologic = consultClaim(claimIdInteger);
				String claimStatuString = claimInfosEcologic.getResponseData().getLastStatus();
				tableDatas[i][5] = claimStatuString;
				claimsStateEcologic.add(claimInfosEcologic);
			}
		}
		
		return tableDatas;
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
	
	public String[][] getValidationErrors(int id) {
		
		List<ValidationError> validationsErrors = new ArrayList<>();
		for (ResponseEcologic responseEcologic : claimsStateEcologic) {
			if (responseEcologic.getResponseData().getClaimId() == id) {
				validationsErrors = responseEcologic.getResponseData().getValidationsErrorsList();
			}
		}
		
		if (validationsErrors != null) {
			String[][] errorsList = new String [validationsErrors.size()][2];
			int err=0;
			for (ValidationError error : validationsErrors) {
				errorsList[err][0] = error.getField();
				errorsList[err++][1] = error.getErrorMessage();
			}
			return errorsList;
		}
		else {
			String[][] errorsList = {{"","Ras"}};
			return errorsList;
		}
		
	}
	
	private Boolean checkForError(String jsonResponse){
		Gson gson = new Gson();
		ResponseEcologic response = gson.fromJson(jsonResponse, ResponseEcologic.class);

		return response.getIsValid();
	}
	
}
