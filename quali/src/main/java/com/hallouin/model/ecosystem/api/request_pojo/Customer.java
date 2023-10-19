package com.hallouin.model.ecosystem.api.request_pojo;

import com.google.gson.annotations.SerializedName;

public class Customer {
	@SerializedName("Title")
	private String title;
	@SerializedName("LastName")
	private String lastName;
	@SerializedName("FirstName")
	private String firstName;
	@SerializedName("Email")
	private String email;
	@SerializedName("PhoneNumber")
	private String phoneNumber;
	@SerializedName("StreetLine1")
	private String streetLine1;
	@SerializedName("PostalCode")
	private String postalCode;
	@SerializedName("City")
	private String city;
	@SerializedName("Country")
	private String country = "FR";

	public Customer(String title, String lastName, String firstName, String phoneNumber, String streetLine1,
			String postalCode, String city, String email) {
		super();
		this.title = title;
		this.lastName = lastName;
		this.firstName = firstName;
		this.phoneNumber = phoneNumber;
		this.streetLine1 = streetLine1;
		this.postalCode = postalCode;
		this.city = city;
		this.email = email;
	}

	public String getTitle() {
		return title;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getEmail() {
		return email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getStreetLine1() {
		return streetLine1;
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
}
