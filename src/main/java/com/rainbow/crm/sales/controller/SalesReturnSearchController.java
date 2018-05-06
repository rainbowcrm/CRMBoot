package com.rainbow.crm.sales.controller;



import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMGeneralController;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.config.service.ConfigurationManager;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.sales.model.SalesReturnSearch;
import com.rainbow.crm.sales.service.ISalesService;
import com.rainbow.crm.sales.validator.SalesErrorCodes;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.utils.Utils;

public class SalesReturnSearchController  extends CRMGeneralController{

	private void prepareForReturn(Sales sales) throws Exception  {
		sales.setOriginalBillNo(sales.getBillNumber());
		sales.setBillNumber(null);
		sales.setOriginalDate(Utils.dateToString(sales.getSalesDate(), "dd-mmm-yyyy"));
		sales.setSalesDate(null);
		sales.setOriginalSalesId(sales.getId());
		sales.getSalesLines().forEach( salesLine ->  { 
			salesLine.setOriginalPrice(salesLine.getUnitPrice());
			salesLine.setOriginalQty(salesLine.getQty());
			salesLine.setQty(0);
			salesLine.setLineTotal(0);
			salesLine.setReturnPrice(salesLine.getUnitPrice());
		});
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
		boolean allowReturns  = isReturnAllowed();
		if(! allowReturns) {
			PageResult pageResult = new PageResult();
			pageResult.addError(CRMValidator.getErrorforCode(SalesErrorCodes.NOT_ALLOWED_TODO_RETURNS));
			return pageResult;
			
		}
		try {
		SalesReturnSearch search = (SalesReturnSearch) object; 
		if(search != null && !Utils.isNull(search.getOriginalBilllNumber())) {
			ISalesService salesService = (ISalesService)SpringObjectFactory.INSTANCE.getInstance("ISalesService") ;
			Sales sales = salesService.getByBillNumberforReturn(search.getDivision(), search.getOriginalBilllNumber());
			PageResult result= new PageResult();
			if(sales == null)
			{
				result.addError(CRMValidator.getErrorforCode(getContext().getLocale() ,SalesErrorCodes.SALESBILL_NOT_FOUND, search.getOriginalBilllNumber()));
				return result;
			}else
			{
				prepareForReturn(sales);
				result.setObject(sales);
				result.setNextPageKey("newsalereturn");
				return result;
			}
		}
		}catch(Exception ex) {
			Logwriter.INSTANCE.error(ex);
		}
			
		return new PageResult();
	}

	
}
