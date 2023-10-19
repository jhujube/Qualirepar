package com.hallouin.model.ecosystem.api.response_pojo;

import com.google.gson.annotations.SerializedName;

public class ProductSheet {

		@SerializedName("Type")
		private String type;
		@SerializedName("ExpectedIdType")
		private String expectedIdType;
		@SerializedName("DefectIdType")
		private String defectIdType;
		@SerializedName("ValidFrom")
		private String validFrom;
		@SerializedName("ValidTo")
		private String validTo;

		public String getType() {
			return type;
		}
		public String getExpectedIdType() {
			return expectedIdType;
		}
		public String getDefectIdType() {
			return defectIdType;
		}
		public String getValidFrom() {
			return validFrom;
		}
		public String getValidTo() {
			return validTo;
		}
}
