package com.hallouin.model.ecosystem.api.request_pojo.partners;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Partners {
	@SerializedName("Items")
    private List<Partner>partnersList;

	public List<Partner> getPartnersList() {
		return partnersList;
	}
}
