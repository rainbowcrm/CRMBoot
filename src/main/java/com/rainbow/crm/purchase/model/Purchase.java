package com.rainbow.crm.purchase.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.vendor.model.Vendor;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

public class Purchase extends CRMBusinessModelObject{

	Division division;
	String billNumber;
	String docNumber;
	Vendor vendor;
	Date purchaseDate ;
	double discAmount;
	double discPercent;
	double totalDisc;
	double taxPerc;
	double taxAmount;
	double netAmount;
	boolean creditPurchase ;
	boolean settled =true;
	boolean voided;
	boolean returned;
	boolean realised; 
	String commments;
	Set<PurchaseLine> purchaseLines;
	
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public Division getDivision() {
		return division;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public void setDivision(Division division) {
		this.division = division;
	}
	
	public String getBillNumber() {
		return billNumber;
	}
	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}
	
	@RadsPropertySet(isBK=true)
	public String getDocNumber() {
		return docNumber;
	}
	@RadsPropertySet(isBK=true)
	public void setDocNumber(String docNumber) {
		this.docNumber = docNumber;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public Vendor getVendor() {
		return vendor;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public double getDiscAmount() {
		return discAmount;
	}
	public void setDiscAmount(double discAmount) {
		this.discAmount = discAmount;
	}
	public double getDiscPercent() {
		return discPercent;
	}
	public void setDiscPercent(double discPercent) {
		this.discPercent = discPercent;
	}
	public double getTotalDisc() {
		return totalDisc;
	}
	public void setTotalDisc(double totalDisc) {
		this.totalDisc = totalDisc;
	}
	public double getTaxPerc() {
		return taxPerc;
	}
	public void setTaxPerc(double taxPerc) {
		this.taxPerc = taxPerc;
	}
	public double getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(double taxAmount) {
		this.taxAmount = taxAmount;
	}
	public double getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(double netAmount) {
		this.netAmount = netAmount;
	}
	public boolean isCreditPurchase() {
		return creditPurchase;
	}
	public void setCreditPurchase(boolean creditPurchase) {
		this.creditPurchase = creditPurchase;
	}
	public boolean isSettled() {
		return settled;
	}
	public void setSettled(boolean settled) {
		this.settled = settled;
	}

	public boolean isVoided() {
		return voided;
	}
	public void setVoided(boolean voided) {
		this.voided = voided;
	}
	public boolean isReturned() {
		return returned;
	}
	public void setReturned(boolean returned) {
		this.returned = returned;
	}
	public String getCommments() {
		return commments;
	}
	public void setCommments(String commments) {
		this.commments = commments;
	}
	public Set<PurchaseLine> getPurchaseLines() {
		return purchaseLines;
	}
	public void setPurchaseLines(Set<PurchaseLine> purchaseLines) {
		this.purchaseLines = purchaseLines;
	}
	public void addPurchaseLine(PurchaseLine purchaseLine) {
		if (purchaseLines == null )
			purchaseLines = new LinkedHashSet <PurchaseLine> ();
		this.purchaseLines.add(purchaseLine);
	}
	public boolean isRealised() {
		return realised;
	}
	public void setRealised(boolean realised) {
		this.realised = realised;
	}
	
		
}
