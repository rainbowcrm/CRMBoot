package com.rainbow.crm.enquiry.model;

import com.rainbow.crm.abstratcs.model.CRMItemLine;
import com.techtrade.rads.framework.annotations.RadsPropertySet;
import com.techtrade.rads.framework.utils.Utils;

public class EnquiryLine extends CRMItemLine{

	String docNumber;
	int lineNumber;
	String comments;
	Enquiry enquiry;
	boolean deleted;
	
	public String getDocNumber() {
		return docNumber;
	}
	public void setDocNumber(String docNumber) {
		this.docNumber = docNumber;
	}
	public int getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	@RadsPropertySet(excludeFromJSON=true,excludeFromMap =true, excludeFromXML =true)
	public Enquiry getEnquiry() {
		return enquiry;
	}
	@RadsPropertySet(excludeFromJSON=true,excludeFromMap =true, excludeFromXML =true)
	public void setEnquiry(Enquiry enquiry) {
		this.enquiry = enquiry;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	@Override
	public boolean isNullContent() {
		if(getSku() == null || Utils.isNullString(getSku().getName()) )
			return true;
		return false;
	
	}
	
	
	
}
