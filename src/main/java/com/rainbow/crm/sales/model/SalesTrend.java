package com.rainbow.crm.sales.model;

import java.util.Date;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.product.model.Product;
import com.techtrade.rads.framework.model.graphdata.LineChartData;

public class SalesTrend extends CRMModelObject{
	
	Product product ;
	LineChartData chartData;
	Date fromDate;
	Date toDate ;
	int noItervals;
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public LineChartData getChartData() {
		return chartData;
	}
	public void setChartData(LineChartData chartData) {
		this.chartData = chartData;
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
	public int getNoItervals() {
		return noItervals;
	}
	public void setNoItervals(int noItervals) {
		this.noItervals = noItervals;
	}
	
	
	

}
