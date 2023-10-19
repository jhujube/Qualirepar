package com.hallouin.model.bill;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Labour implements Serializable{
	private Double qty = 0.0;
	private Double unitPExclVat = 0.0;
	private Double totalExclVat = 0.0;

	public Labour (Double qty, Double unitPVat, Double totalVat) {
		this.qty = qty;
		this.unitPExclVat = unitPVat;
		this.totalExclVat = totalVat;
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