package com.accurate.erp.model.invoice;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/*import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;*/

/*import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;*/


@Entity
@Table(name="customer")
public class CustomerDO {
	
	
	@Column(name="Gst_No")
	String gstNo;
	
	@Column(name="shippingGst_No")
	String shippingGstNo;

	@Column(name="address1")
	String address1;

	@Column(name="address2")
	String address2;

	@Column(name="city")
	String city;

	@Column(name="pincode")
	Integer pincode;

	@Column(name="state")
	String state;
	
	@Column(name="ShippingState")
	String shippingState;

	@Column(name="country")
	String country;

	@Column(name="email")
	String email;

	@Column(name="contact_no")
	String contactNo;
	
	@Column(name="contact_person")
	String contactPerson;
	
	@Column(name="vendor_code")
	String vendorCode;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Customer_Id")
	Integer customerId;

	@Column(name = "Customer_Name")
	String customerName;
	
	@Column(name="ShippingAddress1")
	String shippingAddress1;
	
	
	@Column(name="payment_terms")
	String termsAndCondition;
	
	
	@Column(name="stateCode")
	String stateCode;

	@Column(name="ShippingCustomer_Name")
	String ShippingCustomerName;

	@Column(name="ShippingCity")
	String shippingCity;

	@Column(name="ShippingPincode")
	Integer shippingPinCode;

	@Column(name="ShippingstateCode")
	String ShippingStateCode;

	@Column(name="ShippingCountry")
	String shippingCountry;

	@Column(name="Accounting_Group")
	String accountingGroup;

	@Column(name="User_Id")
	Integer userId;

	@Column(name="Register_Id")
	Integer registerId;

	@Column(name="Created_Date")
	String createdDate;

	@Column(name="Dr_Cr")
	String DrCr;
	
	@Column(name="shippingcontact_person")
	String shippingContactPerson;
	
	@Column(name="shippingContact_no")
	String shippingContactNumber;

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getShippingCustomerName() {
		return ShippingCustomerName;
	}

	public void setShippingCustomerName(String shippingCustomerName) {
		ShippingCustomerName = shippingCustomerName;
	}

	public String getShippingCity() {
		return shippingCity;
	}

	public void setShippingCity(String shippingCity) {
		this.shippingCity = shippingCity;
	}



	public Integer getShippingPinCode() {
		return shippingPinCode;
	}

	public void setShippingPinCode(Integer shippingPinCode) {
		this.shippingPinCode = shippingPinCode;
	}

	public String getShippingStateCode() {
		return ShippingStateCode;
	}

	public void setShippingStateCode(String shippingStateCode) {
		ShippingStateCode = shippingStateCode;
	}

	public String getShippingCountry() {
		return shippingCountry;
	}

	public void setShippingCountry(String shippingCountry) {
		this.shippingCountry = shippingCountry;
	}

	public String getAccountingGroup() {
		return accountingGroup;
	}

	public void setAccountingGroup(String accountingGroup) {
		this.accountingGroup = accountingGroup;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRegisterId() {
		return registerId;
	}

	public void setRegisterId(Integer registerId) {
		this.registerId = registerId;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getDrCr() {
		return DrCr;
	}

	public void setDrCr(String drCr) {
		DrCr = drCr;
	}
	
	
	
	

	public String getTermsAndCondition() {
		return termsAndCondition;
	}

	public void setTermsAndCondition(String termsAndCondition) {
		this.termsAndCondition = termsAndCondition;
	}

	public String getShippingState() {
		return shippingState;
	}

	public void setShippingState(String shippingState) {
		this.shippingState = shippingState;
	}

	public String getShippingAddress1() {
		return shippingAddress1;
	}

	public void setShippingAddress1(String shippingAddress1) {
		this.shippingAddress1 = shippingAddress1;
	}

	public String getShippingAddress2() {
		return shippingAddress2;
	}

	public void setShippingAddress2(String shippingAddress2) {
		this.shippingAddress2 = shippingAddress2;
	}

	@Column(name="ShippingAddress2")
	String shippingAddress2;
	
	@Column(name="PO_No")
	String poNumber;

	@Column(name="remark")
	String remarks;
	
	@Column(name = "Opening_Stock")
	Integer openingStock;



	public Integer getOpeningStock() {
		return openingStock;
	}

	public void setOpeningStock(Integer openingStock) {
		this.openingStock = openingStock;
	}
	
	
	public String getShippingGstNo() {
		return shippingGstNo;
	}

	public void setShippingGstNo(String shippingGstNo) {
		this.shippingGstNo = shippingGstNo;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getPincode() {
		return pincode;
	}

	public void setPincode(Integer pincode) {
		this.pincode = pincode;
	}

	

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	
	
	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	
	
	public String getShippingContactPerson() {
		return shippingContactPerson;
	}

	public void setShippingContactPerson(String shippingContactPerson) {
		this.shippingContactPerson = shippingContactPerson;
	}

	public String getShippingContactNumber() {
		return shippingContactNumber;
	}

	public void setShippingContactNumber(String shippingContactNumber) {
		this.shippingContactNumber = shippingContactNumber;
	}

	@Override
	public String toString() {
		return "CustomerDO [gstNo=" + gstNo + ", address1=" + address1 + ", address2=" + address2 + ", city=" + city
				+ ", pincode=" + pincode + ", state=" + state + ", country=" + country + ", email=" + email
				+ ", contactNo=" + contactNo + ", customerId=" + customerId + ", customerName=" + customerName + "]";
	}
	
	
}
