package com.hallouin.model.ecosystem.api.response_pojo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.hallouin.model.ecosystem.api.pojo.FileInfos;
import com.hallouin.model.ecosystem.api.request_pojo.partners.Partner;

public class ClaimSentInfos {
	@SerializedName("CreationDate")
	private String creationDate;
	@SerializedName("RepairDate")
	private String RepairDate;
	@SerializedName("ReimbursementClaimID")
	private String reimbursementClaimID;
	@SerializedName("PurchaseOrderByCustomer")
	private String PurchaseOrderByCustomer;
	@SerializedName("Repairer")
	private Partner repairer;
	@SerializedName("SapServiceOrder")
	private String sapServiceOrder;
	@SerializedName("RequestStatus")
	private String requestStatus;
	@SerializedName("AttachedFiles")
	private List<FileInfos> attachedFiles;

	public Date getCreationDate(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
		Date date = null;
		try {
			date = dateFormat.parse(creationDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	public String getRepairDate() {
		return RepairDate;
	}
	public String getReimbursementClaimID() {
		return reimbursementClaimID;
	}
	public String getPurchaseOrderByCustomer() {
		return PurchaseOrderByCustomer;
	}
	public Partner getRepairer() {
		return repairer;
	}
	public String getSapServiceOrder() {
		return sapServiceOrder;
	}
	public String getRequestStatus() {
		return requestStatus;
	}
	public List<FileInfos> getAttachedFiles() {
		return attachedFiles;
	}

}
