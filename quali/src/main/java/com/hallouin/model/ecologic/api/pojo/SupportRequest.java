package com.hallouin.model.ecologic.api.pojo;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class SupportRequest {

	@SerializedName("Consumer")
    private Consumer consumer;

    @SerializedName("Product")
    private Product product;

    @SerializedName("Quote")
    private Quote quote;

    @SerializedName("SpareParts")
    private List <SparePartEcl> spareParts;

    // Constructeurs, getters et setters
    public SupportRequest(Consumer consumer, Product product, Quote quote) {
		super();
		this.consumer = consumer;
		this.product = product;
		this.quote = quote;

	}


    public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public void setQuote(Quote quote) {
		this.quote = quote;
	}

	public void setSpareParts(List<SparePartEcl> spareParts) {
		this.spareParts = spareParts;
	}


	public static class Consumer {
        @SerializedName("Title")
        private int title;

        @SerializedName("LastName")
        private String lastName;

        @SerializedName("FirstName")
        private String firstName = " ";

        @SerializedName("StreetNumber")
        private String streetNumber;

        @SerializedName("Address1")
        private String address1;

        @SerializedName("Address2")
        private String address2 = "";

        @SerializedName("Address3")
        private String address3 = "";

        @SerializedName("Zip")
        private String zip;

        @SerializedName("City")
        private String city;

        @SerializedName("Country")
        private int country = 250;

        @SerializedName("Phone")
        private String phone = "";

        @SerializedName("Email")
        private String email = "";

        @SerializedName("AutoValidation")
        private Boolean autoValidation;


     // Constructeur, getters et setters

		public Consumer(int title, String lastName, String firstName, String streetNumber, String address1, String zip, String city,String phoneNumber) {
			super();
			this.title = title;
			this.lastName = lastName;
			this.firstName = firstName;
			this.streetNumber = streetNumber;
			this.address1 = address1;
			this.zip = zip;
			this.city = city;
			this.phone = phoneNumber;
			this.autoValidation = true;
		}

		public void setTitle(int title) {
			this.title = title;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public void setStreetNumber(String streetNumber) {
			this.streetNumber = streetNumber;
		}

		public void setAddress1(String address1) {
			this.address1 = address1;
		}

		public void setAddress2(String address2) {
			this.address2 = address2;
		}

		public void setAddress3(String address3) {
			this.address3 = address3;
		}

		public void setZip(String zip) {
			this.zip = zip;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public void setCountry(int country) {
			this.country = country;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public void setAutoValidation(Boolean autoValidation) {
			this.autoValidation = autoValidation;
		}


    }

    public static class Product {
        @SerializedName("ProductId")
        private String productId;

        @SerializedName("BrandId")
        private String brandId;

        @SerializedName("CommercialRef")
        private String commercialRef = "";

        @SerializedName("SerialNumber")
        private String serialNumber;

        @SerializedName("PurchaseDate")
        private String purchaseDate = "";

        @SerializedName("IRISCondition")
        private String irisCondition = "";

        @SerializedName("IRISConditionEX")
        private String irisConditionEX = "";

        @SerializedName("IRISSymptom")
        private String irisSymptom;

        @SerializedName("IRISSection")
        private String irisSection = "";

        @SerializedName("IRISDefault")
        private String irisDefault = "";

        @SerializedName("IRISRepair")
        private String irisRepair = "";

        @SerializedName("FailureDescription")
        private String failureDescription = "";

        @SerializedName("DefectCode")
        private String defectCode = "";

		public Product(String productId, String brandId, String CommercialRef, String serialNumber, String irisSymptom, String irisSection,String failureDescription) {
			super();
			this.productId = productId;
			this.brandId = brandId;
			this.commercialRef = CommercialRef;
			this.serialNumber = serialNumber;
			this.irisSymptom = irisSymptom;
			this.irisSection = irisSection;
			this.failureDescription = failureDescription;
		}

		public void setProductId(String productId) {
			this.productId = productId;
		}

		public void setBrandId(String brandId) {
			this.brandId = brandId;
		}

		public void setCommercialRef(String commercialRef) {
			this.commercialRef = commercialRef;
		}

		public void setSerialNumber(String serialNumber) {
			this.serialNumber = serialNumber;
		}

		public void setPurchaseDate(String purchaseDate) {
			this.purchaseDate = purchaseDate;
		}

		public void setIrisCondition(String irisCondition) {
			this.irisCondition = irisCondition;
		}

		public void setIrisConditionEX(String irisConditionEX) {
			this.irisConditionEX = irisConditionEX;
		}

		public void setIrisSymptom(String irisSymptom) {
			this.irisSymptom = irisSymptom;
		}

		public void setIrisSection(String irisSection) {
			this.irisSection = irisSection;
		}

		public void setIrisDefault(String irisDefault) {
			this.irisDefault = irisDefault;
		}

		public void setIrisRepair(String irisRepair) {
			this.irisRepair = irisRepair;
		}

		public void setFailureDescription(String failureDescription) {
			this.failureDescription = failureDescription;
		}

		public void setDefectCode(String defectCode) {
			this.defectCode = defectCode;
		}

        // Constructeur, getters et setters

    }

    public static class Quote {
        @SerializedName("LaborCost")
        private Cost laborCost;

        @SerializedName("SparePartsCost")
        private Cost sparePartsCost;

        @SerializedName("TravelCost")
        private Cost travelCost;

        @SerializedName("TotalAmountExclVAT")
        private Cost totalAmountExclVAT;

        @SerializedName("TotalAmountInclVAT")
        private Cost totalAmountInclVAT;

        @SerializedName("SupportAmount")
        private Cost supportAmount;

     // Constructeur, getters et setters

		public Quote(Cost laborCost, Cost sparePartsCost, Cost travelCost, Cost totalAmountExclVAT, Cost totalAmountInclVAT, Cost supportAmount) {
			super();
			this.laborCost = laborCost;
			this.sparePartsCost = sparePartsCost;
			this.travelCost = travelCost;
			this.totalAmountExclVAT = totalAmountExclVAT;
			this.totalAmountInclVAT = totalAmountInclVAT;
			this.supportAmount = supportAmount;
		}

		public void setLaborCost(Cost laborCost) {
			this.laborCost = laborCost;
		}

		public void setSparePartsCost(Cost sparePartsCost) {
			this.sparePartsCost = sparePartsCost;
		}

		public void setTravelCost(Cost travelCost) {
			this.travelCost = travelCost;
		}

		public void setTotalAmountExclVAT(Cost totalAmountExclVAT) {
			this.totalAmountExclVAT = totalAmountExclVAT;
		}

		public void setTotalAmountInclVAT(Cost totalAmountInclVAT) {
			this.totalAmountInclVAT = totalAmountInclVAT;
		}

		public void setSupportAmount(Cost supportAmount) {
			this.supportAmount = supportAmount;
		}


    }

    public static class Cost {
        @SerializedName("Amount")
        private double amount = 0.0;

        @SerializedName("Currency")
        private String currency = "EUR";

        // Constructeur, getters et setters

		public Cost(double amount) {
			super();
			this.amount = amount;
		}

		public void setAmount(double amount) {
			this.amount = amount;
		}

		public void setCurrency(String currency) {
			this.currency = currency;
		}

    }

    public static class SparePartEcl {
        @SerializedName("Partref")
        private String partRef;

        @SerializedName("Quantity")
        private double quantity;

        @SerializedName("Status")
        private String status = "";

     // Constructeur, getters et setters

		public SparePartEcl(String partRef, Double quantity) {
			super();
			this.partRef = partRef;
			this.quantity = quantity;
		}

		public void setPartRef(String partRef) {
			this.partRef = partRef;
		}

		public void setQuantity(double quantity) {
			this.quantity = quantity;
		}

		public void setStatus(String status) {
			this.status = status;
		}


    }
}
