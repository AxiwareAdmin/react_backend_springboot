package com.accurate.erp.model.invoice;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "user_profile")
public class UserDO implements UserDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	Integer userId;
	
	
	@Column(name="user_name")
	String userName;
	
	@Column(name="account_number")
	String accountNumber;
	
	@Column(name="ifsc")
	String ifscCode;
	
	
	
	@Column(name="designation")
	String designation;
	
	@Column(name="bank_name")
	String bankName;
	
	@Column(name="department")
	String dept;
	
	@Column(name="branch")
	String branch;
	
	
	@Column(name="phone")
	String phone;
	
	
	@Column(name="email")
	String email;
	
	@Column(name="login_id")
	String loginId;
	
	@Column(name="password")
	String password;
	
	@Column(name="user_status")
	String userStatus;
	
	@Column(name="register_id")
	String registerId;
	
	@Column(name="joining_date")
	String joiningDate;
	
	
	@Column(name="employee_id")
	String employeeId;
	
	
	@Column(name="pan_no")
	String panNumber;
	
	@Column(name="passport_no")
	String passportNumber;
	
	
	@Column(name="pf_no")
	String pfNumber;
	
	
	@Column(name="uan_no")
	String uanNumber;
	
	
	@Column(name="qualification")
	String qualification;
	
	@Column(name="experience")
	String experience;
	
	
	@Column(name="gender")
	String gender;
	
	
	@Column(name="birth_date")
	Date birthDate;
	
	@Column(name="branch_1")
	String branch1;
	
	@Column(name="linkedin")
	String linkedIn;
	
	@Column(name="twitter")
	String twitter;
	
	@Column(name="facebook")
	String facebook;
	
	@Column(name="instagram")
	String instagram;
	
	@Column(name="banking")
	String banking;
	
	@Column(name="cashbook")
	String cashbook;
	
	@Column(name="accounting")
	String accounting;
	
	@Column(name="sales")
	String sales;
	
	@Column(name="purchase")
	String purchase;
	
	@Column(name="inventory")
	String inventory;
	
	@Column(name="quotation")
	String quotation;
	
	
	@Column(name="purchase_order")
	String purchaseOrder;
	
	
	
	
	
	
	public String getEmployeeId() {
		return employeeId;
	}



	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}



	public String getPanNumber() {
		return panNumber;
	}



	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}



	public String getPassportNumber() {
		return passportNumber;
	}



	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}



	public String getPfNumber() {
		return pfNumber;
	}



	public void setPfNumber(String pfNumber) {
		this.pfNumber = pfNumber;
	}



	public String getUanNumber() {
		return uanNumber;
	}



	public void setUanNumber(String uanNumber) {
		this.uanNumber = uanNumber;
	}



	public String getQualification() {
		return qualification;
	}



	public void setQualification(String qualification) {
		this.qualification = qualification;
	}



	public String getExperience() {
		return experience;
	}



	public void setExperience(String experience) {
		this.experience = experience;
	}



	public String getGender() {
		return gender;
	}



	public void setGender(String gender) {
		this.gender = gender;
	}



	public Date getBirthDate() {
		return birthDate;
	}



	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}



	public String getBranch1() {
		return branch1;
	}



	public void setBranch1(String branch1) {
		this.branch1 = branch1;
	}



	public String getLinkedIn() {
		return linkedIn;
	}



	public void setLinkedIn(String linkedIn) {
		this.linkedIn = linkedIn;
	}



	public String getTwitter() {
		return twitter;
	}



	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}



	public String getFacebook() {
		return facebook;
	}



	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}



	public String getInstagram() {
		return instagram;
	}



	public void setInstagram(String instagram) {
		this.instagram = instagram;
	}



	public String getBanking() {
		return banking;
	}



	public void setBanking(String banking) {
		this.banking = banking;
	}



	public String getCashbook() {
		return cashbook;
	}



	public void setCashbook(String cashbook) {
		this.cashbook = cashbook;
	}



	public String getAccounting() {
		return accounting;
	}



	public void setAccounting(String accounting) {
		this.accounting = accounting;
	}



	public String getSales() {
		return sales;
	}



	public void setSales(String sales) {
		this.sales = sales;
	}



	public String getPurchase() {
		return purchase;
	}



	public void setPurchase(String purchase) {
		this.purchase = purchase;
	}



	public String getInventory() {
		return inventory;
	}



	public void setInventory(String inventory) {
		this.inventory = inventory;
	}



	public String getQuotation() {
		return quotation;
	}



	public void setQuotation(String quotation) {
		this.quotation = quotation;
	}



	public String getPurchaseOrder() {
		return purchaseOrder;
	}



	public void setPurchaseOrder(String purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}

	@Transient
	List<GrantedAuthority> authorities;
	
	public UserDO() {
		
	}
	
	
	
	public String getJoiningDate() {
		return joiningDate;
	}



	public void setJoiningDate(String joiningDate) {
		this.joiningDate = joiningDate;
	}



	public UserDO(UserDO userDO) {
		authorities=List.of(new SimpleGrantedAuthority(userDO.getDesignation()));
		password=userDO.getPassword();
		userName=userDO.getLoginId();
		userStatus=userDO.getUserStatus();
		userId=userDO.getUserId();
		registerId=userDO.getRegisterId();
	}


	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return userStatus.toLowerCase().equals("active");
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return userStatus.toLowerCase().equals("active");
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return userStatus.toLowerCase().equals("active");
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return userStatus.toLowerCase().equals("active");
	}

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public void setAuthorities(List<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
	
	

}