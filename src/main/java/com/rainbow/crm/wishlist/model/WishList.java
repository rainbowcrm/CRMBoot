package com.rainbow.crm.wishlist.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.saleslead.model.SalesLead;
import com.rainbow.crm.user.model.User;
import com.rainbow.crm.vendor.model.Vendor;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

public class WishList extends CRMBusinessModelObject{

	Division division;
	String docNumber;
	Customer customer;
	User associate;
	Date wishListDate ;
	boolean salesLeadGenerated;
	boolean fulfilled;
	SalesLead salesLead ;
	boolean voided;
	boolean expired;
	String comments;
	Set<WishListLine> wishListLines;
	
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
	public boolean isExpired() {
		return expired;
	}
	public void setExpired(boolean expired) {
		this.expired = expired;
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
	public Date getWishListDate() {
		return wishListDate;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public Customer getCustomer() {
		return customer;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public void setWishListDate(Date wishListDate) {
		this.wishListDate = wishListDate;
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
	public Set<WishListLine> getWishListLines() {
		return wishListLines;
	}
	public void setWishListLines(Set<WishListLine> wishListLines) {
		this.wishListLines = wishListLines;
	}
	public void addWishListLine(WishListLine wishListLine) {
		if (wishListLines == null )
			wishListLines = new LinkedHashSet <WishListLine> ();
		this.wishListLines.add(wishListLine);
	}
	public boolean isFulfilled() {
		return fulfilled;
	}
	public void setFulfilled(boolean fulfilled) {
		this.fulfilled = fulfilled;
	}
	public User getAssociate() {
		return associate;
	}
	public void setAssociate(User associate) {
		this.associate = associate;
	}
	
	
}
