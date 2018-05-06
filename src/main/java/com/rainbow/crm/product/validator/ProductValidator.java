package com.rainbow.crm.product.validator;

import com.rainbow.crm.category.model.Category;
import com.rainbow.crm.category.service.ICategoryService;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonErrorCodes;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.product.model.Product;
import com.rainbow.crm.product.service.IProductService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.utils.Utils;

public class ProductValidator extends CRMValidator {
	
	Product product;
	
	@Override
	protected void checkforCreateErrors(ModelObject object) {
		checkforErrors(object);
		IProductService service =(IProductService)SpringObjectFactory.INSTANCE.getInstance("IProductService");
		Product exist = (Product)service.getByName(product.getCompany().getId(), product.getName());
		if(exist != null) {
				errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Product_Name"))) ;
		}
	}

	@Override
	protected void checkforUpdateErrors(ModelObject object) {
		checkforErrors(object);
		IProductService service =(IProductService)SpringObjectFactory.INSTANCE.getInstance("IProductService");
		Product exist = (Product)service.getByName(product.getCompany().getId(), product.getName());
		if(exist != null && exist.getId() != product.getId()) {
				errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Product_Name"))) ;
		}
	}
	
	protected void checkforErrors(ModelObject object) {
		product =  (Product) object;
		if (product.getCompany() == null){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Company"))) ;
		}
		if (product.getName() == null){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Product_Name"))) ;
		}
		if (product.getCategory() == null){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Category"))) ;
		}else {
			ICategoryService service = (ICategoryService)SpringObjectFactory.INSTANCE.getInstance("ICategoryService");
			Category category = null;
			if(product.getCategory().getId() > 0 )
			 category = (Category)service.getById(product.getCategory().getPK());
			else if  (!Utils.isNullString(product.getCategory().getName())) {
				category = (Category)service.getByName(product.getCompany().getId(), product.getCategory().getName());
			}
			if (category == null ) {
				errors.add(getErrorforCode(CommonErrorCodes.VALUE_NOT_FOUND,externalize.externalize(context, "Category"))) ;
			}else
				product.setCategory(category);
		}
	}

	public ProductValidator(CRMContext context) {
		super(context);
	}
	public ProductValidator(){
		
	}


}
