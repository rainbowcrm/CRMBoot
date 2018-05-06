package com.rainbow.crm.document.controller;

import java.util.List;
import java.util.Map;

import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMListController;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.document.model.Document;
import com.rainbow.crm.document.service.IDocumentService;
import com.rainbow.crm.document.validator.DocumentValidator;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

public class DocumentListController extends CRMListController{

	@Override
	public IBusinessService getService() {
		IDocumentService serv = (IDocumentService) SpringObjectFactory.INSTANCE.getInstance("IDocumentService");
		return serv;
	}

	@Override
	public Object getPrimaryKeyValue(ModelObject object) {
		Document document = (Document) object ;
		return document.getId();
	}

	@Override
	public PageResult submit(List<ModelObject> objects, String submitAction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RadsError> validateforDelete(List<ModelObject> objects) {
		DocumentValidator validator = new DocumentValidator(((CRMContext)getContext()));
		return null;
	}

	@Override
	public List<RadsError> validateforEdit(List<ModelObject> objects) {
		DocumentValidator validator = new DocumentValidator(((CRMContext)getContext()));
		return validator.eligibleForEdit(objects);
	}

	@Override
	public PageResult goToEdit(List<ModelObject> objects) {
		PageResult result = new PageResult();
		result.setNextPageKey("newdocument");
		return result;
	}
	
	public Map <String, String > getDocumentTypes() {
		Map<String, String> ans = GeneralSQLs.getFiniteValues(CRMConstants.FV_DOC_TYPE);
		return ans;
	}
	

	
	
}
