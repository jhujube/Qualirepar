package com.hallouin.model.ecologic.api;

import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.hallouin.model.bill.Bill;
import com.hallouin.model.bill.FileInformations;
import com.hallouin.model.bill.SparePart;
import com.hallouin.model.claim.ClaimModel;
import com.hallouin.model.datas.AppDatas;
import com.hallouin.model.ecologic.api.pojo.SupportRequest;
import com.hallouin.model.ecologic.api.pojo.SupportRequest.Consumer;
import com.hallouin.model.ecologic.api.pojo.SupportRequest.Cost;
import com.hallouin.model.ecologic.api.pojo.SupportRequest.Product;
import com.hallouin.model.ecologic.api.pojo.SupportRequest.Quote;
import com.hallouin.model.ecologic.api.pojo.SupportRequest.SparePartEcl;
import com.hallouin.model.ecologic.api.response_pojo.ResponseEcologic;
import com.hallouin.model.ecologic.api.response_pojo.ResponseEcologic.ValidationError;

public class SendEcologicInvoices {
	private EcologicApi ecologicApi;
	private ClaimModel claimModel;
	private AppDatas appDatas;
	private PropertyChangeSupport pcs;

	public SendEcologicInvoices(PropertyChangeSupport pcs,EcologicApi ecologicApi, ClaimModel claimModel,AppDatas appDatas) {
		this.ecologicApi = ecologicApi;
		this.claimModel = claimModel;
		this.appDatas = appDatas;
		this.pcs = pcs;
	}

	public List<Bill> Send (List<Bill> billsToSendList){
		List<Bill> billWithErrors = new ArrayList<>();
		int claimNumber = 0;
		for(Bill bill : billsToSendList) {
			claimNumber++;
			List<ValidationError> errorsList;
			//  Creation de la demande de soutien
			pcs.firePropertyChange("sendStateTitle", null, "Demande N°"+claimNumber+":"); // Notifie les observateurs qu'il y a eu une mise à jour
			pcs.firePropertyChange("sendStateLabel", null, "Création de la demande de soutien"); // Notifie les observateurs qu'il y a eu une mise à jour

			String jsonResponse = ecologicApi.createRequest(bill);
			bill = updateRequestInfos(bill, jsonResponse);
			errorsList = errorsReturn(jsonResponse);
			claimModel.showErrorsReturnEcolo(errorsList);

			System.out.println("Json create request :"+jsonResponse);

			// Si ok
			if (bill.getEcologicDatas().getIsRequestCreated() && bill.getEcologicDatas().getErrorsList()==null) {

				// Création de la demande de rbsmt
				pcs.firePropertyChange("sendStateLabel", null, "Création de la demande de remboursement"); // Notifie les observateurs qu'il y a eu une mise à jour
				jsonResponse = ecologicApi.createClaim(bill);
				bill = updateClaimInfos(bill, jsonResponse);
				errorsList = errorsReturn(jsonResponse);
				// filtrage des erreurs "Des pièces jointes sont requises..." qui sont présentes systématiquement
				errorsList = filterFilesErrors(errorsList);
				claimModel.showErrorsReturnEcolo(errorsList);
				System.out.println("Json create claim :"+jsonResponse);

				// Envoi ses fichiers
				pcs.firePropertyChange("sendStateLabel", null, "Envoi des fichiers"); // Notifie les observateurs qu'il y a eu une mise à jour
				jsonResponse = sendFiles(bill.getFilesInformationsList(),bill.getEcologicDatas().getClaimId());
				System.out.println("Json send files:"+jsonResponse);

				// Soumission de la demande de rbsmt
				pcs.firePropertyChange("sendStateLabel", null, "Finalisation de la demande"); // Notifie les observateurs qu'il y a eu une mise à jour
				jsonResponse = ecologicApi.submitClaim(bill.getEcologicDatas().getClaimId());
				bill = updateSubmitClaimInfos(bill, jsonResponse);
				System.out.println("Json submit claim:"+jsonResponse);
				//errorsList = submitClaimErrors(jsonResponse);
				//claimModel.showErrorsReturnEcolo(errorsList);
				pcs.firePropertyChange("sendStateLabel", null, "Envoi effectué"); // Notifie les observateurs qu'il y a eu une mise à jour


			} else {
				billWithErrors.add(bill);
			}
			saveBill(bill);

		}

		return billWithErrors;
	}

