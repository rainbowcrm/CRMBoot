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
import com.rainbow.crm.contact.model.Contact;
import com.rainbow.crm.contact.service.IContactService;
import com.rainbow.crm.customer.model.Customer;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.ui.abstracts.ILookupService;
import com.techtrade.rads.framework.ui.abstracts.UIPage;
import com.techtrade.rads.framework.utils.Utils;

public class LookupContacts implements ILookupService{

	@Override
	public Map<String,String> lookupData(IRadsContext ctx, String searchString,
			int from, int noRecords, String lookupParam,List<String > additionalFields) {
		Map<String,String> ans = new LinkedHashMap<String,String> ();
		String condition = null;
		if (!Utils.isNull(searchString)) { 
			searchString = searchString.replace("*", "%");
			condition =  " where firstName like  '" + searchString + "' or  lastName like  '" + searchString + "' or phone like '" + searchString + "'" ;
		}
		IContactService service = (IContactService) SpringObjectFactory.INSTANCE.getInstance("IContactService");
		List<? extends CRMModelObject> contacts = service.listData(from, from  + noRecords, condition,(CRMContext)ctx,null);
		for (ModelObject obj :  contacts) {
			StringBuffer key = new StringBuffer(((Contact)obj).getFullName());
			if(additionalFields != null && additionalFields.contains("phone") )
				 key.append("|" + ((Contact)obj).getPhone());
			if(additionalFields != null && additionalFields.contains("email") )
				 key.append("|" + ((Contact)obj).getEmail());
			ans.put(key.toString(),((Contact)obj).getFullName()  );
		}

		return ans;
	}

	@Override
	public IRadsContext generateContext(HttpServletRequest request,UIPage page) {
		return CommonUtil.generateContext(request, page);
	}
	
	

}
