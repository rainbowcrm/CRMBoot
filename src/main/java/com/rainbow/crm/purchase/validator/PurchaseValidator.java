package com.rainbow.crm.purchase.validator;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonErrorCodes;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.purchase.model.Purchase;
import com.rainbow.crm.purchase.model.PurchaseLine;
import com.rainbow.crm.purchase.service.IPurchaseService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.utils.Utils;

public class PurchaseValidator extends CRMValidator {

	Purchase purchase ;
	 
	@Override
	protected void checkforCreateErrors(ModelObject object) {
		checkforErrors(object);
		IPurchaseService service =(IPurchaseService)SpringObjectFactory.INSTANCE.getInstance("IPurchaseService");
		Purchase exist = (Purchase)service.getByBusinessKey(purchase, context);
		if(exist != null) {
				errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Doc_No"))) ;
		}
		
	}

	@Override
	protected void checkforUpdateErrors(ModelObject object) {
		checkforErrors(object);
		IPurchaseService service =(IPurchaseService)SpringObjectFactory.INSTANCE.getInstance("IPurchaseService");
		Purchase exist = (Purchase)service.getByBusinessKey(purchase, context);
		if(exist != null && exist.getId() != purchase.getId()) {
				errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Doc_No"))) ;
		}
		
	}
	protected void checkforErrors(ModelObject object) {
		purchase = (Purchase) object ;
		if (purchase.getCompany() == null){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Company"))) ;
		}
		if(Utils.isNull(purchase.getBillNumber()) ){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Bill_Number"))) ;
		}
		if(Utils.isNull(purchase.getPurchaseDate()) ){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Date"))) ;
		}
		if(purchase.getVendor() == null  ){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Vendor"))) ;
		}
		if (Utils.isNullSet(purchase.getPurchaseLines())) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Line_Items"))) ;
		}else {
			for (PurchaseLine line : purchase.getPurchaseLines()) {
				if (line.getSku() == null ) {
					errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Item"))) ;
				}else if (line.getSku().isDeleted() ) {
					errors.add(getErrorforCode(CommonErrorCodes.OBJECT_DELETED,externalize.externalize(context, "Item") + line.getSku().getCode())) ;
				}else if (line.getQty() <=0 ) {
					errors.add(getErrorforCode(CommonErrorCodes.SHOULD_BE_GREATER_THAN,externalize.externalize(context, "Qty") + line.getSku().getCode(),"0") ) ;
				}else if (line.getUnitPrice() <=0 ) {
					errors.add(getErrorforCode(CommonErrorCodes.SHOULD_BE_GREATER_THAN,externalize.externalize(context, "Unit_Price") + line.getSku().getCode(),"0") ) ;
				}
			}
		}
	}
	
	public PurchaseValidator(CRMContext context) {
		super(context);
	}
	public PurchaseValidator(){
		
	}

	
}
