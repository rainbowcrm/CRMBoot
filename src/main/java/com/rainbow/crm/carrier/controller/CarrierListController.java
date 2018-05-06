package com.rainbow.crm.carrier.controller;

import java.util.List;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMListController;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.carrier.model.Carrier;
import com.rainbow.crm.carrier.service.ICarrierService;
import com.rainbow.crm.carrier.validator.CarrierValidator;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

public class CarrierListController extends CRMListController{

	@Override
	public IBusinessService getService() {
		ICarrierService serv = (ICarrierService) SpringObjectFactory.INSTANCE.getInstance("ICarrierService");
		return serv;
	}

	@Override
	public Object getPrimaryKeyValue(ModelObject object) {
		Carrier carrier = (Carrier) object ;
		return carrier.getId();
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
		CarrierValidator validator = new CarrierValidator(((CRMContext)getContext()));
		return validator.eligibleForEdit(objects);
	}

	@Override
	public PageResult goToEdit(List<ModelObject> objects) {
		PageResult result = new PageResult();
		result.setNextPageKey("newcarrier");
		return result;
	}
	
	

	
	
}
