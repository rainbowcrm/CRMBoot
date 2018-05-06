package com.rainbow.crm.salesperiod.controller;

import java.util.List;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMListController;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.salesperiod.model.SalesPeriod;
import com.rainbow.crm.salesperiod.service.ISalesPeriodService;
import com.rainbow.crm.salesperiod.validator.SalesPeriodValidator;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

public class SalesPeriodListController extends CRMListController{

	@Override
	public IBusinessService getService() {
		ISalesPeriodService serv = (ISalesPeriodService) SpringObjectFactory.INSTANCE.getInstance("ISalesPeriodService");
		return serv;
	}

	@Override
	public Object getPrimaryKeyValue(ModelObject object) {
		SalesPeriod salesPeriod = (SalesPeriod) object;
		return salesPeriod.getId();
				
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
		SalesPeriodValidator validator = new SalesPeriodValidator((CRMContext) getContext());
		return validator.eligibleForEdit(objects);
	}

	@Override
	public PageResult goToEdit(List<ModelObject> objects) {
		PageResult result = new PageResult();
		result.setNextPageKey("newsalesperiod");
		return result;
	}
	
	

}
