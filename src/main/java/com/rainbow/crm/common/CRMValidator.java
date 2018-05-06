package com.rainbow.crm.common;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import com.rainbow.crm.company.validator.CompanyErrorCodes;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.utils.Utils;

public  abstract class CRMValidator {
	protected List<RadsError> errors = new ArrayList<RadsError>();
	public static ResourceBundle  resourceBundle = null;
	public CRMContext context ;
	public Externalize externalize  ;
	
	public List< RadsError> validateforCreate (ModelObject object) {
		checkforCreateErrors(object);
		return errors;
	}
	
	protected abstract void checkforCreateErrors(ModelObject object) ;
	
	public List< RadsError> validateforUpdate (ModelObject object) {
		checkforUpdateErrors(object);
		return errors;
	}
	
	public List< RadsError> validateforDelete (ModelObject object) {
		checkforDeleteEligibility(object);
		return errors;
	}
	
	protected abstract void checkforUpdateErrors(ModelObject object) ;
	
	protected  void checkforDeleteEligibility(ModelObject object) 
	{
		
	}

	public static  RadsError getErrorforCode(Locale locale ,int errorCode,String ... params) {
		if (resourceBundle == null) {
			resourceBundle = ResourceBundle.getBundle("com.rainbow.crm.common.ErrorMessages",locale);
		}
		String property = (String)resourceBundle.getObject(String.valueOf(errorCode));
		String converted = MessageFormat.format(property, params);
		RadsError error = new RadsError(String.valueOf(errorCode),converted);
		return error;
	}
	
	public static  RadsError getErrorforCode(int errorCode,String ... params) {
		if (resourceBundle == null) {
			resourceBundle = ResourceBundle.getBundle("com.rainbow.crm.common.ErrorMessages",Locale.US);
		}
		String property = (String)resourceBundle.getObject(String.valueOf(errorCode));
		String converted = MessageFormat.format(property, params);
		RadsError error = new RadsError(String.valueOf(errorCode),converted);
		return error;
	}
	
	public  CRMValidator(CRMContext context) {
		resourceBundle = ResourceBundle.getBundle("com.rainbow.crm.common.ErrorMessages",context.getLocale());
		this.context=context;
		externalize = new Externalize();
	}

	public CRMValidator() {
		
	}

	public List<RadsError> eligibleForEdit(List<ModelObject> objects) {
		List<RadsError> errors = new ArrayList<RadsError>();
		if (Utils.isNullList(objects) || objects.size() >1 ) {
			errors.add(getErrorforCode(CommonErrorCodes.ONE_ROW_FOR_EDIT)) ;
		}
		return errors;
	}
	
	public List<RadsError> eligibleForPrint(List<ModelObject> objects) {
		List<RadsError> errors = new ArrayList<RadsError>();
		if (Utils.isNullList(objects) || objects.size() >1 ) {
			errors.add(getErrorforCode(CommonErrorCodes.ONE_ROW_FOR_EDIT)) ;
		}
		return errors;
	}
}
