package com.rainbow.crm.carrier.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.carrier.model.Carrier;
import com.rainbow.crm.database.LoginSQLs;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.IAjaxLookupService;
import com.techtrade.rads.framework.utils.Utils;

public class CarrierAjaxService implements IAjaxLookupService{

	@Override
	public String lookupValues(Map<String, String> searchFields,
			IRadsContext ctx) {
		CRMContext context = (CRMContext)ctx;
		String phone = searchFields.get("Phone");
		String email = searchFields.get("Email");
		ICarrierService service = (ICarrierService)SpringObjectFactory.INSTANCE.getInstance("ICarrierService");
		Carrier carrier = null ;
		if(!Utils.isNullString(phone)) {
			carrier = service.getByPhone(context.getLoggedinCompany(), phone);
		}else 	if(!Utils.isNullString(email)) {
			carrier = service.getByEmail(context.getLoggedinCompany(), email);
		}
		if (carrier != null) {
			return carrier.toJSON();
			
		}
		return null;
	}

	@Override
	public IRadsContext generateContext(HttpServletRequest request) {
		return CommonUtil.generateContext(request,null);
	}
	
	

}
