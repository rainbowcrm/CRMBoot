package com.rainbow.crm.lookups;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.custcategory.model.CustCategory;
import com.rainbow.crm.custcategory.service.ICustCategoryService;
import com.rainbow.crm.user.model.User;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.ui.abstracts.ILookupService;
import com.techtrade.rads.framework.ui.abstracts.UIPage;
import com.techtrade.rads.framework.utils.Utils;

public class LookupCustCategories implements ILookupService{

	@Override
	public Map<String,String> lookupData(IRadsContext ctx, String searchString,
			int from, int noRecords, String lookupParam,List<String > additionalFields) {
		Map<String,String> ans = new LinkedHashMap<String,String> ();
		StringBuffer condition = new StringBuffer("");
		if (!Utils.isNull(searchString)) { 
			searchString = searchString.replace("*", "%");
			condition.append(" where name like  '" + searchString + "'") ;
			
		}
		
		if (!Utils.isNullString(lookupParam)) {
			if (condition.length() > 2)
				condition.append( " and ");
			else
				condition.append( " where ");
			condition.append("  (  division.id=" + lookupParam +   " or division is null ) ");
		}
		ICustCategoryService service = (ICustCategoryService) SpringObjectFactory.INSTANCE.getInstance("ICustCategoryService");
		List<? extends CRMModelObject> custCategories = service.listData(from, from  + noRecords, condition.toString(),(CRMContext)ctx,null);
		for (ModelObject obj :  custCategories) {
			ans.put(((CustCategory)obj).getName(),((CustCategory)obj).getName());
		}

		return ans;
	}

	@Override
	public IRadsContext generateContext(HttpServletRequest request,UIPage page) {
		return CommonUtil.generateContext(request,page);
	}

}
