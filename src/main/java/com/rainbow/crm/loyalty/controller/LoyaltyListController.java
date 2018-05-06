package com.rainbow.crm.loyalty.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMListController;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.loyalty.model.Loyalty;
import com.rainbow.crm.loyalty.service.ILoyaltyService;
import com.rainbow.crm.loyalty.validator.LoyaltyValidator;
import com.techtrade.rads.framework.filter.Filter;
import com.techtrade.rads.framework.filter.FilterNode;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult.Result;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.utils.Utils;

public class LoyaltyListController extends CRMListController{

	@Override
	public IBusinessService getService() {
		ILoyaltyService serv = (ILoyaltyService) SpringObjectFactory.INSTANCE.getInstance("ILoyaltyService");
		return serv;
	}

	@Override
	public Object getPrimaryKeyValue(ModelObject object) {
		Loyalty loyalty = (Loyalty) object ;
		return loyalty.getId();
	}

	@Override
	public PageResult submit(List<ModelObject> objects, String submitAction) {
		
		PageResult resut = new 	PageResult();
		resut.setResult(Result.SUCCESS);
		return resut;
	}

	@Override
	public List<RadsError> validateforDelete(List<ModelObject> objects) {
		return null;
	}

	@Override
	public List<RadsError> validateforEdit(List<ModelObject> objects) {
		LoyaltyValidator validator = new LoyaltyValidator(((CRMContext)getContext()));
		return validator.eligibleForEdit(objects);
	}

	@Override
	public PageResult goToEdit(List<ModelObject> objects) {
		// TODO Auto-generated method stub
		return null;
	}


		
}
