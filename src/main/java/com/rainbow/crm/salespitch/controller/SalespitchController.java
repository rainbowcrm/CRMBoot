package com.rainbow.crm.salespitch.controller;



import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.rainbow.crm.common.CRMCRUDController;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.salespitch.service.ISalespitchService;
import com.rainbow.crm.territory.model.Territory;
import com.rainbow.crm.territory.service.ITerritoryService;
import com.rainbow.crm.database.GeneralSQLs;
import com.techtrade.rads.framework.utils.Utils;

public class SalespitchController extends CRMCRUDController{
	
	public IBusinessService getService() {
		ISalespitchService serv = (ISalespitchService) SpringObjectFactory.INSTANCE.getInstance("ISalespitchService");
		return serv;
	}

	public Map <String, String > getSalespitchTypes() {
		Map<String, String> ans = GeneralSQLs.getFiniteValues(CRMConstants.FV_SPITCH_TYPE);
		return ans;
	}
	
	public Map <String, String > getAllTerritories() {
		Map<String, String> ans = new LinkedHashMap<String, String> ();
		ITerritoryService service =(ITerritoryService) SpringObjectFactory.INSTANCE.getInstance("ITerritoryService");
		List<Territory> territorries = (List<Territory>)service.findAll("Territory", "", "territory", (CRMContext)getContext());
		if (!Utils.isNullList(territorries)) {
			for (Territory territory : territorries) {
				ans.put(String.valueOf(territory.getId()), territory.getTerritory());
			}
		}
		return ans;
	}
}
