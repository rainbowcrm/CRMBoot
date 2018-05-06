package com.rainbow.crm.swot.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMGeneralController;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.division.service.IDivisionService;
import com.rainbow.crm.swot.model.SWOTAnalysis;
import com.rainbow.crm.swot.service.ISWOTAnalysisService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.graphdata.BarChartData;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.utils.Utils;

public class SWOTAnalysisController extends CRMGeneralController {

	@Override
	public PageResult submit(ModelObject object) {
		SWOTAnalysis swotAnalysis = (SWOTAnalysis) object;
		if(swotAnalysis.getFromDate() != null && swotAnalysis.getToDate() != null) {
			ISWOTAnalysisService service = (ISWOTAnalysisService) SpringObjectFactory.INSTANCE.getInstance("ISWOTAnalysisService");
			
			BarChartData strength = service.getStrengths(swotAnalysis.getDivision(),swotAnalysis.getFromDate(), swotAnalysis.getToDate(),(CRMContext)getContext()) ;
			swotAnalysis.setStrengths(strength);
			
			BarChartData weakness = service.getWeaknesses(swotAnalysis.getDivision(),swotAnalysis.getFromDate(), swotAnalysis.getToDate(),(CRMContext)getContext()) ;
			swotAnalysis.setWeaknesses(weakness);
			
			BarChartData opporunties = service.getOpportunities(swotAnalysis.getDivision(),swotAnalysis.getFromDate(), swotAnalysis.getToDate(),(CRMContext)getContext()) ;
			swotAnalysis.setOpportunities(opporunties);
			
			BarChartData threats = service.getThreats(swotAnalysis.getDivision(),swotAnalysis.getFromDate(), swotAnalysis.getToDate(),(CRMContext)getContext()) ;
			swotAnalysis.setThreats(threats);
			
			service.reArrangeRange(strength, weakness, opporunties, threats);
			
			PageResult result = new PageResult();
			
			result.setObject(swotAnalysis);
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
}
