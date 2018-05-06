package com.rainbow.crm.territory.validator;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonErrorCodes;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.territory.model.Territory;
import com.rainbow.crm.territory.model.TerritoryLine;
import com.rainbow.crm.territory.service.ITerritoryService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.utils.Utils;

public class TerritoryValidator extends CRMValidator {

	Territory territory ;
	 
	@Override
	protected void checkforCreateErrors(ModelObject object) {
		checkforErrors(object);
		ITerritoryService service =(ITerritoryService)SpringObjectFactory.INSTANCE.getInstance("ITerritoryService");
		Territory exist = (Territory)service.getByBusinessKey(territory, context);
		if(exist != null) {
				errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Territory"))) ;
		}
		
	}

	@Override
	protected void checkforUpdateErrors(ModelObject object) {
		checkforErrors(object);
		ITerritoryService service =(ITerritoryService)SpringObjectFactory.INSTANCE.getInstance("ITerritoryService");
		Territory exist = (Territory)service.getByBusinessKey(territory, context);
		if(exist != null && exist.getId() != territory.getId()) {
				errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Territory"))) ;
		}
		
	}
	protected void checkforErrors(ModelObject object) {
		territory = (Territory) object ;
		if (territory.getCompany() == null){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Company"))) ;
		}
			for (TerritoryLine line : territory.getTerritoryLines()) {
				if (line.getZipCode() == null ) {
					errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "ZipCode"))) ;
				}
			}
		
	}
	
	public TerritoryValidator(CRMContext context) {
		super(context);
	}
	public TerritoryValidator(){
		
	}

	
}
