package com.rainbow.crm.alert.model;

import java.util.Date;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.saleslead.model.SalesLead;
import com.rainbow.crm.user.model.User;
import com.techtrade.rads.framework.annotations.RadsPropertySet;
import com.techtrade.rads.framework.utils.Utils;

public class Alert extends CRMBusinessModelObject{
   
	Division division;
	FiniteValue type;
	FiniteValue status ;
	Date raisedDate ;
	Date actionDate ;
	Date acknowDate ;
	User owner;
	String raisedBy;
	String data;
	String url;
	
	@RadsPropertySet(useBKForJSON=true,useBKForXML=true,usePKForMap=true)
	public Division getDivision() {
		return division;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForXML=true,usePKForMap=true)
	public void setDivision(Division division) {
		this.division = division;
	}
	
	public FiniteValue getType() {
		return type;
	}
	
	public void setType(FiniteValue type) {
		this.type = type;
	}
	public FiniteValue getStatus() {
		return status;
	}
	public void setStatus(FiniteValue status) {
		this.status = status;
	}
	public Date getRaisedDate() {
		return raisedDate;
	}
	public void setRaisedDate(Date raisedDate) {
		this.raisedDate = raisedDate;
	}
	public Date getActionDate() {
		return actionDate;
	}
	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}
	public Date getAcknowDate() {
		return acknowDate;
	}
	public void setAcknowDate(Date acknowDate) {
		this.acknowDate = acknowDate;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	public String getRaisedBy() {
		return raisedBy;
	}
	public void setRaisedBy(String raisedBy) {
		this.raisedBy = raisedBy;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
	
}
