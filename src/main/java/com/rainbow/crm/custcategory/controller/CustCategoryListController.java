package com.rainbow.crm.custcategory.controller;

import java.util.List;
import java.util.Map;

import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMListController;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.custcategory.model.CustCategory;
import com.rainbow.crm.custcategory.service.ICustCategoryService;
import com.rainbow.crm.custcategory.validator.CustCategoryValidator;
import com.rainbow.crm.database.GeneralSQLs;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

public class CustCategoryListController extends CRMListController{

	@Override
	public IBusinessService getService() {
		ICustCategoryService serv = (ICustCategoryService) SpringObjectFactory.INSTANCE.getInstance("ICustCategoryService");
		return serv;
	}

	@Override
	public Object getPrimaryKeyValue(ModelObject object) {
		CustCategory custCategory = (CustCategory) object ;
		return custCategory.getId();
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
		CustCategoryValidator validator = new CustCategoryValidator(((CRMContext)getContext()));
		return validator.eligibleForEdit(objects);
	}

	@Override
	public PageResult goToEdit(List<ModelObject> objects) {
		PageResult result = new PageResult();
		result.setNextPageKey("newcustcategory");
		return result;
	}
	
	public Map <String, String > getEvalCriteria() {
		Map<String, String> ans = GeneralSQLs.getFiniteValues(CRMConstants.FV_EVALCRIT);
		return ans;
	}
	
	

	
	
}
