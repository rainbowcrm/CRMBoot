package com.rainbow.crm.salesperiod.model;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.user.model.User;
import com.techtrade.rads.framework.annotations.RadsPropertySet;
import com.techtrade.rads.framework.utils.Utils;

public class SalesPeriodAssociate extends CRMBusinessModelObject {
	String period;
	int lineNumber;
	String comments;
	double lineTotal;

	User user;

	boolean voided;

	SalesPeriod salesPeriodDoc;

	public SalesPeriodAssociate() {
	}



	@RadsPropertySet(useBKForJSON = true, useBKForXML = true, useBKForMap = true)
	public User getUser() {
		return user;
	}

	@RadsPropertySet(useBKForJSON = true, useBKForXML = true, useBKForMap = true)
	public void setUser(User user) {
		this.user = user;
	}


	public boolean getVoided() {
		return voided;
	}

	public void setVoided(boolean isVoided) {
		this.voided = isVoided;
	}

	@RadsPropertySet(isBK = true)
	public String getPeriod() {
		return period;
	}

	@RadsPropertySet(isBK = true)
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

	@RadsPropertySet(excludeFromJSON = true, excludeFromMap = true, excludeFromXML = true)
	public SalesPeriod getSalesPeriodDoc() {
		return salesPeriodDoc;
	}

	@RadsPropertySet(excludeFromJSON = true, excludeFromMap = true, excludeFromXML = true)
	public void setSalesPeriodDoc(SalesPeriod salesPeriodDoc) {
		this.salesPeriodDoc = salesPeriodDoc;
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
		if ((user == null || user.isNullContent()) && lineTotal <=0  && Utils.isNullString(comments) )
			return true;
		else
			return false;
	}

}
