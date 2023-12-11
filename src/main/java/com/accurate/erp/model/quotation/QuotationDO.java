package com.accurate.erp.model.quotation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "QUOTATION")
public class QuotationDO {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Quotation_id")
	Integer quotationId;

	@Column(name = "Quotation_No")
	String quotationNo;

	@Column(name = "Quotation_Date")
	Date quotationDate;

	@Column(name = "Customer_Name")
	String customerName;

	@Column(name = "Billing_Address")
	String billingAddress;

	@Column(name = "City")
	String city;

	@Column(name = "State")
	String state;

	@Column(name = "GST_No")
	String gstNo;

	@Column(name = "ShippingCustomer_Name")
	String ShippingCustomerName;

	@Column(name = "Shipping_Address")
	String shippingAddress;

	@Column(name = "Shipping_City")
	String shippingCity;

	@Column(name = "Shipping_State")
	String shippingState;

	@Column(name = "ShippingGST_No")
	String shippingGstNo;

	@Column(name = "PO_No")
	String poNumber;

	@Column(name = "PO_Date")
	Date poDate;

	@Column(name = "Challan_No")
	String challanNo;

	@Column(name = "Challan_Date")
	Date challanDate;

	@Column(name = "Transport_Mode")
	String transportMode;

	@Column(name = "Vehicle_No")
	String vehicleNo;

	@Column(name = "Payment_Terms")
	String paymentTerms;

	@Column(name = "Due_Date")
	Date dueDate;

	@Column(name = "Remark")
	String remarks;

	@Column(name = "Additional_Terms")
	String additionalTerms;

	@Column(name = "Transport_Charges")
	String transportCharges;

	@Column(name = "Additional_Charges")
	String additionalCharges;

	@Column(name = "Quotation_Status")
	String quotationStatus;

	@Column(name = "Quotation_Value")
	BigDecimal quotationValue;
	
	@Column(name = "Taxable_Value")
	BigDecimal taxableValue;
	
	@Column(name = "CGST_Value")
	BigDecimal cgstValue;
	
	@Column(name = "SGST_Value")
	BigDecimal sgstValue;
	
	@Column(name = "IGST_Value")
	BigDecimal igstValue;
	
	
	@Column(name="Discount")
	BigDecimal discount;
	
	@Column(name="Other_Discount")
	BigDecimal otherDiscount;
	
	@Column(name="Quotation_Product_ID")
	Integer quotationProductId;

	@Column(name="Month")
	String month;
	
	@Column(name="service_invoice")
	String serviceCheck;
	
	@Column(name="Created_Date")
	Date createdDate;
	
	@OneToMany(mappedBy="quotation",fetch=FetchType.EAGER,cascade= CascadeType.ALL)
	List<QuotationProductDO> quotationProductDO=new ArrayList<>();

	public Integer getQuotationId() {
		return quotationId;
	}

	public void setQuotationId(Integer quotationId) {
		this.quotationId = quotationId;
	}

	public String getQuotationNo() {
		return quotationNo;
	}

	public void setQuotationNo(String quotationNo) {
		this.quotationNo = quotationNo;
	}

	public Date getQuotationDate() {
		return quotationDate;
	}

	public void setQuotationDate(Date quotationDate) {
		this.quotationDate = quotationDate;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	public String getShippingCustomerName() {
		return ShippingCustomerName;
	}

	public void setShippingCustomerName(String shippingCustomerName) {
		ShippingCustomerName = shippingCustomerName;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String getShippingCity() {
		return shippingCity;
	}

	public void setShippingCity(String shippingCity) {
		this.shippingCity = shippingCity;
	}

	public String getShippingState() {
		return shippingState;
	}

	public void setShippingState(String shippingState) {
		this.shippingState = shippingState;
	}

	public String getShippingGstNo() {
		return shippingGstNo;
	}

	public void setShippingGstNo(String shippingGstNo) {
		this.shippingGstNo = shippingGstNo;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public Date getPoDate() {
		return poDate;
	}

	public void setPoDate(Date poDate) {
		this.poDate = poDate;
	}

	public String getChallanNo() {
		return challanNo;
	}

	public void setChallanNo(String challanNo) {
		this.challanNo = challanNo;
	}

	public Date getChallanDate() {
		return challanDate;
	}

	public void setChallanDate(Date challanDate) {
		this.challanDate = challanDate;
	}

	public String getTransportMode() {
		return transportMode;
	}

	public void setTransportMode(String transportMode) {
		this.transportMode = transportMode;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getAdditionalTerms() {
		return additionalTerms;
	}

	public void setAdditionalTerms(String additionalTerms) {
		this.additionalTerms = additionalTerms;
	}

	public String getTransportCharges() {
		return transportCharges;
	}

	public void setTransportCharges(String transportCharges) {
		this.transportCharges = transportCharges;
	}

	public String getAdditionalCharges() {
		return additionalCharges;
	}

	public void setAdditionalCharges(String additionalCharges) {
		this.additionalCharges = additionalCharges;
	}

	public String getQuotationStatus() {
		return quotationStatus;
	}

	public void setQuotationStatus(String quotationStatus) {
		this.quotationStatus = quotationStatus;
	}

	public BigDecimal getQuotationValue() {
		return quotationValue;
	}

	public void setQuotationValue(BigDecimal quotationValue) {
		this.quotationValue = quotationValue;
	}

	public BigDecimal getTaxableValue() {
		return taxableValue;
	}

	public void setTaxableValue(BigDecimal taxableValue) {
		this.taxableValue = taxableValue;
	}

	public BigDecimal getCgstValue() {
		return cgstValue;
	}

	public void setCgstValue(BigDecimal cgstValue) {
		this.cgstValue = cgstValue;
	}

	public BigDecimal getSgstValue() {
		return sgstValue;
	}

	public void setSgstValue(BigDecimal sgstValue) {
		this.sgstValue = sgstValue;
	}

	public BigDecimal getIgstValue() {
		return igstValue;
	}

	public void setIgstValue(BigDecimal igstValue) {
		this.igstValue = igstValue;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public BigDecimal getOtherDiscount() {
		return otherDiscount;
	}

	public void setOtherDiscount(BigDecimal otherDiscount) {
		this.otherDiscount = otherDiscount;
	}

	public Integer getQuotationProductId() {
		return quotationProductId;
	}

	public void setQuotationProductId(Integer quotationProductId) {
		this.quotationProductId = quotationProductId;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getServiceCheck() {
		return serviceCheck;
	}

	public void setServiceCheck(String serviceCheck) {
		this.serviceCheck = serviceCheck;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public List<QuotationProductDO> getQuotationProductDO() {
		return quotationProductDO;
	}

	public void setQuotationProductDO(List<QuotationProductDO> quotationProductDO) {
		this.quotationProductDO = quotationProductDO;
	}
	

}
