package com.hallouin.model.ecosystem.api.response_pojo;

import com.google.gson.annotations.SerializedName;

public class Amounts {
	@SerializedName("AmountValidFrom")
	private String amountValidFrom;
	@SerializedName("AmountValidUntil")
	private String amountValidUntil;
	@SerializedName("Amount")
	private String amount;
	@SerializedName("Currency")
	private String currency;

	public String getAmountValidFrom() {
		return amountValidFrom;
	}
	public String getAmountValidUntil() {
		return amountValidUntil;
	}
	public String getAmount() {
		return amount;
	}
	public String getCurrency() {
		return currency;
	}


}
