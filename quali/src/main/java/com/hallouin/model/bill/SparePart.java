package com.hallouin.model.bill;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SparePart implements Serializable{
	private final Double vat = 20.00;
	private String ref;
	private String designation;
	private Double qty;
	private Double unitPExclVat;
	private Double unitPVat;
	private Double totalPExclVat;
	private Double totalPVat;

	/*
	public SparePart (String ref, Double qty, Double unitPExclVat, Double totalPExclVat) {
		this.ref = ref;
		this.qty = qty;
		this.unitPExclVat = unitPExclVat;
		this.unitPVat = addVat(unitPExclVat);
		this.totalPExclVat = totalPExclVat;
		this.totalPVal = addVat(totalPExclVat);

	}
	*/

	public SparePart (String ref, Double qty, Double unitPVat, Double totalPVat) {
		this.ref = ref;
		this.qty = qty;
		this.unitPVat = unitPVat;
		this.unitPExclVat = exclVat(unitPVat);
		this.totalPVat = totalPVat;
		this.totalPExclVat = exclVat(totalPVat);

	}

	public Double getUnitPVat() {
		return unitPVat;
	}

	public Double getTotalPVat() {
		return totalPVat;
	}

	public Double getTotalPExclVat() {
		return totalPExclVat;
	}

	public Double getUnitPExclVat() {
		return unitPExclVat;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public Double getQty() {
		return qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	private Double addVat(Double priceExclVat) {

		double priceInclVat = priceExclVat*(1+vat/100);
		priceInclVat = Math.round(priceInclVat*100.0)/100.0;

		return priceInclVat;
	}
	private Double exclVat(Double priceVat) {

		double priceExclVat = priceVat/(1+vat/100);
		priceExclVat = Math.round(priceExclVat*100.0)/100.0;

		return priceExclVat;
	}
}