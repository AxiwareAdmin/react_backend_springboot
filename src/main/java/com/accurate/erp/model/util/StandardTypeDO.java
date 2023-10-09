package com.accurate.erp.model.util;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "standard_type")
public class StandardTypeDO {

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	Integer standardTypeId;
	
	@Column(name = "module")
	String module;
	
	@Column(name="submodule")
	String subModule;
	
	@Column(name = "value")
	String value;
	
}
