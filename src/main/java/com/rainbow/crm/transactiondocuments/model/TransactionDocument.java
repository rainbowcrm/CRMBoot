package com.rainbow.crm.transactiondocuments.model;

import com.techtrade.rads.framework.model.abstracts.ModelObject;

public class TransactionDocument  extends ModelObject{
	
	String docType;
	int pk;
	String document;
	
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public int getPk() {
		return pk;
	}
	public void setPk(int pk) {
		this.pk = pk;
	}
	public String getDocument() {
		return document;
	}
	public void setDocument(String document) {
		this.document = document;
	}
	
	
}
