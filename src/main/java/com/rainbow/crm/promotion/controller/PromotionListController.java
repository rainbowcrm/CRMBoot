package com.rainbow.crm.promotion.controller;

import java.util.List;
import java.util.Map;

import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMListController;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.promotion.model.Promotion;
import com.rainbow.crm.promotion.service.IPromotionService;
import com.rainbow.crm.promotion.validator.PromotionValidator;
import com.rainbow.crm.database.GeneralSQLs;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

public class PromotionListController extends CRMListController{

	@Override
	public IBusinessService getService() {
		IPromotionService serv = (IPromotionService) SpringObjectFactory.INSTANCE.getInstance("IPromotionService");
		return serv;
	}

	@Override
	public Object getPrimaryKeyValue(ModelObject object) {
		Promotion promotion = (Promotion) object ;
		return promotion.getId();
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
		PromotionValidator validator = new PromotionValidator(((CRMContext)getContext()));
		return validator.eligibleForEdit(objects);
	}

	@Override
	public PageResult goToEdit(List<ModelObject> objects) {
		PageResult result = new PageResult();
		result.setNextPageKey("newpromotions");
		return result;
	}
	
	public Map <String, String > getEvalCriteria() {
		Map<String, String> ans = GeneralSQLs.getFiniteValues(CRMConstants.FV_EVALCRIT);
		return ans;
	}
	
	

	
	
}
