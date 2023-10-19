package com.hallouin.model.ecosystem.api.pojo;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.hallouin.model.ecosystem.api.response_pojo.Amounts;
import com.hallouin.model.ecosystem.api.response_pojo.ProductSheet;
import com.hallouin.model.ecosystem.api.response_pojo.RepairCodeEcosystem;
import com.hallouin.model.ecosystem.api.response_pojo.Threshold;

public class Product {
	@SerializedName("ProductID")
	private String productID;
	@SerializedName("ProductName")
	private String productName;
	@SerializedName("ProductEligibleFrom")
	private String productEligibleFrom;
	@SerializedName("ProductEligibleUntil")
	private String ProductEligibleUntil;
	@SerializedName("Brands")
	private List<BrandEcosystem> brandsList;
	@SerializedName("RepairCode")
	private List<RepairCodeEcosystem> repairCodesList;
	@SerializedName("Amounts")
	private List<Amounts> amountsList;
	@SerializedName("ProductSheet")
	private ProductSheet productSheet;
	@SerializedName("Threshold")
	private List<Threshold> ThresholdsList;


	public Product(String productName) {
		super();
		this.productName = productName;
	}

	public String getProductID() {
		return productID;
	}
	public String getProductName() {
		return productName;
	}
	public String getProductEligibleFrom() {
		return productEligibleFrom;
	}
	public String getProductEligibleUntil() {
		return ProductEligibleUntil;
	}
	public List<BrandEcosystem> getBrandsList() {
		return brandsList;
	}
	public List<RepairCodeEcosystem> getRepairCodesList() {
		return repairCodesList;
	}
	public List<Amounts> getAmountsList() {
		return amountsList;
	}
	public ProductSheet getProductSheet() {
		return productSheet;
	}
	public List<Threshold> getThresholdsList() {
		return ThresholdsList;
	}
}
