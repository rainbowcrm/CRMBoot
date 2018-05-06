package com.rainbow.crm.customer.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.database.LoginSQLs;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.IAjaxLookupService;
import com.techtrade.rads.framework.utils.Utils;

public class CustomerAjaxService implements IAjaxLookupService{

	@Override
	public String lookupValues(Map<String, String> searchFields,
			IRadsContext ctx) {
		CRMContext context = (CRMContext)ctx;
		String phone = searchFields.get("Phone");
		String email = searchFields.get("Email");
		ICustomerService service = (ICustomerService)SpringObjectFactory.INSTANCE.getInstance("ICustomerService");
		Customer customer = null ;
		if(!Utils.isNullString(phone)) {
			customer = service.getByPhone(context.getLoggedinCompany(), phone);
		}else 	if(!Utils.isNullString(email)) {
			customer = service.getByEmail(context.getLoggedinCompany(), email);
		}
		if (customer != null) {
			return customer.toJSON();
			
		}
		return null;
	}

	@Override
	public IRadsContext generateContext(HttpServletRequest request) {
		return CommonUtil.generateContext(request,null);
	}
	
	

}
