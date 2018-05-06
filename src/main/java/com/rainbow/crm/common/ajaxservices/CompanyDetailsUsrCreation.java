package com.rainbow.crm.common.ajaxservices;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.logger.Logwriter;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.IAjaxLookupService;
import com.techtrade.rads.framework.utils.Utils;

public class CompanyDetailsUsrCreation  implements IAjaxLookupService{

	@Override
	public String lookupValues(Map<String, String> searchFields,
			IRadsContext ctx) {
		JSONObject json = new JSONObject();
		try {
			String name = searchFields.get("companyName");
			String code = searchFields.get("companyCode");
			ICompanyService service = (ICompanyService) SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
			Company company = null;
			if(!Utils.isNullString(code)) {
				company = service.findByCode(code);
			} else if(!Utils.isNullString(name)) {
				company  =service.findByName(name);
			}
			if (company != null) {
				json.put("companyCode", company.getCode());
				json.put("companyName", company.getName());
				json.put("userNameSuffix", "@" + company.getCode());
			}
		}catch(Exception ex) {
			Logwriter.INSTANCE.error(ex);
		}
		return json.toString();

	}

	@Override
	public IRadsContext generateContext(HttpServletRequest request) {
		return LoginSQLs.loggedInUser(request.getSession().getId());
	}
	
	

}
