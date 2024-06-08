package com.hallouin.model.datas;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import com.hallouin.model.bill.Bill;
import com.hallouin.model.bill.BillInfos;
import com.hallouin.model.bill.Client;
import com.hallouin.model.bill.Device;
import com.hallouin.model.bill.Displacement;
import com.hallouin.model.bill.Invoice;
import com.hallouin.model.bill.Labour;
import com.hallouin.model.bill.SparePart;
import com.hallouin.model.ecosystem.api.pojo.BrandEcosystem;
import com.hallouin.model.ecosystem.api.pojo.Product;
import com.hallouin.view.DialogsView;

public class DatasFromPdf {

	private static final String UNKNOWN_BRAND = "MARQUE INCONNUE";
	private static final String MAIL = "mail@label-qualirepar.fr";

	private Product product;
	private boolean isEcosystem;
	private DialogsView dialogsView;
	private Bill bill;
	private Device device;
	private Client client;
	private BillInfos billInfos;
	private Invoice invoice;
	private Displacement displacement;
	private Labour labour;
	private List<SparePart> sparePartsList;
	private Double totalSparesExclVat = 0.0;

	public Bill Extract (File billPdf, DialogsView dialogsView,Product product) {

		this.dialogsView = dialogsView;
		this.product = product;
		PDDocument document = null;
		PDFTextStripper pdfStripper = null;
		String text = "";

		try {
			document = PDDocument.load(billPdf);
			pdfStripper = new PDFTextStripper();

			// Boucle à travers chaque page du PDF
	        for (int page = 1; page <= document.getNumberOfPages(); ++page) {
	            pdfStripper.setStartPage(page);
	            pdfStripper.setEndPage(page);

					text += pdfStripper.getText(document);

	        }

		document.close();
		} catch (IOException e) {
			dialogsView.simpleMessage("Fichier non lu","Erreur!");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        System.out.println(text);

    	device = fillDevice(text);
    	client = fillClient(text);
    	billInfos = fillInfos(text);
    	sparePartsList = findSpareParts("PD/","\r\n",text);
    	invoice = fillInvoice(text);
    	displacement = fillDisplacement(text);
    	labour = fillLabour(text);

    	bill = new Bill(billInfos, invoice, client, device, labour, displacement, sparePartsList);

		return bill;
	}

	private Device fillDevice (String text) {
		String brandFromPdf= "";
        String reference= "";
        String symptom= "";
        String serial= "";
		try {
			brandFromPdf = text.split("Marque : ")[1].split("\n")[0].replace("\r", "");
			isMissingField(brandFromPdf, "Marque de l'appareil non renseignée");
	        reference = text.split("Réf.Article : ")[1].split("N° produit")[0].trim();
	        isMissingField(reference, "Référence de l'appareil non renseignée");
	        symptom = text.split("signalé\\(s\\):\\r\\n")[1].split("\n")[0].replace("\r", "");
	        serial = text.split("N° de série : ")[1].split("Marque")[0].trim();
	        isMissingField(serial, "N° de série de l'appareil non renseigné");

		} catch(ArrayIndexOutOfBoundsException e){
        	e.printStackTrace();
        	dialogsView.simpleMessage("Erreur de lecture des infos concernant l'appareil sur la facture", "Erreur");
        }

		BrandEcosystem brand = findBrandObject(brandFromPdf);
		if (brand.getBrandName().equals(UNKNOWN_BRAND))
			dialogsView.simpleMessage("Marque inexistante chez les l'ecoorganismes", "Erreur");

		device = new Device(reference, serial);
		device.setBrand(brand);
		device.setIsEcosystem(isEcosystem);
		device.setSymptom(symptom);

        return device;
	}

	private Client fillClient (String text) {
		String gender= "";
	    String name= "";
	    String firstName= "";
	    String streetNumber= "";
	    String street= "";
	    String zipCode= "";
	    String town= "";
	    String phone= "";
	    String mail = "";

		try {
			String [] coordonnees;
			int offset = 1;
			if (text.contains("Facture SAV")) {
				coordonnees = text.split("sarlhallouin@free.fr\\r\\n")[1].split("Facture SAV")[0].split("\n");
			} else {
				coordonnees = text.split("sarlhallouin@free.fr\\r\\n")[1].split("Facture")[0].split("\n");
			}
			
			if (text.contains("Email")) {
				String [] email = text.split("Email : ")[1].split("\\s");
				if (!email[0].isEmpty()) {
					mail = email[0];
				}else {
					mail = MAIL;
	            	dialogsView.simpleMessage("Le mail client n'est pas renseigné","Attention !");
	            }
				offset = 2;
			}
            String [] genderNameFirstName = coordonnees[0].split("\\s+");

            gender = calculateGender(genderNameFirstName[0]);
            String nomPrenom = coordonnees[0].split("(Monsieur )|(Madame )|(Mademoiselle )")[1].split("\n")[0];
            String [] nomPrenom2 = nomPrenom.split("\\s+");
            firstName = nomPrenom2[nomPrenom2.length-1].replace("\r", "").trim();
            name = nomPrenom.replaceAll(firstName, "").replace("\r", "").trim();
            isMissingField(name, "Nom du client non renseigné");

            String [] adresse = coordonnees[1].split("\\s+");
            streetNumber = adresse[0];
            street = coordonnees[1].replaceAll(streetNumber, "").replace("\r", "").trim();
            isMissingField(street, "Rue non renseignée");

            String [] cpVille = coordonnees[coordonnees.length-offset].split("\\s+");
            zipCode = cpVille[0];
            isMissingField(zipCode, "Code postal non renseigné");
            town = coordonnees[coordonnees.length-offset].replaceAll(zipCode, "").replace("\r", "").trim();
            isMissingField(town, "Ville non renseignée");          

            String [] phoneLine = text.split("Date")[1].split("\\nTechnicien")[0].split(":");
            phone = phoneLine[phoneLine.length-1].replace("\r", "").replace(" ", "");
            if (phone.length()!=10)
            	dialogsView.simpleMessage("Le numéro de téléphone n'est pas conforme","Attention !");
            isMissingField(phone, "N° de téléphone non renseigné");
 
		} catch(ArrayIndexOutOfBoundsException e ){
        	e.printStackTrace();
        	dialogsView.simpleMessage("Erreur de lecture des infos concernant le client sur la facture", "Erreur");
        }

		client = new Client(name);
		client.setGender(gender);
		client.setFirstName(firstName);
		client.setStreetNumber(streetNumber);
		client.setStreet(street);
		client.setZipCode(zipCode);
		client.setTown(town);
		client.setPhone(phone);
		if (!mail.isEmpty())
			client.setMail(mail);

        return client;
	}

	private BillInfos fillInfos (String text) {
		String pieceNumber= "";
		String repairDate= "";
		try{
            pieceNumber = text.split("N°Pièce : ")[1].split("\n")[0].replace("\r", "");
            repairDate = text.split("Date intervention : ")[1].split("\n")[0].replace("\r", "").trim();
		}
        catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            dialogsView.simpleMessage("Erreur sur N° de facture ou date d'intervention", "Erreur");
        }
		return new BillInfos(pieceNumber, repairDate);
	}