	private SupportRequest createSupportRequest (Bill bill){
		LocalDate currentDate = LocalDate.now();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String callDate = currentDate.format(formatter);
		String quoteNumber = bill.getBillInfos().getBillNumber();
		String repairSiteID = bill.getEcologicDatas().getRepairSite().getSiteId();

		String title = bill.getClient().getGender();
		int titleEcologic = calculateTitle(title);
		String lastName = bill.getClient().getName();
		String firstName = bill.getClient().getFirstName();
		String streetNumber = bill.getClient().getStreetNumber();
		String streetLine1 = bill.getClient().getStreet();
		String zipCode = bill.getClient().getZipCode();
		String city = bill.getClient().getTown();
		String phoneNumber = bill.getClient().getPhone();
		String email = bill.getClient().getMail();

		Consumer consumer = new Consumer(titleEcologic, lastName, firstName, streetNumber, streetLine1, zipCode, city, phoneNumber);
		if (!email.isEmpty())
			consumer.setEmail(email);

		String productId = bill.getDevice().getProductId();
		String brandId = bill.getDevice().getBrandId();
		String commercialReference = bill.getDevice().getReference();
		//String manualBrandName = bill.getDevice().getManualBrandName();
		String productIdentificationNumber = bill.getDevice().getSerial();
		String iRISSymptom = bill.getDevice().getIrisSymptomCode();
		String iRISSection = bill.getDevice().getIrisSectionCode();
		String failureDescriptionString ="Symptome Iris"+iRISSymptom;

		Product product = new Product(productId, brandId, commercialReference, productIdentificationNumber, iRISSymptom, iRISSection,failureDescriptionString);

		Cost laborCost = new Cost(bill.getLabour().getTotalExclVat());
		Cost sparePartsCost = new Cost(bill.getInvoice().getTotalSparesExclVat());
		Cost travelCost = new Cost(bill.getDisplacement().getTotalExclVat());
		Cost totalAmountCostExclVat = new Cost(bill.getInvoice().getTotalExclVat());
		Cost totalAmountCostVat = new Cost(bill.getInvoice().getTotalVat());
		Cost supportCost = new Cost(bill.getInvoice().getSupportAmount());

		Quote quote = new Quote(laborCost, sparePartsCost, travelCost, totalAmountCostExclVat, totalAmountCostVat, supportCost);

		List<SparePartEcl> spareList = createSparesList(bill.getSparesList());

		SupportRequest supportRequest = new SupportRequest(consumer, product, quote);
		if (bill.getInvoice().getTotalSparesExclVat() > 0.0 )
			supportRequest.setSpareParts(spareList);

		return supportRequest;
	}
	private int calculateTitle (String title) {
		int titleEcologic = 1;
		if (title.contains("Monsieur"))
			titleEcologic = 3;
		if (title.contains("Madame"))
			titleEcologic = 2;
		if (title.contains("Société"))
			titleEcologic = 4;
		return titleEcologic;
	}
	private List<SparePartEcl> createSparesList(List<SparePart> sparesList){
		List<SparePartEcl> sparesListRequest = new ArrayList<>();
		for (SparePart sparePart : sparesList) {
			SparePartEcl spare = new SparePartEcl(sparePart.getRef(), sparePart.getQty());
			sparesListRequest.add(spare);
		}
		return sparesListRequest;
	}
	private Bill updateRequestInfos(Bill bill, String jsonResponse) {
		Gson gson = new Gson();
		ResponseEcologic response = gson.fromJson(jsonResponse, ResponseEcologic.class);

		Boolean isRequestCreated = response.getIsValid();
		bill.getEcologicDatas().setIsRequestCreated(isRequestCreated);

		if (isRequestCreated) {
			bill.getEcologicDatas().setRequestId(response.getResponseData().getRequestId());
			if (!response.getResponseData().getIsValid())
				bill.getEcologicDatas().setValidationErrorsList(response.getResponseData().getValidationsErrorsList());
		}
    	//System.out.println("createdClaim :"+createdClaim.getReimbursementClaimID());

    	return bill;
    }
	private Bill updateClaimInfos(Bill bill, String jsonResponse) {
		Gson gson = new Gson();
		ResponseEcologic response = gson.fromJson(jsonResponse, ResponseEcologic.class);

		Boolean isClaimCreated= response.getIsValid();
		bill.getEcologicDatas().setIsClaimCreated(isClaimCreated);

		if (isClaimCreated) {
			bill.getEcologicDatas().setClaimId(response.getResponseData().getClaimId());
			if (!response.getResponseData().getIsValid())
				bill.getEcologicDatas().setValidationErrorsList(response.getResponseData().getValidationsErrorsList());
		}
    	//System.out.println("createdClaim :"+createdClaim.getReimbursementClaimID());

    	return bill;

    }
	private Bill updateSubmitClaimInfos(Bill bill, String jsonResponse) {
		Gson gson = new Gson();
		ResponseEcologic response = gson.fromJson(jsonResponse, ResponseEcologic.class);
		bill.getEcologicDatas().setLastStatus(response.getResponseData().getLastStatus());

    	return bill;

    }
	private String sendFiles(List<FileInformations> fileInformationsList,int claimId) {
		String jsonResponse = "";
		for (FileInformations fileInformations : fileInformationsList) {
			File fileToSend = new File (fileInformations.getPath());

			String fileType = fileInformations.getType();
			switch (fileType) {
			case "invoice":
				fileType = "INVOICE";
				break;
			case "serial_tag":
				fileType = "NAMEPLATE";
				break;
			case "device_picture":
				fileType = "PRODUCTPICTURE";
				break;
			case "certificate_client":
				fileType = "CONSUMERVALIDATION";
				break;
			default:
				break;
			}

			try {
				System.out.println("claimId :"+claimId+"/"+"fileType :"+fileType);
				jsonResponse = ecologicApi.attachFile(claimId, fileToSend, fileType);
				System.out.println("rep :"+jsonResponse);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return jsonResponse;
	}
	private List<ValidationError> errorsReturn(String jsonResponse){

		List<ValidationError> errorsList = new ArrayList<>();

		Gson gson = new Gson();
		ResponseEcologic rqError = gson.fromJson(jsonResponse, ResponseEcologic.class);

		// Gestion des erreurs Ecologic
		if (!rqError.getResponseData().getIsValid()) {

			for (ValidationError validationError : rqError.getResponseData().getValidationsErrorsList()) {
				errorsList.add(validationError);
			}
		}

		return errorsList;
	}
	private List<ValidationError> submitClaimErrors(String jsonResponse){

		List<ValidationError> errorsList = new ArrayList<>();

		Gson gson = new Gson();
		ResponseEcologic rqError = gson.fromJson(jsonResponse, ResponseEcologic.class);

		// Gestion des erreurs Ecologic
		if (!rqError.getResponseData().getIsValid()) {

			// recup de l'id de la demande et getClaimstatus sur cette demande précise pour récuperer la liste d'erreurs
			int claimId = rqError.getResponseData().getClaimId();
			jsonResponse = ecologicApi.checkClaimStatus(claimId);
			rqError = gson.fromJson(jsonResponse, ResponseEcologic.class);

			if (rqError != null) {
				for (ValidationError validationError : rqError.getResponseData().getValidationsErrorsList()) {
					errorsList.add(validationError);
				}
			}
		}

		return errorsList;
	}

	private List<ValidationError> filterFilesErrors(List<ValidationError> errorsList){
		Iterator<ValidationError> iterator = errorsList.iterator();

	    while (iterator.hasNext()) {
	        ValidationError validationError = iterator.next();

			if (validationError.getErrorMessage().contains("Des pièces jointes sont requises")){
				iterator.remove();
			}
		}
		return errorsList;
	}

	private boolean saveBill (Bill bill) {
		boolean state = true;

        // Définissez le nom du fichier dans lequel vous souhaitez enregistrer l'objet
        String nomFichier = bill.getBillInfos().getBillNumber()+".bill";

        try {
            // Créez un flux de sortie de fichier
            FileOutputStream fichierSortie = new FileOutputStream(appDatas.getDatasPath()+"/ecologic/"+nomFichier);

            // Créez un ObjectOutputStream pour écrire l'objet dans le fichier
            ObjectOutputStream objOutStream = new ObjectOutputStream(fichierSortie);

            // Écrivez l'objet dans le fichier
            objOutStream.writeObject(bill);

            // Fermez le flux de sortie
            objOutStream.close();

            System.out.println("L'objet a été enregistré dans le fichier " + nomFichier);
        } catch (IOException e) {
            e.printStackTrace();
            state = false;
        }

		return state;
	}
}
