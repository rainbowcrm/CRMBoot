package com.rainbow.crm.sales.model;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.abstratcs.model.CRMItemLine;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.promotion.model.Promotion;
import com.rainbow.crm.user.model.User;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

public class SalesLine extends CRMItemLine {
	String billNumber;
	int lineNumber;

	double unitPrice;

	String comments;
	double unitDisc;
	double discPercent;
	double lineTotalDisc;
	double taxPercent;
	double taxAmount;
	double lineTotal;

	Sales salesDoc;
	User user;

	boolean isReturnLine;
	Double returnPrice;
	Integer originalQty;
	Double originalPrice;
	
	Promotion promotion;
	boolean isMasterLine;
	boolean isChildLine;
	Integer masterlineId;
	

	public SalesLine() {
	}

	public String getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}

	@RadsPropertySet(isBK = true)
	public int getLineNumber() {
		return lineNumber;
	}

	@RadsPropertySet(isBK = true)
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
	
	

	public Promotion getPromotion() {
		return promotion;
	}

	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}

	public boolean getIsMasterLine() {
		return isMasterLine;
	}

	public void setIsMasterLine(boolean isMasterLine) {
		this.isMasterLine = isMasterLine;
	}

	public boolean getIsChildLine() {
		return isChildLine;
	}

	public void setIsChildLine(boolean isChildLine) {
		this.isChildLine = isChildLine;
	}

	public Integer getMasterlineId() {
		return masterlineId;
	}

	public void setMasterlineId(Integer masterlineId) {
		this.masterlineId = masterlineId;
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

	@RadsPropertySet(excludeFromJSON = true, excludeFromMap = true, excludeFromXML = true)
	public double getAbsLineTotal() {
		return Math.abs(lineTotal);
	}
	
	@RadsPropertySet(excludeFromJSON = true, excludeFromMap = true, excludeFromXML = true)
	public void setAbsLineTotal(double linetotal) {
		this.lineTotal = linetotal;
	}

	@RadsPropertySet(excludeFromJSON = true, excludeFromMap = true, excludeFromXML = true)
	public int getAbsQty() {
		return Math.abs(getQty());
	}
	
	@RadsPropertySet(excludeFromJSON = true, excludeFromMap = true, excludeFromXML = true)
	public void setAbsQty(int qty) {
		setQty(qty);
	}
	
	
	@RadsPropertySet(excludeFromJSON = true, excludeFromMap = true, excludeFromXML = true)
	public Sales getSalesDoc() {
		return salesDoc;
	}

	@RadsPropertySet(excludeFromJSON = true, excludeFromMap = true, excludeFromXML = true)
	public void setSalesDoc(Sales salesDoc) {
		this.salesDoc = salesDoc;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isReturnLine() {
		return isReturnLine;
	}

	public void setReturnLine(boolean isReturnLine) {
		this.isReturnLine = isReturnLine;
	}

	public Double getReturnPrice() {
		return returnPrice;
	}

	public void setReturnPrice(Double returnPrice) {
		this.returnPrice = returnPrice;
	}

	public Integer getOriginalQty() {
		return originalQty;
	}

	public void setOriginalQty(Integer originalQty) {
		this.originalQty = originalQty;
	}

	public Double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(Double originalPrice) {
		this.originalPrice = originalPrice;
	}
	
	
	


}
