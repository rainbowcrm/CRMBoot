package com.rainbow.crm.corpsalesperiod.model;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.brand.model.Brand;
import com.techtrade.rads.framework.annotations.RadsPropertySet;
import com.techtrade.rads.framework.utils.Utils;

public class CorpSalesPeriodBrand extends CRMBusinessModelObject {

	String period;
	int lineNumber;
	String comments;
	double lineTotal;

	Brand brand;

	boolean voided;

	CorpSalesPeriod corpSalesPeriodDoc;

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public double getLineTotal() {
		return lineTotal;
	}

	public void setLineTotal(double lineTotal) {
		this.lineTotal = lineTotal;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public boolean isVoided() {
		return voided;
	}

	public void setVoided(boolean voided) {
		this.voided = voided;
	}

	@RadsPropertySet(excludeFromJSON=true,excludeFromMap=true,excludeFromXML=true)
	public CorpSalesPeriod getCorpSalesPeriodDoc() {
		return corpSalesPeriodDoc;
	}

	@RadsPropertySet(excludeFromJSON=true,excludeFromMap=true,excludeFromXML=true)
	public void setCorpSalesPeriodDoc(CorpSalesPeriod corpSalesPeriodDoc) {
		this.corpSalesPeriodDoc = corpSalesPeriodDoc;
	}


	@Override
	@RadsPropertySet(excludeFromJSON=true,excludeFromMap=true,excludeFromXML=true)
	public boolean isNullContent() {
		if ((brand == null || brand.isNullContent()) && lineTotal <=0  && Utils.isNullString(comments) )
			return true;
		else
			return false;
	}
	
	
}
