package com.hallouin.model.ecosystem.api.response_pojo;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class LoginInformations {
	@SerializedName("IdToken")
	private String idToken;
	@SerializedName("ExpiresIn")
    private String expiresIn;
	@SerializedName("AllowedRepairer")
    private List<String> allowedRepairer;
	@SerializedName("isIntermediary")
    private Boolean isIntermediary;


	public String getIdToken() {
		return idToken;
	}
	public String getExpiresIn() {
		return expiresIn;
	}
	public List<String> getAllowedRepairer() {
		return allowedRepairer;
	}
	public Boolean getIsIntermediary() {
		return isIntermediary;
	}
}