	private Invoice fillInvoice(String text) {
		Double totalVat= 0.0;
		Double totalExclVat= 0.0;
		Double advance= 0.0;
		Double bonusReparation= 0.0;

		try {
			totalVat = Double.valueOf(text.split("MONTANT TTC ")[1].split(" €")[0].replaceAll(",","."));
            isFieldZero(totalVat, "Le Total TTC de la facture n'est pas valide");
            totalExclVat = Double.valueOf(text.split("MONTANT HT ")[1].split(" €")[0].replaceAll(",","."));
            isFieldZero(totalVat, "Le Total HT de la facture n'est pas valide");
            
            String resteAPayer = text.split("RESTE A PAYER ")[1].split(" €")[0].replaceAll(",",".");
            double remToPay = Double.parseDouble(resteAPayer);
            if (remToPay != 0) {
            	dialogsView.simpleMessage("La facture n'est pas acquitée", "Erreur");
            }

            if (text.contains("Acompte")) {
                String [] advanceText = text.split("Acompte")[1].split("\\n")[0].split("\\s+");
                advance = Double.valueOf(advanceText[advanceText.length-1].replaceAll(",","."));
            }
		}
		catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            dialogsView.simpleMessage("Le fichier sélectionné n'est pas valide.", "Erreur");
        }
		
