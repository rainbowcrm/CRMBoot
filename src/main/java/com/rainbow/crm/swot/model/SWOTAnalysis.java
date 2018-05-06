package com.rainbow.crm.swot.model;

import java.util.Date;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.division.model.Division;
import com.techtrade.rads.framework.model.graphdata.BarChartData;

public class SWOTAnalysis  extends CRMModelObject{
	

	BarChartData strengths;
	BarChartData weaknesses;
	BarChartData opportunities;
	BarChartData threats;
	
	Division division;
	
	Date fromDate;
	Date toDate;
	
	public BarChartData getStrengths() {
		return strengths;
	}
	public void setStrengths(BarChartData strengths) {
		this.strengths = strengths;
	}
	public BarChartData getWeaknesses() {
		return weaknesses;
	}
	public void setWeaknesses(BarChartData weaknesses) {
		this.weaknesses = weaknesses;
	}
	public BarChartData getOpportunities() {
		return opportunities;
	}
	public void setOpportunities(BarChartData opportunities) {
		this.opportunities = opportunities;
	}
	public BarChartData getThreats() {
		return threats;
	}
	public void setThreats(BarChartData threats) {
		this.threats = threats;
	}
	public Division getDivision() {
		return division;
	}
	public void setDivision(Division division) {
		this.division = division;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	
	
	
	
	
}
