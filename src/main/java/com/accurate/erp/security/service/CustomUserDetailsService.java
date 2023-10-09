package com.accurate.erp.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.accurate.erp.dao.invoice.InvoiceDao;
import com.accurate.erp.model.invoice.UserDO;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	InvoiceDao invoiceDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		UserDO userDO=invoiceDao.getUserDO(username);
		if(userDO==null) throw new UsernameNotFoundException("Username or Password is invalid.");
		return new UserDO(userDO);
	}

	
}