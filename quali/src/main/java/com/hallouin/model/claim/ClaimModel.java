package com.hallouin.model.claim;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.hallouin.controler.claim.ClaimController;
import com.hallouin.model.bill.Bill;
import com.hallouin.model.bill.FileInformations;
import com.hallouin.model.datas.AppDatas;
import com.hallouin.model.ecologic.api.EcologicApi;
import com.hallouin.model.ecologic.api.SendEcologicInvoices;
import com.hallouin.model.ecologic.api.pojo.BrandEcologic;
import com.hallouin.model.ecologic.api.pojo.CodeLabel;
import com.hallouin.model.ecologic.api.pojo.LabeledProductType;
import com.hallouin.model.ecologic.api.pojo.RepairSite;
import com.hallouin.model.ecologic.api.response_pojo.ResponseEcologic;
import com.hallouin.model.ecologic.api.response_pojo.ResponseEcologic.ValidationError;
import com.hallouin.model.ecosystem.api.EcosystemApi;
import com.hallouin.model.ecosystem.api.SendEcosystemInvoices;
import com.hallouin.model.ecosystem.api.pojo.BrandEcosystem;
import com.hallouin.model.ecosystem.api.pojo.Product;
import com.hallouin.model.ecosystem.api.request_pojo.partners.Partner;
import com.hallouin.model.ecosystem.api.response_pojo.RepairCodeEcosystem;
import com.hallouin.model.ecosystem.api.response_pojo.RqError;
import com.hallouin.model.ecosystem.pojo.Id_Name;

public class ClaimModel {
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	private AppDatas appDatas;
	private ClaimController claimController;
	private EcosystemApi ecosystemApi;
	private List<Product> productsList;
	private List<Id_Name> idPartnersList;
	private List<FileInformations> filesInformationsList;
	private List<Bill> billsToSendList;


	private Product selectedProduct;
	private String selectedBrandId;
	private String selectedBrandName;
	private String selectedProductId;
	private String ecoorganism;
	private Id_Name partnerId;

	public ClaimModel(AppDatas appDatas,EcosystemApi ecosystemApi) {
		super();
		this.appDatas = appDatas;
		this.ecosystemApi = ecosystemApi;
		this.productsList = new ArrayList<>();
		this.filesInformationsList = new ArrayList<>();
		this.billsToSendList = new ArrayList<>();
	}

	public void setController(ClaimController claimController) {
		this.claimController = claimController;
	}

