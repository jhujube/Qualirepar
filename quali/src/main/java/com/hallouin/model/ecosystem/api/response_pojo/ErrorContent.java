package com.hallouin.model.ecosystem.api.response_pojo;

import com.google.gson.annotations.SerializedName;

public class ErrorContent {
	@SerializedName("code")
	private int code;
	@SerializedName("status")
	private String status = "";
	@SerializedName("message")
	private String message = "";

	public int getCode() {
		return code;
	}
	public String getStatus() {
		return status;
	}
	public String getMessage() {
		return message;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
