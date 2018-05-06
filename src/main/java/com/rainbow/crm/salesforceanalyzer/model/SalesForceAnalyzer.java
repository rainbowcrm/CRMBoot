package com.rainbow.crm.salesforceanalyzer.model;

import java.util.Date;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.division.model.Division;
import com.techtrade.rads.framework.model.graphdata.PieChartData;

public class SalesForceAnalyzer  extends CRMModelObject{

	Division division;
	PieChartData salesData  ;
	
	Date fromDate;
	Date toDate;
	
	String basedOn;
	
	
	public String getBasedOn() {
		return basedOn;
	}
	public void setBasedOn(String basedOn) {
		this.basedOn = basedOn;
	}
	public Division getDivision() {
		return division;
	}
	public void setDivision(Division division) {
		this.division = division;
	}
	public PieChartData getSalesData() {
		return salesData;
	}
	public void setSalesData(PieChartData salesData) {
		this.salesData = salesData;
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
