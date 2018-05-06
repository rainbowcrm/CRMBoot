package com.rainbow.crm.common.ajaxservices;

import java.util.Iterator;
import java.util.Map;






import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.filter.dao.CRMFilterDAO;
import com.rainbow.crm.logger.Logwriter;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.IAjaxLookupService;

public class AllFiltersAjaxService implements IAjaxLookupService{

	@Override
	public String lookupValues(Map<String, String> searchFields,
			IRadsContext ctx) {
		JSONObject json = new JSONObject();
		try {
		String page=searchFields.get("page");
		String user = ctx.getUser();
		Map<String,String> filters = CRMFilterDAO.INSTANCE.findAllByPage(user, page) ;
		if (filters != null) {
			Iterator<String> it = filters.keySet().iterator();
			while(it.hasNext()) {
				String key = it.next() ;
				String value = filters.get(key);
				json.put(key, value);
			}
		}
		}catch(Exception ex) {
			Logwriter.INSTANCE.error(ex);
		}
		return json.toString();
	}

	@Override
	public IRadsContext generateContext(HttpServletRequest request) {
		// TODO Auto-generated method stub
		 return CommonUtil.generateContext(request,null);
	}
	
	

}
