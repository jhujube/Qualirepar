package com.hallouin.model.ecosystem.api.request_pojo.partners;

import com.google.gson.annotations.SerializedName;

public class BusinessSheet {
	@SerializedName("Type")
	private String type;
	@SerializedName("NumberId")
	private String numberId;
	@SerializedName("Description")
	private String description;
	@SerializedName("Institution")
	private String institution;
	@SerializedName("ValidFrom")
	private String validFrom;
	@SerializedName("ValidTo")
	private String validTo;

	public String getType() {
		return type;
	}
	public String getNumberId() {
		return numberId;
	}
	public String getDescription() {
		return description;
	}
	public String getInstitution() {
		return institution;
	}
	public String getValidFrom() {
		return validFrom;
	}
	public String getValidTo() {
		return validTo;
	}
}
