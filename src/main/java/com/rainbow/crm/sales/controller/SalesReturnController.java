package com.rainbow.crm.sales.controller;

import java.util.List;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMTransactionController;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.ITransactionService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.config.service.ConfigurationManager;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.sales.service.ISalesService;
import com.rainbow.crm.sales.validator.SalesErrorCodes;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.utils.Utils;

public class SalesReturnController extends CRMTransactionController{

	@Override
	public ITransactionService getService() {
		ISalesService serv = (ISalesService) SpringObjectFactory.INSTANCE.getInstance("ISalesService");
		return serv;
	}

	@Override
	public List<RadsError> adaptfromUI(ModelObject modelObject) {
		Sales sales =(Sales) modelObject;
		sales.setReturn(true);
		return super.adaptfromUI(sales);
	}

	@Override
	public List<RadsError> adapttoUI(ModelObject modelObject) {
		Sales sales =(Sales) modelObject;
		sales.setReturn(true);
		return super.adaptfromUI(sales);
	}

	public  boolean isReturnAllowed()
	{
		CRMContext ctx = (CRMContext)getContext();
		String allowReturnStr= ConfigurationManager.getConfig(ConfigurationManager.ALLOW_RETURNS, ctx);
		boolean allowReturns  = Utils.getBooleanValue(allowReturnStr);
		return allowReturns;
	}
	
	@Override
	public PageResult submit(ModelObject object) {
		boolean allowReturns = isReturnAllowed();
		if(! allowReturns) {
			PageResult pageResult = new PageResult();
			pageResult.addError(CRMValidator.getErrorforCode(SalesErrorCodes.NOT_ALLOWED_TODO_RETURNS));
			return pageResult;
		}
		return super.submit(object);
	}

	@Override
	public PageResult submit(ModelObject object, String actionParam) {
		CRMContext ctx = (CRMContext)getContext();
		String allowReturnStr= ConfigurationManager.getConfig(ConfigurationManager.ALLOW_RETURNS, ctx);
		boolean allowReturns  = Utils.getBooleanValue(allowReturnStr);
		if(! allowReturns) {
			PageResult pageResult = new PageResult();
			pageResult.addError(CRMValidator.getErrorforCode(SalesErrorCodes.NOT_ALLOWED_TODO_RETURNS));
			return pageResult;
			
		}
		return super.submit(object, actionParam);
	}
	
	
	
	

	
}
