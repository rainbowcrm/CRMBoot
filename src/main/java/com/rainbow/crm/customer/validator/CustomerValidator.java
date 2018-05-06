package com.rainbow.crm.customer.validator;

import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.item.service.ISkuService;
import com.rainbow.crm.user.validator.UserErrorCodes;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonErrorCodes;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.customer.service.ICustomerService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.utils.Utils;

public class CustomerValidator extends CRMValidator {

	Customer customer = null;
	
	@Override
	protected void checkforCreateErrors(ModelObject object) {
		checkforErrors(object);
		ICustomerService  service = (ICustomerService) SpringObjectFactory.INSTANCE.getInstance("ICustomerService");
		Customer  exist = (Customer)service.getByEmail(customer.getCompany().getId(), customer.getEmail());
		if(exist != null ) {
			errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Email"))) ;
		}
		exist = (Customer)service.getByPhone(customer.getCompany().getId(), customer.getPhone());
		if(exist != null ) {
			errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Phone"))) ;
		}
	}

	@Override
	protected void checkforUpdateErrors(ModelObject object) {
		checkforErrors(object);
		ICustomerService  service = (ICustomerService) SpringObjectFactory.INSTANCE.getInstance("ICustomerService");
		Customer  exist = (Customer)service.getByEmail(customer.getCompany().getId(), customer.getEmail());
		if(exist != null  && exist.getId() != customer.getId() ) {
			errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Email"))) ;
		}
		exist = (Customer)service.getByEmail(customer.getCompany().getId(), customer.getPhone());
		if(exist != null && exist.getId() != customer.getId() ) {
			errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Phone"))) ;
		}
		
	}
	
	protected void checkforErrors(ModelObject object) {
		customer = (Customer) object;
		System.out.println("Cust XML=" + customer.toXML());
		if(Utils.isNullString(customer.getFirstName())) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "First_Name"))) ;
		}
		if(Utils.isNullString(customer.getFirstName())) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Last_Name"))) ;
		}
		if (Utils.isNull(customer.getEmail())) {
			errors.add(getErrorforCode(UserErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Email"))) ;
		}
		if (Utils.isNull(customer.getPhone())) {
			errors.add(getErrorforCode(UserErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Phone"))) ;
		}
		if (!Utils.isNull(customer.getEmail()) && (!customer.getEmail().contains("@") || !customer.getEmail().contains(".")) ) {
			errors.add(getErrorforCode(UserErrorCodes.FIELD_NOT_VALID,externalize.externalize(context, "Email"))) ;
		}
	}
	
	public CustomerValidator(CRMContext context) {
		super(context);
	}
	
	public CustomerValidator(){
		
	}

	
}
