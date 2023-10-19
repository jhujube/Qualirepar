package com.hallouin.model.bill;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Displacement implements Serializable{
	private Double qty;
	private Double unitPExclVat;
	private Double totalExclVat;

	public Displacement (Double qty) {
		this.qty = qty;
	}

	public Displacement (Double qty, Double unitPExclVat, Double totalExclVat) {
		this.qty = qty;
		this.unitPExclVat = unitPExclVat;
		this.totalExclVat = totalExclVat;
	}

	public Double getQty() {
		return qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	public Double getUnitPExclVat() {
		return unitPExclVat;
	}

	public void setUnitPExclVat(Double unitPExclVat) {
		this.unitPExclVat = unitPExclVat;
	}

	public Double getTotalExclVat() {
		return totalExclVat;
	}

	public void setTotalExclVat(Double totalExclVat) {
		this.totalExclVat = totalExclVat;
	}
}