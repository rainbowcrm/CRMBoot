package com.rainbow.crm.address.validator;

import com.rainbow.crm.user.validator.UserErrorCodes;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.address.model.Address;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.utils.Utils;

public class AddressValidator extends CRMValidator {

	Address Address = null;
	
	@Override
	protected void checkforCreateErrors(ModelObject object) {
		checkforErrors(object);
		
	}

	@Override
	protected void checkforUpdateErrors(ModelObject object) {
		checkforErrors(object);
		
		
	}
	
	protected void checkforErrors(ModelObject object) {
		Address = (Address) object;
		System.out.println("Address XML=" + Address.toXML());
		if (Utils.isNull(Address.getEmail())) {
			errors.add(getErrorforCode(UserErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Email"))) ;
		}
		if (Utils.isNull(Address.getPhone())) {
			errors.add(getErrorforCode(UserErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Phone"))) ;
		}
		if (Utils.isNull(Address.getRecipient())) {
			errors.add(getErrorforCode(UserErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Recipient"))) ;
		}
		if (Utils.isNull(Address.getAddress1())) {
			errors.add(getErrorforCode(UserErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Address1"))) ;
		}
		if (Utils.isNull(Address.getAddress2())) {
			errors.add(getErrorforCode(UserErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Address2"))) ;
		}
		if (Utils.isNull(Address.getCity())) {
			errors.add(getErrorforCode(UserErrorCodes.FIELD_EMPTY,externalize.externalize(context, "City"))) ;
		}
		if (Utils.isNull(Address.getZipcode())) {
			errors.add(getErrorforCode(UserErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Zipcode"))) ;
		}
		if (!Utils.isNull(Address.getEmail()) && (!Address.getEmail().contains("@") || !Address.getEmail().contains(".")) ) {
			errors.add(getErrorforCode(UserErrorCodes.FIELD_NOT_VALID,externalize.externalize(context, "Email"))) ;
		}
	}
	
	public AddressValidator(CRMContext context) {
		super(context);
	}
	
	public AddressValidator(){
		
	}

	
}
