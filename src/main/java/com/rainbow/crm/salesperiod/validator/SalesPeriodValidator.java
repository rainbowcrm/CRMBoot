package com.rainbow.crm.salesperiod.validator;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonErrorCodes;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.corpsalesperiod.validator.CorpSalesPeriodErrorCodes;
import com.rainbow.crm.salesperiod.model.SalesPeriod;
import com.rainbow.crm.salesperiod.model.SalesPeriodAssociate;
import com.rainbow.crm.salesperiod.model.SalesPeriodBrand;
import com.rainbow.crm.salesperiod.model.SalesPeriodCategory;
import com.rainbow.crm.salesperiod.model.SalesPeriodLine;
import com.rainbow.crm.salesperiod.model.SalesPeriodProduct;
import com.rainbow.crm.salesperiod.model.SalesPeriodTerritory;
import com.rainbow.crm.salesperiod.service.ISalesPeriodService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.utils.Utils;

public class SalesPeriodValidator extends CRMValidator {

	SalesPeriod salesPeriod ;
	 
	@Override
	protected void checkforCreateErrors(ModelObject object) {
		checkforErrors(object);
		ISalesPeriodService service =(ISalesPeriodService)SpringObjectFactory.INSTANCE.getInstance("ISalesPeriodService");
		SalesPeriod exist = (SalesPeriod)service.getByBusinessKey(salesPeriod, context);
		if(exist != null) {
				errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Doc_No"))) ;
		}
		
	}

	@Override
	protected void checkforUpdateErrors(ModelObject object) {
		checkforErrors(object);
		ISalesPeriodService service =(ISalesPeriodService)SpringObjectFactory.INSTANCE.getInstance("ISalesPeriodService");
		SalesPeriod exist = (SalesPeriod)service.getByBusinessKey(salesPeriod, context);
		if(exist != null && exist.getId() != salesPeriod.getId()) {
				errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Doc_No"))) ;
		}
		
	}
	protected void checkforErrors(ModelObject object) {
		salesPeriod = (SalesPeriod) object ;
		if (salesPeriod.getCompany() == null){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Company"))) ;
		}
		if(Utils.isNull(salesPeriod.getPeriod()) ){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Sales_Period"))) ;
		}
		if(Utils.isNull(salesPeriod.getFromDate()) ){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "From_Date"))) ;
		}
		if(Utils.isNull(salesPeriod.getToDate()) ){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "To_Date"))) ;
		}
		if (Utils.isNullSet(salesPeriod.getSalesPeriodLines()) &&  Utils.isNullSet(salesPeriod.getSalesPeriodAssociates())  &&
				Utils.isNullSet(salesPeriod.getSalesPeriodTerritories()) &&  Utils.isNullSet(salesPeriod.getSalesPeriodCategories()) && 
				Utils.isNullSet(salesPeriod.getSalesPeriodProducts()) &&  Utils.isNullSet(salesPeriod.getSalesPeriodBrands()) ) {
			errors.add(getErrorforCode(CorpSalesPeriodErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Entries"))) ;
		}else {
			if (!Utils.isNullSet(salesPeriod.getSalesPeriodLines())) {
			for (SalesPeriodLine line : salesPeriod.getSalesPeriodLines()) {
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
		
		if(!Utils.isNullSet(salesPeriod.getSalesPeriodAssociates())){
			for (SalesPeriodAssociate line: salesPeriod.getSalesPeriodAssociates()) {
				if (line.getUser() == null ) {
					errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Associate"))) ;
				}else if (line.getUser().isDeleted() ) {
					errors.add(getErrorforCode(CommonErrorCodes.OBJECT_DELETED,externalize.externalize(context, "Associate") + line.getUser().getUserId())) ;
				}else if (line.getLineTotal() <=0 ) {
					errors.add(getErrorforCode(CommonErrorCodes.SHOULD_BE_GREATER_THAN,externalize.externalize(context, "Total") + line.getUser().getUserId(),"0") ) ;
				}
				
			}
			
		}
		
		if(!Utils.isNullSet(salesPeriod.getSalesPeriodTerritories())){
			for (SalesPeriodTerritory line: salesPeriod.getSalesPeriodTerritories()) {
				if (line.getTerritory() == null ) {
					errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Territory"))) ;
				}else if (line.getTerritory().isDeleted() ) {
					errors.add(getErrorforCode(CommonErrorCodes.OBJECT_DELETED,externalize.externalize(context, "Territory") + line.getTerritory().getTerritory())) ;
				}else if (line.getLineTotal() <=0 ) {
					errors.add(getErrorforCode(CommonErrorCodes.SHOULD_BE_GREATER_THAN,externalize.externalize(context, "Total") + line.getTerritory().getTerritory(),"0") ) ;
				}
			}
		}
		
		if(!Utils.isNullSet(salesPeriod.getSalesPeriodBrands())){
			for (SalesPeriodBrand line: salesPeriod.getSalesPeriodBrands()) {
				if (line.getBrand() == null ) {
					errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Brand"))) ;
				}else if (line.getBrand().isDeleted() ) {
					errors.add(getErrorforCode(CommonErrorCodes.OBJECT_DELETED,externalize.externalize(context, "Brand") + line.getBrand().getName())) ;
				}else if (line.getLineTotal() <=0 ) {
					errors.add(getErrorforCode(CommonErrorCodes.SHOULD_BE_GREATER_THAN,externalize.externalize(context, "Total") + line.getBrand().getName(),"0") ) ;
				}
			}
		}
		
		if(!Utils.isNullSet(salesPeriod.getSalesPeriodCategories())){
			for (SalesPeriodCategory line: salesPeriod.getSalesPeriodCategories()) {
				if (line.getCategory() == null ) {
					errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Category"))) ;
				}else if (line.getCategory().isDeleted() ) {
					errors.add(getErrorforCode(CommonErrorCodes.OBJECT_DELETED,externalize.externalize(context, "Category") + line.getCategory().getName())) ;
				}else if (line.getLineTotal() <=0 ) {
					errors.add(getErrorforCode(CommonErrorCodes.SHOULD_BE_GREATER_THAN,externalize.externalize(context, "Total") + line.getCategory().getName(),"0") ) ;
				}
			}
		}
		
		if(!Utils.isNullSet(salesPeriod.getSalesPeriodProducts())){
			for (SalesPeriodProduct line: salesPeriod.getSalesPeriodProducts()) {
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
	public SalesPeriodValidator(CRMContext context) {
		super(context);
	}
	public SalesPeriodValidator(){
		
	}

	
}
