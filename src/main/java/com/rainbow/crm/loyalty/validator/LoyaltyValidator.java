package com.rainbow.crm.loyalty.validator;

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
import com.rainbow.crm.loyalty.model.Loyalty;
import com.rainbow.crm.loyalty.service.ILoyaltyService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.utils.Utils;

public class LoyaltyValidator extends CRMValidator {

	Loyalty loyalty = null;
	
	@Override
	protected void checkforCreateErrors(ModelObject object) {
		checkforErrors(object);
		
	}

	@Override
	protected void checkforUpdateErrors(ModelObject object) {
		checkforErrors(object);
		
	}
	
	protected void checkforErrors(ModelObject object) {

	}
	
	public LoyaltyValidator(CRMContext context) {
		super(context);
	}
	
	public LoyaltyValidator(){
		
	}

	
}
