package com.rainbow.crm.feedback.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.sales.model.SalesLine;
import com.rainbow.crm.sales.service.ISalesService;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.IAjaxLookupService;
import com.techtrade.rads.framework.utils.Utils;

public class FeedbackAjaxObjectsService implements IAjaxLookupService {

	@Override
	public String lookupValues(Map<String, String> searchFields,
			IRadsContext ctx) {
		CRMContext context = (CRMContext) ctx;
		String billNumber = searchFields.get("BillNo");
		String objectType = searchFields.get("ObjectType");
		ISalesService salesService= (ISalesService)SpringObjectFactory.INSTANCE.getInstance("ISalesService");
		Sales sales = new Sales();
		sales.setBillNumber(billNumber);
		sales = (Sales)salesService.getByBusinessKey(sales, context);
		JSONObject json = new JSONObject();
		try {
			JSONArray array = new JSONArray();
			if(CRMConstants.FEEDBACK_ON.SALES_ASSOCIATE.equals(objectType)) {
				array= getAssociatesforSales(sales);
			}
			if(CRMConstants.FEEDBACK_ON.SALES_LINE.equals(objectType)) {
				array = getSKUforSales(sales);
			}
			json.put("objects", array);
		}catch(Exception ex) 
		{
			Logwriter.INSTANCE.error(ex);
		}
		return json.toString();
	}

	private JSONArray getSKUforSales(Sales sales)  throws Exception
	{
		JSONArray result = new JSONArray();
		if( ! Utils.isNullSet(sales.getSalesLines())) {
			for(SalesLine salesLine : sales.getSalesLines()){
				JSONObject state = new JSONObject();
				state.put("value", salesLine.getSku().getId());
				state.put("text", salesLine.getSku().getName());
				result.put(state);
			}
		}
		return result;
	}
	
	private JSONArray getAssociatesforSales(Sales sales)  throws Exception
	{
		JSONArray result = new JSONArray();
		if (sales.getSalesMan() != null ) {
			JSONObject state = new JSONObject();
			state.put("value", sales.getSalesMan().getUserId());
			state.put("text", sales.getSalesMan().getUserId());
			result.put(state);
		}
		if( ! Utils.isNullSet(sales.getSalesLines())) {
			for(SalesLine salesLine : sales.getSalesLines()){
				JSONObject state = new JSONObject();
				state.put("value", salesLine.getUser().getUserId());
				state.put("text", salesLine.getUser().getUserId());
				result.put(state);
			}
		}
		return result;
	}
	
	@Override
	public IRadsContext generateContext(HttpServletRequest request) {
		return CommonUtil.generateContext(request,null);
	}

	
}

