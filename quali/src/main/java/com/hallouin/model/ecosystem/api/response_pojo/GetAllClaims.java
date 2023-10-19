package com.hallouin.model.ecosystem.api.response_pojo;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class GetAllClaims {
	@SerializedName("Items")
	private List<ClaimSentInfos> claimsSentList;
	@SerializedName("Count")
	private int count;

	public List<ClaimSentInfos> getClaimsSentList() {
		return claimsSentList;
	}
	public void setClaimsSentList(List<ClaimSentInfos> claimsSentList) {
		this.claimsSentList = claimsSentList;
	}
	public int getCount() {
		return count;
	}


}
