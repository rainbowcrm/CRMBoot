package com.rainbow.crm.enquiry.validator;

import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.item.service.ISkuService;
import com.rainbow.crm.saleslead.model.SalesLead;
import com.rainbow.crm.saleslead.service.ISalesLeadService;
import com.rainbow.crm.territory.model.Territory;
import com.rainbow.crm.user.validator.UserErrorCodes;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonErrorCodes;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.contact.model.Contact;
import com.rainbow.crm.enquiry.model.Enquiry;
import com.rainbow.crm.enquiry.service.IEnquiryService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.utils.Utils;

public class EnquiryValidator extends CRMValidator {

	Enquiry enquiry = null;
	
	@Override
	protected void checkforCreateErrors(ModelObject object) {
		checkforErrors(object);
		System.out.println(object.toJSON());
	}

	@Override
	protected void checkforUpdateErrors(ModelObject object) {
		checkforErrors(object);
	}
	
	protected void checkforErrors(ModelObject object) {
		enquiry = (Enquiry) object;
		
		if(enquiry.getEnqDate() == null) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Date"))) ;
		}
		if(enquiry.getPhone() == null) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Phone"))) ;
		}
		if(enquiry.getFirstName() == null) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "First_Name"))) ;
		}
		if(enquiry.getLastName() == null) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Last_Name"))) ;
		}
	}
	
	public EnquiryValidator(CRMContext context) {
		super(context);
	}
	
	public EnquiryValidator(){
		
	}

	
}
