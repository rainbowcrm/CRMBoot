package com.rainbow.crm.company.validator;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.utils.Utils;

public class CompanyValidator extends CRMValidator{
	Company company = null ;
	
	protected void checkforErrors(ModelObject object) {
		
		company = (Company) object ;
		if  (Utils.isNullString(company.getCode())) {
			errors.add(getErrorforCode(CompanyErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Company_Code"))) ;
		}
		if  (Utils.isNullString(company.getName())) {
			errors.add(getErrorforCode(CompanyErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Company_Name"))) ;
		}
		if  (Utils.isNullString(company.getAdminName())) {
			errors.add(getErrorforCode(CompanyErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Administrator"))) ;
		}
		if (company.getActivationDate() == null  ) {
			if(!context.isGuestLogin())
				errors.add(getErrorforCode(CompanyErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Activate_on"))) ;
			else {
				company.setActivationDate(new java.util.Date());
				company.setServiceExpiryDate( new java.util.Date( new java.util.Date().getTime() + ( 45l * 24l * 3600l * 1000l )));
			}
		}
		if (company.getActivationDate() != null  && company.getServiceExpiryDate() != null && 
				company.getServiceExpiryDate().getTime() < company.getActivationDate().getTime()) {
			errors.add(getErrorforCode(CompanyErrorCodes.EXPIRY_DATE_LESSER_ACTIVIATION_DATE)) ;
		}
	}
	 
	
	
	
	@Override
	protected void checkforCreateErrors(ModelObject object) {
		checkforErrors(object);
		ICompanyService service = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company exist = service.findByCode(company.getCode());
		if (exist != null) {
			errors.add(getErrorforCode(CompanyErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Company_Code"))) ;
		}
		exist = service.findByName(company.getName());
		if (exist != null) {
			errors.add(getErrorforCode(CompanyErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Company_Name"))) ;
		}
	}




	@Override
	protected void checkforUpdateErrors(ModelObject object) {
		checkforErrors(object);
		ICompanyService service = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company exist = service.findByName(company.getName());
		if (exist != null && !exist.getCode().equals(company.getCode())) {
			errors.add(getErrorforCode(CompanyErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Company_Name"))) ;
		}

	}




	public  CompanyValidator(CRMContext context) {
		super(context);
	}
	
	public  CompanyValidator() {
	}
	

}
