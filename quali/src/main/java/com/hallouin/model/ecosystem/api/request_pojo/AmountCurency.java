package com.hallouin.model.ecosystem.api.request_pojo;

import com.google.gson.annotations.SerializedName;

public class AmountCurency {
	@SerializedName("amount")
	private Double amount;
	@SerializedName("currency")
	private String currency = "EUR";

	public AmountCurency(Double amount) {
		super();
		this.amount = amount;
	}

	public Double getAmount() {
		return amount;
	}

	public String getCurrency() {
		return currency;
	}


}
