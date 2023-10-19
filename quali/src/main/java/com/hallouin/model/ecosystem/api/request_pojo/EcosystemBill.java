package com.hallouin.model.ecosystem.api.request_pojo;

import com.google.gson.annotations.SerializedName;

public class EcosystemBill {

	@SerializedName("AmountBeforeTax")
	private AmountCurency amountBeforeTax;
	@SerializedName("LaborCost")
	private AmountCurency laborCost;
	@SerializedName("SparePartsCost")
	private AmountCurency sparePartsCost;
	@SerializedName("TravelExpenses")
	private AmountCurency travelExpenses;
	@SerializedName("TotalAmountInclVAT")
	private AmountCurency totalAmountInclVAT;
	@SerializedName("AmountCovered")
	private AmountCurency amountCovered;

	public EcosystemBill(AmountCurency amountBeforeTax, AmountCurency laborCost, AmountCurency totalAmountInclVAT,
			AmountCurency amountCovered) {
		super();
		this.amountBeforeTax = amountBeforeTax;
		this.laborCost = laborCost;
		this.totalAmountInclVAT = totalAmountInclVAT;
		this.amountCovered = amountCovered;
	}

	public EcosystemBill(AmountCurency totalAmountInclVAT, AmountCurency amountCovered) {
		super();
		this.totalAmountInclVAT = totalAmountInclVAT;
		this.amountCovered = amountCovered;
	}

	public void setSparePartsCost(AmountCurency sparePartsCost) {
		this.sparePartsCost = sparePartsCost;
	}

	public void setTravelExpenses(AmountCurency travelExpenses) {
		this.travelExpenses = travelExpenses;
	}

	public Double getAmountBeforeTax() {
		if (amountBeforeTax == null)
			this.amountBeforeTax = new AmountCurency(0.0);
		return amountBeforeTax.getAmount();
	}

	public Double getLaborCost() {
		if (laborCost == null)
			this.laborCost = new AmountCurency(0.0);
		return laborCost.getAmount();
	}

	public Double getSparePartsCost() {
		if (sparePartsCost == null)
			this.sparePartsCost = new AmountCurency(0.0);
		return sparePartsCost.getAmount();
	}

	public Double getTravelExpenses() {
		if (travelExpenses == null)
			this.travelExpenses = new AmountCurency(0.0);
		return travelExpenses.getAmount();
	}

	public Double getTotalAmountInclVAT() {
		return totalAmountInclVAT.getAmount();
	}

	public Double getAmountCovered() {
		return amountCovered.getAmount();
	}

}
