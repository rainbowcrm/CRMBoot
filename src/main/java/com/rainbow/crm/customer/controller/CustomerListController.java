package com.rainbow.crm.customer.controller;

import java.util.List;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMListController;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.customer.service.ICustomerService;
import com.rainbow.crm.customer.validator.CustomerValidator;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

public class CustomerListController extends CRMListController{

	@Override
	public IBusinessService getService() {
		ICustomerService serv = (ICustomerService) SpringObjectFactory.INSTANCE.getInstance("ICustomerService");
		return serv;
	}

	@Override
	public Object getPrimaryKeyValue(ModelObject object) {
		Customer customer = (Customer) object ;
		return customer.getId();
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
		CustomerValidator validator = new CustomerValidator(((CRMContext)getContext()));
		return validator.eligibleForEdit(objects);
	}

	@Override
	public PageResult goToEdit(List<ModelObject> objects) {
		PageResult result = new PageResult();
		result.setNextPageKey("newcustomer");
		return result;
	}
	
	

	
	
}
