package com.rainbow.crm.corpsalesperiod.controller;

import java.util.List;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMListController;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.corpsalesperiod.model.CorpSalesPeriod;
import com.rainbow.crm.corpsalesperiod.service.ICorpSalesPeriodService;
import com.rainbow.crm.corpsalesperiod.validator.CorpSalesPeriodValidator;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

public class CorpSalesPeriodListController extends CRMListController{

	@Override
	public IBusinessService getService() {
		ICorpSalesPeriodService serv = (ICorpSalesPeriodService) SpringObjectFactory.INSTANCE.getInstance("ICorpSalesPeriodService");
		return serv;
	}

	@Override
	public Object getPrimaryKeyValue(ModelObject object) {
		CorpSalesPeriod corpSalesPeriod = (CorpSalesPeriod) object;
		return corpSalesPeriod.getId();
				
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
		CorpSalesPeriodValidator validator = new CorpSalesPeriodValidator((CRMContext) getContext());
		return validator.eligibleForEdit(objects);
	}

	@Override
	public PageResult goToEdit(List<ModelObject> objects) {
		PageResult result = new PageResult();
		result.setNextPageKey("newcorpsalesperiod");
		return result;
	}
	
	

}
