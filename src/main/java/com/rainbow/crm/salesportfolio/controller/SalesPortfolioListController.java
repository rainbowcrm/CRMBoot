package com.rainbow.crm.salesportfolio.controller;

import java.util.List;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMListController;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.salesportfolio.model.SalesPortfolio;
import com.rainbow.crm.salesportfolio.service.ISalesPortfolioService;
import com.rainbow.crm.salesportfolio.validator.SalesPortfolioValidator;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

public class SalesPortfolioListController extends CRMListController{

	@Override
	public IBusinessService getService() {
		ISalesPortfolioService serv = (ISalesPortfolioService) SpringObjectFactory.INSTANCE.getInstance("ISalesPortfolioService");
		return serv;
	}

	@Override
	public Object getPrimaryKeyValue(ModelObject object) {
		SalesPortfolio salesPortfolio = (SalesPortfolio) object;
		return salesPortfolio.getId();
				
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
		SalesPortfolioValidator validator = new SalesPortfolioValidator((CRMContext) getContext());
		return validator.eligibleForEdit(objects);
	}

	@Override
	public PageResult goToEdit(List<ModelObject> objects) {
		PageResult result = new PageResult();
		result.setNextPageKey("newsalesportfolio");
		return result;
	}
	
	

}
