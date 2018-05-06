package com.rainbow.crm.promotion.controller;

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
import com.rainbow.crm.promotion.model.Promotion;
import com.rainbow.crm.promotion.service.IPromotionService;
import com.rainbow.crm.promotion.sql.PromotionSQLs;
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

public class PromotionController extends CRMTransactionController{
	
	
	public ITransactionService getService() {
		IPromotionService serv = (IPromotionService) SpringObjectFactory.INSTANCE.getInstance("IPromotionService");
		return serv;
	}


	

	

	@Override
	public PageResult submit(ModelObject object, String actionParam) {
		
		return super.submit(object, actionParam);
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
	
	public Map <String, String > getAllPromoTypes() {
		Map<String, String> ans = GeneralSQLs.getFiniteValues(CRMConstants.FV_PROMO_TYPE);
		return ans;
	}

	
	public Map <String, String > getAllItemClasses() {
		Map<String, String> ans = GeneralSQLs.getFiniteValuesWithSelect(CRMConstants.FV_ITEMCLASS_TYPE);
		return ans;
	}
	
	public Map <String, String > getAllTypes() {
		Map<String, String> ans = GeneralSQLs.getFiniteValuesWithSelect(CRMConstants.FV_SPFTYPE);
		return ans;
	}
	
	public Map <String, String > getAllBundlePricigOptions(){
		Map<String, String> ans = GeneralSQLs.getFiniteValuesWithSelect(CRMConstants.FV_BUNDLEPRICING);
		return ans;
	}
	
}
