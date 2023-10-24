package com.hallouin.model.ecosystem.api;

import java.beans.PropertyChangeSupport;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.text.View;

import com.google.gson.Gson;
import com.hallouin.controler.claim.ClaimController;
import com.hallouin.model.bill.Bill;
import com.hallouin.model.bill.FileInformations;
import com.hallouin.model.bill.SparePart;
import com.hallouin.model.claim.ClaimModel;
import com.hallouin.model.ecosystem.api.request_pojo.AmountCurency;
import com.hallouin.model.ecosystem.api.request_pojo.Customer;
import com.hallouin.model.ecosystem.api.request_pojo.EcosystemBill;
import com.hallouin.model.ecosystem.api.request_pojo.NewClaim;
import com.hallouin.model.ecosystem.api.request_pojo.NewClaim.Spares;
import com.hallouin.model.ecosystem.api.request_pojo.Product;
import com.hallouin.model.ecosystem.api.response_pojo.CreatedClaim;
import com.hallouin.model.ecosystem.api.response_pojo.RqError;
import com.hallouin.model.ecosystem.api.response_pojo.UploadFileUrl;

import okio.Options;


public class SendEcosystemInvoices {
	private EcosystemApi ecosystemApi;
	private ClaimModel claimModel;
	private ClaimController claimController;
	private PropertyChangeSupport pcs;

	public SendEcosystemInvoices(PropertyChangeSupport pcs, EcosystemApi ecosystemApi, ClaimModel claimModel, ClaimController claimController) {
		this.ecosystemApi = ecosystemApi;
		this.claimModel = claimModel;
		this.claimController = claimController;
		this.pcs = pcs;
	}

	public List<Bill> Send (List<Bill> billsToSendList){	
		List<Bill> billWithErrors = new ArrayList<>();
		int claimNumber = 0;
		for(Bill bill : billsToSendList) {
			claimNumber++;
			pcs.firePropertyChange("sendStateTitle", null, "Demande N°"+claimNumber+":"); // Notifie les observateurs qu'il y a eu une mise à jour
			pcs.firePropertyChange("sendStateLabel", null, "Création de la demande"); // Notifie les observateurs qu'il y a eu une mise à jour
			
			NewClaim newClaim = createEcosystemClaim(bill);
			String jsonResponse = ecosystemApi.postNewClaim(newClaim);
			claimModel.isErrorReturnEcosys(jsonResponse);
			System.out.println("Réponse :"+jsonResponse);
			Boolean isOk = false;
			bill = updateClaimInfos(bill, jsonResponse);
			if (bill.getEcosystemDatas().getReimbursementClaimID() != "") {
				pcs.firePropertyChange("sendStateLabel", null, "Envoi des fichiers"); // Notifie les observateurs qu'il y a eu une mise à jour

				for(FileInformations fileInfo : bill.getFilesInformationsList()) {
					jsonResponse = ecosystemApi.postUploadRequest(bill.getEcosystemDatas().getReimbursementClaimID(), fileInfo);
					claimModel.isErrorReturnEcosys(jsonResponse);
					String uploadUrl = getUploadUrl(jsonResponse);
					if (uploadUrl != null)
						isOk = ecosystemApi.putUploadFile(uploadUrl, fileInfo);
					else
						break;
				}

			}

			if (isOk) {
				pcs.firePropertyChange("sendStateLabel", null, "Finalisation de la demande"); // Notifie les observateurs qu'il y a eu une mise à jour
				jsonResponse = ecosystemApi.postConfirmClaim(bill.getEcosystemDatas().getReimbursementClaimID());
				Boolean error = claimModel.isErrorReturnEcosys(jsonResponse);
				if (error) {
					pcs.firePropertyChange("sendStateLabel", null, "Non finalisé"); // Notifie les observateurs qu'il y a eu une mise à jour
					billWithErrors.add(bill);
				//showConfirmClaimResult(jsonResponse);
				}
			}
			if (!bill.getEcosystemDatas().getCorrectlySent()){
				pcs.firePropertyChange("sendStateLabel", null, "Problème lors de l'envoi"); // Notifie les observateurs qu'il y a eu une mise à jour
				billWithErrors.add(bill);
			} else {
				pcs.firePropertyChange("sendStateLabel", null, "Envoi effectué"); // Notifie les observateurs qu'il y a eu une mise à jour
			}

		}

		//System.out.println("Toutes les demandes ont été envoyées");
		return billWithErrors;
	}

	private NewClaim createEcosystemClaim (Bill bill){
		String repairDate = createRepairDate(bill.getBillInfos().getRepairDate());
		String purchaseOrderByCustomer = bill.getClient().getName()+" "+bill.getBillInfos().getBillNumber();
		String repairPlaceID = getRepairPlaceId(bill);
		String repairerID = bill.getEcosystemDatas().getPartner().getRepairerID();

		String productId = bill.getDevice().getProductId();
		String brandId = bill.getDevice().getBrandId();
		String manualBrandName = bill.getDevice().getManualBrandName();
		String ProductIdentificationNumber = bill.getDevice().getSerial();
		String repairTypeCode = bill.getDevice().getIrisSectionCode();
		String commercialReference = bill.getDevice().getReference();
		Product product = new Product (productId, brandId, ProductIdentificationNumber, repairTypeCode, commercialReference);

		//List<Spare> spareList = createSparesList(bill.getSparesList());
		Spares spares = createTotalSpares(bill.getSparesList());

		String title = bill.getClient().getGender();
		String lastName = bill.getClient().getName();
		String firstName = bill.getClient().getFirstName();
		String phoneNumber = bill.getClient().getPhone();
		String streetLine1 = bill.getClient().getStreetNumber()+" "+bill.getClient().getStreet();
		String postalCode = bill.getClient().getZipCode();
		String city = bill.getClient().getTown();
		String email = bill.getClient().getMail();
		Customer customer = new Customer (title, lastName, firstName, phoneNumber, streetLine1, postalCode, city, email);

		EcosystemBill ecosystemBill = createEcosystemBill(bill);

		NewClaim newClaim = new NewClaim(repairDate, purchaseOrderByCustomer, repairPlaceID, repairerID,
			 product, customer,	ecosystemBill);

		if (bill.getDevice().getBrandId().contentEquals("0000"))
			newClaim.getProduct().setPartnerProduct(manualBrandName);

		if (bill.getInvoice().getTotalSparesExclVat() > 0.0 )
			newClaim.setSpares(spares);
			//newClaim.setSparePartsList(spareList);

		return newClaim;
	}

