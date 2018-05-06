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
import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.sales.service.ISalesService;
import com.rainbow.crm.saleslead.model.SalesLead;
import com.rainbow.crm.saleslead.service.ISalesLeadService;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.ui.abstracts.ILookupService;
import com.techtrade.rads.framework.ui.abstracts.UIPage;
import com.techtrade.rads.framework.utils.Utils;

public class LookupSales implements ILookupService{

	@Override
	public Map<String,String> lookupData(IRadsContext ctx, String searchString,
			int from, int noRecords, String lookupParam,List<String > additionalFields) {
		Map<String,String> ans = new LinkedHashMap<String,String> ();
		StringBuffer condition = new StringBuffer ( "") ;
		if (!Utils.isNull(searchString)) { 
			searchString = searchString.replace("*", "%");
			condition.append(" where billNumber like  '" + searchString + "'" );
		}
		if (!Utils.isNullString(lookupParam)) {
			if (condition.length() > 2)
				condition.append( " and ");
			else
				condition.append( " where ");
			condition.append("  division.id=" + lookupParam);
		}
		
		
		ISalesService service = (ISalesService) SpringObjectFactory.INSTANCE.getInstance("ISalesService");
		List<? extends CRMModelObject> sls = service.listData(from, from  + noRecords, condition.toString(),(CRMContext)ctx,null);
		for (ModelObject obj :  sls) {
			Sales  sales = (Sales) obj;
			Customer customer = sales.getCustomer() ;
			StringBuffer key = new StringBuffer(sales.getBillNumber());
				if(additionalFields != null && additionalFields.contains("customer.phone") ){
					if (customer != null ) 
						key.append("|" + customer.getPhone());
					else
						key.append("|" + "");
					 
				} 
				if(additionalFields != null && additionalFields.contains("customer.email")  ){
					if (customer != null )
						key.append("|" + customer.getEmail());
					else
						key.append("|" + "");
				}	
				if(additionalFields != null && additionalFields.contains("customer.fullName")  ){
					if (customer != null )
						key.append("|" + customer.getFullName());
					else
						key.append("|" + "");
				}
			ans.put(key.toString(),((Sales)obj).getBillNumber());
		}

		return ans;
	}

	@Override
	public IRadsContext generateContext(HttpServletRequest request,UIPage page) {
		return CommonUtil.generateContext(request,page);
	}
}