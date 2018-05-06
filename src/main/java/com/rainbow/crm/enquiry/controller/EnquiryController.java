package com.rainbow.crm.enquiry.controller;



import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.rainbow.crm.common.CRMCRUDController;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMTransactionController;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.ITransactionService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.enquiry.model.Enquiry;
import com.rainbow.crm.enquiry.service.IEnquiryService;
import com.rainbow.crm.reasoncode.model.ReasonCode;
import com.rainbow.crm.reasoncode.service.IReasonCodeService;
import com.rainbow.crm.territory.model.Territory;
import com.rainbow.crm.territory.service.ITerritoryService;
import com.rainbow.crm.database.GeneralSQLs;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.utils.Utils;

public class EnquiryController extends CRMTransactionController{
	
	public IEnquiryService getService() {
		IEnquiryService serv = (IEnquiryService) SpringObjectFactory.INSTANCE.getInstance("IEnquiryService");
		return serv;
	}

	public Map <String, String > getEnquiryTypes() {
		Map<String, String> ans = GeneralSQLs.getFiniteValues(CRMConstants.FV_ENQUIRY_TYPE);
		return ans;
	}
	
	public Map <String, String > getEnquirySources() {
		Map<String, String> ans = GeneralSQLs.getFiniteValues(CRMConstants.FV_ENQUIRY_SOURCE);
		return ans;
	}
	
public Map <String, String > getAllReasons() {
		
		IReasonCodeService reasonCodeService = (IReasonCodeService)SpringObjectFactory.INSTANCE.getInstance("IReasonCodeService");
		Map<String,String > ans = new LinkedHashMap<String,String> ();
		ans.put("-1", "--Select One--");
		List<ReasonCode> reasons = reasonCodeService.getAllReasonsforType(new FiniteValue(CRMConstants.REASON_TYPE.ENQUIRY_PROCESS_REASON), (CRMContext) getContext());
		reasons.forEach( reasonCode ->  {
			ans.put(String.valueOf(reasonCode. getId()),reasonCode.getReason());
		});
		
		return ans;
		
	}
	
	public Map <String, String > getAllTerritories() {
		Map<String, String> ans = new LinkedHashMap<String, String> ();
		ITerritoryService service =(ITerritoryService) SpringObjectFactory.INSTANCE.getInstance("ITerritoryService");
		List<Territory> territorries = (List<Territory>)service.findAll("Territory", "", "territory", (CRMContext)getContext());
		if (!Utils.isNullList(territorries)) {
			for (Territory territory : territorries) {
				ans.put(String.valueOf(territory.getId()), territory.getTerritory());
			}
		}
		return ans;
	}

	@Override
	public PageResult submit(ModelObject object, String actionParam) {
		PageResult result = new PageResult();
		if("GenerateLead".equalsIgnoreCase(actionParam)) {
			IEnquiryService service = (IEnquiryService) SpringObjectFactory.INSTANCE.getInstance("IEnquiryService");
			TransactionResult res = service.generateLead((Enquiry) object,(CRMContext) getContext());
			result.setErrors(res.getErrors());
			result.setObject(res.getObject());
			if(res.hasErrors())
				result.setResult(TransactionResult.Result.SUCCESS);
			else
				result.setResult(TransactionResult.Result.FAILURE);
		}
		return result;
	}
	
	
}
