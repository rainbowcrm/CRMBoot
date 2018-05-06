package com.rainbow.crm.contact.validator;

import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.item.service.ISkuService;
import com.rainbow.crm.user.validator.UserErrorCodes;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonErrorCodes;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.contact.model.Contact;
import com.rainbow.crm.contact.service.IContactService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.utils.Utils;

public class ContactValidator extends CRMValidator {

	Contact contact = null;
	
	@Override
	protected void checkforCreateErrors(ModelObject object) {
		checkforErrors(object);
		IContactService  service = (IContactService) SpringObjectFactory.INSTANCE.getInstance("IContactService");
		Contact  exist = (Contact)service.getByEmail(contact.getCompany().getId(), contact.getEmail());
		if(exist != null ) {
			errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Email"))) ;
		}
		exist = (Contact)service.getByPhone(contact.getCompany().getId(), contact.getPhone());
		if(exist != null ) {
			errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Phone"))) ;
		}
	}

	@Override
	protected void checkforUpdateErrors(ModelObject object) {
		checkforErrors(object);
		IContactService  service = (IContactService) SpringObjectFactory.INSTANCE.getInstance("IContactService");
		Contact  exist = (Contact)service.getByEmail(contact.getCompany().getId(), contact.getEmail());
		if(exist != null  && exist.getId() != contact.getId() ) {
			errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Email"))) ;
		}
		exist = (Contact)service.getByEmail(contact.getCompany().getId(), contact.getPhone());
		if(exist != null && exist.getId() != contact.getId() ) {
			errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Phone"))) ;
		}
		
	}
	
	protected void checkforErrors(ModelObject object) {
		contact = (Contact) object;
		System.out.println("Cust XML=" + contact.toXML());
		if(Utils.isNullString(contact.getFirstName())) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "First_Name"))) ;
		}
		if(Utils.isNullString(contact.getFirstName())) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Last_Name"))) ;
		}
		if (Utils.isNull(contact.getEmail())) {
			errors.add(getErrorforCode(UserErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Email"))) ;
		}
		if (Utils.isNull(contact.getPhone())) {
			errors.add(getErrorforCode(UserErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Phone"))) ;
		}
		if (!Utils.isNull(contact.getEmail()) && (!contact.getEmail().contains("@") || !contact.getEmail().contains(".")) ) {
			errors.add(getErrorforCode(UserErrorCodes.FIELD_NOT_VALID,externalize.externalize(context, "Email"))) ;
		}
	}
	
	public ContactValidator(CRMContext context) {
		super(context);
	}
	
	public ContactValidator(){
		
	}

	
}
