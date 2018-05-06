package com.rainbow.crm.salespitch.controller;

import java.util.List;
import java.util.Map;

import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMListController;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.salespitch.model.Salespitch;
import com.rainbow.crm.salespitch.service.ISalespitchService;
import com.rainbow.crm.salespitch.validator.SalespitchValidator;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

public class SalespitchListController extends CRMListController{

	@Override
	public IBusinessService getService() {
		ISalespitchService serv = (ISalespitchService) SpringObjectFactory.INSTANCE.getInstance("ISalespitchService");
		return serv;
	}

	@Override
	public Object getPrimaryKeyValue(ModelObject object) {
		Salespitch salespitch = (Salespitch) object ;
		return salespitch.getId();
	}

	@Override
	public PageResult submit(List<ModelObject> objects, String submitAction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RadsError> validateforDelete(List<ModelObject> objects) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RadsError> validateforEdit(List<ModelObject> objects) {
		SalespitchValidator validator = new SalespitchValidator(((CRMContext)getContext()));
		return validator.eligibleForEdit(objects);
	}

	@Override
	public PageResult goToEdit(List<ModelObject> objects) {
		PageResult result = new PageResult();
		result.setNextPageKey("newsalespitch");
		return result;
	}
	
	public Map <String, String > getSalespitchTypes() {
		Map<String, String> ans = GeneralSQLs.getFiniteValues(CRMConstants.FV_SPITCH_TYPE);
		return ans;
	}
	

	
	
}
