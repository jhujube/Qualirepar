package com.hallouin.model.ecologic.api;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hallouin.model.bill.Bill;
import com.hallouin.model.bill.SparePart;
import com.hallouin.model.datas.AppDatas;
import com.hallouin.model.ecologic.api.pojo.SupportRequest;


public class EcologicApi {
	private static  String HEADER = "https://preprod-";
    private static final String URL_PRINTBRANDLIST = "apiecologic.e-reparateur.eco/api/v1/ecosupport/printbrandlist";
    private static final String URL_GETREPAIRSITEBYATS = "apiecologic.e-reparateur.eco/api/v1/ecosupport/getrepairsitesbyats";
    private static final String URL_PRINTPRODUCTTYPEWITHLABELLIST = "apiecologic.e-reparateur.eco/api/v1/ecosupport/printproducttypewithlabellist";
    private static final String URL_CALCULATEECOSUPPORT = "apiecologic.e-reparateur.eco/api/v1/ecosupport/calculateecosupport";
    private static final String URL_SUPPORTREQUESTSTATUS = "apiecologic.e-reparateur.eco/api/v1/ecosupport/getsupportrequestStatus";
    private static final String URL_CREATECLAIM = "apiecologic.e-reparateur.eco/api/v1/ecosupport/createclaim";
    private static final String URL_ATTACHFILE = "apiecologic.e-reparateur.eco/api/v1/ecosupport/Attachfile";
    private static final String URL_CLAIMREQUESTSTATUS = "apiecologic.e-reparateur.eco/api/v1/ecosupport/GetClaimStatus";
    private static final String URL_UPDATECLAIM = "apiecologic.e-reparateur.eco/api/v1/ecosupport/updateclaim";
    private static final String URL_SUBMITCLAIM = "apiecologic.e-reparateur.eco/api/v1/ecosupport/submitclaim";

    private EcologicApiClient ecologicApiClient;

	public EcologicApi (String apiKey, Boolean isSandBox) {
		super();
		this.ecologicApiClient = new EcologicApiClient(apiKey);
		if (!isSandBox)
			HEADER = "https://";
	}

	public EcologicApi (AppDatas appDatas) {
		super();
		this.ecologicApiClient = new EcologicApiClient(appDatas.getEcologicApiKey());
		if (appDatas.getSandBox().contentEquals("false"))
			HEADER = "https://";
	}

