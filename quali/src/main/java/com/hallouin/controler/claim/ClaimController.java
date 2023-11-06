package com.hallouin.controler.claim;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.hallouin.model.bill.Bill;
import com.hallouin.model.bill.BillInfos;
import com.hallouin.model.bill.Client;
import com.hallouin.model.bill.Device;
import com.hallouin.model.bill.Displacement;
import com.hallouin.model.bill.FileInformations;
import com.hallouin.model.bill.Invoice;
import com.hallouin.model.bill.Labour;
import com.hallouin.model.bill.SparePart;
import com.hallouin.model.claim.ClaimModel;
import com.hallouin.model.datas.AppDatas;
import com.hallouin.model.datas.DatasFromPdf;
import com.hallouin.model.ecosystem.api.pojo.BrandEcosystem;
import com.hallouin.model.ecosystem.api.pojo.Product;
import com.hallouin.model.ecosystem.pojo.Id_Name;
import com.hallouin.view.DialogsView;
import com.hallouin.view.claimCreation.ClaimView;

public class ClaimController {
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	private ClaimModel claimModel;
	private ClaimView claimView;
	private AppDatas appDatas;
	private DialogsView dialogsView;
	private Product selectedProduct;

	public ClaimController(ClaimView claimView,DialogsView dialogsView , ClaimModel claimModel,AppDatas appDatas ) {
		super();
		claimView.setController(this);
		this.claimModel = claimModel;
		claimModel.setController(this);
		this.dialogsView = dialogsView;
		this.appDatas = appDatas;

		start();
	}

	private void start() {
		claimModel.setProductsList();
	}
	// Méthode pour ajouter un PropertyChangeListener au modèle
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }
    // Méthode pour supprimer un PropertyChangeListener du modèle
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

	public void setView(ClaimView claimView) {
		this.claimView = claimView;
	}

	public void setProduct(Product product) {
		selectedProduct = product;
		claimModel.selectedProduct(product);
	}

	public void setBrandEcosystem(BrandEcosystem brand) {
		claimModel.selectedBrandEcosystem(brand);
	}

	public void importInvoice() {
		
		File file = dialogsView.showFileChooser(appDatas.getBillPath(), "pdf");
		Bill bill = new DatasFromPdf().Extract(file,dialogsView,selectedProduct);
		claimModel.fillClaimWithInfosFromBill(bill);
		claimModel.addFileToSend(selectFile(file, "invoice"));
	}

	public void clearInvoice() {
		claimView.clearInvoice();
	}

	public void addInvoice() {
		Client client = claimView.getClient();
		BillInfos billInfos = claimView.getBillInfos();
		Id_Name partnerId = claimView.getPartnerId();
		Device device = claimView.getDevice();
		Labour labour = claimView.getLabour();
		Displacement displacement = claimView.getDisplacement();
		Invoice invoice = claimView.getInvoice();
		invoice.setSupportAmount(claimView.getAmount());
		List<SparePart> sparePartsList = new ArrayList<>();
		sparePartsList.addAll(claimView.getSpareParts());
		List<String> filesToSendList = claimView.getFilesList();

		Boolean areFilesOk;
		String filesList;
		if (device.getIsEcosystem()) {
			filesList = "invoiceField ";
		} else {
			filesList = "invoiceField serial_tagField certificate_clientField ";
		}
		areFilesOk = isFilesOk(filesList,filesToSendList);

		if (client != null && billInfos != null && partnerId != null && device != null && displacement != null && invoice != null && sparePartsList != null && areFilesOk) {
			claimModel.addPartnerId(partnerId);
			Bill bill = new Bill(billInfos, invoice, client, device, labour, displacement, sparePartsList);
			claimModel.addBillToSend(bill);

		} else {
			if (!areFilesOk)
				System.out.println("Fichiers à envoyer manquants");
			else {
				System.out.println("Informations incomplètes");
				System.out.println("billInfos :"+billInfos);
				System.out.println("partner :"+partnerId);
				System.out.println("client :"+client);
				System.out.println("device :"+device);
				System.out.println("displacement :"+displacement);
				System.out.println("invoice :"+invoice);
				System.out.println("sparePartsList :"+sparePartsList);
			}
		}
	}
	private Boolean isFilesOk(String filesList, List<String> filesToSendList) {
		for (String string : filesToSendList) {
			if (filesList.contains(string))
				filesList = filesList.replaceAll(string+" ","");
		}
		if (filesList.isEmpty())
			return true;
		claimView.panelFilesSetColor(255);
		return false;
	}
	public void selectFileToSend(String fileType) {
		File file = dialogsView.showFileChooser(appDatas.getBillPath(),"*");
		claimModel.addFileToSend(selectFile(file, fileType));
	}

	public FileInformations selectFile(File file , String fileType) {

		String[] name = file.getName().split("\\.");
		FileInformations fileInformations = new FileInformations(file.getPath(),name[0],name[1], fileType, convertToMB(file.length()));

		return fileInformations;
	}

	private Double convertToMB(long totalSpace) {
		 double totalSpaceInMegaBytes = totalSpace / (1024.0 * 1024.0); // Conversion en méga-octets

		 // Formater le résultat avec un chiffre après la virgule
		 String formattedTotalSpace = String.format("%.1f", totalSpaceInMegaBytes);
		 formattedTotalSpace = formattedTotalSpace.replace(",", ".");
		 System.out.println("taille :"+formattedTotalSpace);
		 return Double.parseDouble(formattedTotalSpace);

	}

	public void enableFields(boolean state) {
    	claimView.enableFields(state);
    }

	public void sendInvoices() {
		
		// mise de la demande d'envoi dans un thread différent pour ne pas bloquer la maj de l'ui
		CompletableFuture<Boolean> isEcosystem = new CompletableFuture<>();
		
		// recupération du résultat
		isEcosystem.whenComplete((value,error) -> {
			if (value)
				pcs.firePropertyChange("newClaimEcosystem", false, true); // Notifie les observateurs qu'il y a eu une mise à jour Ecosystem
			else
				pcs.firePropertyChange("newClaimEcologic", false, true); // Notifie les observateurs qu'il y a eu une mise à jour Ecologic

		});
		// demarrage de thread
		CompletableFuture.runAsync(() -> {
			isEcosystem.complete(claimModel.sendInvoices());
		});

	}

	public void showSimpleMessage(String message, String title) {
		dialogsView.simpleMessage(message, title);
	}
	public int optionDialog(Object[] options, String message, String title) {
		return dialogsView.optionDialogue(options, message, title);
	}
}
