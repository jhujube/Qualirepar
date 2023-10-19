package com.hallouin.model.ecosystem.api.response_pojo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import com.hallouin.model.ecosystem.api.pojo.FileInfos;
import com.hallouin.model.ecosystem.api.request_pojo.Customer;
import com.hallouin.model.ecosystem.api.request_pojo.EcosystemBill;
import com.hallouin.model.ecosystem.api.request_pojo.Repairer;
import com.hallouin.model.ecosystem.api.request_pojo.partners.RepairPlace;

public class ClaimDetails {
	@SerializedName ("SpareParts")
    private JsonElement spareParts;
	@SerializedName ("RepairDate")
	String repairDate;
	@SerializedName ("ReimbursementClaimID")
	String reimbursementClaimID;
	@SerializedName ("Bill")
	private EcosystemBill bill;
	@SerializedName("RepairPlace")
	private RepairPlace repairPlace;
	@SerializedName("PurchaseOrderByCustomer")
	private String purchaseOrderByCustomer;
	@SerializedName("Product")
	private ProductDetails product;
	@SerializedName ("SapServiceOrder")
	String sapServiceOrder;
	@SerializedName("Repairer")
	private Repairer repairer;
	@SerializedName ("RejectReason")
	String rejectReason;
	@SerializedName ("RequestStatus")
	String requestStatus;
	@SerializedName("AttachedFiles")
	private List<FileInfos> filesInfosList;
	@SerializedName("Customer")
	private Customer customer;

	public List<Spare> getSparesList() {
		if (spareParts != null) {
            if (spareParts.isJsonArray()) {
                // Si SpareParts est un tableau JSON, le convertir en liste de Spare
                return Arrays.asList(new Gson().fromJson(spareParts, Spare[].class));
            } else if (spareParts.isJsonObject()) {
            	List<Spare> singleSpare = new ArrayList<>();
            	Spare spare = new Gson().fromJson(spareParts, Spare.class);
            	singleSpare.add(spare);
                // Si SpareParts est un objet JSON, le convertir en un seul objet Spare
                return singleSpare;
            }
        }
        // Si SpareParts est null ou d'un type inattendu, retourner null ou une valeur par défaut selon le cas
        return null;
	}

	public String getRepairDate() {
		return repairDate;
	}
	public String getReimbursementClaimID() {
		return reimbursementClaimID;
	}
	public EcosystemBill getBill() {
		return bill;
	}
	public RepairPlace getRepairPlace() {
		return repairPlace;
	}
	public String getPurchaseOrderByCustomer() {
		return purchaseOrderByCustomer;
	}
	public ProductDetails getProduct() {
		return product;
	}
	public String getSapServiceOrder() {
		return sapServiceOrder;
	}
	public Repairer getRepairer() {
		return repairer;
	}
	public String getRejectReason() {
		return rejectReason;
	}
	public String getRequestStatus() {
		return requestStatus;
	}
	public List<FileInfos> getFilesInfosList() {
		return filesInfosList;
	}
	public Customer getCustomer() {
		return customer;
	}

}
