package com.hallouin.model.ecologic.api.pojo;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class SupportRequestResponse {

	@SerializedName("EcoOrganizationId")
	private Integer ecoOrganizationId;
	@SerializedName("RequestId")
	private Integer requestId;
	@SerializedName("IsValid")
	private Boolean isValid;
	@SerializedName ("ValidationErrors")
    private List<ValidationErrors> validationsErrors;

	public SupportRequestResponse (Integer ecoOrganizationId, Integer requestId, Boolean isValid) {
		this.ecoOrganizationId = ecoOrganizationId;
		this.requestId = requestId;
		this.isValid = isValid;
	}

	public Integer getEcoOrganizationId() {
		return ecoOrganizationId;
	}

	public Integer getRequestId() {
		return requestId;
	}

	public Boolean getIsValid() {
		return isValid;
	}

	public List<ValidationErrors> getValidationErrors() {
		return validationsErrors;
	}

}
