package com.rainbow.crm.corpsalesperiod.model;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.techtrade.rads.framework.model.graphdata.BarChartData;

public class CorpSalesPeriodAnalyzer extends CRMModelObject{
	
	int salePeriod ;
	BarChartData salesData;
	
	String basedOn;

	
	public int getSalePeriod() {
		return salePeriod;
	}

	public void setSalePeriod(int salePeriod) {
		this.salePeriod = salePeriod;
	}

	public BarChartData getSalesData() {
		return salesData;
	}

	public void setSalesData(BarChartData salesData) {
		this.salesData = salesData;
	}

	public String getBasedOn() {
		return basedOn;
	}

	public void setBasedOn(String basedOn) {
		this.basedOn = basedOn;
	}

	
	
	
		

}
