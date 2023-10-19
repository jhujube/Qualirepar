package com.hallouin.model.ecologic.api.response_pojo;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.hallouin.model.ecologic.api.pojo.BrandEcologic;

public class ResponseEcologicBrandsList {

	@SerializedName("ResponseData")
	private List<BrandEcologic> brandEcologic;

	public List<BrandEcologic> getBrandEcologic() {
		return brandEcologic;
	}

}
