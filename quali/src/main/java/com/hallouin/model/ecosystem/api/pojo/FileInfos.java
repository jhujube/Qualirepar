package com.hallouin.model.ecosystem.api.pojo;

import com.google.gson.annotations.SerializedName;

public class FileInfos {
	@SerializedName("FileName")
	private String fileName;
	@SerializedName("FileType")
	private String fileType;
	@SerializedName("FileSizeInMB")
	private Double fileSizeInMB;

	public FileInfos(String fileName, String fileType, Double fileSizeInMB) {
		super();
		this.fileName = fileName;
		this.fileType = fileType;
		this.fileSizeInMB = fileSizeInMB;
	}

	public String getFileName() {
		return fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public Double getFileSizeInMB() {
		return fileSizeInMB;
	}

}
