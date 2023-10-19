package com.hallouin.model.ecologic.api.response_pojo;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.hallouin.model.ecologic.api.pojo.RepairSite;

public class ResponseEcologicRepairSitesList {
	@SerializedName("ResponseData")
	private List<RepairSite> repairSites;

	public List<RepairSite> getRepairSites() {
		return repairSites;
	}
}
