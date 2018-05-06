package com.rainbow.crm.territory.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.crm.territory.model.Territory;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.IAjaxLookupService;

public class DivTerritoryAjaxService implements IAjaxLookupService {

	@Override
	public String lookupValues(Map<String, String> searchFields,
			IRadsContext ctx) {
		try { 
		CRMContext context = (CRMContext) ctx;
		String divisionId = searchFields.get("divisionId");
		ITerritoryService service = (ITerritoryService)SpringObjectFactory.INSTANCE.getInstance("ITerritoryService");
		List<Territory> territories = service.getAllTerritoriesforDivision(Integer.parseInt(divisionId));
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		if (territories != null ) {
			for(Territory territory : territories) {
				JSONObject state = new JSONObject();
				state.put("value", territory.getId());
				state.put("text", territory.getTerritory());
				array.put(state);
			}
		}
		json.put("territories", array) ;
		return json.toString();
		}catch(Exception ex) {
			Logwriter.INSTANCE.error(ex);
		}
		return null;
	}

	@Override
	public IRadsContext generateContext(HttpServletRequest request) {
		return CommonUtil.generateContext(request,null);
	}
	
	

}
