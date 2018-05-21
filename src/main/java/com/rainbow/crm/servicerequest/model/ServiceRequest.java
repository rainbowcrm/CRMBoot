package com.rainbow.crm.servicerequest.model;

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

public class ServiceRequest extends CRMBusinessModelObject{

	Division division;
	String docNumber;
	Customer customer;
	
	Date serviceRequestDate ;
	Date serviceCompletionDate;
	User serviceAssociate;
	Sales sales ;
	FiniteValue serviceStatus;
	FiniteValue serviceType;
	FiniteValue visitMode;
	boolean paidService ;

	double serviceCharge;
	double serviceTax;
	double totalCharge;


	boolean deleted;
	String comments;
	Set<ServiceRequestLine> serviceRequestLines;
	
	
	
	
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
	public Set<ServiceRequestLine> getServiceRequestLines() {
		return serviceRequestLines;
	}
	public void setServiceRequestLines(Set<ServiceRequestLine> serviceRequestLines) {
		this.serviceRequestLines = serviceRequestLines;
	}
	public void addServiceRequestLine(ServiceRequestLine serviceRequestLine) {
		if (serviceRequestLines == null )
			serviceRequestLines = new LinkedHashSet <ServiceRequestLine> ();
		this.serviceRequestLines.add(serviceRequestLine);
	}
	public Date getServiceRequestDate() {
		return serviceRequestDate;
	}
	public void setServiceRequestDate(Date serviceRequestDate) {
		this.serviceRequestDate = serviceRequestDate;
	}


	public Date getServiceCompletionDate() {
		return serviceCompletionDate;
	}

	public void setServiceCompletionDate(Date serviceCompletionDate) {
		this.serviceCompletionDate = serviceCompletionDate;
	}

	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public User getServiceAssociate() {
		return serviceAssociate;
	}

	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public void setServiceAssociate(User serviceAssociate) {
		this.serviceAssociate = serviceAssociate;
	}

	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public FiniteValue getServiceStatus() {
		return serviceStatus;
	}

	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public void setServiceStatus(FiniteValue serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public FiniteValue getServiceType() {
		return serviceType;
	}

	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public void setServiceType(FiniteValue serviceType) {
		this.serviceType = serviceType;
	}

	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public FiniteValue getVisitMode() {
		return visitMode;
	}

	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public void setVisitMode(FiniteValue visitMode) {
		this.visitMode = visitMode;
	}

	public boolean isPaidService() {
		return paidService;
	}

	public void setPaidService(boolean paidService) {
		this.paidService = paidService;
	}

	public double getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(double serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public double getServiceTax() {
		return serviceTax;
	}

	public void setServiceTax(double serviceTax) {
		this.serviceTax = serviceTax;
	}

	public double getTotalCharge() {
		return totalCharge;
	}

	public void setTotalCharge(double totalCharge) {
		this.totalCharge = totalCharge;
	}







	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	
	
}
