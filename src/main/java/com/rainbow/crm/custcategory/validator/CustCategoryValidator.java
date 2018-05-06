package com.rainbow.crm.custcategory.validator;

import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.item.service.ISkuService;
import com.rainbow.crm.saleslead.model.SalesLead;
import com.rainbow.crm.saleslead.service.ISalesLeadService;
import com.rainbow.crm.user.validator.UserErrorCodes;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonErrorCodes;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.custcategory.model.CustCategory;
import com.rainbow.crm.custcategory.service.ICustCategoryService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.utils.Utils;

public class CustCategoryValidator extends CRMValidator {

	CustCategory custCategory = null;
	
	@Override
	protected void checkforCreateErrors(ModelObject object) {
		checkforErrors(object);
		ICustCategoryService  service = (ICustCategoryService) SpringObjectFactory.INSTANCE.getInstance("ICustCategoryService");
		
	}

	@Override
	protected void checkforUpdateErrors(ModelObject object) {
		checkforErrors(object);
		ICustCategoryService  service = (ICustCategoryService) SpringObjectFactory.INSTANCE.getInstance("ICustCategoryService");
		
		
	}
	
	protected void checkforErrors(ModelObject object) {
		custCategory = (CustCategory) object;
		System.out.println("Cust XML=" + custCategory.toXML());
		
	}
	
	public CustCategoryValidator(CRMContext context) {
		super(context);
	}
	
	public CustCategoryValidator(){
		
	}

	
}
