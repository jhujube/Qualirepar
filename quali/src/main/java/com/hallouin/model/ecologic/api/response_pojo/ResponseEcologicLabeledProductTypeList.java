package com.hallouin.model.ecologic.api.response_pojo;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.hallouin.model.ecologic.api.pojo.LabeledProductType;

public class ResponseEcologicLabeledProductTypeList {
	@SerializedName("ResponseData")
	private List<LabeledProductType> labeledProductTypes;

	public List<LabeledProductType> getLabeledProductTypes() {
		return labeledProductTypes;
	}
}
