package com.rainbow.crm.feedback.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMGeneralController;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.division.service.IDivisionService;
import com.rainbow.crm.feedback.model.FeedBackAnalyzer;
import com.rainbow.crm.feedback.service.IFeedBackService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.graphdata.BarChartData;
import com.techtrade.rads.framework.model.graphdata.GaugeChartData;
import com.techtrade.rads.framework.model.graphdata.PieChartData;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.utils.Utils;

public class FeedBackAnalyzerController extends CRMGeneralController {

	@Override
	public PageResult submit(ModelObject object) {
		FeedBackAnalyzer analyzer = (FeedBackAnalyzer) object ;
		if(analyzer.getFromDate() != null && analyzer.getToDate() != null) {
			IFeedBackService service = (IFeedBackService)SpringObjectFactory.INSTANCE.getInstance("IFeedBackService");
			PieChartData positiveSplits =service.getPositiveFeedBacksByReason(analyzer.getDivision(), analyzer.getFromDate(), analyzer.getToDate(),(CRMContext) getContext(), 
					analyzer.getFeedBackOn());
			analyzer.setPositiveSplits(positiveSplits);
			
			PieChartData negativeSplits =service.getNegativeFeedBacksByReason(analyzer.getDivision(), analyzer.getFromDate(), analyzer.getToDate(),(CRMContext) getContext(), 
					analyzer.getFeedBackOn());
			analyzer.setNegativeSplits(negativeSplits);
			
			GaugeChartData custSatisfactionData  = service.getCustomerSatisfactionIndex(analyzer.getDivision(), analyzer.getFromDate(), analyzer.getToDate(),(CRMContext) getContext(), 
					analyzer.getFeedBackOn());
			analyzer.setCustSatisfactionIndex(custSatisfactionData);
			
			BarChartData barChartData =  service.getFeedBackValue(analyzer.getDivision(), analyzer.getFromDate(), analyzer.getToDate(),(CRMContext) getContext());
			analyzer.setFeedBackRatio(barChartData);
			
			PageResult result = new PageResult();
		
			result.setObject(analyzer);
			return result;
		}
		return new PageResult();
	}
	
	@Override
	public PageResult read(ModelObject object) {
		return submit(object) ;
	}
	
	public Map <String, String > getAllDivisions() {
		CRMContext ctx = ((CRMContext) getContext());
		boolean allowAll =CommonUtil.allowAllDivisionAccess(ctx);
		Map<String, String> ans = new LinkedHashMap<String, String>();
		IDivisionService service = (IDivisionService) SpringObjectFactory.INSTANCE
				.getInstance("IDivisionService");
		List<Division> divisions = service.getAllDivisions(ctx
				.getLoggedinCompany());
		if (allowAll)
			ans.put("-1", "All Divisions");
		if (!Utils.isNullList(divisions)) {
			for (Division division : divisions) {
				if (allowAll || division.getId() == ctx.getLoggedInUser().getDivision().getId())
					ans.put(String.valueOf(division.getId()), division.getName());
			}
		}
		return ans;
	}
	
	public Map <String, String >  getAllFeedbackElements()
	{
		return GeneralSQLs.getFiniteValues(CRMConstants.FV_FEEDBACK_ON) ;
	}

}
