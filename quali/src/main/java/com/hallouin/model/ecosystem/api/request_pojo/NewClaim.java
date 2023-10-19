package com.hallouin.model.ecosystem.api.request_pojo;

import com.google.gson.annotations.SerializedName;

public class NewClaim {
	@SerializedName("RepairDate")
	private String repairDate;

	@SerializedName("PurchaseOrderByCustomer")
	private String purchaseOrderByCustomer;

	@SerializedName("RepairPlaceID")
	private String repairPlaceID;

	@SerializedName("RepairerId")
	private String repairerID;

	@SerializedName("Product")
	private Product product;

	@SerializedName ("SpareParts")
	//List<Spare> sparesList;
	private Spares spares;

	@SerializedName ("Customer")
	private Customer customer;

	@SerializedName ("Bill")
	private EcosystemBill bill;

	public NewClaim(String repairDate, String purchaseOrderByCustomer, String repairPlaceID, String repairerID,
			Product product, Customer customer,	EcosystemBill bill) {
		super();
		this.repairDate = repairDate;
		this.purchaseOrderByCustomer = purchaseOrderByCustomer;
		this.repairPlaceID = repairPlaceID;
		this.repairerID = repairerID;
		this.product = product;
		this.customer = customer;
		this.bill = bill;
	}

	public static class Spares {
		@SerializedName("NewSparePartsAmount")
        private Double newSparePartsAmount;

		@SerializedName("SecondHandSparePartsAmount")
        private Double secondHandSparePartsAmount;

		public Spares (Double NewSparePartsAmount, Double SecondHandSparePartsAmount) {
			this.newSparePartsAmount = NewSparePartsAmount;
			this.secondHandSparePartsAmount = SecondHandSparePartsAmount;
		}
	}
	public void setSpares(Spares spares) {
		this.spares = spares;
	}
/*
	public void setSparePartsList(List<Spare> sparesList) {
		this.sparesList = sparesList;
	}
*/
	public Product getProduct() {
		return product;
	}

}
