package com.hallouin.model.ecosystem.api.request_pojo.partners;

import com.google.gson.annotations.SerializedName;

public class RepairPlace {
	@SerializedName("RepairPlaceID")
	private String repairPlaceID;
	@SerializedName("HouseNumber")
	private String houseNumber;
	@SerializedName("Country")
	private String country;
	@SerializedName("PostalCode")
	private String postalCode;
	@SerializedName("City")
	private String city;
	@SerializedName("CorporateName")
	private String CorporateName;
	@SerializedName("StreetLine1")
	private String streetLine1;
	@SerializedName("StreetLine2")
	private String streetLine2;
	@SerializedName("StreetSuffix")
	private String streetSuffix;
	@SerializedName("AlternativeName")
	private String alternativeName;
	@SerializedName("Siret")
	private String Siret;


	public String getRepairPlaceID() {
		return repairPlaceID;
	}
	public String getHouseNumber() {
		return houseNumber;
	}
	public String getCountry() {
		return country;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public String getCity() {
		return city;
	}
	public String getCorporateName() {
		return CorporateName;
	}
	public String getStreetLine1() {
		return streetLine1;
	}
	public String getStreetLine2() {
		return streetLine2;
	}
	public String getStreetSuffix() {
		return streetSuffix;
	}
	public String getAlternativeName() {
		return alternativeName;
	}
	public String getSiret() {
		return Siret;
	}

}
