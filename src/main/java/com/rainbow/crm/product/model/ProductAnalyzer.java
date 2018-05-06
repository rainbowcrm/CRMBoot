package com.rainbow.crm.product.model;

import java.util.Date;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.techtrade.rads.framework.model.graphdata.PieChartData;

public class ProductAnalyzer extends CRMModelObject{
	
	PieChartData salesData  ;
	PieChartData topSalesData;
	PieChartData upMedSalesData;
	PieChartData lowMedSalesData;
	PieChartData econSalesData;
	Product product ;
	Date fromDate;
	Date toDate;
	
	
	
	
	public PieChartData getTopSalesData() {
		return topSalesData;
	}
	public void setTopSalesData(PieChartData topSalesData) {
		this.topSalesData = topSalesData;
	}
	public PieChartData getUpMedSalesData() {
		return upMedSalesData;
	}
	public void setUpMedSalesData(PieChartData upMedSalesData) {
		this.upMedSalesData = upMedSalesData;
	}
	public PieChartData getLowMedSalesData() {
		return lowMedSalesData;
	}
	public void setLowMedSalesData(PieChartData lowMedSalesData) {
		this.lowMedSalesData = lowMedSalesData;
	}
	public PieChartData getEconSalesData() {
		return econSalesData;
	}
	public void setEconSalesData(PieChartData econSalesData) {
		this.econSalesData = econSalesData;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
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
