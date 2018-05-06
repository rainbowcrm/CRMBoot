package com.rainbow.crm.saleslead.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMGeneralController;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.dashboard.service.IDashBoardService;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.division.service.IDivisionService;
import com.rainbow.crm.saleslead.model.SalesLeadAnalyzer;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.graphdata.BarChartData;
import com.techtrade.rads.framework.model.graphdata.PieChartData;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.utils.Utils;

public class SalesLeadAnalyzerController extends CRMGeneralController {

	@Override
	public PageResult submit(ModelObject object) {
		SalesLeadAnalyzer analyzer = (SalesLeadAnalyzer) object ;
		if(analyzer.getFromDate() != null && analyzer.getToDate() != null) {
			IDashBoardService dashBoardService = (IDashBoardService)SpringObjectFactory.INSTANCE.getInstance("IDashBoardService");
			BarChartData barChartData = dashBoardService.getSalesLeadPotentials(analyzer.getDivision(), analyzer.getFromDate(),analyzer.getToDate(),(CRMContext) getContext());
			analyzer.setLeadsBarData(barChartData);
			
			PieChartData pieChartData = dashBoardService.getLeadSplitsByStatus(analyzer.getDivision(), analyzer.getFromDate(),analyzer.getToDate(),(CRMContext) getContext());
			analyzer.setSalesleadSplits(pieChartData);
			
			PieChartData pieChartReasnData = dashBoardService.getLeadSplitsByReason(analyzer.getDivision(), analyzer.getFromDate(),analyzer.getToDate(),(CRMContext) getContext());
			analyzer.setSalesReasonSplits(pieChartReasnData);
			
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


}