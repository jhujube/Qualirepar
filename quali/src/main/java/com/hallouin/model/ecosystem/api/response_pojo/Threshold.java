package com.hallouin.model.ecosystem.api.response_pojo;

import com.google.gson.annotations.SerializedName;

public class Threshold {
	@SerializedName("AmountValidFrom")
	private String amountValidFrom;
	@SerializedName("AmountValidTo")
	private String amountValidTo;
	@SerializedName("MinimumAmount")
	private String minimumAmount;
	@SerializedName("Currency")
	private String currency;

	public String getAmountValidFrom() {
		return amountValidFrom;
	}
	public String getAmountValidTo() {
		return amountValidTo;
	}
	public String getMinimumAmount() {
		return minimumAmount;
	}
	public String getCurrency() {
		return currency;
	}

}
