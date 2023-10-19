package com.hallouin.model.bill;

import java.io.Serializable;

import com.hallouin.model.ecosystem.api.pojo.BrandEcosystem;

@SuppressWarnings("serial")
public class Device implements Serializable{
	private BrandEcosystem brand;
	private String serial;
    private String manualBrandName;
	private String reference;
    private String type;
    private String symptom;
    private String irisSymptomCode;
    private String irisSectionCode;
    private String productName;
    private String productId;
    private String brandId;
    private String brandName;
	private Boolean isEcosystem;

	public Device(String reference, String serial) {
		this.reference = reference;
		this.serial = serial;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String type) {
		this.productName = type;
	}


	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getManualBrandName() {
		return manualBrandName;
	}

	public void setManualBrandName(String manualBrandName) {
		this.manualBrandName = manualBrandName;
	}
	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getSymptom() {
		return symptom;
	}

	public void setSymptom(String symptom) {
		this.symptom = symptom;
	}


	public String getIrisSymptomCode() {
		return irisSymptomCode;
	}

	public void setIrisSymptomCode(String irisSymptomCode) {
		this.irisSymptomCode = irisSymptomCode;
	}

	public String getIrisSectionCode() {
		return irisSectionCode;
	}

	public void setIrisSectionCode(String irisSectionCode) {
		this.irisSectionCode = irisSectionCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getIsEcosystem() {
		return isEcosystem;
	}

	public void setIsEcosystem(Boolean isEcosystem) {
		this.isEcosystem = isEcosystem;
	}

	public BrandEcosystem getBrand() {
		return brand;
	}

	public void setBrand(BrandEcosystem brand) {
		this.brand = brand;
	}


}