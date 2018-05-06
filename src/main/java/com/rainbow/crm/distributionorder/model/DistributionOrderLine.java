package com.rainbow.crm.distributionorder.model;

import java.util.Date;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.abstratcs.model.CRMItemLine;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.sales.model.Sales;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

public class DistributionOrderLine extends CRMItemLine{
	
	String docNumber;
	int lineNumber;
	Date pickDate;
	boolean picked ;
	String comments;
	boolean voided;
	DistributionOrder distributionOrderDoc;
	
	public DistributionOrderLine() {
	
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
	public DistributionOrder getDistributionOrderDoc() {
		return distributionOrderDoc;
	}
	@RadsPropertySet(excludeFromJSON=true,excludeFromMap=true,excludeFromXML=true)
	public void setDistributionOrderDoc(DistributionOrder distributionOrderDoc) {
		this.distributionOrderDoc = distributionOrderDoc;
	}
	public String getDocNumber() {
		return docNumber;
	}
	public void setDocNumber(String docNumber) {
		this.docNumber = docNumber;
	}

	public boolean isVoided() {
		return voided;
	}
	public void setVoided(boolean voided) {
		this.voided = voided;
	}
	public Date getPickDate() {
		return pickDate;
	}
	public void setPickDate(Date pickDate) {
		this.pickDate = pickDate;
	}
	public boolean isPicked() {
		return picked ;
	}
	public void setPicked(boolean picked) {
		this.picked = picked;
	}
	


}
