package com.hallouin.model.ecosystem.api.response_pojo;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.hallouin.model.ecosystem.api.request_pojo.partners.Partner;


public class GetPartners {
	@SerializedName("Items")
	private List<Partner> partnersList;

	public List<Partner> getPartnersList() {
		return partnersList;
	}

}
