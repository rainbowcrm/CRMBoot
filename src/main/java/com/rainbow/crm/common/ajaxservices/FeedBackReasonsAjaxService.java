package com.rainbow.crm.common.ajaxservices;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.config.service.ConfigurationManager;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.crm.reasoncode.model.ReasonCode;
import com.rainbow.crm.reasoncode.service.IReasonCodeService;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.IAjaxLookupService;

public class FeedBackReasonsAjaxService implements IAjaxLookupService{
	
	
	
	
	@Override
	public String lookupValues(Map<String, String> searchFields,
			IRadsContext ctx) {
		String rating=searchFields.get("rating");
		String benchMark =ConfigurationManager.getConfig(ConfigurationManager.FEEDBACK_RATING_BENCHMARK, (CRMContext) ctx);
		String orientation = CRMConstants.BUSINESS_ORIENTATION.POSITIVE ;
		if (Integer.parseInt(benchMark) > Integer.parseInt(rating))
			orientation =  CRMConstants.BUSINESS_ORIENTATION.NEGATIVE ;
		IReasonCodeService reasonCodeService = (IReasonCodeService)SpringObjectFactory.INSTANCE.getInstance("IReasonCodeService") ;
		List<ReasonCode> reasons = reasonCodeService.getAllReasonsforTypeAndOrientation(new FiniteValue(CRMConstants.REASON_TYPE.FEEDBACK_REASON),
				(CRMContext) ctx, new FiniteValue(orientation)) ;
		try  { 
		JSONObject json = new JSONObject();
		JSONArray result = new JSONArray();
		for (int i = 0 ;   i < reasons.size() ; i ++ ) {
			JSONObject state = new JSONObject();
			state.put("value", reasons.get(i).getId());
			state.put("text", reasons.get(i).getReason());
			result.put(state);
		}
		json.put("reasonCodes", result);
		return json.toString();
		}catch(Exception ex) {
			Logwriter.INSTANCE.error(ex);
		}
		return null;
	}
	
	@Override
	public IRadsContext generateContext(HttpServletRequest request) {
		return CommonUtil.generateContext(request, null);
	}

}
