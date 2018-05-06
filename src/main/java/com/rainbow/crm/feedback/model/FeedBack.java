package com.rainbow.crm.feedback.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.contact.model.Contact;
import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.territory.model.Territory;
import com.rainbow.crm.user.model.User;
import com.rainbow.crm.vendor.model.Vendor;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

public class FeedBack extends CRMBusinessModelObject{

	Division division;
	String docNumber;
	Customer customer;
	
	Date feedBackDate ;
	User capturedBy;
	Sales sales ;
	FiniteValue communicationMode;
	boolean deleted;
	String comments;
	Set<FeedBackLine> feedBackLines;
	
	
	
	
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public Division getDivision() {
		return division;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public void setDivision(Division division) {
		this.division = division;
	}
	@RadsPropertySet(isBK =true)
	public String getDocNumber() {
		return docNumber;
	}
	@RadsPropertySet(isBK =true)
	public void setDocNumber(String docNumber) {
		this.docNumber = docNumber;
	}
		
	public Sales getSales() {
		return sales;
	}
	public void setSales(Sales sales) {
		this.sales = sales;
	}
	
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public Customer getCustomer() {
		return customer;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public String getComments() {
		return comments;
	}
	public void setComments(String commments) {
		this.comments = commments;
	}
	public Set<FeedBackLine> getFeedBackLines() {
		return feedBackLines;
	}
	public void setFeedBackLines(Set<FeedBackLine> feedBackLines) {
		this.feedBackLines = feedBackLines;
	}
	public void addFeedBackLine(FeedBackLine feedBackLine) {
		if (feedBackLines == null )
			feedBackLines = new LinkedHashSet <FeedBackLine> ();
		this.feedBackLines.add(feedBackLine);
	}
	public Date getFeedBackDate() {
		return feedBackDate;
	}
	public void setFeedBackDate(Date feedBackDate) {
		this.feedBackDate = feedBackDate;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public User getCapturedBy() {
		return capturedBy;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public void setCapturedBy(User capturedBy) {
		this.capturedBy = capturedBy;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public FiniteValue getCommunicationMode() {
		return communicationMode;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public void setCommunicationMode(FiniteValue communicationMode) {
		this.communicationMode = communicationMode;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	
	
}
