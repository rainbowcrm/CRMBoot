package com.rainbow.crm.salesportfolio.model;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.abstratcs.model.CRMItemLine;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.item.model.Sku;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

public class SalesPortfolioLine extends CRMBusinessModelObject {

	int lineNumber;
	FiniteValue portfolioType;
	String portfolioKey;
	String portfolioValue;
	boolean voided;
	String comments;

	SalesPortfolio salesPortfolioDoc;

	public SalesPortfolioLine() {
		id = 1;
	}

	public boolean getVoided() {
		return voided;
	}

	public void setVoided(boolean isVoided) {
		this.voided = isVoided;
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

	@RadsPropertySet(excludeFromJSON = true, excludeFromMap = true, excludeFromXML = true)
	public SalesPortfolio getSalesPortfolioDoc() {
		return salesPortfolioDoc;
	}

	@RadsPropertySet(excludeFromJSON = true, excludeFromMap = true, excludeFromXML = true)
	public void setSalesPortfolioDoc(SalesPortfolio salesPortfolioDoc) {
		this.salesPortfolioDoc = salesPortfolioDoc;
	}

	public FiniteValue getPortfolioType() {
		return portfolioType;
	}

	public void setPortfolioType(FiniteValue portfolioType) {
		this.portfolioType = portfolioType;
	}

	public String getPortfolioKey() {
		return portfolioKey;
	}

	public void setPortfolioKey(String portfolioKey) {
		this.portfolioKey = portfolioKey;
	}

	public String getPortfolioValue() {
		return portfolioValue;
	}

	public void setPortfolioValue(String portfolioValue) {
		this.portfolioValue = portfolioValue;
	}
	
	

}
