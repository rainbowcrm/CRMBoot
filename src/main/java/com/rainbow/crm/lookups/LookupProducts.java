package com.rainbow.crm.lookups;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.product.model.Product;
import com.rainbow.crm.product.service.IProductService;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.ui.abstracts.ILookupService;
import com.techtrade.rads.framework.ui.abstracts.UIPage;
import com.techtrade.rads.framework.utils.Utils;

public class LookupProducts implements ILookupService{

	@Override
	public Map<String,String> lookupData(IRadsContext ctx, String searchString,
			int from, int noRecords, String lookupParam,List<String > additionalFields) {
		Map<String,String> ans = new LinkedHashMap<String,String> ();
		String condition = null;
		if (!Utils.isNull(searchString)) { 
			searchString = searchString.replace("*", "%");
			condition =  " where name like  '" + searchString + "'" ;
		}
		IProductService service = (IProductService) SpringObjectFactory.INSTANCE.getInstance("IProductService");
		List<?  extends CRMModelObject> products = service.listData(from, from  + noRecords, condition,(CRMContext)ctx,null);
		for (ModelObject obj :  products) {
			ans.put(((Product)obj).getName(),((Product)obj).getName());
		}

		return ans;
	}

	@Override
	public IRadsContext generateContext(HttpServletRequest request,UIPage page) {
		return CommonUtil.generateContext(request,page);
	}
	
	

}
