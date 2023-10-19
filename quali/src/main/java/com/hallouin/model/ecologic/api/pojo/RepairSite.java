package com.hallouin.model.ecologic.api.pojo;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("serial")
public class RepairSite implements Serializable{
	@SerializedName("SiteId")
	private String siteId;
	@SerializedName("Name")
	private String name;
	@SerializedName("CommercialName")
	private	String commercialName;
	@SerializedName("Zip")
	private String zip;
	@SerializedName("City")
	private String city;

	public String getSiteId() {
		return siteId;
	}

	public String getName() {
		return name;
	}

	public String getCommercialName() {
		return commercialName;
	}

	public String getZip() {
		return zip;
	}

	public String getCity() {
		return city;
	}
}
