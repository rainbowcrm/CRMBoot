package com.rainbow.crm.expensevoucher.controller;

import java.util.List;

import com.techtrade.rads.framework.filter.Filter;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

public class MgrExpenseVoucherListController  extends ExpenseVoucherListController{
	
	protected String getFilter(Filter filterData ) {
		StringBuffer filter =  new StringBuffer (super.getFilter(filterData));
		String prefix = " " ; 
		if (filter.length() < 1) 
			prefix = " where " ;
		else 
			prefix = " and " ;
		filter.append(  prefix +  "  status.code in  ('EXPREQ','EXPREREQ','EXPPEN' )" ) ;
		return filter.toString();
	}
	
	@Override
	public PageResult goToEdit(List<ModelObject> objects) {
		PageResult result = new PageResult();
		result.setNextPageKey("mgrexpensevoucher");
		return result;
	}
	

}
