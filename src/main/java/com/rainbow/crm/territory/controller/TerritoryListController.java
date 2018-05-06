package com.rainbow.crm.territory.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMListController;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.territory.model.Territory;
import com.rainbow.crm.territory.service.ITerritoryService;
import com.rainbow.crm.territory.validator.TerritoryValidator;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

public class TerritoryListController extends CRMListController{

	
	@Override
	public IBusinessService getService() {
		ITerritoryService serv = (ITerritoryService) SpringObjectFactory.INSTANCE.getInstance("ITerritoryService");
		return serv;
	}

	@Override
	public Object getPrimaryKeyValue(ModelObject object) {
		Territory territory = (Territory) object;
		return territory.getId();
				
	}

	@Override
	public PageResult submit(List<ModelObject> objects, String submitAction) {
		PageResult result = new PageResult();
		
		return result;
	}

	@Override
	public List<RadsError> validateforDelete(List<ModelObject> objects) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RadsError> validateforEdit(List<ModelObject> objects) {
		TerritoryValidator validator = new TerritoryValidator((CRMContext) getContext());
		return validator.eligibleForEdit(objects);
	}

	@Override
	public PageResult goToEdit(List<ModelObject> objects) {
		PageResult result = new PageResult();
		result.setNextPageKey("newterritory");
		return result;
	}
	
	

}
