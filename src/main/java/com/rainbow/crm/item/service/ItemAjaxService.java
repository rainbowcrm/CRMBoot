package com.rainbow.crm.item.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.logger.Logwriter;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.IAjaxLookupService;
import com.techtrade.rads.framework.utils.Utils;

public class ItemAjaxService implements IAjaxLookupService{

	@Override
	public String lookupValues(Map<String, String> searchFields,
			IRadsContext ctx) {
		CRMContext context = (CRMContext)ctx;
		String code  = searchFields.get("Code");
		String itName = searchFields.get("Name");
		IItemService service = (IItemService)SpringObjectFactory.INSTANCE.getInstance("IItemService");
		Item item = null;
		if(!Utils.isNull(itName)){
			item =service.getByName(context.getLoggedinCompany(), itName);
		}else if(!Utils.isNull(code)){
			item =service.getByCode(context.getLoggedinCompany(), code);
		}
		try {
		if (item != null) {
			JSONObject json = new JSONObject();
			json.put("Code", item.getCode());
			json.put("Name", item.getName());

			json.put("Specification", item.getSpecification());
			json.put("PurchasePrice", item.getPurchasePrice());
			json.put("RetailPrice", item.getRetailPrice());
			return json.toString();
		}
		}catch(Exception ex) {
			Logwriter.INSTANCE.error(ex);
		}
		
		return null;
	}

	@Override
	public IRadsContext generateContext(HttpServletRequest request) {
		return CommonUtil.generateContext(request,null);
	}
	

}
