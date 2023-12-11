package com.accurate.erp.model.modelmaster;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="document_seq_master")
	
public class DocumentSeqMasterDO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "document_seq_Id")
	Integer documentSeqId;
	
	@Column(name = "Document_Name")
	String documentName;
	
	@Column(name = "Prefix_1")
	String prefix1;
	
	@Column(name = "Prefix_2")
	String prefix2;
	
	@Column(name = "Series")
	String series;
	
	@Column(name = "Mode")
	String mode;

	public Integer getDocumentSeqId() {
		return documentSeqId;
	}

	public void setDocumentSeqId(Integer documentSeqId) {
		this.documentSeqId = documentSeqId;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getPrefix1() {
		return prefix1;
	}

	public void setPrefix1(String prefix1) {
		this.prefix1 = prefix1;
	}

	public String getPrefix2() {
		return prefix2;
	}

	public void setPrefix2(String prefix2) {
		this.prefix2 = prefix2;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
	
}
