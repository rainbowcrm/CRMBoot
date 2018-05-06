package com.rainbow.crm.item.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMListController;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.division.validator.DivisionValidator;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.item.service.ISkuService;
import com.rainbow.crm.item.validator.ItemValidator;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

public class SkuListController extends CRMListController {

	@Override
	public IBusinessService getService() {
		ISkuService serv = (ISkuService) SpringObjectFactory.INSTANCE.getInstance("ISkuService");
		return serv;
	}

	@Override
	public Object getPrimaryKeyValue(ModelObject object) {
		Sku item = (Sku) object;
		return item.getId();
	}

	@Override
	public PageResult submit(List<ModelObject> objects, String submitAction) {
		return null;
	}

	@Override
	public List<RadsError> validateforDelete(List<ModelObject> objects) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RadsError> validateforEdit(List<ModelObject> objects) {
		ItemValidator validator = new ItemValidator(((CRMContext)getContext()));
		return validator.eligibleForEdit(objects);
	}

	@Override
	public PageResult goToEdit(List<ModelObject> objects) {
		PageResult result = new PageResult();
		result.setNextPageKey("newsku");
		return result;
	}
	
	public Map <String, String > getItemClasses() {
		Map<String, String> ans = new LinkedHashMap<String, String>() ; 
		ans.put("null", "---Select one---") ;
		ans.putAll(GeneralSQLs.getFiniteValues(CRMConstants.FV_ITEMCLASS_TYPE));
		return ans;
	}

	

}
