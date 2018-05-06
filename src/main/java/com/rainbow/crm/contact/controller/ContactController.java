package com.rainbow.crm.contact.controller;



import java.util.Map;

import com.rainbow.crm.common.CRMCRUDController;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.contact.service.IContactService;
import com.rainbow.crm.database.GeneralSQLs;

public class ContactController extends CRMCRUDController{
	
	public IBusinessService getService() {
		IContactService serv = (IContactService) SpringObjectFactory.INSTANCE.getInstance("IContactService");
		return serv;
	}

	public Map <String, String > getContactTypes() {
		Map<String, String> ans = GeneralSQLs.getFiniteValues(CRMConstants.FV_CONTACT_TYPE);
		return ans;
	}
}
