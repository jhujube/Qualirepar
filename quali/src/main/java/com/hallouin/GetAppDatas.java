package com.hallouin;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Properties;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.hallouin.model.datas.AppDatas;
import com.hallouin.model.ecologic.api.EcologicApi;
import com.hallouin.model.ecologic.api.pojo.BrandEcologic;
import com.hallouin.model.ecologic.api.pojo.LabeledProductType;
import com.hallouin.model.ecologic.api.pojo.RepairSite;
import com.hallouin.model.ecologic.api.response_pojo.ResponseEcologicBrandsList;
import com.hallouin.model.ecologic.api.response_pojo.ResponseEcologicLabeledProductTypeList;
import com.hallouin.model.ecologic.api.response_pojo.ResponseEcologicRepairSitesList;
import com.hallouin.model.ecosystem.api.EcosystemApi;
import com.hallouin.model.ecosystem.api.pojo.Product;
import com.hallouin.model.ecosystem.api.request_pojo.partners.Partner;
import com.hallouin.model.ecosystem.api.response_pojo.GetCatalog;
import com.hallouin.model.ecosystem.api.response_pojo.GetPartners;
import com.hallouin.view.DialogsView;

public class GetAppDatas {
	private static final Integer DAY_MAX_AGE = 7;
	private static final String CATALOG_QUERY = "/catalog?brands=true&repair-codes=true&reimbursement-amounts=true&product-sheets=true&threshold=true&ecologic=true";
	private static final String PARTNERS_QUERY = "/partners?subcontractor=false&repair-places=true&business-sheets=true";
	private static AppDatas appDatas;

	public static AppDatas getDatas(DialogsView dialogsView) {
		
		Properties properties = new Properties();

        try (FileInputStream fichierProprietes = new FileInputStream("/Users/halju/Documents/Fact/qualirepar.properties")) {
		//try (FileInputStream fichierProprietes = new FileInputStream("qualirepar.properties")) {
			properties.load(fichierProprietes);
        } catch (IOException e) {
            e.printStackTrace();
            dialogsView.simpleMessage("Le fichier de configuration qualirepar.properties est introuvable","Erreur");
            end();
        }

        // Récupérer les valeurs des propriétés
        String sandBox = properties.getProperty("sandBox");

        String ecosystemPwd = properties.getProperty("ecosystemPwdSandBox");
        String ecosystemUser = properties.getProperty("ecosystemUserSandBox");
        String ecosystemUrl = properties.getProperty("ecosystemUrlSandBox");
        String ecologicApiKey = properties.getProperty("ecologicApiKeySandBox");
        Boolean ecologicSandboxBoolean = true;
        if (sandBox.contentEquals("false")){
        	ecologicSandboxBoolean = false;
        	ecosystemPwd = properties.getProperty("ecosystemPwd");
            ecosystemUser = properties.getProperty("ecosystemUser");
            ecosystemUrl = properties.getProperty("ecosystemUrl");
            ecologicApiKey = properties.getProperty("ecologicApiKey");
        }

        String billPath = properties.getProperty("billPath");
        String datasPath = properties.getProperty("datasPath");
        String firefoxPath = properties.getProperty("firefoxPath");
        String gekoPath = properties.getProperty("gekoPath");

        // mise à jour du catalogue de produits ecologic et des partners ecosystem si necessaire
        updateEcologicFiles(datasPath, ecologicApiKey,ecologicSandboxBoolean );

        List<BrandEcologic> brandsList = getBrandsList(datasPath, "brandsList");
        List<LabeledProductType> labeledProductType = getLabeledProductList(datasPath, "productTypeList");
        List<RepairSite> repairSitesList = getRepairSitesList(datasPath,"repairSitesList");

        // mise à jour du catalogue de produits ecosystem et des partners ecosystem si necessaire
        updateEcosystemFiles(datasPath, ecosystemUser, ecosystemPwd, ecosystemUrl);
        // Récupération du catalogue de produits Ecosystem depuis un fichier sur dd
        GetCatalog catalog = getEcosystemProductsList(datasPath, "catalogEcosystem");
        List<Product> ecosystemProductsList = catalog.getProductsList();

        // Récupération de la liste des partenaires depuis un fichier sur dd
        GetPartners partners = getEcosystemPartnersList(datasPath, "partnersEcosystem");
        List<Partner> ecosystemPartnersList = partners.getPartnersList();

        // Création de l'objet appDatas contenant les infos pour le fct de l'appli
        appDatas = new AppDatas(sandBox,ecosystemUser,ecosystemPwd,ecosystemUrl,ecologicApiKey,billPath, datasPath,firefoxPath,gekoPath,brandsList,labeledProductType,repairSitesList,ecosystemProductsList,ecosystemPartnersList);

        // Utiliser les valeurs récupérées
        System.out.println("Identifiant : " + appDatas.getEcosystemUser());
        System.out.println("Mot de passe : " + appDatas.getEcosystemPwd());
        System.out.println("path : " + appDatas.getBillPath());
        System.out.println("Ref ecosystem : " + appDatas.getEcosystemRef());
        System.out.println("path firefox : " + appDatas.getFirefoxPath());
        System.out.println("path Geko : " + appDatas.getGekoPath());

        return appDatas;
    }

	private static List<BrandEcologic> getBrandsList(String path, String fileName){
		List<BrandEcologic> brandsList = null;
		String filePath = path+"/"+fileName+".json";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            JsonElement jsonElement = JsonParser.parseReader(reader);

            Gson gson = new Gson();
            ResponseEcologicBrandsList response =  gson.fromJson(jsonElement, ResponseEcologicBrandsList.class);
            brandsList = response.getBrandEcologic();

        } catch (Exception e) {
            e.printStackTrace();
        }

