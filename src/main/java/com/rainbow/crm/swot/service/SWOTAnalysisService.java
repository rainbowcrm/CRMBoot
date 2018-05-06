package com.rainbow.crm.swot.service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.swot.sqls.SWOTAnalysisSQLs;
import com.techtrade.rads.framework.model.graphdata.BarChartData;
import com.techtrade.rads.framework.model.graphdata.BarData;
import com.techtrade.rads.framework.model.graphdata.BarChartData.Range;
import com.techtrade.rads.framework.utils.Utils;

public class SWOTAnalysisService implements ISWOTAnalysisService {

	@Override
	public BarChartData getStrengths(Division division, Date fromDate,
			Date toDate, CRMContext context) {
	
		AtomicInteger maxValue = new AtomicInteger(0);
		Map<String,Integer> strengthLeads =SWOTAnalysisSQLs.getSalesLeadReasonSplitups(division.getId(), context.getLoggedinCompany(),
				Utils.getSQLDate(fromDate), Utils.getSQLDate(toDate),CRMConstants.FACTOR_TYPE.INTERNAL, 
				CRMConstants.BUSINESS_ORIENTATION.POSITIVE);
		
		Map<String,Integer> strengthfeedBackss =SWOTAnalysisSQLs.getFeedBackReasonSplitups(division.getId(), context.getLoggedinCompany(),
				Utils.getSQLDate(fromDate), Utils.getSQLDate(toDate),CRMConstants.FACTOR_TYPE.INTERNAL, 
				CRMConstants.BUSINESS_ORIENTATION.POSITIVE);
		strengthLeads.putAll(strengthfeedBackss);
		
		Map<String,Integer> strengthEnquiries =SWOTAnalysisSQLs.getEnquiryReasonSplitups(division.getId(), context.getLoggedinCompany(),
				Utils.getSQLDate(fromDate), Utils.getSQLDate(toDate),CRMConstants.FACTOR_TYPE.INTERNAL, 
				CRMConstants.BUSINESS_ORIENTATION.POSITIVE);
		strengthLeads.putAll(strengthEnquiries);
		
		BarChartData barChartData = new BarChartData();
		barChartData.setTitle("Strengths");
		barChartData.setSubTitle(" ");
		
		strengthLeads.entrySet().forEach(entry  -> { ;
			BarData tagetBarData = new BarData();
			BarChartData.Division targetDivis = barChartData.new Division();
			targetDivis.setDivisionTitle(entry.getKey());
			tagetBarData.setText(entry.getKey());
			tagetBarData.setLegend(entry.getKey());
			tagetBarData.setValue(entry.getValue());
			tagetBarData.setColor("Green");
			targetDivis.addBarData(tagetBarData);
			barChartData.addDivision(targetDivis);
			if(entry.getValue() > maxValue.get()) {
				maxValue.set(entry.getValue());
			}
		}  );
		
		BarChartData.Range range =  barChartData.new  Range();
		range.setyMax(maxValue.get());
		range.setyMin(0);
		range.setxMin(0);
		range.setxMax(100);
		barChartData.setRange(range);
		
		return barChartData;
	}

