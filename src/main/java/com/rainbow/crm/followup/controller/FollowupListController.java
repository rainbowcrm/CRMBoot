package com.rainbow.crm.followup.controller;

import java.util.List;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMListController;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.followup.model.Followup;
import com.rainbow.crm.followup.service.IFollowupService;
import com.rainbow.crm.followup.validator.FollowupValidator;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

public class FollowupListController extends CRMListController{

	@Override
	public IBusinessService getService() {
		IFollowupService serv = (IFollowupService) SpringObjectFactory.INSTANCE.getInstance("IFollowupService");
		return serv;
	}

	@Override
	public Object getPrimaryKeyValue(ModelObject object) {
		Followup followup = (Followup) object ;
		return followup.getId();
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
		FollowupValidator validator = new FollowupValidator(((CRMContext)getContext()));
		return validator.eligibleForEdit(objects);
	}

	@Override
	public PageResult goToEdit(List<ModelObject> objects) {
		PageResult result = new PageResult();
		result.setNextPageKey("newfollowup");
		return result;
	}
	
	

	
	
}
