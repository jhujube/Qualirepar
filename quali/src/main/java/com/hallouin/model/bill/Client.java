package com.hallouin.model.bill;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Client implements Serializable{

    private String gender = "";
    private String name = "";
    private String firstName = "";
    private String streetNumber = "";
    private String street = "";
    private String zipCode = "";
    private String town = "";
    private String phone = "";
    private String mail = "";

	public Client(String name) {
		this.name = name;
	}

	public Client (String gender, String name, String firstName, String streetNumber, String street, String zipCode, String town, String phone) {
		this.gender = gender;
		this.name = name;
		this.firstName = firstName;
		this.streetNumber = streetNumber;
		this.street = street;
		this.zipCode = zipCode;
		this.town = town;
		this.phone = phone;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
}