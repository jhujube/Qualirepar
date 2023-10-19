package com.hallouin.model.ecosystem.api.response_pojo;

import com.google.gson.annotations.SerializedName;

public class Spare {
	@SerializedName("Status")
	private String status;
	@SerializedName("IdentificationNumber")
	private String identificationNumber;
	@SerializedName("Quantity")
	private Double quantity;

	@SerializedName("NewSparePartsAmount")
	private Double newSparePartsAmount;
	@SerializedName("SecondHandSparePartsAmount")
	private Double secondHandSparePartsAmount;

	public Spare(String status, String identificationNumber, Double quantity) {
		super();
		this.status = status;
		this.identificationNumber = identificationNumber;
		this.quantity = quantity;
	}
	public Spare(Double newSparePartsAmount, Double secondHandSparePartsAmount) {
		super();
		this.newSparePartsAmount = newSparePartsAmount;
		this.secondHandSparePartsAmount = secondHandSparePartsAmount;
	}

	public String getStatus() {
		return status;
	}

	public String getIdentificationNumber() {
		return identificationNumber;
	}

	public Double getQuantity() {
		return quantity;
	}
	public Double getNewSparePartsAmount() {
		return newSparePartsAmount;
	}
	public Double getSecondHandSparePartsAmount() {
		return secondHandSparePartsAmount;
	}
}
