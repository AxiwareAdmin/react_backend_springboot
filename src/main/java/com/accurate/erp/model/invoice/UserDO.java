package com.accurate.erp.model.invoice;

import java.util.Collection;
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
	
	@Transient
	List<GrantedAuthority> authorities;
	
	public UserDO() {
		
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