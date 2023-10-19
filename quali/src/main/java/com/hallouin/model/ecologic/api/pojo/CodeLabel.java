package com.hallouin.model.ecologic.api.pojo;

import com.google.gson.annotations.SerializedName;

public class CodeLabel {

	@SerializedName("Code")
	private String code;
	@SerializedName("Label")
	private String label;

	public CodeLabel (String code, String label) {
		this.code = code;
		this.label = label;
	}

	public String getCode() {
		return code;
	}

	public String getLabel() {
		return label;
	}
}
