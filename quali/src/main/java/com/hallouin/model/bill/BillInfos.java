package com.hallouin.model.bill;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BillInfos implements Serializable{
	private String billNumber;
	private String repairDate;
	public BillInfos(String billNumber, String repairDate) {
		super();
		this.billNumber = billNumber;
		this.repairDate = repairDate;
	}
	public String getBillNumber() {
		return billNumber;
	}
	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}
	public String getRepairDate() {
		return repairDate;
	}
	public void setRepairDate(String repairDate) {
		this.repairDate = repairDate;
	}

}
