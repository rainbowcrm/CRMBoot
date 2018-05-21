package com.rainbow.crm.servicerequest.model;

import java.util.Date;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.abstratcs.model.CRMItemLine;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.reasoncode.model.ReasonCode;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.user.model.User;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

public class ServiceRequestLine extends CRMBusinessModelObject{
	
	int lineNumber;
	Sku sku;
	boolean rectified;
	String technicianNotes;
	String customerNotes;


	String comments;
	boolean deleted;
	
	ServiceRequest serviceRequestDoc;


	public ServiceRequestLine() {
	
	}
	@RadsPropertySet(isBK=true)
	public int getLineNumber() {
		return lineNumber;
	}
	@RadsPropertySet(isBK=true)
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	@RadsPropertySet(excludeFromJSON=true,excludeFromMap=true,excludeFromXML=true)
	public ServiceRequest getServiceRequestDoc() {
		return serviceRequestDoc;
	}
	@RadsPropertySet(excludeFromJSON=true,excludeFromMap=true,excludeFromXML=true)
	public void setServiceRequestDoc(ServiceRequest serviceRequestDoc) {
		this.serviceRequestDoc = serviceRequestDoc;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Sku getSku() {
		return sku;
	}

	public void setSku(Sku sku) {
		this.sku = sku;
	}

	public boolean isRectified() {
		return rectified;
	}

	public void setRectified(boolean rectified) {
		this.rectified = rectified;
	}

	public String getTechnicianNotes() {
		return technicianNotes;
	}

	public void setTechnicianNotes(String technicianNotes) {
		this.technicianNotes = technicianNotes;
	}

	public String getCustomerNotes() {
		return customerNotes;
	}

	public void setCustomerNotes(String customerNotes) {
		this.customerNotes = customerNotes;
	}
}
