package com.hallouin.model.datas;

import java.util.List;

import com.hallouin.model.ecologic.api.pojo.BrandEcologic;
import com.hallouin.model.ecologic.api.pojo.LabeledProductType;
import com.hallouin.model.ecologic.api.pojo.RepairSite;
import com.hallouin.model.ecosystem.api.pojo.Product;
import com.hallouin.model.ecosystem.api.request_pojo.partners.Partner;


public class AppDatas {

	private String sandBox;
	private String ecosystemPwd;
	private String ecosystemUser;
	private String ecosystemUrl;
	private String ecologicApiKey;
	private String billPath;
	private String datasPath;
	private String ecosystemRef;
	private String firefoxPath;
	private String gekoPath;
	private List<BrandEcologic> brands;
	private List<LabeledProductType> labeledProductType;
	private List<RepairSite> RepairSitesList;
	private List<Product> ecosystemProducts;
	private List<Partner> ecosystemPartners;

	public AppDatas (String sandBox,String ecosystemUser, String ecosystemPwd, String ecosystemUrl, String ecologicApiKey, String billPath, String datasPath, String firefoxPath, String gekoPath, List<BrandEcologic> brandList, List<LabeledProductType> labeledProductList, List<RepairSite> repairSitesList, List<Product> ecosystemProducts, List<Partner> ecosystemPartners) {
		this.sandBox = sandBox;
		this.ecosystemPwd = ecosystemPwd;
		this.ecosystemUser = ecosystemUser;
		this.ecosystemUrl = ecosystemUrl;
		this.ecologicApiKey = ecologicApiKey;
		this.billPath = billPath;
		this.datasPath = datasPath;
		this.firefoxPath = firefoxPath;
		this.gekoPath = gekoPath;
		this.brands = brandList;
		this.labeledProductType = labeledProductList;
		this.RepairSitesList = repairSitesList;
		this.ecosystemProducts = ecosystemProducts;
		this.ecosystemPartners = ecosystemPartners;
	}

	public String getSandBox() {
		return sandBox;
	}
	public String getEcologicApiKey() {
		return ecologicApiKey;
	}

	public String getEcosystemUrl() {
		return ecosystemUrl;
	}

	public String getEcosystemPwd() {
		return ecosystemPwd;
	}

	public String getEcosystemUser() {
		return ecosystemUser;
	}

	public String getBillPath() {
		return billPath;
	}

	public String getDatasPath() {
		return datasPath;
	}

	public String getFirefoxPath() {
		return firefoxPath;
	}

	public String getGekoPath() {
		return gekoPath;
	}

	public String getEcosystemRef() {
		return ecosystemRef;
	}

	public void setEcosystemRef(String ecosystemRef) {
		this.ecosystemRef = ecosystemRef;
	}

	public List<BrandEcologic> getBrandsList() {
		return brands;
	}

	public List<LabeledProductType> getLabeledProductTypeList() {
		return labeledProductType;
	}

	public List<RepairSite> getRepairSitesList() {
		return RepairSitesList;
	}

	public List<Product> getEcosystemProducts() {
		return ecosystemProducts;
	}

	public List<Partner> getEcosystemPartners() {
		return ecosystemPartners;
	}



}