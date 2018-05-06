package com.rainbow.crm.saleslead.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMDBException;
import com.rainbow.crm.common.CRMTransactionController;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.division.service.IDivisionService;
import com.rainbow.crm.reasoncode.model.ReasonCode;
import com.rainbow.crm.reasoncode.service.IReasonCodeService;
import com.rainbow.crm.saleslead.model.SalesLead;
import com.rainbow.crm.saleslead.model.SalesLeadExtended;
import com.rainbow.crm.saleslead.service.ISalesLeadService;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.TransactionController;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult.Result;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.utils.Utils;

public class SalesLeadController extends CRMTransactionController{
	
	

	public ISalesLeadService getService() {
		ISalesLeadService serv = (ISalesLeadService) SpringObjectFactory.INSTANCE.getInstance("ISalesLeadService");
		return serv;
	}

	

	public boolean completedForAssociate() 
	{
		SalesLead lead = (SalesLead)getObject();
		CRMContext ctx = (CRMContext)getContext();
		if(lead == null) return false;
		if(lead.getStatus() != null && ( lead.getStatus().getCode().equalsIgnoreCase(CRMConstants.SALESCYCLE_STATUS.CLOSED) ||
				 lead.getStatus().getCode().equalsIgnoreCase(CRMConstants.SALESCYCLE_STATUS.FAILED) )) {
			if(!CommonUtil.isManagerRole(ctx.getLoggedInUser())) 
				return true;
		}
		return false;
		
	}
	
	public Map <String, String > getAllReasons() {
		
		IReasonCodeService reasonCodeService = (IReasonCodeService)SpringObjectFactory.INSTANCE.getInstance("IReasonCodeService");
		Map<String,String > ans = new HashMap<String,String> ();
		List<ReasonCode> reasons = reasonCodeService.getAllReasonsforType(new FiniteValue(CRMConstants.REASON_TYPE.SALESLEAD_REASON), (CRMContext) getContext());
		reasons.forEach( reasonCode ->  {
			ans.put(String.valueOf(reasonCode. getId()),reasonCode.getReason());
		});
		
		return ans;
		
	}
	
	public boolean completedForManager() 
	{
		SalesLead lead = (SalesLead)getObject();
		CRMContext ctx = (CRMContext)getContext();
		if(lead == null) return false;
		if(lead.getStatus() != null && ( lead.getStatus().getCode().equalsIgnoreCase(CRMConstants.SALESCYCLE_STATUS.CLOSED) ||
				 lead.getStatus().getCode().equalsIgnoreCase(CRMConstants.SALESCYCLE_STATUS.FAILED) )) {
			if(CommonUtil.isManagerRole(ctx.getLoggedInUser())) 
				return true;
		}
		return false;
	}


	
	

}
