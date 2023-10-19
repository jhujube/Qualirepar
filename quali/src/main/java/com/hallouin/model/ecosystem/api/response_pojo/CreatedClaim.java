package com.hallouin.model.ecosystem.api.response_pojo;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class CreatedClaim {
	@SerializedName("ReimbursementClaimID")
	private String reimbursementClaimID;
	@SerializedName("RequestStatus")
	private String requestStatus;
	@SerializedName("RepairDate")
	private String repairDate;
	@SerializedName("PurchaseOrderByCustomer")
	private String purchaseOrderByCustomer;
	@SerializedName("AttachedFiles")
	private List <String> attachedFiles;
	@SerializedName("CreationDate")
	private String creationDate;

	public CreatedClaim(String reimbursementClaimID, String requestStatus, String repairDate,
			String purchaseOrderByCustomer, List<String> attachedFiles, String creationDate) {
		super();
		this.reimbursementClaimID = reimbursementClaimID;
		this.requestStatus = requestStatus;
		this.repairDate = repairDate;
		this.purchaseOrderByCustomer = purchaseOrderByCustomer;
		this.attachedFiles = attachedFiles;
		this.creationDate = creationDate;
	}
	public String getReimbursementClaimID() {
		return reimbursementClaimID;
	}
	public String getRequestStatus() {
		return requestStatus;
	}
	public String getRepairDate() {
		return repairDate;
	}
	public String getPurchaseOrderByCustomer() {
		return purchaseOrderByCustomer;
	}
	public List<String> getAttachedFiles() {
		return attachedFiles;
	}
	public String getCreationDate() {
		return creationDate;
	}
}
