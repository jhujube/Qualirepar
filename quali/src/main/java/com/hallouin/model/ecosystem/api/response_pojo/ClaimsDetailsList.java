package com.hallouin.model.ecosystem.api.response_pojo;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ClaimsDetailsList {
	@SerializedName("Items")
	private List <ClaimDetails> claimsDetailsList;

	@SerializedName("Count")
	private int count;

	public List<ClaimDetails> getClaimsDetailsList() {
		return claimsDetailsList;
	}
	public int getCount() {
		return count;
	}
}
