package com.rainbow.crm.vendor.validator;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonErrorCodes;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.item.service.ISkuService;
import com.rainbow.crm.user.validator.UserErrorCodes;
import com.rainbow.crm.vendor.model.Vendor;
import com.rainbow.crm.vendor.service.IVendorService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.utils.Utils;

public class VendorValidator extends CRMValidator {

	Vendor vendor = null;
	
	@Override
	protected void checkforCreateErrors(ModelObject object) {
		checkforErrors(object);
		IVendorService  service = (IVendorService) SpringObjectFactory.INSTANCE.getInstance("IVendorService");
		Vendor  exist = (Vendor)service.getByCode(vendor.getCompany().getId(), vendor.getCode());
		if(exist != null ) {
			errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Vendor_Code"))) ;
		}
		exist = (Vendor)service.getByName(vendor.getCompany().getId(), vendor.getName());
		if(exist != null ) {
			errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Vendor_Name"))) ;
		}
	}

	@Override
	protected void checkforUpdateErrors(ModelObject object) {
		checkforErrors(object);
		IVendorService  service = (IVendorService) SpringObjectFactory.INSTANCE.getInstance("IVendorService");
		Vendor  exist = (Vendor)service.getByCode(vendor.getCompany().getId(), vendor.getCode());
		if(exist != null  && exist.getId() != vendor.getId() ) {
			errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Vendor_Code"))) ;
		}
		exist = (Vendor)service.getByName(vendor.getCompany().getId(), vendor.getName());
		if(exist != null && exist.getId() != vendor.getId() ) {
			errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Vendor_Name"))) ;
		}
		
	}
	
	protected void checkforErrors(ModelObject object) {
		vendor = (Vendor) object;
		if(vendor.getCode() == null) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Vendor_Code"))) ;
		}
		if(vendor.getName() == null) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Vendor_Name"))) ;
		}
		if (Utils.isNull(vendor.getEmail())) {
			errors.add(getErrorforCode(UserErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Email"))) ;
		}
		if (Utils.isNull(vendor.getPhone())) {
			errors.add(getErrorforCode(UserErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Phone"))) ;
		}
		if (!Utils.isNull(vendor.getEmail()) && (!vendor.getEmail().contains("@") || !vendor.getEmail().contains(".")) ) {
			errors.add(getErrorforCode(UserErrorCodes.FIELD_NOT_VALID,externalize.externalize(context, "Email"))) ;
		}
	}
	
	public VendorValidator(CRMContext context) {
		super(context);
	}
	
	public VendorValidator(){
		
	}

	
}