	@Override
	public BarChartData getWeaknesses(Division division, Date fromDate,
			Date toDate, CRMContext context) {
		AtomicInteger maxValue = new AtomicInteger(0);
		Map<String,Integer> strengthLeads =SWOTAnalysisSQLs.getSalesLeadReasonSplitups(division.getId(), context.getLoggedinCompany(),
				Utils.getSQLDate(fromDate), Utils.getSQLDate(toDate),CRMConstants.FACTOR_TYPE.INTERNAL, 
				CRMConstants.BUSINESS_ORIENTATION.NEGATIVE);
		
		Map<String,Integer> strengthfeedBackss =SWOTAnalysisSQLs.getFeedBackReasonSplitups(division.getId(), context.getLoggedinCompany(),
				Utils.getSQLDate(fromDate), Utils.getSQLDate(toDate),CRMConstants.FACTOR_TYPE.INTERNAL, 
				CRMConstants.BUSINESS_ORIENTATION.NEGATIVE);
		strengthLeads.putAll(strengthfeedBackss);
		
		Map<String,Integer> weaknesEnquiries =SWOTAnalysisSQLs.getEnquiryReasonSplitups(division.getId(), context.getLoggedinCompany(),
				Utils.getSQLDate(fromDate), Utils.getSQLDate(toDate),CRMConstants.FACTOR_TYPE.INTERNAL, 
				CRMConstants.BUSINESS_ORIENTATION.NEGATIVE);
		strengthLeads.putAll(weaknesEnquiries);
		
		BarChartData barChartData = new BarChartData();
		barChartData.setTitle("Weakness");
		barChartData.setSubTitle(" ");
		
		strengthLeads.entrySet().forEach(entry  -> { ;
			BarData tagetBarData = new BarData();
			BarChartData.Division targetDivis = barChartData.new Division();
			targetDivis.setDivisionTitle(entry.getKey());
			tagetBarData.setText(entry.getKey());
			tagetBarData.setLegend(entry.getKey());
			tagetBarData.setValue(entry.getValue());
			tagetBarData.setColor("Grey");
			targetDivis.addBarData(tagetBarData);
			barChartData.addDivision(targetDivis);
			if(entry.getValue() > maxValue.get()) {
				maxValue.set(entry.getValue());
			}
		}  );
		
		BarChartData.Range range =  barChartData.new  Range();
		range.setyMax(maxValue.get());
		range.setyMin(0);
		range.setxMin(0);
		range.setxMax(100);
		barChartData.setRange(range);
		
		return barChartData;
	}

	@Override
	public BarChartData getOpportunities(Division division, Date fromDate,
			Date toDate, CRMContext context) {
		AtomicInteger maxValue = new AtomicInteger(0);
		Map<String,Integer> strengthLeads =SWOTAnalysisSQLs.getSalesLeadReasonSplitups(division.getId(), context.getLoggedinCompany(),
				Utils.getSQLDate(fromDate), Utils.getSQLDate(toDate),CRMConstants.FACTOR_TYPE.EXTERNAL, 
				CRMConstants.BUSINESS_ORIENTATION.POSITIVE);
		
		Map<String,Integer> strengthfeedBackss =SWOTAnalysisSQLs.getFeedBackReasonSplitups(division.getId(), context.getLoggedinCompany(),
				Utils.getSQLDate(fromDate), Utils.getSQLDate(toDate),CRMConstants.FACTOR_TYPE.EXTERNAL, 
				CRMConstants.BUSINESS_ORIENTATION.POSITIVE);
		strengthLeads.putAll(strengthfeedBackss);
		
		Map<String,Integer> weaknesEnquiries =SWOTAnalysisSQLs.getEnquiryReasonSplitups(division.getId(), context.getLoggedinCompany(),
				Utils.getSQLDate(fromDate), Utils.getSQLDate(toDate),CRMConstants.FACTOR_TYPE.EXTERNAL, 
				CRMConstants.BUSINESS_ORIENTATION.POSITIVE);
		strengthLeads.putAll(weaknesEnquiries);
		
		BarChartData barChartData = new BarChartData();
		barChartData.setTitle("Opportunities");
		barChartData.setSubTitle(" ");
		
		strengthLeads.entrySet().forEach(entry  -> { ;
			BarData tagetBarData = new BarData();
			BarChartData.Division targetDivis = barChartData.new Division();
			targetDivis.setDivisionTitle(entry.getKey());
			tagetBarData.setText(entry.getKey());
			tagetBarData.setLegend(entry.getKey());
			tagetBarData.setValue(entry.getValue());
			tagetBarData.setColor("Blue");
			targetDivis.addBarData(tagetBarData);
			barChartData.addDivision(targetDivis);
			if(entry.getValue() > maxValue.get()) {
				maxValue.set(entry.getValue());
			}
		}  );
		
		BarChartData.Range range =  barChartData.new  Range();
		range.setyMax(maxValue.get());
		range.setyMin(0);
		range.setxMin(0);
		range.setxMax(100);
		barChartData.setRange(range);
		
		return barChartData;
	}

