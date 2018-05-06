package com.rainbow.crm.user.controller;

import java.util.Map;

import com.rainbow.crm.common.CRMCRUDController;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.user.model.User;
import com.rainbow.crm.user.service.IUserService;
import com.techtrade.rads.framework.utils.Utils;

public class UserController extends CRMCRUDController{

	public IBusinessService getService() {
		IUserService serv = (IUserService) SpringObjectFactory.INSTANCE.getInstance("IUserService");
		return serv;
	}

	
		
	public Map <String, String > getRoleTypes() {
		Map<String, String> ans = GeneralSQLs.getFiniteValues(CRMConstants.FV_ROLE_TYPE);
		return ans;
	}
	
	public String getSuffix() {
		System.out.println("Context  =" + getContext().getUser());
		if (object != null)  {
			User currUsr = (User)object;
			if (!Utils.isNull(currUsr.getSuffix()))
			  return "@" + currUsr.getSuffix() ;
		}
		
		CRMContext ctx=(CRMContext) getContext();
		return "@" + ctx.getLoggedinCompanyCode();
	}
	
	

}
