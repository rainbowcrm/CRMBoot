package com.rainbow.crm.dashboard.model;

import java.util.Date;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.techtrade.rads.framework.model.graphdata.BarChartData;
import com.techtrade.rads.framework.model.graphdata.LineChartData;
import com.techtrade.rads.framework.model.graphdata.PieChartData;

public class SalesDashBoard extends CRMModelObject{
	
	BarChartData salesTargetData  ;
	LineChartData salesHistory;
	PieChartData portfolioSplits;
	PieChartData leadSplits;
	
	BarChartData divManagersalesTargetData  ;
	PieChartData  divManagerSalesAssociateSplits;
	PieChartData divManagerSaleProductsSplits;
	PieChartData divManagersalesleadSplits;
	LineChartData divSalesHistory;
	String graphId;
	
	String salespiecriteria;
	
	String classification;
	
	
	
	
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}
	public String getSalespiecriteria() {
		return salespiecriteria;
	}
	public void setSalespiecriteria(String salespiecriteria) {
		this.salespiecriteria = salespiecriteria;
	}
	public BarChartData getSalesTargetData() {
		return salesTargetData;
	}
	public void setSalesTargetData(BarChartData salesTargetData) {
		this.salesTargetData = salesTargetData;
	}
	public LineChartData getSalesHistory() {
		return salesHistory;
	}
	public void setSalesHistory(LineChartData salesHistory) {
		this.salesHistory = salesHistory;
	}
	public PieChartData getPortfolioSplits() {
		return portfolioSplits;
	}
	public void setPortfolioSplits(PieChartData portfolioSplits) {
		this.portfolioSplits = portfolioSplits;
	}
	public String getGraphId() {
		return graphId;
	}
	public void setGraphId(String graphId) {
		this.graphId = graphId;
	}
	public BarChartData getDivManagersalesTargetData() {
		return divManagersalesTargetData;
	}
	public void setDivManagersalesTargetData(BarChartData divManagersalesTargetData) {
		this.divManagersalesTargetData = divManagersalesTargetData;
	}
	public PieChartData getDivManagerSalesAssociateSplits() {
		return divManagerSalesAssociateSplits;
	}
	public void setDivManagerSalesAssociateSplits(
			PieChartData divManagerSalesAssociateSplits) {
		this.divManagerSalesAssociateSplits = divManagerSalesAssociateSplits;
	}
	public PieChartData getDivManagerSaleProductsSplits() {
		return divManagerSaleProductsSplits;
	}
	public void setDivManagerSaleProductsSplits(
			PieChartData divManagerSaleProductsSplits) {
		this.divManagerSaleProductsSplits = divManagerSaleProductsSplits;
	}
	public PieChartData getLeadSplits() {
		return leadSplits;
	}
	public void setLeadSplits(PieChartData leadSplits) {
		this.leadSplits = leadSplits;
	}
	public PieChartData getDivManagersalesleadSplits() {
		return divManagersalesleadSplits;
	}
	public void setDivManagersalesleadSplits(PieChartData divManagersalesleadSplits) {
		this.divManagersalesleadSplits = divManagersalesleadSplits;
	}
	public LineChartData getDivSalesHistory() {
		return divSalesHistory;
	}
	public void setDivSalesHistory(LineChartData divSalesHistory) {
		this.divSalesHistory = divSalesHistory;
	}
	
	

}