	// Méthode pour ajouter un PropertyChangeListener au modèle
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }
    // Méthode pour supprimer un PropertyChangeListener du modèle
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

	public void setProductsList() {
		productsList = appDatas.getEcosystemProducts();
		pcs.firePropertyChange("productsList", null, productsList); // Notifie les observateurs qu'il y a eu une mise à jour
	}

	public void selectedProduct(Product product) {
		Product newSelectedProduct = product;
		String newEcoorganism = "";

		// La connaissance du produit permet de connaitre la valeur du remboursement
		String newAmount = product.getAmountsList().get(0).getAmount();

		pcs.firePropertyChange("ecsBrandsList", selectedProduct, newSelectedProduct.getBrandsList()); // Notifie les observateurs qu'il y a eu une mise à jour
		pcs.firePropertyChange("amount", null, newAmount); // Notifie les observateurs qu'il y a eu une mise à jour
		pcs.firePropertyChange("ecoorganism",ecoorganism,newEcoorganism);

		selectedProduct = newSelectedProduct;
		ecoorganism = newEcoorganism;
		updateRepairerList(ecoorganism);
		updateFilesButtonsList(ecoorganism);
		claimController.enableFields(true);
	}

	public void selectedBrandEcosystem (BrandEcosystem brandEcosystem) {
		selectedBrandId = "";
		String newEcoorganism = "";
		try {
			selectedBrandName = brandEcosystem.getBrandName();
			if (brandEcosystem.getIsEcosystemBrand()) {		// quel ecoorganism est associé à cette marque?
				newEcoorganism = "Ecosystem";
				selectedBrandId = brandEcosystem.getBrandId();
				selectedProductId = selectedProduct.getProductID();
			} else {
				newEcoorganism = "Ecologic";
				selectedBrandId = findEcologicBrandId(selectedBrandName);
				selectedProductId = findEcologicProductId(selectedProduct.getProductName());
			}
		}
		catch(NullPointerException e) {
			claimController.showSimpleMessage("pas de correspondance marque sur facture / marque ecoorganisme", "Erreur !");
			System.exit(0);
		}

		pcs.firePropertyChange("ecoorganism", ecoorganism, newEcoorganism); // Notifie les observateurs qu'il y a eu une mise à jour
		ecoorganism = newEcoorganism;

		updateIrisCodesList(selectedProduct.getProductID());
		updateRepairerList(ecoorganism);
		updateFilesButtonsList(ecoorganism);
	}

	public void fillClaimWithInfosFromBill(Bill bill) {
		selectedBrandEcosystem(bill.getDevice().getBrand());
		pcs.firePropertyChange("infosClient", null, bill.getClient()); // Notifie les observateurs qu'il y a eu une mise à jour
		pcs.firePropertyChange("infosDevice", null, bill.getDevice()); // Notifie les observateurs qu'il y a eu une mise à jour
		pcs.firePropertyChange("infosBill", null, bill.getBillInfos()); // Notifie les observateurs qu'il y a eu une mise à jour
		pcs.firePropertyChange("infosSpares", null, bill); // Notifie les observateurs qu'il y a eu une mise à jour

	}

	private void updateRepairerList(String ecoorganism) {
		// mise à jour du partenaire réparateur en fct de l'ecoorganisme concerné
				List<Id_Name> newIdPartnersList = new ArrayList<>();
				if (ecoorganism.equals("")) {
					newIdPartnersList.add(new Id_Name("",""));
				}
				if (ecoorganism.equals("Ecosystem")){
					for (Partner partner : appDatas.getEcosystemPartners()) {
						newIdPartnersList.add(new Id_Name(partner.getRepairerID(),partner.getCorporateName()));
					}
				}
				else if (ecoorganism.equals("Ecologic")) {
					for (RepairSite repairSite : appDatas.getRepairSitesList()) {
						newIdPartnersList.add(new Id_Name(repairSite.getSiteId(),repairSite.getName()));
					}
				}
				pcs.firePropertyChange("partnersList", idPartnersList, newIdPartnersList ); // Notifie les observateurs qu'il y a eu une mise à jour
				idPartnersList = newIdPartnersList;
	}

	private void updateFilesButtonsList(String ecoorganism) {
		List<Boolean> buttonsStatesBooleans = new ArrayList<>();
		if (ecoorganism.contentEquals("Ecosystem")) {
			buttonsStatesBooleans.add(true);
			buttonsStatesBooleans.add(true);
			buttonsStatesBooleans.add(true);
			buttonsStatesBooleans.add(false);
		}else if (ecoorganism.contentEquals("Ecologic")) {
			buttonsStatesBooleans.add(true);
			buttonsStatesBooleans.add(true);
			buttonsStatesBooleans.add(false);
			buttonsStatesBooleans.add(true);
		}else {
			buttonsStatesBooleans.add(false);
			buttonsStatesBooleans.add(false);
			buttonsStatesBooleans.add(false);
			buttonsStatesBooleans.add(false);
		}
		pcs.firePropertyChange("filesButtonsStates", null, buttonsStatesBooleans); // Notifie les observateurs qu'il y a eu une mise à jour

	}

	private String findEcologicBrandId (String brandName) {

		String brandId = "";
		for(BrandEcologic brandEcologic : appDatas.getBrandsList()) {
			if (brandEcologic.getBrandName().contentEquals(brandName)) {
				brandId = brandEcologic.getBrandId();
				break;
			}
		}
		return brandId;
	}
	private String findEcologicProductId (String productName) {

		String productId = "";
		for(LabeledProductType labeledProductType : appDatas.getLabeledProductTypeList()) {
			if (labeledProductType.getProductName().contentEquals(productName)) {
				productId = labeledProductType.getProductId();
				break;
			}
		}
		return productId;
	}

	private void updateIrisCodesList(String productId){
		List<Id_Name> irisSectionCodesList = new ArrayList<>();
		List<Id_Name> irisSymptomsCodesList = new ArrayList<>();

		if (ecoorganism.contentEquals("Ecosystem")) {
			for(RepairCodeEcosystem repairCode : selectedProduct.getRepairCodesList()) {
				irisSectionCodesList.add(new Id_Name(repairCode.getCode(), repairCode.getDescription()));
			}
		} else {
			// on retrouve le produit chez Ecologic par comparaison de nom et on extrait sa liste de codes irisSection
			List<CodeLabel> codesLabelList = null;
			List<CodeLabel> iRISSymtomsList = null;
			for(LabeledProductType labeledProductType : appDatas.getLabeledProductTypeList()) {
				if (selectedProduct.getProductName().contentEquals(labeledProductType.getProductName())) {
					codesLabelList = labeledProductType.getRepairCodes();
					iRISSymtomsList = labeledProductType.getiRISSymtoms();
					break;
				}
			}
			for (CodeLabel codeLabel : codesLabelList) {
				irisSectionCodesList.add(new Id_Name(codeLabel.getCode(),codeLabel.getLabel()));
			}
			for (CodeLabel codeLabel : iRISSymtomsList) {
				irisSymptomsCodesList.add(new Id_Name(codeLabel.getCode(),codeLabel.getLabel()));
			}
			pcs.firePropertyChange("irisSymptomsList", null, irisSymptomsCodesList); // Notifie les observateurs qu'il y a eu une mise à jour

		}

		pcs.firePropertyChange("irisSectionsList", null, irisSectionCodesList); // Notifie les observateurs qu'il y a eu une mise à jour
	}

	public void addFileToSend(FileInformations fileInformations) {
		if (filesInformationsList == null) {
            filesInformationsList = new ArrayList<>();
        }

        boolean found = false;
        for (int i = 0; i < filesInformationsList.size(); i++) {
            if (filesInformationsList.get(i).getType().equals(fileInformations.getType())) {
                filesInformationsList.set(i, fileInformations);
                found = true;
                break;
            }
        }

        if (!found) {
            filesInformationsList.add(fileInformations);
        }

        pcs.firePropertyChange("fileToUpload", null, fileInformations); // Notifie les observateurs qu'il y a eu une mise à jour
	}

	public void addBillToSend (Bill bill) {
		bill.setFilesInformationsList(filesInformationsList);
		bill.getDevice().setBrandId(selectedBrandId);
		bill.getDevice().setBrandName(selectedBrandName);
		bill.getDevice().setProductId(selectedProductId);
		bill.getDevice().setProductName(selectedProduct.getProductName());
		bill.getDevice().setSymptom("Code IRIS Symptome "+bill.getDevice().getIrisSymptomCode());

		if (ecoorganism.contentEquals("Ecosystem")) {
			bill.setIsEcosystem(true);
			String purchaseOrderByCustomer = bill.getBillInfos().getBillNumber()+" "+bill.getClient().getName();
			bill.getEcosystemDatas().setPurchaseOrderByCustomer(purchaseOrderByCustomer);

			Partner partner = findEcoSystemPartner(partnerId);
			bill.getEcosystemDatas().setPartner(partner);
			
		}
		if (ecoorganism.contentEquals("Ecologic")) {
			bill.setIsEcosystem(false);
			String quoteNumber = bill.getClient().getName()+" "+bill.getBillInfos().getBillNumber();
			bill.getEcologicDatas().setQuoteNumber(quoteNumber);

			RepairSite repairSite = findEcoLogicRepairSite(partnerId);
			bill.getEcologicDatas().setRepairSite(repairSite);
		}

		// Verification que toutes les demandes a envoyer vont au meme ecoorganisme
		if (!billsToSendList.isEmpty()) {
			if (billsToSendList.get(0).getIsEcosystem()==bill.getIsEcosystem()) {
				billsToSendList.add(bill);
				filesInformationsList = null;
				String[][] claimsList = claimsToSendListForPanel(billsToSendList);
				pcs.firePropertyChange("claimsToSend", null, claimsList);
				claimController.clearInvoice();
			} else {
				claimController.showSimpleMessage("Ajout impossible, pas le même ecoOrganisme", "Attention");
			}
		} else {
			billsToSendList.add(bill);
			filesInformationsList = null;
			String[][] claimsList = claimsToSendListForPanel(billsToSendList);
			pcs.firePropertyChange("claimsToSend", null, claimsList);
			claimController.clearInvoice();
		}
	}

	public void addPartnerId (Id_Name partnerId) {
		this.partnerId = partnerId;
	}
	private Partner findEcoSystemPartner(Id_Name partnerId) {
		for (Partner partner : appDatas.getEcosystemPartners()) {
			if (partner.getRepairerID().equals(partnerId.getId())) {
				return partner;
			}
		}
		return null;
	}
	private RepairSite findEcoLogicRepairSite(Id_Name partnerId) {
		for (RepairSite repairSite : appDatas.getRepairSitesList()) {
			if (repairSite.getSiteId().equals(partnerId.getId())) {
				return repairSite;
			}
		}
		return null;
	}
	private String[][] claimsToSendListForPanel(List<Bill> billsToSendList){
		String[][] claimsList= new String[billsToSendList.size()][5];
		int pointer = 0;
		for (Bill bill : billsToSendList) {
			String filesList = filesToSend(bill);

			String status = "Ko";
			if (bill.getIsEcosystem()) {
				try {
					bill.getEcosystemDatas().getErrorContent().getMessage();
					System.out.println("/"+bill.getEcosystemDatas().getErrorContent().getMessage());
				} catch (NullPointerException e) {
					status = "Ok";
				}
				claimsList[billsToSendList.size() - ++pointer]= new String[]{bill.getEcosystemDatas().getPurchaseOrderByCustomer(),"Ecosystem",bill.getInvoice().getSupportAmount()+" €",filesList,status};
			} else {
				// A modifier
				status = "Ok";
				claimsList[billsToSendList.size() - ++pointer]= new String[]{bill.getBillInfos().getBillNumber(),"Ecologic",bill.getInvoice().getSupportAmount()+" €",filesList,status};
			}
		}
		return claimsList;
	}
	private String filesToSend(Bill bill) {
		String filesToSend = "";

		for (FileInformations fileInformations : bill.getFilesInformationsList()) {
			filesToSend += fileInformations.getName()+"."+fileInformations.getExtension()+" ";
		}
		System.out.println("Fichiers :"+filesToSend);
		return filesToSend;
	}

	public boolean sendInvoices() {
		boolean isEcosystem;
		List<Bill> billsNotSentList = new ArrayList<>();
		if (ecoorganism.contentEquals("Ecosystem")) {
			isEcosystem = true;
			SendEcosystemInvoices sendInvoices = new SendEcosystemInvoices(pcs, ecosystemApi, this, claimController );
			billsNotSentList = sendInvoices.Send(billsToSendList);
		} else {
			isEcosystem = false;
			EcologicApi ecologicApi = new EcologicApi(appDatas);
			SendEcologicInvoices sendInvoices = new SendEcologicInvoices(pcs, ecologicApi, this, appDatas);
			billsNotSentList = sendInvoices.Send(billsToSendList);
		}

		if (billsNotSentList.isEmpty()) {
			claimController.showSimpleMessage("L'envoi s'est effectué correctement", "Informations");

		} else {
			int nbClaimsNotSent = billsNotSentList.size();
			if (nbClaimsNotSent == 1)
				claimController.showSimpleMessage("Un envoi ne se s'est pas effectué correctement. Consultez la liste", "Informations");
			else
				claimController.showSimpleMessage("Plusieurs envois ne se sont pas effectués correctement. Consultez la liste", "Informations");
		}
		// suppression de la liste des demandes non envoyées 
		billsNotSentList.removeAll(billsNotSentList);
		// suppression des fichiers sur le disque des demandes envoyées
		deleteFiles(billsToSendList);
		
		billsToSendList = new ArrayList<>();

		String[][] claimsList = claimsToSendListForPanel(billsNotSentList);
		pcs.firePropertyChange("claimsToSend", null, claimsList);
		return isEcosystem;
	}

	public boolean isErrorReturnEcosys(String jsonResponse){
		boolean error = false;

		// Gestion des erreurs Ecosystem
		if (jsonResponse.contains("error")) {
			Gson gson = new Gson();
			RqError rqError = gson.fromJson(jsonResponse, RqError.class);
			claimController.showSimpleMessage(rqError.getError().getMessage(), rqError.getError().getStatus());
			error = true;
		}

		// Gestion des erreurs Ecologic
		if (jsonResponse.contains("ValidationErrors")) {
			Gson gson = new Gson();
			ResponseEcologic rqError = gson.fromJson(jsonResponse, ResponseEcologic.class);
			for (ValidationError validationError : rqError.getResponseData().getValidationsErrorsList()) {
				claimController.showSimpleMessage(validationError.getErrorMessage(), validationError.getField());
			}
			error = true;
		}

		return error;
	}

	public void showErrorsReturnEcolo(List<ValidationError> validationErrors){

		for (ValidationError validationError : validationErrors) {
			claimController.showSimpleMessage(validationError.getErrorMessage(), validationError.getField());
		}
	}
	private void deleteFiles (List<Bill> billsToDeleteList) {
		String options[] = {"Oui","Non"}; 
		int selection = claimController.optionDialog(options, "Supprimer les fichiers envoyés ?", "Suppression");
		if (selection == 0) {
			for (Bill bill : billsToDeleteList) {
				List<FileInformations> filesInfo = bill.getFilesInformationsList();
				for (FileInformations fileInformations : filesInfo) {
					File fileToDelete = new File(fileInformations.getPath());
					if (fileToDelete.exists()) {
			            // Supprimez le fichier en utilisant la méthode delete()
			            if (fileToDelete.delete()) {
			                System.out.println("Le fichier a été supprimé avec succès.");
			            } else {
			                System.out.println("Impossible de supprimer le fichier.");
			            }
			        } else {
			            System.out.println("Le fichier n'existe pas.");
			        }
				}
			}
		}
	}
}
