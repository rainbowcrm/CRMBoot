package com.rainbow.crm.enquiry.controller;

import java.util.List;
import java.util.Map;

import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMListController;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.enquiry.model.Enquiry;
import com.rainbow.crm.enquiry.service.IEnquiryService;
import com.rainbow.crm.enquiry.validator.EnquiryValidator;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

public class EnquiryListController extends CRMListController{

	@Override
	public IBusinessService getService() {
		IEnquiryService serv = (IEnquiryService) SpringObjectFactory.INSTANCE.getInstance("IEnquiryService");
		return serv;
	}

	@Override
	public Object getPrimaryKeyValue(ModelObject object) {
		Enquiry enquiry = (Enquiry) object ;
		return enquiry.getId();
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
		EnquiryValidator validator = new EnquiryValidator(((CRMContext)getContext()));
		return validator.eligibleForEdit(objects);
	}

	@Override
	public PageResult goToEdit(List<ModelObject> objects) {
		PageResult result = new PageResult();
		result.setNextPageKey("newenquiry");
		return result;
	}
	
	public Map <String, String > getEnquiryTypes() {
		Map<String, String> ans = GeneralSQLs.getFiniteValues(CRMConstants.FV_ENQUIRY_TYPE);
		return ans;
	}
	

	
	
}
