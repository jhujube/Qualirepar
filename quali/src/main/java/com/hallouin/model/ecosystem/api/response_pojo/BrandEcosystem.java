package com.hallouin.model.ecosystem.api.response_pojo;

import com.google.gson.annotations.SerializedName;

public class BrandEcosystem {
	@SerializedName("BrandID")
	private String brandId;
	@SerializedName("BrandName")
	private String brandName;
	@SerializedName("isEcosystemBrand")
	private Boolean isEcosystemBrand;

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
