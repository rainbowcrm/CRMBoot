package com.rainbow.crm.common;

import java.util.ArrayList;
import java.util.List;

import com.techtrade.rads.framework.model.abstracts.RadsError;
 
public class CRMDBException  extends RuntimeException {
	
	public static final int ERROR_UNABLE_TO_CREATE = 900001;
	public static final int ERROR_UNABLE_TO_READ = 900002;
	public static final int ERROR_UNABLE_TO_CONNECT = 900003;
	public static final int ERROR_DIRTY_READ = 900004;
	
	private int  errorCode ;

	List<RadsError> errors; 
	
	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	
	public CRMDBException(Exception ex) {
		super(ex) ;
	}
	
	public CRMDBException(Exception ex, int fixedError) {
		super(ex) ;
		this.errorCode = fixedError;
	}
	
	public CRMDBException() {
		
	}
	
	public CRMDBException(RadsError ex) {
		errors= new ArrayList<RadsError> ();
		errors.add(ex);
	}

	public List<RadsError> getErrors() {
		return errors;
	}

	public void setErrors(List<RadsError> errors) {
		this.errors = errors;
	}
	public void addError(RadsError error) {
		if (errors == null)
			errors = new ArrayList<RadsError>();
		this.errors.add(error);
	}
	

}
