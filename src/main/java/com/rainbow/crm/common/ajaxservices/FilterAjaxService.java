package com.rainbow.crm.common.ajaxservices;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.filter.dao.CRMFilterDAO;
import com.rainbow.crm.filter.model.CRMFilter;
import com.rainbow.crm.filter.model.CRMFilterDetails;
import com.rainbow.crm.logger.Logwriter;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.IAjaxLookupService;
import com.techtrade.rads.framework.utils.Utils;

public class FilterAjaxService implements IAjaxLookupService{

	@Override
	public String lookupValues(Map<String, String> searchFields,IRadsContext ctx) {
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		CRMFilter filter =null ;
		try { 
		String filterId=searchFields.get("filterId");
		String filterName=searchFields.get("filterName");
		String page=searchFields.get("page");
		if (!Utils.isNullString(filterId)) {
			filter =(CRMFilter) CRMFilterDAO.INSTANCE.getById(filterId);
		} else if (!Utils.isNullString(filterName) &&  !Utils.isNullString(page)) {
			filter =(CRMFilter) CRMFilterDAO.INSTANCE.findByKey(ctx.getUser(), page, filterName);
		}
		AtomicInteger index = new AtomicInteger(0);
		CRMContext context = (CRMContext) ctx;
			if (context.isMobileLogin()) {
				if (filter != null) {

					for (CRMFilterDetails det : filter.getDetails()) {
						JSONObject filtJSON = new JSONObject();
						filtJSON.put("field", det.getField());
						filtJSON.put("value", det.getValue());
						array.put(index.getAndIncrement(), filtJSON);
					}
				}
				json.put("filter", array);
			} else {
				if (filter != null) {
					json.put("FilterName", filter.getName());
					for (CRMFilterDetails det : filter.getDetails()) {
						json.put(det.getField(), det.getValue());
					}
				}
			}
		
		}catch(Exception ex) {
			Logwriter.INSTANCE.error(ex);
		}
		return json.toString();
		
	}
	
	
	@Override
	public IRadsContext generateContext(HttpServletRequest request) {
		return CommonUtil.generateContext(request,null);
	}

	
	

}
