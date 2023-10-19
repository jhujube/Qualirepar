package com.hallouin.model.bill;

import com.hallouin.model.ecosystem.api.request_pojo.partners.Partner;
import com.hallouin.model.ecosystem.api.response_pojo.ErrorContent;

public class EcosystemDatas {
	private Partner partner;
	private String reimbursementClaimID = "";
	private String purchaseOrderByCustomer = "";
	private String RequestStatus = "";
	private ErrorContent errorContent;
	private Boolean correctlySent;

	public Partner getPartner() {
		return partner;
	}
	public void setPartner(Partner partner) {
		this.partner = partner;
	}
	public String getReimbursementClaimID() {
		return reimbursementClaimID;
	}
	public void setReimbursementClaimID(String reimbursementClaimID) {
		this.reimbursementClaimID = reimbursementClaimID;
	}
	public String getPurchaseOrderByCustomer() {
		return purchaseOrderByCustomer;
	}
	public void setPurchaseOrderByCustomer(String purchaseOrderByCustomer) {
		this.purchaseOrderByCustomer = purchaseOrderByCustomer;
	}
	public String getRequestStatus() {
		return RequestStatus;
	}
	public void setRequestStatus(String requestStatus) {
		RequestStatus = requestStatus;
	}
	public ErrorContent getErrorContent() {
		return errorContent;
	}
	public void setErrorContent(ErrorContent errorContent) {
		this.errorContent = errorContent;
	}
	public Boolean getCorrectlySent() {
		return correctlySent;
	}
	public void setCorrectlySent(Boolean correctlySent) {
		this.correctlySent = correctlySent;
	}

}
