package com.rainbow.crm.common.ajaxservices;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.database.LoginSQLs;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.IAjaxLookupService;
import com.techtrade.rads.framework.utils.Utils;

public class CompanyDetailsAjaxService implements IAjaxLookupService{

	@Override
	public String lookupValues(Map<String, String> searchFields,
			IRadsContext ctx) {
		String name = searchFields.get("companyName");
		String code = searchFields.get("companyCode");
		ICompanyService service = (ICompanyService) SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = null;
		if(!Utils.isNullString(name)) {
			company = service.findByCode(code);
		} else if(!Utils.isNullString(name)) {
			company  =service.findByCode(name);
		}
		return "";
		
	}

	@Override
	public IRadsContext generateContext(HttpServletRequest request) {
		return CommonUtil.generateContext(request,null);
	}
	
	

}
