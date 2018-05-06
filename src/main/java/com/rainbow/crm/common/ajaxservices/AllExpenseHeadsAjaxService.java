package com.rainbow.crm.common.ajaxservices;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.expensehead.model.ExpenseHead;
import com.rainbow.crm.expensehead.service.IExpenseHeadService;
import com.rainbow.crm.filter.dao.CRMFilterDAO;
import com.rainbow.crm.logger.Logwriter;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.IAjaxLookupService;

public class AllExpenseHeadsAjaxService implements IAjaxLookupService{
	
	
	@Override
	public String lookupValues(Map<String, String> searchFields,
			IRadsContext ctx) {
		JSONObject json = new JSONObject();
		StringBuffer replies = new StringBuffer (  " {\n \"allExpenseHeads\": [  ");
		try {
		IExpenseHeadService expenseHeadService = (IExpenseHeadService)SpringObjectFactory.INSTANCE.getInstance("IExpenseHeadService");
		List<ExpenseHead> expenseHeads = expenseHeadService.getAllExpenseHeads(((CRMContext)ctx).getLoggedinCompany());
		if (expenseHeads != null) {
			for (int i = 0 ; i < expenseHeads.size() ; i ++ ) {
				replies.append("{"); 
				replies.append("\"Id\":" + expenseHeads.get(i).getId() + ",\n");
				replies.append("\"Name\":\"" + expenseHeads.get(i).getName() + "\"\n }" );
				if (i < expenseHeads.size() -1 ) 
					replies.append( ",");
				replies.append("\n");
				
			
			}	
		}
		replies.append("]\n");
		replies.append("}");
		}catch(Exception ex) {
			Logwriter.INSTANCE.error(ex);
		}
		return replies.toString();
	}

	
	@Override
	public IRadsContext generateContext(HttpServletRequest request) {
		// TODO Auto-generated method stub
		 return CommonUtil.generateContext(request,null);
	}

}
