package com.hallouin.model.ecologic.api.pojo;

import com.google.gson.annotations.SerializedName;

public class ValidationErrors {

	@SerializedName("Field")
	private String field ;

	@SerializedName("ErrorMessage")
    private String errorMessage;

	@SerializedName("MessageType")
    private String messageType;

	public ValidationErrors (String field, String errorMessage, String messageType) {
		this.field = field;
		this.errorMessage = errorMessage;
		this.messageType = messageType;
	}

	public String getField() {
		return field;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getMessageType() {
		return messageType;
	}

}
