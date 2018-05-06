package com.rainbow.crm.swot.service;

import java.util.Date;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.division.model.Division;
import com.techtrade.rads.framework.model.graphdata.BarChartData;

public interface ISWOTAnalysisService {
	
	public BarChartData getStrengths (Division division, Date fromDate, Date toDate , CRMContext context) ;
	public BarChartData getWeaknesses (Division division, Date fromDate, Date toDate , CRMContext context) ;
	public BarChartData getOpportunities (Division division, Date fromDate, Date toDate , CRMContext context) ;
	public BarChartData getThreats (Division division, Date fromDate, Date toDate , CRMContext context) ;

	public void reArrangeRange(BarChartData data1 , BarChartData data2, BarChartData data3, BarChartData data4);
}
