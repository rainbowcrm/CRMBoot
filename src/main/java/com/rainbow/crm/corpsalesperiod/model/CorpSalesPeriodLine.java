package com.rainbow.crm.corpsalesperiod.model;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.abstratcs.model.CRMItemLine;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.item.model.Sku;
import com.techtrade.rads.framework.annotations.RadsPropertySet;
import com.techtrade.rads.framework.utils.Utils;

public class CorpSalesPeriodLine extends CRMBusinessModelObject {
	String period;
	int lineNumber;
	double targetPrice;
	String comments;
	double lineTotal;
	
	Item item;
	int qty;
	boolean voided;
	
	CorpSalesPeriod corpSalesPeriodDoc;
	
	public CorpSalesPeriodLine() {
		id =2;
	}
	
	
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public Item getItem() {
		return item;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public void setItem(Item item) {
		this.item = item;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	
	public boolean getVoided() {
		return voided;
	}
	public void setVoided(boolean isVoided) {
		this.voided = isVoided;
	}
	
	@RadsPropertySet(isBK=true)
	public String getPeriod() {
		return period;
	}
	@RadsPropertySet(isBK=true)
	public void setPeriod(String period) {
		this.period = period;
	}
	public int getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	public double getTargetPrice() {
		return targetPrice;
	}
	public void setTargetPrice(double targetPrice) {
		this.targetPrice = targetPrice;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	@RadsPropertySet(excludeFromJSON=true,excludeFromMap=true,excludeFromXML=true)
	public CorpSalesPeriod getCorpSalesPeriodDoc() {
		return corpSalesPeriodDoc;
	}
	@RadsPropertySet(excludeFromJSON=true,excludeFromMap=true,excludeFromXML=true)
	public void setCorpSalesPeriodDoc(CorpSalesPeriod corpSalesPeriodDoc) {
		this.corpSalesPeriodDoc = corpSalesPeriodDoc;
	}

	public double getLineTotal() {
		return lineTotal;
	}

	public void setLineTotal(double lineTotal) {
		this.lineTotal = lineTotal;
	}
	
	
	@Override
	@RadsPropertySet(excludeFromJSON=true,excludeFromMap=true,excludeFromXML=true)
	public boolean isNullContent() {
		if ((item == null || item.isNullContent()) && lineTotal <=0  && Utils.isNullString(comments) )
			return true;
		else
			return false;
	}

}
