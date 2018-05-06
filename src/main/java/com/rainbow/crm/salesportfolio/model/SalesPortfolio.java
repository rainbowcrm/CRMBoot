package com.rainbow.crm.salesportfolio.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.user.model.User;
import com.rainbow.crm.vendor.model.Vendor;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

public class SalesPortfolio extends CRMBusinessModelObject {

	Division division;
	User user;
	Date startDate;
	Date endDate;
	boolean voided;
	boolean expired;
	String comments;

	Set<SalesPortfolioLine> salesPortfolioLines;

	@RadsPropertySet(useBKForJSON = true, useBKForXML = true, useBKForMap = true)
	public Division getDivision() {
		return division;
	}

	@RadsPropertySet(useBKForJSON = true, useBKForXML = true, useBKForMap = true)
	public void setDivision(Division division) {
		this.division = division;
	}

	@RadsPropertySet(isBK=true)
	public User getUser() {
		return user;
	}

	@RadsPropertySet(isBK=true)
	public void setUser(User user) {
		this.user = user;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public boolean isVoided() {
		return voided;
	}

	public void setVoided(boolean voided) {
		this.voided = voided;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Set<SalesPortfolioLine> getSalesPortfolioLines() {
		return salesPortfolioLines;
	}

	public void setSalesPortfolioLines(
			Set<SalesPortfolioLine> salesPortfolioLines) {
		this.salesPortfolioLines = salesPortfolioLines;
	}
	
	public void addSalesPortfolioLine(SalesPortfolioLine line) {
		if (salesPortfolioLines == null)
			salesPortfolioLines = new LinkedHashSet<SalesPortfolioLine>();
		salesPortfolioLines.add(line);
	}

}