	@Override
	public BarChartData getThreats(Division division, Date fromDate,
			Date toDate, CRMContext context) {
		AtomicInteger maxValue = new AtomicInteger(0);
		Map<String,Integer> strengthLeads =SWOTAnalysisSQLs.getSalesLeadReasonSplitups(division.getId(), context.getLoggedinCompany(),
				Utils.getSQLDate(fromDate), Utils.getSQLDate(toDate),CRMConstants.FACTOR_TYPE.EXTERNAL, 
				CRMConstants.BUSINESS_ORIENTATION.NEGATIVE);
		
		Map<String,Integer> strengthfeedBackss =SWOTAnalysisSQLs.getFeedBackReasonSplitups(division.getId(), context.getLoggedinCompany(),
				Utils.getSQLDate(fromDate), Utils.getSQLDate(toDate),CRMConstants.FACTOR_TYPE.EXTERNAL, 
				CRMConstants.BUSINESS_ORIENTATION.NEGATIVE);
		strengthLeads.putAll(strengthfeedBackss);
		
		Map<String,Integer> weaknesEnquiries =SWOTAnalysisSQLs.getEnquiryReasonSplitups(division.getId(), context.getLoggedinCompany(),
				Utils.getSQLDate(fromDate), Utils.getSQLDate(toDate),CRMConstants.FACTOR_TYPE.EXTERNAL, 
				CRMConstants.BUSINESS_ORIENTATION.NEGATIVE);
		strengthLeads.putAll(weaknesEnquiries);
		
		
		BarChartData barChartData = new BarChartData();
		barChartData.setTitle("Threats");
		barChartData.setSubTitle(" ");
		
		strengthLeads.entrySet().forEach(entry  -> { ;
			BarData tagetBarData = new BarData();
			BarChartData.Division targetDivis = barChartData.new Division();
			targetDivis.setDivisionTitle(entry.getKey());
			tagetBarData.setText(entry.getKey());
			tagetBarData.setLegend(entry.getKey());
			tagetBarData.setValue(entry.getValue());
			tagetBarData.setColor("Red");
			targetDivis.addBarData(tagetBarData);
			barChartData.addDivision(targetDivis);
			if(entry.getValue() > maxValue.get()) {
				maxValue.set(entry.getValue());
			}
		}  );
		
		BarChartData.Range range =  barChartData.new  Range();
		range.setyMax(maxValue.get());
		range.setyMin(0);
		range.setxMin(0);
		range.setxMax(100);
		barChartData.setRange(range);
		
		return barChartData;
	}
	
	
	@Override
	public void reArrangeRange(BarChartData data1 , BarChartData data2, BarChartData data3, BarChartData data4)
	{
		AtomicInteger maxRange =new AtomicInteger(0);
		if (data1.getRange().getyMax() >  maxRange.get())
			maxRange.set(data1.getRange().getyMax());
		if (data2.getRange().getyMax() >  maxRange.get())
			maxRange.set(data2.getRange().getyMax());
		if (data3.getRange().getyMax() >  maxRange.get())
			maxRange.set(data3.getRange().getyMax());
		if (data4.getRange().getyMax() >  maxRange.get())
			maxRange.set(data4.getRange().getyMax());
		
		data1.getRange().setyMax(maxRange.get());
		data2.getRange().setyMax(maxRange.get());
		data3.getRange().setyMax(maxRange.get());
		data4.getRange().setyMax(maxRange.get());
		
	}

}
