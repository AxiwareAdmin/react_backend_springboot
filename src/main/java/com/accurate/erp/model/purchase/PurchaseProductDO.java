package com.accurate.erp.model.purchase;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="purchase_products")
public class PurchaseProductDO {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="purchase_product_id")
	Integer purchaseProductId;
	
	@Column(name="product_name")
	String productName;
	
	@Column(name="product_description")
	String productDescription;
	
	
	@Column(name="hsn_sac")
	String hsnSac;
	
	@Column(name="quantity")
	String quantity;
	
	@Column(name="unit")
	String unit;
	
	@Column(name="rate")
	String rate;
	
	@Column(name="discount")
	String discount;
	
	@Column(name="amount")
	String amount;
	
	@Column(name="tax")
	String tax;

	@Column(name="invoice_no")
	String invoiceNumber;
	
	@Column(name="invoice_date")
	Date invoiceDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="invoice_id")
	PurchaseDO purchaseDO;
	
	@Column(name="month")
	String month;
	
	@Column(name="financial_year")
	String year;
	
	@Column(name="createdBy")
	String createdBy;
	
	@Column(name="created_date")
	Date createdDate;
	
	@Column(name="register_id")
	String registerId;
	
	@Column(name="user_id")
	String userId;
	


	public Integer getPurchaseProductId() {
		return purchaseProductId;
	}

	public void setPurchaseProductId(Integer purchaseProductId) {
		this.purchaseProductId = purchaseProductId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getHsnSac() {
		return hsnSac;
	}

	public void setHsnSac(String hsnSac) {
		this.hsnSac = hsnSac;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	/*
	 * public PurchaseDO getPurchaseDO() { return purchaseDO; }
	 */

	public void setPurchaseDO(PurchaseDO purchaseDO) {
		this.purchaseDO = purchaseDO;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	
	

	
}
