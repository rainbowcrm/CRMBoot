package com.rainbow.crm.document.controller;



import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.rainbow.crm.common.CRMCRUDController;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.document.service.IDocumentService;
import com.rainbow.crm.territory.model.Territory;
import com.rainbow.crm.territory.service.ITerritoryService;
import com.rainbow.crm.database.GeneralSQLs;
import com.techtrade.rads.framework.utils.Utils;

public class DocumentController extends CRMCRUDController{
	
	public IBusinessService getService() {
		IDocumentService serv = (IDocumentService) SpringObjectFactory.INSTANCE.getInstance("IDocumentService");
		return serv;
	}

	public Map <String, String > getDocumentTypes() {
		Map<String, String> ans = GeneralSQLs.getFiniteValues(CRMConstants.FV_DOC_TYPE);
		return ans;
	}
	
	
}