		try {
			bonusReparation = Double.valueOf(text.split("Bonus réparation ")[1].split(" €")[0].replaceAll(",","."));
            isFieldZero(totalVat, "Le montant du bonus réparation est incorrect");
		}
		catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            dialogsView.simpleMessage("Le bonus réparation n'est pas renseigné.", "Erreur");
        }
		
		
		Invoice invoice = new Invoice(totalVat,totalExclVat, totalSparesExclVat);
		invoice.setSupportAmount(bonusReparation);
		if (advance!=0.0)
			invoice.setAdvance(advance);

		return invoice;
	}

	private Displacement fillDisplacement(String text) {
		try {
			if (text.contains("DEPL 20%")) {
                String [] depl = text.split("DEPL 20%")[1].split("\\n")[0].split("\\s+");
                displacement = extractDisplacementInformations(depl);
                isFieldZero(displacement.getTotalExclVat(), "Le montant HT de déplacement n'est pas valide");
                return displacement;
            }
		}
		catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            dialogsView.simpleMessage("Le fichier sélectionné n'est pas valide.", "Erreur");
        }
		return null;
	}

	private Labour fillLabour(String text) {

		try {
			if (text.contains("MO 20%")) {
                String [] mo = text.split("MO 20%")[1].split("\\n")[0].split("\\s+");
                labour = extractLabourInformations(mo);
                Double labourExclVat = Double.valueOf(mo[mo.length-1].replaceAll(",","."));
                isFieldZero(labourExclVat, "Le montant de la main d'oeuvre HT n'est pas valide");
                return labour;
            }
		}
		catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            dialogsView.simpleMessage("Le fichier sélectionné n'est pas valide.", "Erreur");
        }
	return null;
	}

	private List<SparePart> findSpareParts(String start, String stop, String text) {
		List<SparePart> spareParts = new ArrayList<>();

        // Créez le motif regex
        String motifRegex = start + "(.*?)" + stop;

        // Compilez le motif regex en un objet Pattern
        Pattern pattern = Pattern.compile(motifRegex);

        // Créez un objet Matcher avec le texte à rechercher
        Matcher matcher = pattern.matcher(text);

        // Parcourez les correspondances et extrayez les informations
        while (matcher.find()) {

            String spare = matcher.group(1);
            String [] spareTab = spare.split("\\s+");

            String ref = spareTab[0];			// reference
            Double qty = Double.valueOf(spareTab[spareTab.length-4].replaceAll(",","."));		// quantité
            isFieldZero(qty, "La quantité d'une pièce détachée est nulle");
            Double unitPVat = Double.valueOf(spareTab[spareTab.length-3].replaceAll(",","."));		// PU TTC
            isFieldZero(unitPVat, "Le montant unitaire d'une pièce détachée est nul");
            Double totalPVat = Double.valueOf(spareTab[spareTab.length-1].replaceAll(",","."));		// Ptotal TTC
            isFieldZero(totalPVat, "Le montant total d'une pièce détachée est nul");

            String designation = spare.replace(spareTab[spareTab.length-4],"");
            designation = designation.replace(spareTab[spareTab.length-3],"");
            designation = designation.replace(spareTab[spareTab.length-2],"");
            designation = designation.replace(spareTab[spareTab.length-1],"");
            designation = designation.replace(spareTab[0],"");

            SparePart sparePart = new SparePart(ref,qty,unitPVat,totalPVat);
            sparePart.setDesignation(designation);
            totalSparesExclVat += totalPVat;
            spareParts.add(sparePart);
        }

        return spareParts;
    }

	private Labour extractLabourInformations(String[] mo) {

		Double qty = Double.parseDouble(mo[mo.length-4].replaceAll(",", "."));
        isFieldZero(qty, "La quantité de main d'oeuvre est nulle");
		Double unitPExclVat = Double.parseDouble(mo[mo.length-3].replaceAll(",", "."));
        isFieldZero(unitPExclVat, "Le montant unitaire de la main d'oeuvre est nul");
		Double totalPExclVat = Double.parseDouble(mo[mo.length-1].replaceAll(",", "."));
        isFieldZero(totalPExclVat, "Le montant total de main d'oeuvre est nul");

		Labour labour = new Labour(qty,unitPExclVat,totalPExclVat);
		return labour;
	}

	private Displacement extractDisplacementInformations(String[] depl) {
		Double qty = Double.parseDouble(depl[depl.length-4].replaceAll(",", "."));
        isFieldZero(qty, "Le déplacement est nul");
		Double unitPExclVat = Double.parseDouble(depl[depl.length-3].replaceAll(",", "."));
        isFieldZero(unitPExclVat, "Le montant unitaire de déplacement est nul");
		Double totalPExclVat = Double.parseDouble(depl[depl.length-1].replaceAll(",", "."));
        isFieldZero(totalPExclVat, "Le montant total de déplacement est nul");


		Displacement displacement = new Displacement (qty);
		displacement.setUnitPExclVat(unitPExclVat);
		displacement.setTotalExclVat(totalPExclVat);
		return displacement;
	}

	private BrandEcosystem findBrandObject (String brandName) {

		// Initialisation de brand avec la marque "MARQUE INCONNUE"
		BrandEcosystem brand = product.getBrandsList().get(0);
		for(BrandEcosystem brandEcosystem : product.getBrandsList()) {
			if (brandName.toUpperCase().contains(brandEcosystem.getBrandName().toUpperCase())) {
			//if (brandEcosystem.getBrandName().toUpperCase().contains(brandName.toUpperCase())) {
				brand = brandEcosystem;
				break;
			}
		}

		return brand;

	}


	private void isMissingField(String field, String Message) {
		if (field == "" || field == null) {
			dialogsView.simpleMessage(Message, "Erreur!");
		}
	}

	private void isFieldZero(Double field, String Message) {
		if (field == 0.0 || field == null) {
			dialogsView.simpleMessage(Message, "Erreur!");
		}
	}

	private String calculateGender(String gender) {
		switch (gender) {
		case "Madame":
			gender = "Mme";
			break;
		case "Monsieur":
			gender = "M.";
			break;
		case "Mademoiselle":
			gender = "Mlle";
			break;
		default:
			gender = "M.";
			break;
		}
		return gender;
	}

}