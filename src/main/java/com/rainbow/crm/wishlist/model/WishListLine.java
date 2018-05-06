package com.rainbow.crm.wishlist.model;

import java.util.Date;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.abstratcs.model.CRMItemLine;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.saleslead.model.SalesLead;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

public class WishListLine extends CRMItemLine{
	String docNumber;
	int lineNumber;
	Division division;
	
	String comments;
	Date desiredDate ;
	boolean salesLeadGenerated;
	SalesLead salesLead;
	boolean fulfilled;
	boolean expired;
	String reasonCode;
	double desiredPrice;
	
	WishList wishListDoc;
	
	public WishListLine() {
		id =2;
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
	public WishList getWishListDoc() {
		return wishListDoc;
	}
	@RadsPropertySet(excludeFromJSON=true,excludeFromMap=true,excludeFromXML=true)
	public void setWishListDoc(WishList wishListDoc) {
		this.wishListDoc = wishListDoc;
	}
	public String getDocNumber() {
		return docNumber;
	}
	public void setDocNumber(String docNumber) {
		this.docNumber = docNumber;
	}
	public Division getDivision() {
		return division;
	}
	public void setDivision(Division division) {
		this.division = division;
	}
	public Date getDesiredDate() {
		return desiredDate;
	}
	public void setDesiredDate(Date desiredDate) {
		this.desiredDate = desiredDate;
	}
	public boolean isSalesLeadGenerated() {
		return salesLeadGenerated;
	}
	public void setSalesLeadGenerated(boolean salesLeadGenerated) {
		this.salesLeadGenerated = salesLeadGenerated;
	}
	public SalesLead getSalesLead() {
		return salesLead;
	}
	public void setSalesLead(SalesLead salesLead) {
		this.salesLead = salesLead;
	}
	public boolean isFulfilled() {
		return fulfilled;
	}
	public void setFulfilled(boolean fulfilled) {
		this.fulfilled = fulfilled;
	}

	public boolean isExpired() {
		return expired;
	}
	public void setExpired(boolean expired) {
		this.expired = expired;
	}
	public String getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	public double getDesiredPrice() {
		return desiredPrice;
	}
	public void setDesiredPrice(double unitPrice) {
		this.desiredPrice = unitPrice;
	}
	
	

}
