package com.hallouin.model.ecosystem.api.response_pojo;

import com.google.gson.annotations.SerializedName;

public class RqError {
	@SerializedName("error")
	private ErrorContent error;

	public ErrorContent getError() {
		return error;
	}

}
