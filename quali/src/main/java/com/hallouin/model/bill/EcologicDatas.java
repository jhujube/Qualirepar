package com.hallouin.model.bill;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.hallouin.model.ecologic.api.pojo.RepairSite;
import com.hallouin.model.ecologic.api.response_pojo.ResponseEcologic.ValidationError;

@SuppressWarnings("serial")
public class EcologicDatas implements Serializable{
	private int requestId = 0 ;
	private int claimId = 0;
	private LocalDateTime creationDate;
	private String quoteNumber;
	private RepairSite repairSite;
	private List<ValidationError> validationErrorsList;
	private String lastStatus;
	private Boolean isRequestCreated;
	private Boolean isClaimCreated;

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public void setClaimId(int claimId) {
		this.claimId = claimId;
	}

	public int getRequestId() {
		return requestId;
	}

	public int getClaimId() {
		return claimId;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public RepairSite getRepairSite() {
		return repairSite;
	}

	public void setRepairSite(RepairSite site) {
		this.repairSite = site;
	}
	public String getQuoteNumber() {
		return quoteNumber;
	}

	public void setQuoteNumber(String quoteNumber) {
		this.quoteNumber = quoteNumber;
	}
	public List<ValidationError> getErrorsList() {
		return validationErrorsList;
	}
	public void setValidationErrorsList(List<ValidationError> validationErrorsList) {
		this.validationErrorsList = validationErrorsList;
	}
	public String getLastStatus() {
		return lastStatus;
	}

	public void setLastStatus(String lastStatus) {
		this.lastStatus = lastStatus;
	}
	public Boolean getIsRequestCreated() {
		return isRequestCreated;
	}
	public void setIsRequestCreated(Boolean isRequestCreated) {
		this.isRequestCreated = isRequestCreated;
	}

	public Boolean getIsClaimCreated() {
		return isClaimCreated;
	}

	public void setIsClaimCreated(Boolean isClaimCreated) {
		this.isClaimCreated = isClaimCreated;
	}
}
