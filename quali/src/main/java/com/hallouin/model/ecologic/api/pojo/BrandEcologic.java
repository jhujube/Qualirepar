package com.hallouin.model.ecologic.api.pojo;

import com.google.gson.annotations.SerializedName;

public class BrandEcologic {

	@SerializedName("BrandName")
	private String BrandName;
	@SerializedName("BrandId")
    private String BrandId;


	public String getBrandName() {
		return BrandName;
	}
	public String getBrandId() {
		return BrandId;
	}

}
