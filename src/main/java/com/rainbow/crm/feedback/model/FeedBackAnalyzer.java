package com.rainbow.crm.feedback.model;

import java.util.Date;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.division.model.Division;
import com.techtrade.rads.framework.model.graphdata.BarChartData;
import com.techtrade.rads.framework.model.graphdata.GaugeChartData;
import com.techtrade.rads.framework.model.graphdata.PieChartData;

public class FeedBackAnalyzer extends CRMModelObject{
	
	Division division;
	
	FiniteValue feedBackOn;
	Date fromDate;
	Date toDate;
	
	PieChartData positiveSplits;
	PieChartData negativeSplits;
	
	GaugeChartData custSatisfactionIndex;
	BarChartData feedBackRatio ;
	
	
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
	public PieChartData getPositiveSplits() {
		return positiveSplits;
	}
	public void setPositiveSplits(PieChartData positiveSplits) {
		this.positiveSplits = positiveSplits;
	}
	public PieChartData getNegativeSplits() {
		return negativeSplits;
	}
	public void setNegativeSplits(PieChartData negativeSplits) {
		this.negativeSplits = negativeSplits;
	}
	public FiniteValue getFeedBackOn() {
		return feedBackOn;
	}
	public void setFeedBackOn(FiniteValue feedBackOn) {
		this.feedBackOn = feedBackOn;
	}
	public GaugeChartData getCustSatisfactionIndex() {
		return custSatisfactionIndex;
	}
	public void setCustSatisfactionIndex(GaugeChartData custSatisfactionIndex) {
		this.custSatisfactionIndex = custSatisfactionIndex;
	}
	public BarChartData getFeedBackRatio() {
		return feedBackRatio;
	}
	public void setFeedBackRatio(BarChartData feedBackRatio) {
		this.feedBackRatio = feedBackRatio;
	}
	
	
	

}
