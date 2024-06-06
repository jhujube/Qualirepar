package com.hallouin.model.ecologic.api.response_pojo;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ResponseEcologic {

	@SerializedName("ResponseData")
    private ResponseData responseData;

    @SerializedName("ResponseStatus")
    private String responseStatus;

    @SerializedName ("IsValid")
    private Boolean isValid;

    @SerializedName("ResponseMessage")
    private String responseMessage;

    @SerializedName("ResponseErrorMessage")
    private String responseErrorMessage;

    public ResponseData getResponseData() {
		return responseData;
	}

	public String getResponseStatus() {
		return responseStatus;
	}

	public Boolean getIsValid() {
		return isValid;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public String getResponseErrorMessage() {
		return responseErrorMessage;
	}

    public static class ResponseData{

		@SerializedName("EcoOrganizationId")
        private String ecoOrganizationId;

    	@SerializedName("RequestId")
        private int requestId;

    	@SerializedName("ClaimId")
        private int claimId;

		@SerializedName ("IsValid")
        private Boolean isValid;

		@SerializedName ("LastStatus")
        private String lastStatus;

		@SerializedName ("Comment")
        private String comment;

		@SerializedName ("CreateDate")
        private String createDate;

        @SerializedName ("ValidationErrors")
        private List<ValidationError> validationsErrorsList;

    	public String getEcoOrganizationId() {
			return ecoOrganizationId;
		}

		public int getRequestId() {
			return requestId;
		}

        public int getClaimId() {
			return claimId;
		}

		public Boolean getIsValid() {
			return isValid;
		}

		public String getLastStatus() {
			return lastStatus;
		}

		public String getComment() {
			return comment;
		}

		public String getCreateDate() {
			return createDate;
		}

		public List<ValidationError> getValidationsErrorsList() {
			return validationsErrorsList;
		}

    }

    @SuppressWarnings("serial")
	public static class ValidationError implements Serializable{

		@SerializedName("Field")
        private String field;

    	@SerializedName("ErrorMessage")
        private String errorMessage;

        @SerializedName ("MessageType")
        private String messageType;

        public ValidationError (String field, String errorMessage, String messageType) {
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
}
