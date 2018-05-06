package com.rainbow.crm.expensevoucher.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMListController;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.expensevoucher.model.ExpenseVoucher;
import com.rainbow.crm.expensevoucher.service.IExpenseVoucherService;
import com.rainbow.crm.expensevoucher.validator.ExpenseVoucherValidator;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

public class ExpenseVoucherListController extends CRMListController{

	
	
	@Override
	public IBusinessService getService() {
		IExpenseVoucherService serv = (IExpenseVoucherService) SpringObjectFactory.INSTANCE.getInstance("IExpenseVoucherService");
		return serv;
	}

	@Override
	public Object getPrimaryKeyValue(ModelObject object) {
		ExpenseVoucher expenseVoucher = (ExpenseVoucher) object;
		return expenseVoucher.getId();
				
	}

	@Override
	public PageResult submit(List<ModelObject> objects, String submitAction) {
		PageResult result = new PageResult();
		IExpenseVoucherService service = (IExpenseVoucherService) getService();

		
		return result;
	}

	@Override
	public List<RadsError> validateforDelete(List<ModelObject> objects) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RadsError> validateforEdit(List<ModelObject> objects) {
		ExpenseVoucherValidator validator = new ExpenseVoucherValidator((CRMContext) getContext());
		return validator.eligibleForEdit(objects);
	}

	@Override
	public PageResult goToEdit(List<ModelObject> objects) {
		PageResult result = new PageResult();
		result.setNextPageKey("newexpensevoucher");
		return result;
	}
	
	

}
