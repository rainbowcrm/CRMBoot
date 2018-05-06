package com.rainbow.crm.category.validator;

import com.rainbow.crm.category.model.Category;
import com.rainbow.crm.category.service.ICategoryService;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonErrorCodes;
import com.rainbow.crm.common.SpringObjectFactory;
import com.techtrade.rads.framework.model.abstracts.ModelObject;

public class CategoryValidator extends CRMValidator {

	Category  category ;
	
	@Override
	protected void checkforCreateErrors(ModelObject object) {
		checkforErrors(object);
		ICategoryService service =(ICategoryService)SpringObjectFactory.INSTANCE.getInstance("ICategoryService");
		Category exist = (Category)service.getByName(category.getCompany().getId(), category.getName());
		if(exist != null) {
				errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Category_Name"))) ;
		}
	}

	@Override
	protected void checkforUpdateErrors(ModelObject object) {
		checkforErrors(object);
		ICategoryService service =(ICategoryService)SpringObjectFactory.INSTANCE.getInstance("ICategoryService");
		Category exist = (Category)service.getByName(category.getCompany().getId(), category.getName());
		if(exist != null && exist.getId() != category.getId()) {
				errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Category_Name"))) ;
		}
	}
	
	protected void checkforErrors(ModelObject object) {
		category =  (Category) object;
		if (category.getCompany() == null){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Company"))) ;
		}
		if (category.getName() == null){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Category_Name"))) ;
		}
		if (category.getPrimaryUOM() == null){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "UOM_Type"))) ;
		}
	}

	public CategoryValidator(CRMContext context) {
		super(context);
	}
	public CategoryValidator(){
		
	}
	
}
