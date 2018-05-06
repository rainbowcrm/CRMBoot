package com.rainbow.crm.reports.model;

import java.util.Date;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.division.model.Division;

public class SalesReport  extends CRMModelObject {

	private String reportType;
	
	Date from;
	Date to;
	
	Division division;
	
	
	



	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	} 
	
	
	
}
