package com.rainbow.crm.custcategory.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.rainbow.crm.common.CRMCRUDController;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMTransactionController;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.ITransactionService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.custcategory.model.CustCategory;
import com.rainbow.crm.custcategory.service.ICustCategoryService;
import com.rainbow.crm.custcategory.sql.CustCategorySQLs;
import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.division.service.IDivisionService;
import com.rainbow.framework.query.model.Query;
import com.rainbow.framework.query.model.QueryReport;
import com.rainbow.framework.query.service.IQueryService;
import com.rainbow.framework.setup.sql.MetadataSQL;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.ui.abstracts.PageResult.ResponseAction;
import com.techtrade.rads.framework.utils.Utils;

public class CustCategoryController extends CRMTransactionController{
	
	private boolean resultFetched;
	
	public boolean getResultFetched()
	{
		return resultFetched;
	}
	
	public ITransactionService getService() {
		ICustCategoryService serv = (ICustCategoryService) SpringObjectFactory.INSTANCE.getInstance("ICustCategoryService");
		return serv;
	}

	public Map <String, String > getEvaluationPeriods() {
		Map<String, String> ans = GeneralSQLs.getFiniteValues(CRMConstants.FV_EVALDATE);
		return ans;
	}
	public Map <String, String > getEvalCriteria() {
		Map<String, String> ans = GeneralSQLs.getFiniteValues(CRMConstants.FV_EVALCRIT);
		return ans;
	}

	public Map <String, String > getOperators() {
		Map<String, String> ans = new HashMap<String, String>();
		ans.put("=", "=");
		ans.put("<=", "<=");
		ans.put("<", "<");
		ans.put(">=", ">=");
		ans.put(">", ">");
		ans.put("!=", "!=");
		
		return ans;
	}
	
	
	
	
	

	@Override
	public PageResult submit(ModelObject object, String actionParam) {
		if("runQuery".equals(actionParam)) 
		{
			ICustCategoryService service  =(ICustCategoryService)getService();
			QueryReport queryReport = service.checCustomers((CustCategory) object ,(CRMContext) getContext());
			IQueryService queryService = (IQueryService)SpringObjectFactory.INSTANCE.getInstance("IQueryService") ;
			String reportData= queryService.getVelocityConverted(queryReport,  (CRMContext  )getContext());
			((CustCategory) object).setReportData(reportData);
			resultFetched = true;
			PageResult result = new PageResult();
			result.setNextPageKey("newcustcategory");
			result.setObject(object);
			result.setResponseAction(ResponseAction.FULLRELOAD);
			return result;
			
		}
		return super.submit(object, actionParam);
	}

	public Map <String, String > 	getAllEntityFields() {
		return CustCategorySQLs.getAllCriteriaFields("SALES");
	}
	
	public Map <String, String > getAllDivisions() {
		CRMContext ctx = ((CRMContext) getContext());
		boolean allowAll =CommonUtil.allowAllDivisionAccess(ctx);
		Map<String, String> ans = new LinkedHashMap<String, String>();
		IDivisionService service = (IDivisionService) SpringObjectFactory.INSTANCE
				.getInstance("IDivisionService");
		List<Division> divisions = service.getAllDivisions(ctx
				.getLoggedinCompany());
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
