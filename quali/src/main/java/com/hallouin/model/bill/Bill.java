package com.hallouin.model.bill;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Bill implements Serializable{
	private BillInfos billInfos;
	private Invoice invoice;
	private Client client;
	private Device device;
	private Labour labour;
	private Displacement displacement;
	private EcologicDatas ecologicDatas;
	private transient EcosystemDatas ecosystemDatas;	// rend l'objet non sérialisable
	private List<FileInformations> filesList;
	private List<SparePart> sparesList;
	private Boolean isEcosystem;

	public Bill(Client client, Device device) {
        super();
        this.client = client;
        this.device = device;
        this.ecosystemDatas = new EcosystemDatas();
        this.ecologicDatas = new EcologicDatas();
        this.filesList = new ArrayList<>();
    }

	public Bill(BillInfos billInfos, Invoice invoice, Client client, Device device, Labour labour, Displacement displacement, List<SparePart> sparesList) {
        super();
        this.billInfos = billInfos;
        this.invoice = invoice;
        this.client = client;
        this.device = device;
        this.labour = labour;
        this.displacement = displacement;
        this.sparesList = sparesList;
        this.ecosystemDatas = new EcosystemDatas();
        this.ecologicDatas = new EcologicDatas();
        this.filesList = new ArrayList<>();
    }

	public Boolean getIsEcosystem() {
		return isEcosystem;
	}

	public void setIsEcosystem(Boolean isEcosystem) {
		this.isEcosystem = isEcosystem;
	}

	public BillInfos getBillInfos() {
		return billInfos;
	}

	public void setBillInfos(BillInfos billInfos) {
		this.billInfos = billInfos;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public Client getClient() {
		return client;
	}

	public Device getDevice() {
		return device;
	}

	public Labour getLabour() {
		return labour;
	}

	public void setLabour(Labour labour) {
		this.labour = labour;
	}

	public Displacement getDisplacement() {
		return displacement;
	}

	public void setDisplacement(Displacement displacement) {
		this.displacement = displacement;
	}

	public List<SparePart> getSparesList() {
		return sparesList;
	}

	public void setSparesList(List<SparePart> sparesList) {
		this.sparesList = sparesList;
	}

	public EcologicDatas getEcologicDatas() {
		return ecologicDatas;
	}

	public void setEcologicDatas(EcologicDatas ecologicDatas) {
		this.ecologicDatas = ecologicDatas;
	}

	public EcosystemDatas getEcosystemDatas() {
		return ecosystemDatas;
	}

	public void setEcosystemDatas(EcosystemDatas ecosystemDatas) {
		this.ecosystemDatas = ecosystemDatas;
	}

	public List<FileInformations> getFilesInformationsList() {
		return filesList;
	}

	public void setFilesInformationsList(List<FileInformations> filesInformationsList) {
		this.filesList = filesInformationsList;
	}

	public void addFileInformations(FileInformations fileInfos) {
		this.filesList.add(fileInfos);
	}

	public void removeFileInformations(FileInformations fileInfos) {
		this.filesList.remove(fileInfos);
	}

}