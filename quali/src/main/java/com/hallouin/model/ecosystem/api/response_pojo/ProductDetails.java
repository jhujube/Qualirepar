package com.hallouin.model.ecosystem.api.response_pojo;

import com.google.gson.annotations.SerializedName;

public class ProductDetails {
	@SerializedName("BrandName")
	private String brandName;
	@SerializedName("ProductSubCategory")
	private String productSubCategory;
	@SerializedName("RepairTypeCode")
	private String repairTypeCode;
	@SerializedName("ProductName")
	private String productName;
	@SerializedName("CommercialReference")
	private String commercialReference;
	@SerializedName("ProductIdentificationNumber")
	private String productIdentificationNumber;
	@SerializedName("RepairDescription")
	private String repairDescription;
	@SerializedName("ProductId")
	private String productId;
	@SerializedName("BrandId")
	private String brandId;
	@SerializedName("PartnerProduct")
	private String manualBrandName;
	@SerializedName("IrisCode")
	private String irisCode;
	public String getBrandName() {
		return brandName;
	}
	public String getProductSubCategory() {
		return productSubCategory;
	}
	public String getRepairTypeCode() {
		return repairTypeCode;
	}
	public String getProductName() {
		return productName;
	}
	public String getCommercialReference() {
		return commercialReference;
	}
	public String getProductIdentificationNumber() {
		return productIdentificationNumber;
	}
	public String getRepairDescription() {
		return repairDescription;
	}
	public String getProductId() {
		return productId;
	}
	public String getBrandId() {
		return brandId;
	}
	public String getManualBrandName() {
		return manualBrandName;
	}
	public String getIrisCode() {
		return irisCode;
	}

}
