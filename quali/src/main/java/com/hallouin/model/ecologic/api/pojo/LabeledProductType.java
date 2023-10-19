package com.hallouin.model.ecologic.api.pojo;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class LabeledProductType implements Comparable<LabeledProductType>{

	@SerializedName("ProductId")
	private String productId;
	@SerializedName("ProductName")
	private String productName;
	@SerializedName("EligibilityStartDate")
	private	String eligibilityStartDate;
	@SerializedName("EligibilityEndDate")
	private String eligibilityEndDate;
	@SerializedName("RepairCodes")
	private List<CodeLabel> repairCodes;
	@SerializedName("IRISSymtoms")
	private List<CodeLabel> iRISSymtoms;

	public String getProductId() {
		return productId;
	}
	public String getProductName() {
		return productName;
	}
	public String getEligibilityStartDate() {
		return eligibilityStartDate;
	}
	public String getEligibilityEndDate() {
		return eligibilityEndDate;
	}
	public List<CodeLabel> getRepairCodes() {
		return repairCodes;
	}
	public List<CodeLabel> getiRISSymtoms() {
		return iRISSymtoms;
	}
	 @Override
	    public int compareTo(LabeledProductType other) {
	        return this.productName.compareTo(other.productName);
	    }

}
