package com.hallouin.model.ecosystem.api.response_pojo;

import com.google.gson.annotations.SerializedName;

public class RepairCodeEcosystem {
	@SerializedName("Code")
	private String code;
	@SerializedName("Description")
	private String description;

	public String getCode() {
		return code;
	}
	public String getDescription() {
		return description;
	}


}
