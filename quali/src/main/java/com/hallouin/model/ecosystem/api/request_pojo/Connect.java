package com.hallouin.model.ecosystem.api.request_pojo;

import com.google.gson.annotations.SerializedName;

public class Connect {
	@SerializedName("username")
	private String username;
	@SerializedName("password")
    private String password;

	public Connect(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getusername() {
		return username;
	}
	public String getpassword() {
		return password;
	}
}
