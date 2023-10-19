package com.hallouin.model.bill;

import java.io.Serializable;

@SuppressWarnings("serial")
public class FileInformations implements Serializable{
	private String path;
	private String name;
	private String extension;
	private String type;
	private Double size;

	public FileInformations(String path, String name, String extension, String type, Double size) {
		super();
		this.path = path;
		this.name = name;
		this.extension = extension.toLowerCase();
		this.type = type;
		this.size = size;
	}

	public String getPath() {
		return path;
	}

	public String getName() {
		return name;
	}

	public String getExtension() {
		return extension;
	}

	public String getType() {
		return type;
	}

	public Double getSize() {
		return size;
	}

	@Override
	public String toString() {
		return "Path "+path+" Name :"+name+" Extension :"+extension+" Type :"+type+" Taille :"+size;
	}
}