	public String getLabeledProductType() {

		String url = HEADER+URL_PRINTPRODUCTTYPEWITHLABELLIST;
		String responseJson="";

		try {
			responseJson = ecologicApiClient.sendGetRequest(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println ("getLabeledProductType :"+responseJson);

		return responseJson;
	}

	public String getBrands(){
		//EcologicApi ecologicApi = new EcologicApi();
		String url = HEADER+URL_PRINTBRANDLIST;
		String responseJson="";

		try {
			responseJson = ecologicApiClient.sendGetRequest(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println ("getBrands :"+responseJson);

		return responseJson;

	}
	public String getRepairSites() {

		String url = HEADER+URL_GETREPAIRSITEBYATS;
		String responseJson="";

		try {
			responseJson = ecologicApiClient.sendGetRequest(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println (responseJson);

		return responseJson;
	}

	public String getEcoSupport(Bill bill) {

		String url = HEADER+URL_CALCULATEECOSUPPORT+"?totalAmountInclVAT="+bill.getInvoice().getTotalVat()+"&totalAmountInclVATCurrency=EUR&brandId="+bill.getDevice().getBrandId()+"&productId="+bill.getDevice().getProductId()+"&iRISSymptom="+bill.getDevice().getIrisSymptomCode();
		String responseJson="";

		try {
			responseJson = ecologicApiClient.sendGetRequest(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println (responseJson);

		return responseJson;

	}

	public String CheckSupportStatus(int requestId) {

		String url = HEADER+URL_SUPPORTREQUESTSTATUS+"?requestId="+requestId;
		String responseJson="";
		System.out.println(url);
		try {
			responseJson = ecologicApiClient.sendGetRequest(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println (responseJson);

		return responseJson;
	}

	public String createClaim(Bill bill) {
		boolean sendErrorBoolean = true;

		//String creationClaimDate = getNowDateTimeFormatIso8601();
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
		String creationClaimDate = now.format(formatter);

		//int requestId = bill.getEcologicDatas().getRequestId();
    	//String url = HEADER+URL_CREATECLAIM+"?RequestId="+requestId+"&RepairEndDate="+creationClaimDate+"&RepairSiteId="+bill.getEcologicDatas().getRepairSite().getSiteId()+"&ConsumerInvoiceNumber="+bill.getBillInfos().getBillNumber()+"&quoteNumber="+bill.getEcologicDatas().getQuoteNumber();
		String url = HEADER+URL_CREATECLAIM+"?RepairEndDate="+creationClaimDate+"&RepairSiteId="+bill.getEcologicDatas().getRepairSite().getSiteId()+"&ConsumerInvoiceNumber="+bill.getBillInfos().getBillNumber()+"&quoteNumber="+bill.getEcologicDatas().getQuoteNumber();
		String responseJson="";
    	String jsonBody = "";

    	System.out.println(url);

		try {
			jsonBody = createRequestJsonBody(bill);
		} catch (Exception e) {
			sendErrorBoolean = false;
			e.printStackTrace();
		}
		if (sendErrorBoolean)
			bill.getEcologicDatas().setCreationDate(now);
    	System.out.println(jsonBody);

		try {
			responseJson = ecologicApiClient.sendPostRequest(url,jsonBody);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println ("Create claim :"+responseJson);
		return responseJson;
	}

	public String attachFile(int claimId, File file,String fileType) throws Exception {

		String responseJson = "";
		String fileNameWthExtension = file.getName();
		String url = HEADER+URL_ATTACHFILE+"?ClaimId="+claimId+"&filename="+getFileName(fileNameWthExtension)+"&fileextension="+getFileExtension(fileNameWthExtension)+"&documentType="+fileType;

		String body = convertFileToBase64(file);
        String jsonBody = addFileJsonBody(body);

		try {
			responseJson = ecologicApiClient.sendPostRequest(url,jsonBody);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println ("Attach file :"+responseJson);
		return responseJson;
	}
	public String checkClaimStatus(int claimId) {

		String url = HEADER+URL_CLAIMREQUESTSTATUS+"?ClaimId="+claimId;
		String responseJson="";

		try {
			responseJson = ecologicApiClient.sendGetRequest(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println ("Check claim :"+responseJson);

		return responseJson;
	}
	public String updateClaim(Bill bill) {

		String repairEndDate = getNowDateTimeFormatIso8601();
		int claimId = bill.getEcologicDatas().getClaimId();
    	String url = HEADER+URL_UPDATECLAIM+"?ClaimId="+claimId+"&repairEndDate="+repairEndDate+"&RepairSiteId="+bill.getEcologicDatas().getRepairSite().getSiteId()+"&consumerInvoiceNumber="+bill.getBillInfos().getBillNumber()+"&quoteNumber="+bill.getClient().getName()+bill.getBillInfos().getBillNumber();
    	String jsonBody = "";
    	String responseJson="";
    	System.out.println(url);

    	jsonBody = createRequestJsonBody(bill);
        System.out.println(jsonBody);


        try {
			responseJson = ecologicApiClient.sendPostRequest(url,jsonBody);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println (responseJson);

		return responseJson;

	}
	public String submitClaim(int claimId) {

		String url = HEADER+URL_SUBMITCLAIM+"?ClaimId="+claimId;
		String responseJson="";

		try {
			responseJson = ecologicApiClient.sendPostRequest(url,"");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println ("Json response to submit claim :"+responseJson);

		return responseJson;
	}

	public static String getFileName (String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex != -1) {
            return fileName.substring(0, dotIndex);
        } else {
            return fileName;
        }
    }
    public static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex != -1 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1);
        } else {
            return "";
        }
    }
    private static String convertDateFormat(String inputDate) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yy");
            SimpleDateFormat outputFormat = new SimpleDateFormat("MM/dd/yy");

            // Conversion de la chaîne de caractères en objet Date
            Date date = inputFormat.parse(inputDate);

            // Conversion de la date en chaîne de caractères avec le nouveau format
            String outputDate = outputFormat.format(date);

            return outputDate;
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // En cas d'erreur de parsing, retourne null ou gère l'exception appropriée.
        }
    }
    private static String createRequestJsonBody(Bill bill) {
    	String jsonBody = "";
    	int gender = convertGender(bill.getClient().getGender());
    	SupportRequest.Consumer consumer = new SupportRequest.Consumer(gender, bill.getClient().getName(), bill.getClient().getFirstName(), bill.getClient().getStreetNumber(), bill.getClient().getStreet(), bill.getClient().getZipCode(), bill.getClient().getTown(), bill.getClient().getPhone());
    	if (bill.getClient().getMail() != null)
    		consumer.setEmail(bill.getClient().getMail());
    	//if (bill.getClient().getPhone() != null)
    	//	consumer.setPhone(bill.getClient().getPhone());

    	//String failureDescriptionString ="Symptome Iris"+bill.getDevice().getIrisSymptomCode();
    	String failureDescriptionString =bill.getDevice().getSymptom();
    	System.out.println("Sysmptom :"+failureDescriptionString );
    	SupportRequest.Product product = new SupportRequest.Product(bill.getDevice().getProductId(), bill.getDevice().getBrandId(),bill.getDevice().getReference(),bill.getDevice().getSerial(), bill.getDevice().getIrisSymptomCode(), bill.getDevice().getIrisSectionCode(),failureDescriptionString);

    	SupportRequest.Cost laborCost = new SupportRequest.Cost(bill.getLabour().getTotalExclVat());
    	SupportRequest.Cost sparePartsCost = new SupportRequest.Cost(bill.getInvoice().getTotalSparesExclVat());
    	SupportRequest.Cost travelCost = new SupportRequest.Cost(bill.getDisplacement().getTotalExclVat());
    	SupportRequest.Cost totalAmountExclVAT = new SupportRequest.Cost(bill.getInvoice().getTotalExclVat());
    	SupportRequest.Cost totalAmountInclVAT = new SupportRequest.Cost(bill.getInvoice().getTotalVat());
    	SupportRequest.Cost supportAmount = new SupportRequest.Cost(bill.getInvoice().getSupportAmount());

    	SupportRequest.Quote quote = new SupportRequest.Quote(laborCost, sparePartsCost, travelCost, totalAmountExclVAT, totalAmountInclVAT, supportAmount);

    	SupportRequest supReq = new SupportRequest(consumer, product, quote);

    	if (!bill.getSparesList().isEmpty()) {
    		List<SparePart> sparesList = bill.getSparesList();
    		List<SupportRequest.SparePartEcl> supportRequestSpareList = new ArrayList<>();
    		for (SparePart sparePart: sparesList) {
    			SupportRequest.SparePartEcl sp = new SupportRequest.SparePartEcl(sparePart.getRef(), sparePart.getQty());
    			supportRequestSpareList.add(sp);
    		}
    		supReq.setSpareParts(supportRequestSpareList);
    	}


    	Gson gson = new Gson();
    	jsonBody = gson.toJson(supReq);

    	return jsonBody;
    }
    private static int convertGender(String genderString) {
	   int genderInt = 3;

	   switch (genderString) {
			case "Madame":
				genderInt = 2;
				break;
			case "Mlle":
				genderInt = 1;
				break;
			case "Société":
				genderInt = 4;
				break;
	   }

	   return genderInt;
   }
    private static String getNowDateTimeFormatIso8601() {
    	OffsetDateTime currentDateTime = OffsetDateTime.now(ZoneOffset.UTC);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        return currentDateTime.format(formatter);
    }
    private static String convertFileToBase64(File file)  {

        // Lecture du contenu du fichier en tant qu'octets
        byte[] fileContent= null;
		try {
			fileContent = Files.readAllBytes(file.toPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Le fichier n'existe pas!");
		}

        // Encodage du contenu en base64
        String base64String = Base64.getEncoder().encodeToString(fileContent);

        return base64String;
    }
    private static String addFileJsonBody(String fileBase64) {
    	// Création de l'objet JSON
        JsonObject jsonRequest = new JsonObject();
        jsonRequest.addProperty("FileContent", fileBase64);

        // Conversion de l'objet en JSON
        Gson gson = new Gson();

        return gson.toJson(jsonRequest);
    }


}
