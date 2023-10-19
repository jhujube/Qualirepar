package com.hallouin.model.ecosystem.api.response_pojo;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.hallouin.model.ecosystem.api.pojo.Product;

public class GetCatalog {
	@SerializedName("total")
	private int total;
	@SerializedName("results")
    private List<Product>productsList;

	public int getTotal() {
		return total;
	}
	public List<Product> getProductsList() {
		return productsList;
	}
}
