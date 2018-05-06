package com.rainbow.crm.salesforceanalyzer.controller;

import java.util.Date;
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
import com.rainbow.crm.salesforceanalyzer.model.SalesForceAnalyzer;
import com.rainbow.crm.salesperiod.model.SalesPeriodAnalyzer;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.graphdata.PieChartData;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.utils.Utils;

public class SalesForceAnalyzerController extends CRMGeneralController {

	@Override
	public PageResult submit(ModelObject object) {
		return new PageResult();
	}

	
	
	@Override
	public PageResult read(ModelObject object) {
		SalesForceAnalyzer analyzer = (SalesForceAnalyzer)object;
		if(analyzer.getFromDate() != null && analyzer.getToDate() != null) {
			PieChartData data = loadSalesData(analyzer.getFromDate(),analyzer.getToDate(),analyzer.getDivision(),(CRMContext)getContext(),analyzer.getBasedOn());
			analyzer.setSalesData(data);
			PageResult result = new PageResult();
			result.setObject(analyzer);
			return result;
		}
		return new PageResult();
	}



	private PieChartData loadSalesData (Date fromDate, Date toDate, Division division, CRMContext context, String basedOn)
	{
		IDashBoardService dashBoardService = (IDashBoardService)SpringObjectFactory.INSTANCE.getInstance("IDashBoardService");
		if("Sales".equalsIgnoreCase(basedOn))  {
			PieChartData salesData = dashBoardService.getAssociateSaleSplits(division, fromDate, toDate, context);
			return salesData ;
		}else if("Expense".equalsIgnoreCase(basedOn))  {
			PieChartData salesData = dashBoardService.getAssociateExpenseSplits(division, fromDate, toDate, context);
			return salesData ;
		}else if("Leads".equalsIgnoreCase(basedOn))  {
			PieChartData salesData = dashBoardService.getAssociateSalesLeadSplits(division, fromDate, toDate, context);
			return salesData ;
		}
		return null;
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
