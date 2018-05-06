package com.rainbow.crm.purchase.model;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.abstratcs.model.CRMItemLine;
import com.rainbow.crm.item.model.Sku;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

public class PurchaseLine extends CRMItemLine{
	String docNumber;
	int lineNumber;
	
	double unitPrice;
	
	String comments;
	double unitDisc;
	double discPercent;
	double lineTotalDisc;
	double taxPercent;
	double taxAmount;
	double lineTotal;
	
	Purchase purchaseDoc;
	
	public PurchaseLine() {
	}
	
	@RadsPropertySet(isBK=true)
	public String getDocNumber() {
		return docNumber;
	}
	@RadsPropertySet(isBK=true)
	public void setDocNumber(String docNumber) {
		this.docNumber = docNumber;
	}
	@RadsPropertySet(isBK=true)
	public int getLineNumber() {
		return lineNumber;
	}
	@RadsPropertySet(isBK=true)
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	
	
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public double getUnitDisc() {
		return unitDisc;
	}
	public void setUnitDisc(double unitDisc) {
		this.unitDisc = unitDisc;
	}
	public double getDiscPercent() {
		return discPercent;
	}
	public void setDiscPercent(double discPercent) {
		this.discPercent = discPercent;
	}
	public double getLineTotalDisc() {
		return lineTotalDisc;
	}
	public void setLineTotalDisc(double lineTotalDisc) {
		this.lineTotalDisc = lineTotalDisc;
	}
	public double getTaxPercent() {
		return taxPercent;
	}
	public void setTaxPercent(double taxPercent) {
		this.taxPercent = taxPercent;
	}
	public double getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(double taxAmount) {
		this.taxAmount = taxAmount;
	}
	public double getLineTotal() {
		return lineTotal;
	}
	public void setLineTotal(double lineTotal) {
		this.lineTotal = lineTotal;
	}
	
	
	@RadsPropertySet(excludeFromJSON=true,excludeFromMap=true,excludeFromXML=true)
	public Purchase getPurchaseDoc() {
		return purchaseDoc;
	}
	@RadsPropertySet(excludeFromJSON=true,excludeFromMap=true,excludeFromXML=true)
	public void setPurchaseDoc(Purchase purchaseDoc) {
		this.purchaseDoc = purchaseDoc;
	}
	
	

}
