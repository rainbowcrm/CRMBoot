package com.rainbow.crm.salesportfolio.validator;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonErrorCodes;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.salesportfolio.model.SalesPortfolio;
import com.rainbow.crm.salesportfolio.model.SalesPortfolioLine;
import com.rainbow.crm.salesportfolio.service.ISalesPortfolioService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.utils.Utils;

public class SalesPortfolioValidator extends CRMValidator {

	SalesPortfolio salesPortfolio ;
	 
	@Override
	protected void checkforCreateErrors(ModelObject object) {
		checkforErrors(object);
		ISalesPortfolioService service =(ISalesPortfolioService)SpringObjectFactory.INSTANCE.getInstance("ISalesPortfolioService");
		SalesPortfolio exist = (SalesPortfolio)service.getByBusinessKey(salesPortfolio, context);
		if(exist != null) {
				errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "User"))) ;
		}
		
	}

	@Override
	protected void checkforUpdateErrors(ModelObject object) {
		checkforErrors(object);
		ISalesPortfolioService service =(ISalesPortfolioService)SpringObjectFactory.INSTANCE.getInstance("ISalesPortfolioService");
		SalesPortfolio exist = (SalesPortfolio)service.getByBusinessKey(salesPortfolio, context);
		if(exist != null && exist.getId() != salesPortfolio.getId()) {
				errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "User"))) ;
		}
		
	}
	protected void checkforErrors(ModelObject object) {
		salesPortfolio = (SalesPortfolio) object ;
		if (salesPortfolio.getCompany() == null){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Company"))) ;
		}
		
		if(Utils.isNull(salesPortfolio.getStartDate()) ){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Start_Date"))) ;
		}
		
		if (Utils.isNullSet(salesPortfolio.getSalesPortfolioLines())) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Portfolio_Items"))) ;
		}else {
			for (SalesPortfolioLine line : salesPortfolio.getSalesPortfolioLines()) {
				if (line.getPortfolioKey() == null ) {
					errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Portfolio_key"))) ;
				}
				if (line.getPortfolioType()== null ) {
					errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Portfolio_Type"))) ;
				}
			}
		}
	}
	public SalesPortfolioValidator(CRMContext context) {
		super(context);
	}
	public SalesPortfolioValidator(){
		
	}

	
}
