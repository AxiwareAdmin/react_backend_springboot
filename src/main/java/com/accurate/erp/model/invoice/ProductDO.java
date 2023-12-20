package com.accurate.erp.model.invoice;

import java.math.BigDecimal;
import java.util.Date;

/*import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;*/

import org.springframework.stereotype.Component;

/*import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;*/

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public class ProductDO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Product_Id")
	Integer invoiceProductId;

	@Column(name = "ProductName")
	String productName;

	@Column(name = "Product_Description")
	String productDescription;

	@Column(name="Prodcut_Type")
	String productType;
	
	@Column(name="part_code")
	Integer partCode;
	
	@Column(name = "HSN_Code")
	String hsnCode;

	@Column(name = "Quantity")
	Integer unit;

	@Column(name="Unit")
	String unitVarchar;
	
	@Column(name = "Rate")
	BigDecimal rate;

	@Column(name = "category")
	String category;

	@Column(name = "Applicable_Tax")
	BigDecimal applicableTax;

	@Column(name = "Opening_Stock")
	Integer openingStock;

	
	@Column(name = "As_On_Date")
	Date asOnDate;

		
	@Column(name = "CreatedBy")
	String createdBy;
	
	@Column(name = "Created_Date")
	Date createdDate;
	
	@Column(name = "Register_Id")
	Integer registerId;
	
	@Column(name = "User_Id")
	Integer userId;
	
	
	
	

	public String getUnitVarchar() {
		return unitVarchar;
	}

	public void setUnitVarchar(String unitVarchar) {
		this.unitVarchar = unitVarchar;
	}

	public Integer getInvoiceProductId() {
		return invoiceProductId;
	}

	public void setInvoiceProductId(Integer invoiceProductId) {
		this.invoiceProductId = invoiceProductId;
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

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public Integer getPartCode() {
		return partCode;
	}

	public void setPartCode(Integer partCode) {
		this.partCode = partCode;
	}

	public String getHsnCode() {
		return hsnCode;
	}

	public void setHsnCode(String hsnCode) {
		this.hsnCode = hsnCode;
	}


	public Integer getUnit() {
		return unit;
	}

	public void setUnit(Integer unit) {
		this.unit = unit;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public BigDecimal getApplicableTax() {
		return applicableTax;
	}

	public void setApplicableTax(BigDecimal applicableTax) {
		this.applicableTax = applicableTax;
	}

	public Integer getOpeningStock() {
		return openingStock;
	}

	public void setOpeningStock(Integer openingStock) {
		this.openingStock = openingStock;
	}
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

	public Date getAsOnDate() {
		return asOnDate;
	}

	public void setAsOnDate(Date asOnDate) {
		this.asOnDate = asOnDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	
}
