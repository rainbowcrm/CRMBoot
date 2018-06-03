package com.rainbow.crm.followup.controller;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rainbow.crm.common.*;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.followup.model.Followup;
import com.rainbow.crm.followup.service.IFollowupService;
import com.rainbow.crm.reasoncode.model.ReasonCode;
import com.rainbow.crm.reasoncode.service.IReasonCodeService;
import com.rainbow.crm.database.GeneralSQLs;
import com.techtrade.rads.framework.controller.abstracts.TransactionController;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.utils.Utils;

public class FollowupController extends CRMTransactionController {
	
	public ITransactionService getService() {
		IFollowupService serv = (IFollowupService) SpringObjectFactory.INSTANCE.getInstance("IFollowupService");
		return serv;
	}

	@Override
	public ModelObject populateFullObjectfromPK(ModelObject objects) {
		object = (ModelObject) getService().getById(object.getPK());
		Followup followup = (Followup) object;
		if (followup !=null && followup.getLead() != null ) {
			followup.setDivision(followup.getLead().getDivision());
		}
		return object;
	}
	public boolean  isScheduledVisit()
	{
		Followup followup = (Followup) getObject() ;
		if (followup != null  && CRMConstants.FOLLOWUP_STATUS.SCHEDULED.equals(followup.getStatus().getCode()))
			return true ;
		else
			return false;
	}

	public Map <String, String > getCommunicationModes() {
		Map<String, String> ans = GeneralSQLs.getFiniteValues(CRMConstants.FV_COMMUNICATION_MODE);
		return ans;
	}
	
	public Map <String, String > getConfidenceLevels() {
		Map<String, String> ans = GeneralSQLs.getFiniteValues(CRMConstants.FV_CONFIDENCE_LEVEL);
		return ans;
	}
	
	public Map <String, String > getFollowupResults() {
		Map<String, String> ans = GeneralSQLs.getFiniteValues(CRMConstants.FV_FOLLOWUP_RESULT);
		return ans;
	}
	public Map <String, String > getSuccessReasons() {
		Map<String, String> ans = GeneralSQLs.getFiniteValues(CRMConstants.FV_SUCCESS_REASON);
		return ans;
	}
	public Map <String, String > getFailureReasons() {
		Map<String, String> ans = GeneralSQLs.getFiniteValues(CRMConstants.FV_FAILURE_REASON);
		return ans;
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

	@Override
	public PageResult submit(ModelObject object, String actionParam) {
		PageResult result = new PageResult();
		CRMContext context =(CRMContext) getContext();
		IFollowupService service = (IFollowupService)getService() ;

		Followup followup = (Followup) object;
		List<RadsError> errors = service.validateforUpdate(followup,context);
		if (Utils.isNullList(errors)) {
			if ("abort".equalsIgnoreCase(actionParam)) {
				followup.setStatus(new FiniteValue(CRMConstants.FOLLOWUP_STATUS.ABORT));
				service.update(followup, context);
			} else if ("Complete".equalsIgnoreCase(actionParam)) {
				followup.setStatus(new FiniteValue(CRMConstants.FOLLOWUP_STATUS.COMPLETED));
				service.update(followup, context);
			}
			result.setNextPageKey("followups");
			return result;
		}else  {
			result.setErrors(errors);
			return result;
		}
	}
}
