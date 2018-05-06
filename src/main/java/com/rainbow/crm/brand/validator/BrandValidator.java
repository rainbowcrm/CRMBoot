package com.rainbow.crm.brand.validator;

import com.rainbow.crm.brand.model.Brand;
import com.rainbow.crm.brand.service.IBrandService;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonErrorCodes;
import com.rainbow.crm.common.SpringObjectFactory;
import com.techtrade.rads.framework.model.abstracts.ModelObject;

public class BrandValidator extends CRMValidator {

	Brand  brand ;
	
	@Override
	protected void checkforCreateErrors(ModelObject object) {
		checkforErrors(object);
		IBrandService service =(IBrandService)SpringObjectFactory.INSTANCE.getInstance("IBrandService");
		Brand exist = (Brand)service.getByName(brand.getCompany().getId(), brand.getName());
		if(exist != null) {
				errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Brand_Name"))) ;
		}
	}

	@Override
	protected void checkforUpdateErrors(ModelObject object) {
		checkforErrors(object);
		IBrandService service =(IBrandService)SpringObjectFactory.INSTANCE.getInstance("IBrandService");
		Brand exist = (Brand)service.getByName(brand.getCompany().getId(), brand.getName());
		if(exist != null && exist.getId() != brand.getId()) {
				errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Brand_Name"))) ;
		}
	}
	
	protected void checkforErrors(ModelObject object) {
		brand =  (Brand) object;
		if (brand.getCompany() == null){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Company"))) ;
		}
		if (brand.getName() == null){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Brand_Name"))) ;
		}
		
	}

	public BrandValidator(CRMContext context) {
		super(context);
	}
	public BrandValidator(){
		
	}
	
}
