package com.rainbow.crm.corpsalesperiod.validator;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonErrorCodes;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.corpsalesperiod.model.CorpSalesPeriod;
import com.rainbow.crm.corpsalesperiod.model.CorpSalesPeriodBrand;
import com.rainbow.crm.corpsalesperiod.model.CorpSalesPeriodCategory;
import com.rainbow.crm.corpsalesperiod.model.CorpSalesPeriodDivision;
import com.rainbow.crm.corpsalesperiod.model.CorpSalesPeriodLine;
import com.rainbow.crm.corpsalesperiod.model.CorpSalesPeriodProduct;
import com.rainbow.crm.corpsalesperiod.service.ICorpSalesPeriodService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.utils.Utils;

public class CorpSalesPeriodValidator extends CRMValidator {

	CorpSalesPeriod corpSalesPeriod ;
	 
	@Override
	protected void checkforCreateErrors(ModelObject object) {
		checkforErrors(object);
		ICorpSalesPeriodService service =(ICorpSalesPeriodService)SpringObjectFactory.INSTANCE.getInstance("ICorpSalesPeriodService");
		CorpSalesPeriod exist = (CorpSalesPeriod)service.getByBusinessKey(corpSalesPeriod, context);
		if(exist != null) {
				errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Doc_No"))) ;
		}
		
	}

	@Override
	protected void checkforUpdateErrors(ModelObject object) {
		checkforErrors(object);
		ICorpSalesPeriodService service =(ICorpSalesPeriodService)SpringObjectFactory.INSTANCE.getInstance("ICorpSalesPeriodService");
		CorpSalesPeriod exist = (CorpSalesPeriod)service.getByBusinessKey(corpSalesPeriod, context);
		if(exist != null && exist.getId() != corpSalesPeriod.getId()) {
				errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Doc_No"))) ;
		}
		
	}
	protected void checkforErrors(ModelObject object) {
		corpSalesPeriod = (CorpSalesPeriod) object ;
		if (corpSalesPeriod.getCompany() == null){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Company"))) ;
		}
		if(Utils.isNull(corpSalesPeriod.getPeriod()) ){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Sales_Period"))) ;
		}
		if(Utils.isNull(corpSalesPeriod.getFromDate()) ){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "From_Date"))) ;
		}
		if(Utils.isNull(corpSalesPeriod.getToDate()) ){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "To_Date"))) ;
		}
		if (Utils.isNullSet(corpSalesPeriod.getCorpSalesPeriodLines()) &&   Utils.isNullSet(corpSalesPeriod.getCorpSalesPeriodCategories()) && 
				Utils.isNullSet(corpSalesPeriod.getCorpSalesPeriodProducts()) && Utils.isNullSet(corpSalesPeriod.getCorpSalesPeriodDivisions()) &&
				Utils.isNullSet(corpSalesPeriod.getCorpSalesPeriodBrands()) ) {
			errors.add(getErrorforCode(CorpSalesPeriodErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Entries"))) ;
		}else {
			if (!Utils.isNullSet(corpSalesPeriod.getCorpSalesPeriodLines())) {
			for (CorpSalesPeriodLine line : corpSalesPeriod.getCorpSalesPeriodLines()) {
				if (line.getItem() == null ) {
					errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Item"))) ;
				}else if (line.getItem().isDeleted() ) {
					errors.add(getErrorforCode(CommonErrorCodes.OBJECT_DELETED,externalize.externalize(context, "Item") + line.getItem().getCode())) ;
				}else if (line.getQty() <=0 ) {
					errors.add(getErrorforCode(CommonErrorCodes.SHOULD_BE_GREATER_THAN,externalize.externalize(context, "Target_Qty") + line.getItem().getCode(),"0") ) ;
				}
			}
			}
		}

		if(!Utils.isNullSet(corpSalesPeriod.getCorpSalesPeriodBrands())){
			for (CorpSalesPeriodBrand line: corpSalesPeriod.getCorpSalesPeriodBrands()) {
				if (line.getBrand() == null ) {
					errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Brand"))) ;
				}else if (line.getBrand().isDeleted() ) {
					errors.add(getErrorforCode(CommonErrorCodes.OBJECT_DELETED,externalize.externalize(context, "Brand") + line.getBrand().getName())) ;
				}else if (line.getLineTotal() <=0 ) {
					errors.add(getErrorforCode(CommonErrorCodes.SHOULD_BE_GREATER_THAN,externalize.externalize(context, "Total") + line.getBrand().getName(),"0") ) ;
				}
			}
		}
		
		if(!Utils.isNullSet(corpSalesPeriod.getCorpSalesPeriodDivisions())){
			for (CorpSalesPeriodDivision line: corpSalesPeriod.getCorpSalesPeriodDivisions()) {
				if (line.getDivision() == null ) {
					errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Division"))) ;
				}else if (line.getDivision().isDeleted() ) {
					errors.add(getErrorforCode(CommonErrorCodes.OBJECT_DELETED,externalize.externalize(context, "Division") + line.getDivision().getName())) ;
				}else if (line.getLineTotal() <=0 ) {
					errors.add(getErrorforCode(CommonErrorCodes.SHOULD_BE_GREATER_THAN,externalize.externalize(context, "Total") + line.getDivision().getName(),"0") ) ;
				}
			}
		}
		
		if(!Utils.isNullSet(corpSalesPeriod.getCorpSalesPeriodCategories())){
			for (CorpSalesPeriodCategory line: corpSalesPeriod.getCorpSalesPeriodCategories()) {
				if (line.getCategory() == null ) {
					errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Category"))) ;
				}else if (line.getCategory().isDeleted() ) {
					errors.add(getErrorforCode(CommonErrorCodes.OBJECT_DELETED,externalize.externalize(context, "Category") + line.getCategory().getName())) ;
				}else if (line.getLineTotal() <=0 ) {
					errors.add(getErrorforCode(CommonErrorCodes.SHOULD_BE_GREATER_THAN,externalize.externalize(context, "Total") + line.getCategory().getName(),"0") ) ;
				}
			}
		}
		
		if(!Utils.isNullSet(corpSalesPeriod.getCorpSalesPeriodProducts())){
			for (CorpSalesPeriodProduct line: corpSalesPeriod.getCorpSalesPeriodProducts()) {
				if (line.getProduct() == null ) {
					errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Product"))) ;
				}else if (line.getProduct().isDeleted() ) {
					errors.add(getErrorforCode(CommonErrorCodes.OBJECT_DELETED,externalize.externalize(context, "Product") + line.getProduct().getName())) ;
				}else if (line.getLineTotal() <=0 ) {
					errors.add(getErrorforCode(CommonErrorCodes.SHOULD_BE_GREATER_THAN,externalize.externalize(context, "Total") + line.getProduct().getName(),"0") ) ;
				}
			}
		}
		
	}
	public CorpSalesPeriodValidator(CRMContext context) {
		super(context);
	}
	public CorpSalesPeriodValidator(){
		
	}

	
}
