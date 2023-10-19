package com.hallouin.model.ecosystem.api.request_pojo;

import com.google.gson.annotations.SerializedName;

public class Repairer {
	@SerializedName("HouseNumber")
	private String houseNumber;
	@SerializedName("StreetSuffixName")
	private String streetSuffixName;
	@SerializedName("PostalCode")
	private String postalCode;
	@SerializedName("Country")
	private String country;
	@SerializedName("City")
	private String city;
	@SerializedName("RepairerID")
	private String repairerID;
	@SerializedName("StreetLine2")
	private String streetLine2;
	@SerializedName("CorporateName")
	private String corporateName;
	@SerializedName("AlternativeName")
	private String alternativeName;
	@SerializedName("StreetLine1")
	private String streetLine1;

	public String getHouseNumber() {
		return houseNumber;
	}
	public String getStreetSuffixName() {
		return streetSuffixName;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public String getCountry() {
		return country;
	}
	public String getCity() {
		return city;
	}
	public String getRepairerID() {
		return repairerID;
	}
	public String getStreetLine2() {
		return streetLine2;
	}
	public String getCorporateName() {
		return corporateName;
	}
	public String getAlternativeName() {
		return alternativeName;
	}
	public String getStreetLine1() {
		return streetLine1;
	}

}
