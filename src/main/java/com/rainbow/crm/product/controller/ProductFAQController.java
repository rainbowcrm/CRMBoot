
package com.rainbow.crm.product.controller;

import java.util.List;
import java.util.Map;

import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMTransactionController;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.ITransactionService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.product.model.ProductFAQSet;
import com.rainbow.crm.product.model.ProductPriceRange;
import com.rainbow.crm.product.service.IProductFAQService;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.ui.abstracts.UIPage;
import com.techtrade.rads.framework.utils.Utils;

public class ProductFAQController extends CRMTransactionController {

	@Override
	public PageResult submit(ModelObject object) {
		return null;
	}

	@Override
	public IRadsContext generateContext(String authToken, UIPage page) {
		CRMContext ctx = (CRMContext)CommonUtil.generateContext(authToken,page);
		if(ctx.isMobileLogin())
			ctx.setAuthorized(true);
		return ctx;
	}
	
	@Override
	public PageResult submit(ModelObject object, String actionParam) {
		return super.submit(object, actionParam);
	}

	public Map<String,String> getAllDataTypes()
	{
		return GeneralSQLs.getFiniteValues(CRMConstants.FV_CONFVALTYPE);
	}
	
	@Override
	public ITransactionService getService() {
		IProductFAQService faqService = (IProductFAQService)SpringObjectFactory.INSTANCE.getInstance("IProductFAQService");
		return faqService;
	}

	@Override
	public PageResult read() {
		ProductFAQSet fset = (ProductFAQSet) object ;
		PageResult result = new PageResult();
		if(fset.getProduct() != null && !Utils.isNullString(fset.getProduct().getName())){
			IProductFAQService faqService  =(IProductFAQService)getService();
			fset = faqService.getByProduct(fset.getProduct(), (CRMContext)getContext() );
			setObject(fset);
			result.setObject(fset);
			
		}
		return result;
	}
	
	
	
	
	
	

}
