package com.accurate.erp.model.invoice;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "registration")
public class ClientDO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="Register_id")
	Integer clientId;
	
	@Column(name="contact_person")
	String contactPerson;
	
	@Column(name="company_name")
	String companyName;
	
	@Column(name="mobile")
	String mobile;
	
	@Column(name="email")
	String email;
	
	@Column(name="password")
	String password;
	
	@Column(name="pricing_plan")
	String pricingPlan;
	
	@Column(name="amount")
	BigDecimal amount;
	
	@Column(name="payment_status")
	String paymentStatus;
	
	@Column(name="trans_id")
	String transId;
	
	@Column(name="activation_date")
	Date activationDate;
	
	@Column(name="expiry_date")
	Date expiryDate;
	
	@Column(name="account_status")
	String accountStatus;
	
	@Column(name="renewal_amount")
	BigDecimal renewalAmount;
	
	@Column(name="address1")
	String address1;
	
	@Column(name="address2")
	String address2;
	
	@Column(name="city")
	String city;
	
	
	@Column(name="state")
	String state;
	
	@Column(name="pincode")
	String pinCode;
	
	
	@Column(name="country")
	String country;
	
	@Column(name="gst_no")
	String gstNo;
	
	@Column(name="pan_no")
	String panNumber;
	
	@Column(name="crn_msme")
	String crnMsme;
	
	@Column(name="financial_year")
	String financialYear;
	
	@Column(name="website")
	String website;

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPricingPlan() {
		return pricingPlan;
	}

	public void setPricingPlan(String pricingPlan) {
		this.pricingPlan = pricingPlan;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public Date getActivationDate() {
		return activationDate;
	}

	public void setActivationDate(Date activationDate) {
		this.activationDate = activationDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public BigDecimal getRenewalAmount() {
		return renewalAmount;
	}

	public void setRenewalAmount(BigDecimal renewalAmount) {
		this.renewalAmount = renewalAmount;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getCrnMsme() {
		return crnMsme;
	}

	public void setCrnMsme(String crnMsme) {
		this.crnMsme = crnMsme;
	}

	public String getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}
	
	
	
	
}
