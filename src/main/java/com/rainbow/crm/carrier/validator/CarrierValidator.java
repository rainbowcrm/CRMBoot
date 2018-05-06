package com.rainbow.crm.carrier.validator;

import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.item.service.ISkuService;
import com.rainbow.crm.user.validator.UserErrorCodes;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonErrorCodes;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.carrier.model.Carrier;
import com.rainbow.crm.carrier.service.ICarrierService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.utils.Utils;

public class CarrierValidator extends CRMValidator {

	Carrier carrier = null;
	
	@Override
	protected void checkforCreateErrors(ModelObject object) {
		checkforErrors(object);
		ICarrierService  service = (ICarrierService) SpringObjectFactory.INSTANCE.getInstance("ICarrierService");
		Carrier  exist = (Carrier)service.getByEmail(carrier.getCompany().getId(), carrier.getEmail());
		if(exist != null ) {
			errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Email"))) ;
		}
		exist = (Carrier)service.getByPhone(carrier.getCompany().getId(), carrier.getPhone());
		if(exist != null ) {
			errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Phone"))) ;
		}
		exist = (Carrier)service.getByCode(carrier.getCompany().getId(), carrier.getCode());
		if(exist != null ) {
			errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Code"))) ;
		}
	}

	@Override
	protected void checkforUpdateErrors(ModelObject object) {
		checkforErrors(object);
		ICarrierService  service = (ICarrierService) SpringObjectFactory.INSTANCE.getInstance("ICarrierService");
		Carrier  exist = (Carrier)service.getByEmail(carrier.getCompany().getId(), carrier.getEmail());
		if(exist != null  && exist.getId() != carrier.getId() ) {
			errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Email"))) ;
		}
		exist = (Carrier)service.getByEmail(carrier.getCompany().getId(), carrier.getPhone());
		if(exist != null && exist.getId() != carrier.getId() ) {
			errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Phone"))) ;
		}
		exist = (Carrier)service.getByCode(carrier.getCompany().getId(), carrier.getCode());
		if(exist != null && exist.getId() != carrier.getId() ) {
			errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Code"))) ;
		}
		
	}
	
	protected void checkforErrors(ModelObject object) {
		carrier = (Carrier) object;
		System.out.println("Cust XML=" + carrier.toXML());
		if (Utils.isNull(carrier.getEmail())) {
			errors.add(getErrorforCode(UserErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Email"))) ;
		}
		if (Utils.isNull(carrier.getPhone())) {
			errors.add(getErrorforCode(UserErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Phone"))) ;
		}
		if (Utils.isNull(carrier.getCode())) {
			errors.add(getErrorforCode(UserErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Code"))) ;
		}
		if (Utils.isNull(carrier.getName())) {
			errors.add(getErrorforCode(UserErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Name"))) ;
		}
		if (!Utils.isNull(carrier.getEmail()) && (!carrier.getEmail().contains("@") || !carrier.getEmail().contains(".")) ) {
			errors.add(getErrorforCode(UserErrorCodes.FIELD_NOT_VALID,externalize.externalize(context, "Email"))) ;
		}
	}
	
	public CarrierValidator(CRMContext context) {
		super(context);
	}
	
	public CarrierValidator(){
		
	}

	
}
