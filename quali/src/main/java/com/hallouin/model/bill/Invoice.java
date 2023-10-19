package com.hallouin.model.bill;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Invoice implements Serializable{

	private Double totalVat = 0.0;
    private Double totalExclVat = 0.0;
    private Double totalSparesExclVat = 0.0;
	private Double advance;
	private Double supportAmount;

	public Invoice(Double totalVat, Double totalExclVat,
			Double totalSparesExclVat) {
		super();

		this.totalVat = totalVat;
		this.totalExclVat = totalExclVat;
		this.totalSparesExclVat = totalSparesExclVat;

	}

	public Double getTotalVat() {
		return totalVat;
	}

	public void setTotalVat(Double totalVat) {
		this.totalVat = totalVat;
	}

	public Double getTotalExclVat() {
		return totalExclVat;
	}

	public void setTotalExclVat(Double totalExclVat) {
		this.totalExclVat = totalExclVat;
	}

	public Double getTotalSparesExclVat() {
		return totalSparesExclVat;
	}

	public void setTotalSparesExclVat(Double totalSparesExclVat) {
		this.totalSparesExclVat = totalSparesExclVat;
	}

	public Double getAdvance() {
		return advance;
	}

	public void setAdvance(Double advance) {
		this.advance = advance;
	}

	public Double getSupportAmount() {
		return supportAmount;
	}

	public void setSupportAmount(Double supportAmount) {
		this.supportAmount = supportAmount;
	}

}