		return brandsList;
	}

	private static List<LabeledProductType> getLabeledProductList(String path, String fileName){
		List<LabeledProductType> labeledProductList = null;
		String filePath = path+"/"+fileName+".json";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            JsonElement jsonElement = JsonParser.parseReader(reader);

            Gson gson = new Gson();
            ResponseEcologicLabeledProductTypeList response =  gson.fromJson(jsonElement, ResponseEcologicLabeledProductTypeList.class);
            labeledProductList = response.getLabeledProductTypes();

        } catch (Exception e) {
            e.printStackTrace();
        }

		return labeledProductList;
	}

	private static List<RepairSite> getRepairSitesList(String path, String fileName){
		List<RepairSite> repairSitesList = null;
		String filePath = path+"/"+fileName+".json";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            JsonElement jsonElement = JsonParser.parseReader(reader);

            Gson gson = new Gson();
            ResponseEcologicRepairSitesList response = gson.fromJson(jsonElement, ResponseEcologicRepairSitesList.class);
            repairSitesList = response.getRepairSites();

        } catch (Exception e) {
            e.printStackTrace();
        }

		return repairSitesList;
	}

	private static void updateEcologicFiles(String datasPath,String ecologicApiKey,Boolean isSandbox ) {
		String ecologicBrandsListPath = datasPath+"/brandsList.json";
		Path brandsListPath = Paths.get(ecologicBrandsListPath);
		String productTypeListPath = datasPath+"/productTypeList.json";
		Path productTypePath = Paths.get(productTypeListPath);
		String repairSitesListPath = datasPath+"/repairSitesList.json";
		Path repairSitesPath = Paths.get(repairSitesListPath);

		Instant currentInstant = Instant.now();
    	Duration day = Duration.ofDays(DAY_MAX_AGE);
        Instant modifiedInstant = currentInstant.minus(day);
        FileTime fileMaxAge = FileTime.from(modifiedInstant);

        boolean isTimeToUpdate = true;
        if (Files.exists(brandsListPath) && Files.exists(productTypePath) && Files.exists(repairSitesPath)) {
        	try {
				if (fileMaxAge.compareTo(Files.getLastModifiedTime(brandsListPath)) < 0 && fileMaxAge.compareTo(Files.getLastModifiedTime(productTypePath)) < 0 ) {
					isTimeToUpdate = false;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        if (isTimeToUpdate) {
        	EcologicApi ecologicApi = new EcologicApi(ecologicApiKey, isSandbox);
        	String jsonResponse = ecologicApi.getBrands();
        	updateFile(jsonResponse, ecologicBrandsListPath);
        	jsonResponse = ecologicApi.getLabeledProductType();
        	updateFile(jsonResponse, productTypeListPath);
        	jsonResponse = ecologicApi.getRepairSites();
        	updateFile(jsonResponse, repairSitesListPath);
        }
	}

	private static GetCatalog getEcosystemProductsList(String path, String fileName){
		GetCatalog productsList = null;
		String filePath = path+"/"+fileName+".json";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            JsonElement jsonElement = JsonParser.parseReader(reader);

            Gson gson = new Gson();
            Type listType = new TypeToken<GetCatalog>() {}.getType();
            productsList = gson.fromJson(jsonElement, listType);

        } catch (Exception e) {
            e.printStackTrace();
        }

		return productsList;
	}

	private static GetPartners getEcosystemPartnersList(String path, String fileName){
		GetPartners partnersList = null;
		String filePath = path+"/"+fileName+".json";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            JsonElement jsonElement = JsonParser.parseReader(reader);

            Gson gson = new Gson();
            Type listType = new TypeToken<GetPartners>() {}.getType();
            partnersList = gson.fromJson(jsonElement, listType);

        } catch (Exception e) {
            e.printStackTrace();
        }

		return partnersList;
	}

	private static void updateEcosystemFiles(String datasPath, String user, String pwd,String url) {
		String ecosystemCatalogPath = datasPath+"/catalogEcosystem.json";
		Path catalogPath = Paths.get(ecosystemCatalogPath);
		String ecosystemPartnersPath = datasPath+"/partnersEcosystem.json";
		Path partnersPath = Paths.get(ecosystemPartnersPath);

		Instant currentInstant = Instant.now();
    	Duration day = Duration.ofDays(DAY_MAX_AGE);
        Instant modifiedInstant = currentInstant.minus(day);
        FileTime fileMaxAge = FileTime.from(modifiedInstant);

        boolean isTimeToUpdate = true;
        if (Files.exists(catalogPath) && Files.exists(partnersPath)) {
        	try {
				if (fileMaxAge.compareTo(Files.getLastModifiedTime(catalogPath)) < 0 && fileMaxAge.compareTo(Files.getLastModifiedTime(partnersPath)) < 0 ) {
					isTimeToUpdate = false;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        if (isTimeToUpdate) {
        	EcosystemApi ecosystemApi = new EcosystemApi(user,pwd,url);
        	String jsonResponse = ecosystemApi.getDatas(CATALOG_QUERY);
        	updateFile(jsonResponse, ecosystemCatalogPath);
        	jsonResponse = ecosystemApi.getDatas(PARTNERS_QUERY);
        	updateFile(jsonResponse, ecosystemPartnersPath);
        }
	}

	private static void updateFile(String jsonResponse, String filePath) {

	    try (FileWriter writer = new FileWriter(filePath)) {
	        writer.write(jsonResponse);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	public static void end() {
		System.exit(0);
	}
}
