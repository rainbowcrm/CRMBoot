package com.rainbow.crm.division.validator;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.company.validator.CompanyErrorCodes;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.division.service.IDivisionService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.utils.Utils;

public class DivisionValidator  extends CRMValidator{

	Division division = null;
	@Override
	protected void checkforCreateErrors(ModelObject object) {
		checkforErrors(object);
		IDivisionService divisionService = (IDivisionService)SpringObjectFactory.INSTANCE.getInstance("IDivisionService");
		Division  exist = (Division)divisionService.getByCode(division.getCompany().getId(), division.getCode());
		if(exist != null ) {
			errors.add(getErrorforCode(DivisionErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Division_Code"))) ;
		}
		exist = (Division)divisionService.getByName(division.getCompany().getId(), division.getName());
		if(exist != null ) {
			errors.add(getErrorforCode(DivisionErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Division_Name"))) ;
		}
	}

	@Override
	protected void checkforUpdateErrors(ModelObject object) {
		checkforErrors(object);
		IDivisionService divisionService = (IDivisionService)SpringObjectFactory.INSTANCE.getInstance("IDivisionService");
		Division  exist = (Division)divisionService.getByCode(division.getCompany().getId(), division.getCode());
		if(exist != null && exist.getId() != division.getId()) {
			errors.add(getErrorforCode(DivisionErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Division_Code"))) ;
		}
		exist = (Division)divisionService.getByName(division.getCompany().getId(), division.getName());
		if(exist != null && exist.getId() != division.getId()) {
			errors.add(getErrorforCode(DivisionErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Division_Name"))) ;
		}
	}
	
	protected void checkforErrors(ModelObject object) {
		division=(Division)object ;
		if (division.getCompany() == null){
			errors.add(getErrorforCode(DivisionErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Company"))) ;
		}
		if (Utils.isNullString(division.getCode())){
			errors.add(getErrorforCode(DivisionErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Division_Code"))) ;
		}
		if (Utils.isNullString(division.getName())){
			errors.add(getErrorforCode(DivisionErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Division_Name"))) ;
		}
		if (Utils.isNullString(division.getDivisionType())){
			errors.add(getErrorforCode(DivisionErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Type"))) ;
		}
	}
	public DivisionValidator(CRMContext context) {
		super(context);
	}
	public DivisionValidator(){
		
	}
	

}
