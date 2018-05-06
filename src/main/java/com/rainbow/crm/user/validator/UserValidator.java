package com.rainbow.crm.user.validator;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.company.validator.CompanyErrorCodes;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.division.service.IDivisionService;
import com.rainbow.crm.user.model.User;
import com.rainbow.crm.user.service.IUserService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.utils.Utils;

public class UserValidator  extends CRMValidator{
	User user = null;

	protected void checkforErrors(ModelObject object) {
		user = (User) object;
		int loggedInCompany = context.getLoggedinCompany() ; 
		if (loggedInCompany ==1 && ( user.getCompany() == null || Utils.isNull(user.getCompany().getName()))) {
			errors.add(getErrorforCode(CompanyErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Company"))) ;
		}
		if (user.getDivision()!= null &&  !Utils.isNull(user.getDivision().getName())) {
			IDivisionService service = (IDivisionService)SpringObjectFactory.INSTANCE.getInstance("IDivisionService");
			Division division = service.getByName(context.getLoggedinCompany(), user.getDivision().getName()) ;
			if (division == null ) {
				errors.add(getErrorforCode(UserErrorCodes.VALUE_NOT_FOUND,externalize.externalize(context, "Division"))) ;
			}else
				user.setDivision(division);
		}else {
			user.setDivision(null);
		}
		if (Utils.isNull(user.getFirstName())) {
			errors.add(getErrorforCode(UserErrorCodes.FIELD_EMPTY,externalize.externalize(context, "First_Name"))) ;
		}
		if (Utils.isNull(user.getLastName())) {
			errors.add(getErrorforCode(UserErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Last_Name"))) ;
		}
		if (Utils.isNull(user.getPassword())) {
			errors.add(getErrorforCode(UserErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Password"))) ;
		}
		if (Utils.isNull(user.getEmail())) {
			errors.add(getErrorforCode(UserErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Email"))) ;
		}
		if (Utils.isNull(user.getPhone())) {
			errors.add(getErrorforCode(UserErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Phone"))) ;
		}
		if (!Utils.isNull(user.getEmail()) && (!user.getEmail().contains("@") || !user.getEmail().contains(".")) ) {
			errors.add(getErrorforCode(UserErrorCodes.FIELD_NOT_VALID,externalize.externalize(context, "Email"))) ;
		}
		
	}
	
	@Override
	protected void checkforCreateErrors(ModelObject object) {
		checkforErrors(object);
		if (Utils.isNull(user.getPrefix())) {
			errors.add(getErrorforCode(UserErrorCodes.FIELD_EMPTY,externalize.externalize(context, "User"))) ;
		}
		ICompanyService service = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		if (context.getLoggedinCompany() == 1 ) {
			if (user.getCompany() != null && !Utils.isNull(user.getCompany().getName())) {
				Company company = service.findByName(user.getCompany().getName());
				if (company == null ) {
					errors.add(getErrorforCode(UserErrorCodes.FIELD_NOT_VALID,externalize.externalize(context, "Company"))) ;
				}else { 
					user.setCompany(company);
					user.setSuffix(company.getCode());
					user.setUserId(user.getPrefix() + "@" + company.getCode());
				}
			}
		}else {
			Company company = (Company)service.getById(new Integer(context.getLoggedinCompany()));
			user.setCompany(company);
			user.setSuffix(company.getCode());
			user.setUserId(user.getPrefix() + "@" + company.getCode());
		}
		IUserService userService = (IUserService)SpringObjectFactory.INSTANCE.getInstance("IUserService");
		User  exist = (User)userService.getById(user.getUserId()) ;
		if(exist != null) {
			errors.add(getErrorforCode(UserErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "User_Id"))) ;
		}
		exist = (User)userService.getByEmail(user.getEmail()) ;
		if(exist != null) {
			errors.add(getErrorforCode(UserErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Email"))) ;
		}
		exist = (User)userService.getByPhone(user.getPhone()) ;
		if(exist != null) {
			errors.add(getErrorforCode(UserErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Phone"))) ;
		}
	}

	@Override
	protected void checkforUpdateErrors(ModelObject object) {
		checkforErrors(object);
		IUserService userService = (IUserService)SpringObjectFactory.INSTANCE.getInstance("IUserService");
		User exist = (User)userService.getByEmail(user.getEmail()) ;
		if(exist != null && !exist.getUserId().equals(user.getUserId())) {
			errors.add(getErrorforCode(UserErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Email"))) ;
		}
		exist = (User)userService.getByPhone(user.getPhone()) ;
		if(exist != null  && !exist.getUserId().equals(user.getUserId())) {
			errors.add(getErrorforCode(UserErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Phone"))) ;
		}
	}

	public UserValidator() {
		super();
	}

	public UserValidator(CRMContext context) {
		super(context);
	}


	
	

}