	private String createRepairDate(String repairDate) {

		System.out.println(repairDate);
        String outputFormat = "yyyy-MM-dd";

        // Utilisation de SimpleDateFormat pour parser la date d'entrée
        SimpleDateFormat inputFormatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date;
        try {
            date = inputFormatter.parse(repairDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

        // Conversion de java.util.Date vers java.time.LocalDate
        LocalDate localDate = date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();

        // Formatage de la LocalDate dans le format de sortie désiré
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(outputFormat);
        String outputDate = localDate.format(outputFormatter);

        System.out.println(outputDate);
		return outputDate;
	}
	private String getRepairPlaceId(Bill bill) {
		System.out.println(bill.getEcosystemDatas().getPartner().getRepairerID());
		String repairPlaceId = bill.getEcosystemDatas().getPartner().getRepairerID();
		if (bill.getDisplacement().getQty()>0.0)
			repairPlaceId = bill.getEcosystemDatas().getPartner().getRepairPlacesList().get(0).getRepairPlaceID();
		return repairPlaceId;
	}
	private Bill updateClaimInfos(Bill bill, String jsonResponse) {
		Gson gson = new Gson();

		if (jsonResponse.contains("error")) {
	    	RqError error = gson.fromJson(jsonResponse, RqError.class);
			bill.getEcosystemDatas().setErrorContent(error.getError());
			bill.getEcosystemDatas().setCorrectlySent(false);
			//view.simpleMessage(error.getError().getMessage(), error.getError().getCode()+" :"+ error.getError().getStatus());
		} else {
			CreatedClaim createdClaim = gson.fromJson(jsonResponse, CreatedClaim.class);
    		bill.getEcosystemDatas().setRequestStatus(createdClaim.getRequestStatus());
	    	bill.getEcosystemDatas().setReimbursementClaimID(createdClaim.getReimbursementClaimID());
	    	bill.getEcosystemDatas().setPurchaseOrderByCustomer(createdClaim.getPurchaseOrderByCustomer());
	    	bill.getEcosystemDatas().setCorrectlySent(true);
	    	//view.simpleMessage(createdClaim.getRequestStatus(), "Etat de la demande");
		}
    	//System.out.println("createdClaim :"+createdClaim.getReimbursementClaimID());

    	return bill;

    }
	private String getUploadUrl(String jsonResponse) {
		Gson gson = new Gson();
    	UploadFileUrl url = gson.fromJson(jsonResponse, UploadFileUrl.class);

    	if (url.getUrl() != null)
    		return url.getUrl();
    	else {
    		claimModel.isErrorReturnEcosys(jsonResponse);
    		return null;
    	}
	}
	/*
	private List<Spare> createSparesList(List<SparePart> sparesList){
		List<Spare> sparesListRequest = new ArrayList<>();
		for (SparePart sparePart : sparesList) {
			Spare spare = new Spare ("Neuve", sparePart.getRef(), sparePart.getQty());
			sparesListRequest.add(spare);
		}
		return sparesListRequest;
	}
	*/
	private Spares createTotalSpares(List<SparePart> sparesList){
		Double nbSpares = 0.0;
		for (SparePart sparePart : sparesList) {
			nbSpares += sparePart.getQty();
		}
		return new Spares(nbSpares, 0.0);
	}
	private EcosystemBill createEcosystemBill(Bill bill) {
		//AmountCurency amountBeforeTax = new AmountCurency(bill.getInvoice().getTotalExclVat());
		//AmountCurency laborCost = new AmountCurency(bill.getLabour().getTotalExclVat());
		AmountCurency sparePartsCost;
		//AmountCurency travelExpenses;
		AmountCurency totalAmountInclVAT = new AmountCurency(bill.getInvoice().getTotalVat());
		AmountCurency amountCovered = new AmountCurency(bill.getInvoice().getSupportAmount());
		//EcosystemBill ecosystemBill = new EcosystemBill(amountBeforeTax, laborCost, totalAmountInclVAT, amountCovered);
		EcosystemBill ecosystemBill = new EcosystemBill(totalAmountInclVAT, amountCovered);
		/*
		if (bill.getDisplacement().getTotalExclVat() > 0.0) {
			travelExpenses = new AmountCurency(bill.getDisplacement().getTotalExclVat());
			ecosystemBill.setTravelExpenses(travelExpenses);
		}
		*/
		if (bill.getInvoice().getTotalSparesExclVat() > 0.0 ) {
			sparePartsCost = new AmountCurency(bill.getInvoice().getTotalSparesExclVat());
			ecosystemBill.setSparePartsCost(sparePartsCost);
		}
		return ecosystemBill;
	}
}
