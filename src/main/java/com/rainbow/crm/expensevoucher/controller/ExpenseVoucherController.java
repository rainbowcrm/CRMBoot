package com.rainbow.crm.expensevoucher.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMDBException;
import com.rainbow.crm.common.CRMTransactionController;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.division.service.IDivisionService;
import com.rainbow.crm.expensehead.model.ExpenseHead;
import com.rainbow.crm.expensehead.service.IExpenseHeadService;
import com.rainbow.crm.expensevoucher.model.ExpenseVoucher;
import com.rainbow.crm.expensevoucher.service.IExpenseVoucherService;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.TransactionController;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult.Result;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.utils.Utils;

public class ExpenseVoucherController extends CRMTransactionController{
	
	

	public IExpenseVoucherService getService() {
		IExpenseVoucherService serv = (IExpenseVoucherService) SpringObjectFactory.INSTANCE.getInstance("IExpenseVoucherService");
		return serv;
	}

	
	

	
	@Override
	public PageResult submit(ModelObject object, String actionParam) {
		PageResult result = new PageResult();
		IExpenseVoucherService service =(IExpenseVoucherService) SpringObjectFactory.INSTANCE.getInstance("IExpenseVoucherService");
		if("Approve".equalsIgnoreCase(actionParam)) {
			service.approve((CRMContext)getContext(), (ExpenseVoucher) object);
		}else if ("Counter".equalsIgnoreCase(actionParam)) {
			service.counter((CRMContext)getContext(), (ExpenseVoucher) object);
		}else if ("Reject".equalsIgnoreCase(actionParam)) {
			service.reject((CRMContext)getContext(), (ExpenseVoucher) object);
		}else if ("Hold".equalsIgnoreCase(actionParam)) {
			service.hold((CRMContext)getContext(), (ExpenseVoucher) object);
		}
		result.setNextPageKey("openexpenses");
		return result;
	}






	public Map <String, String > getAllExpenseHeads() {
		Map<String, String> ans = new LinkedHashMap<String, String> ();
		IExpenseHeadService service =(IExpenseHeadService) SpringObjectFactory.INSTANCE.getInstance("IExpenseHeadService");
		List<ExpenseHead> divisions = service.getAllExpenseHeads(((CRMContext)getContext()).getLoggedinCompany());
		if (!Utils.isNullList(divisions)) {
			for (ExpenseHead division : divisions) {
				ans.put(String.valueOf(division.getId()), division.getName());
			}
		}
		return ans;
	}
	

}
