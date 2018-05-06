package com.rainbow.crm.saleslead.model;

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
import com.rainbow.crm.reasoncode.model.ReasonCode;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.territory.model.Territory;
import com.rainbow.crm.vendor.model.Vendor;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

public class SalesLead extends CRMBusinessModelObject{

	Division division;
	String docNumber;
	Customer customer;
	Contact referall; 
	Territory territory;
	Date releasedDate ;
	boolean salesWon;
	String refNo;
	Date refDate; 
	Sales sales ;
	boolean voided;
	String comments;
	Set<SalesLeadLine> salesLeadLines;
	FiniteValue status;
	String salesAssociate ;
	
	
	ReasonCode salesAssReason;
	ReasonCode mgrReason;
	
	boolean alerted; 
	Date closureDate ;
	
	
	
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
	public Date getReleasedDate() {
		return releasedDate;
	}
	public void setReleasedDate(Date releasedDate) {
		this.releasedDate = releasedDate;
	}
	public boolean isSalesWon() {
		return salesWon;
	}
	public void setSalesWon(boolean salesWon) {
		this.salesWon = salesWon;
	}
	
	public Sales getSales() {
		return sales;
	}
	public void setSales(Sales sales) {
		this.sales = sales;
	}
	public FiniteValue getStatus() {
		return status;
	}
	public void setStatus(FiniteValue status) {
		this.status = status;
	}
	public String getSalesAssociate() {
		return salesAssociate;
	}
	public void setSalesAssociate(String salesAssociate) {
		this.salesAssociate = salesAssociate;
	}
	public Date getClosureDate() {
		return closureDate;
	}
	public void setClosureDate(Date closureDate) {
		this.closureDate = closureDate;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public ReasonCode getSalesAssReason() {
		return salesAssReason;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public void setSalesAssReason(ReasonCode salesAssReason) {
		this.salesAssReason = salesAssReason;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public ReasonCode getMgrReason() {
		return mgrReason;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public void setMgrReason(ReasonCode mgrReason) {
		this.mgrReason = mgrReason;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public boolean isVoided() {
		return voided;
	}
	public void setVoided(boolean voided) {
		this.voided = voided;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String commments) {
		this.comments = commments;
	}
	public Set<SalesLeadLine> getSalesLeadLines() {
		return salesLeadLines;
	}
	public void setSalesLeadLines(Set<SalesLeadLine> salesLeadLines) {
		this.salesLeadLines = salesLeadLines;
	}
	public void addSalesLeadLine(SalesLeadLine salesLeadLine) {
		if (salesLeadLines == null )
			salesLeadLines = new LinkedHashSet <SalesLeadLine> ();
		this.salesLeadLines.add(salesLeadLine);
	}
	public boolean isAlerted() {
		return alerted;
	}
	public void setAlerted(boolean alerted) {
		this.alerted = alerted;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public Territory getTerritory() {
		return territory;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public void setTerritory(Territory territory) {
		this.territory = territory;
	}
	public Contact getReferall() {
		return referall;
	}
	public void setReferall(Contact referall) {
		this.referall = referall;
	}
	public String getRefNo() {
		return refNo;
	}
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	public Date getRefDate() {
		return refDate;
	}
	public void setRefDate(Date refDate) {
		this.refDate = refDate;
	}
	
	
	
}
