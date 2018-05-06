package com.rainbow.crm.division.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMListController;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.division.service.IDivisionService;
import com.rainbow.crm.division.validator.DivisionValidator;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

public class DivisionListController extends CRMListController {

	@Override
	public IBusinessService getService() {
		IDivisionService serv = (IDivisionService) SpringObjectFactory.INSTANCE.getInstance("IDivisionService");
		return serv;
	}

	@Override
	public Object getPrimaryKeyValue(ModelObject object) {
		Division div = (Division) object ;
		return div.getId();
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
		DivisionValidator validator = new DivisionValidator(((CRMContext)getContext()));
		return validator.eligibleForEdit(objects);
	}

	@Override
	public PageResult goToEdit(List<ModelObject> objects) {
		PageResult result = new PageResult();
		result.setNextPageKey("newdivision");
		return result;
	}
	
	public Map <String, String > getDivisionTypesWithSelect() {
		Map<String, String> ans = new LinkedHashMap<String, String> ();
		ans.put("null", "---Select one---") ;
		ans.putAll(GeneralSQLs.getFiniteValues(CRMConstants.FV_DIVISION_TYPE));
		return ans;
	}
	

}
