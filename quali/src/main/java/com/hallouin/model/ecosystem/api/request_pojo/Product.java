package com.hallouin.model.ecosystem.api.request_pojo;

import com.google.gson.annotations.SerializedName;

public class Product {
	@SerializedName("ProductID")
	private String productID;
	@SerializedName("BrandID")
	private String brandID;
	@SerializedName("PartnerProduct")
	private String partnerProduct;
	@SerializedName("ProductIdentificationNumber")
	private String productIdentificationNumber;
	@SerializedName("RepairTypeCode")
	private String repairTypeCode;
	@SerializedName("IrisCode")
	private String irisCode = "Code IRIS";
	@SerializedName("CommercialReference")
	private String commercialReference;

	public Product(String productID, String brandID, String productIdentificationNumber, String repairTypeCode,
			String commercialReference) {
		super();
		this.productID = productID;
		this.brandID = brandID;
		this.productIdentificationNumber = productIdentificationNumber;
		this.repairTypeCode = repairTypeCode;
		this.commercialReference = commercialReference;
	}

	public void setIrisCode(String irisCode) {
		this.irisCode = irisCode;
	}

	public void setPartnerProduct(String partnerProduct) {
		this.partnerProduct = partnerProduct;
	}

}
