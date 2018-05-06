package com.rainbow.crm.purchase.controller;

import java.util.List;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMListController;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.purchase.model.Purchase;
import com.rainbow.crm.purchase.service.IPurchaseService;
import com.rainbow.crm.purchase.validator.PurchaseValidator;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

public class PurchaseListController extends CRMListController{

	@Override
	public IBusinessService getService() {
		IPurchaseService serv = (IPurchaseService) SpringObjectFactory.INSTANCE.getInstance("IPurchaseService");
		return serv;
	}

	@Override
	public Object getPrimaryKeyValue(ModelObject object) {
		Purchase purchase = (Purchase) object;
		return purchase.getId();
				
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
		PurchaseValidator validator = new PurchaseValidator((CRMContext) getContext());
		return validator.eligibleForEdit(objects);
	}

	@Override
	public PageResult goToEdit(List<ModelObject> objects) {
		PageResult result = new PageResult();
		result.setNextPageKey("newpurchase");
		return result;
	}
	
	

}
