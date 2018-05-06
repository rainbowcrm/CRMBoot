package com.rainbow.crm.common.ajaxservices;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.reasoncode.model.ReasonCode;
import com.rainbow.crm.reasoncode.service.IReasonCodeService;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.IAjaxLookupService;

public class ReasonCodesAjaxService implements IAjaxLookupService{

	@Override
	public String lookupValues(Map<String, String> searchFields,
			IRadsContext ctx) {
		String type=searchFields.get("type");
		IReasonCodeService reasonCodeService = (IReasonCodeService)SpringObjectFactory.INSTANCE.getInstance("IReasonCodeService") ;
		List<ReasonCode> reasons = reasonCodeService.getAllReasonsforType(new FiniteValue(type),(CRMContext) ctx) ;
		StringBuffer replies = new StringBuffer (  " {\n \"reasonCodes\": [  ");
		for (int i = 0 ;   i < reasons.size() ; i ++ ) {
			String replyJSON = reasons.get(i).toJSON() ;
			replies.append( replyJSON  ) ;
			if (i < reasons.size() -1 ) 
				replies.append( ",");
			replies.append("\n");
		}
		replies.append("]\n");
		replies.append("}");
		return replies.toString();
	}

	@Override
	public IRadsContext generateContext(HttpServletRequest request) {
		return CommonUtil.generateContext(request, null);
	}
	

	

}
