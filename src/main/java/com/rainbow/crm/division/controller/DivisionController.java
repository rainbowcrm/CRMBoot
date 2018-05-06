package com.rainbow.crm.division.controller;

import java.util.Map;


import com.rainbow.crm.common.CRMCRUDController;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.division.service.IDivisionService;

public class DivisionController  extends CRMCRUDController{

	public IBusinessService getService() {
		IDivisionService serv = (IDivisionService) SpringObjectFactory.INSTANCE.getInstance("IDivisionService");
		return serv;
	}
	
		
	public Map <String, String > getDivisionTypes() {
		Map<String, String> ans = GeneralSQLs.getFiniteValues(CRMConstants.FV_DIVISION_TYPE);
		return ans;
	}
	
	

	
}
