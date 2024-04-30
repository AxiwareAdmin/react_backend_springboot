package com.accurate.erp.model.invoice;

import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/*import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;*/

import org.hibernate.annotations.Cascade;
//import javax.persistence.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;

/*import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;*/

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "invoice")
public class InvoiceDO {
	


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Invoice_id")
	Integer invoiceId;

	@Column(name = "Invoice_No")
	String invoiceNo;

	@Column(name = "Invoice_Date")
	Date invoiceDate;

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

	@Column(name = "Invoice_Status")
	String invoiceStatus;

	@Column(name = "Invoice_Value")
	BigDecimal invoiceValue;
	
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
	
	@Column(name="Invoice_Product_ID")
	Integer invoiceProductId;

	@Column(name="Month")
	String month;
	
	@Column(name="transportGst")
	BigDecimal transportGst;
	
	@Column(name="additionalChargesGst")
	BigDecimal additionalChargesGst;
	
	public BigDecimal getTransportGst() {
		return transportGst;
	}

	public void setTransportGst(BigDecimal transportGst) {
		this.transportGst = transportGst;
	}

	public BigDecimal getAdditionalChargesGst() {
		return additionalChargesGst;
	}

	public void setAdditionalChargesGst(BigDecimal additionalChargesGst) {
		this.additionalChargesGst = additionalChargesGst;
	}

	@Column(name="service_invoice")
	String serviceCheck;
	
	@Column(name="Created_Date")
	Date createdDate;
	
	@Column(name="financial_year")
	String financialYear;
	


	@OneToMany(mappedBy="invoiceDO",fetch=FetchType.LAZY,cascade= CascadeType.ALL,orphanRemoval = true)
	List<InvoiceProductDO> invoiceProductDO=new ArrayList<>();
	
	@Column(name="register_id")
	Integer registerId;
	
	@Column(name="user_id")
	Integer userId;
	
	@Column(name="createdby")
	String createdBy;
	
	


	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getRegisterId() {
		return registerId;
	}

	public void setRegisterId(Integer registerId) {
		this.registerId = registerId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getServiceCheck() {
		return serviceCheck;
	}

	public void setServiceCheck(String serviceCheck) {
		this.serviceCheck = serviceCheck;
	}
	
	@Transient
	 boolean includeChildren=true; // Condition to determine if children should be included

	    // Getter and setter for includeChildren
	    public boolean isIncludeChildren() {
	        return includeChildren;
	    }
	    
	    public void setIncludeChildren(boolean includeChildren) {
	        this.includeChildren = includeChildren;
	    }

	public List<InvoiceProductDO> getInvoiceProductDO() {
		 if (includeChildren) {
	            return invoiceProductDO;
	        } else {
	            return null;
	        }
//		 return invoiceProductDO;
	}

	public void setInvoiceProductDO(List<InvoiceProductDO> invoiceProductDO) {
		this.invoiceProductDO.clear();
		this.invoiceProductDO.addAll(invoiceProductDO);
		
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Integer getInvoiceProductId() {
		return invoiceProductId;
	}

	public void setInvoiceProductId(Integer invoiceProductId) {
		this.invoiceProductId = invoiceProductId;
	}

	public Integer getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Integer invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
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

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public BigDecimal getInvoiceValue() {
		return invoiceValue;
	}

	public void setInvoiceValue(BigDecimal invoiceValue) {
		this.invoiceValue = invoiceValue;
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

	public String getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}
	
	
	
}
