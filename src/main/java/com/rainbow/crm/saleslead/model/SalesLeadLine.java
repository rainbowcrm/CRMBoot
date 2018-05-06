package com.rainbow.crm.saleslead.model;

import java.util.Date;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.abstratcs.model.CRMItemLine;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.sales.model.Sales;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

public class SalesLeadLine extends CRMItemLine{
	String docNumber;
	int lineNumber;
	Division division;
	
	String comments;
	boolean salesWon;
	Sales sales;
	String reasonCode;
	double price;
	double negotiatedPrice ;
	
	SalesLead salesLeadDoc;
	
	


	public SalesLeadLine() {
	
	}
	@RadsPropertySet(isBK=true)
	public int getLineNumber() {
		return lineNumber;
	}
	@RadsPropertySet(isBK=true)
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
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
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getNegotiatedPrice() {
		return negotiatedPrice;
	}
	public void setNegotiatedPrice(double negotiatedPrice) {
		this.negotiatedPrice = negotiatedPrice;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	@RadsPropertySet(excludeFromJSON=true,excludeFromMap=true,excludeFromXML=true)
	public SalesLead getSalesLeadDoc() {
		return salesLeadDoc;
	}
	@RadsPropertySet(excludeFromJSON=true,excludeFromMap=true,excludeFromXML=true)
	public void setSalesLeadDoc(SalesLead salesLeadDoc) {
		this.salesLeadDoc = salesLeadDoc;
	}
	public String getDocNumber() {
		return docNumber;
	}
	public void setDocNumber(String docNumber) {
		this.docNumber = docNumber;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public Division getDivision() {
		return division;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public void setDivision(Division division) {
		this.division = division;
	}
	
	public String getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	
	

}
