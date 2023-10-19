package com.hallouin.model.ecosystem.pojo;

public class Id_Name {
	private String idString;
	private String nameString;

	public Id_Name(String idString, String nameString) {
		super();
		this.idString = idString;
		this.nameString = nameString;
	}
	public String getId() {
		return idString;
	}
	public void setId(String idString) {
		this.idString = idString;
	}
	public String getName() {
		return nameString;
	}
	public void setName(String nameString) {
		this.nameString = nameString;
	}


}
