package com.hallouin.model.ecosystem.api.pojo;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("serial")
public class BrandEcosystem implements Serializable{
	@SerializedName("BrandID")
	private String brandId;
	@SerializedName("BrandName")
	private String brandName;
	@SerializedName("isEcosystemBrand")
	private Boolean isEcosystemBrand;

	public BrandEcosystem(String brandId, String brandName, Boolean isEcosystemBrand) {
		super();
		this.brandId = brandId;
		this.brandName = brandName;
		this.isEcosystemBrand = isEcosystemBrand;
	}
	public String getBrandId() {
		return brandId;
	}
	public String getBrandName() {
		return brandName;
	}
	public Boolean getIsEcosystemBrand() {
		return isEcosystemBrand;
	}

}
