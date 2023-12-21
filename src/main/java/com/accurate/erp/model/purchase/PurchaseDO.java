package com.accurate.erp.model.purchase;

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

@Table(name = "purchase")
@Entity
public class PurchaseDO {

	@Column(name = "purchase_Id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer purchaseId;
	
	@Column(name="purchase_No")
	String purchaseNo;
	
	@Column(name="purchase_date")
	Date purchaseDate;
	
	@Column(name="supplier_name")
	String supplierName;
	
	@Column(name="address")
	String address;
	
	@Column(name="city")
	String city;
	
	@Column(name="state")
	String state;
	
	@Column(name="gst_No")
	String gstNo;
	
	@Column(name="po_No")
	String poNo;
	
	@Column(name="po_Date")
	Date poDate;
	
	@Column(name="payment_terms")
	String paymentTerms;
	
	@Column(name="due_date")
	Date dueDate;
	
	@Column(name="remark")
	String remarks;
	
	@Column(name="transport_Charges")
	String transportCharges;
	
	@Column(name="additional_charges")
	String additionalCharges;
	
	@Column(name="discount")
	String discount;
	
	@Column(name="other_Discount")
	String otherDiscount;
	
	@Column(name="taxable_value")
	BigDecimal taxableValue;
	
	@Column(name="invoice_value")
	BigDecimal invoiceValue;
	
	@Column(name="purchase_product_id")
	String purchaseProductId;
	
	@Column(name="month")
	String month;
	
	@OneToMany(mappedBy = "purchaseDO",fetch = FetchType.EAGER,cascade= CascadeType.ALL)
	List<PurchaseProductDO> purchaseProductDOs=new ArrayList<>();
	
	
	

	public List<PurchaseProductDO> getPurchaseProductDOs() {
		return purchaseProductDOs;
	}

	public void setPurchaseProductDOs(List<PurchaseProductDO> purchaseProductDOs) {
		this.purchaseProductDOs = purchaseProductDOs;
	}

	public Integer getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(Integer purchaseId) {
		this.purchaseId = purchaseId;
	}

	public String getPurchaseNo() {
		return purchaseNo;
	}

	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getPoNo() {
		return poNo;
	}

	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}

	public Date getPoDate() {
		return poDate;
	}

	public void setPoDate(Date poDate) {
		this.poDate = poDate;
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

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getOtherDiscount() {
		return otherDiscount;
	}

	public void setOtherDiscount(String otherDiscount) {
		this.otherDiscount = otherDiscount;
	}

	

	public BigDecimal getTaxableValue() {
		return taxableValue;
	}

	public void setTaxableValue(BigDecimal taxableValue) {
		this.taxableValue = taxableValue;
	}

	public BigDecimal getInvoiceValue() {
		return invoiceValue;
	}

	public void setInvoiceValue(BigDecimal invoiceValue) {
		this.invoiceValue = invoiceValue;
	}

	public String getPurchaseProductId() {
		return purchaseProductId;
	}

	public void setPurchaseProductId(String purchaseProductId) {
		this.purchaseProductId = purchaseProductId;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}
	
	
	
	
}
