package com.hallouin.model.ecosystem.api.request_pojo.partners;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("serial")
public class Partner implements Serializable{
	@SerializedName("RepairerID")
	private String repairerID;
	@SerializedName("CorporateName")
	private String corporateName;
	@SerializedName("AlternativeName")
	private String alternativeName;
	@SerializedName("PostalCode")
	private String postalCode;
	@SerializedName("City")
	private String city;
	@SerializedName("Country")
	private String country;
	@SerializedName("HouseNumber")
	private String houseNumber;
	@SerializedName("StreetLine1")
	private String streetLine1;
	@SerializedName("StreetLine2")
	private String streetLine2;
	@SerializedName("StreetSuffixName")
	private String streetSuffixName;
	@SerializedName("RepairPlaces")
	private List<RepairPlace> repairPlacesList;
	@SerializedName("BusinessSheets")
	private List<BusinessSheet> businessSheetsList;


	public String getRepairerID() {
		return repairerID;
	}
	public String getCorporateName() {
		return corporateName;
	}
	public String getAlternativeName() {
		return alternativeName;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public String getCity() {
		return city;
	}
	public String getCountry() {
		return country;
	}
	public String getHouseNumber() {
		return houseNumber;
	}
	public String getStreetLine1() {
		return streetLine1;
	}
	public String getStreetLine2() {
		return streetLine2;
	}
	public String getStreetSuffixName() {
		return streetSuffixName;
	}
	public List<RepairPlace> getRepairPlacesList() {
		return repairPlacesList;
	}
	public List<BusinessSheet> getBusinessSheetsList() {
		return businessSheetsList;
	}
}
