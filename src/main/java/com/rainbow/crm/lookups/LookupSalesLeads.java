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
import com.rainbow.crm.saleslead.model.SalesLead;
import com.rainbow.crm.saleslead.service.ISalesLeadService;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.ui.abstracts.ILookupService;
import com.techtrade.rads.framework.ui.abstracts.UIPage;
import com.techtrade.rads.framework.utils.Utils;

public class LookupSalesLeads implements ILookupService{

	@Override
	public Map<String,String> lookupData(IRadsContext ctx, String searchString,
			int from, int noRecords, String lookupParam,List<String > additionalFields) {
		Map<String,String> ans = new LinkedHashMap<String,String> ();
		StringBuffer condition = new StringBuffer ( "") ; ;
		if (!Utils.isNull(searchString)) { 
			searchString = searchString.replace("*", "%");
			condition.append(" where docNumber like  '" + searchString + "'") ;
		}
		if (!Utils.isNullString(lookupParam)) {
			if (condition.length() > 2)
				condition.append( " and ");
			else
				condition.append( " where ");
			condition.append("  division.id=" + lookupParam);
		}
		ISalesLeadService service = (ISalesLeadService) SpringObjectFactory.INSTANCE.getInstance("ISalesLeadService");
		List<? extends CRMModelObject> slsLeads = service.listData(from, from  + noRecords, condition.toString(),(CRMContext)ctx,null);
		for (ModelObject obj :  slsLeads) {
			StringBuffer key = new StringBuffer(((SalesLead)obj).getDocNumber());
			SalesLead lead =(SalesLead) obj;
			if(additionalFields != null && additionalFields.contains("salesAssociate")  ){
				if(lead.getSalesAssociate() != null) {
					key.append("|" + lead.getSalesAssociate());
				}else
				{
					key.append("|" + "" );
				}
					
			}
			ans.put(key.toString(),((SalesLead)obj).getDocNumber());
		}

		return ans;
	}

	@Override
	public IRadsContext generateContext(HttpServletRequest request,UIPage page) {
		return CommonUtil.generateContext(request,page);
	}
}