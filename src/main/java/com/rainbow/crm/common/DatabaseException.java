package com.rainbow.crm.common;

public class DatabaseException extends RuntimeException{

	public static final int PERSISTENCE_ERROR =1;
	public static final int READ_ERROR =2;
	public static final int CONNECTION_ERROR =3;
	public static final int DIRTY_READ_ERROR =4;
	
	int errorType ;
	public int getErrorType() {
		return errorType;
	}
	public void setErrorType(int errorType) {
		this.errorType = errorType;
	}
	public DatabaseException() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DatabaseException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	
	}
	public DatabaseException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	
	}
	public DatabaseException(String arg0) {
		super(arg0);
	
	}
	public DatabaseException(Throwable arg0) {
		super(arg0);
	
	}
	
	public DatabaseException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3,int errorType) {
		super(arg0, arg1, arg2, arg3);
		setErrorType(errorType);
	
	}
	public DatabaseException(String arg0, Throwable arg1,int errorType) {
		super(arg0, arg1);
		setErrorType(errorType);
	
	}
	public DatabaseException(String arg0,int errorType) {
		super(arg0);
		setErrorType(errorType);
	
	}
	public DatabaseException(Throwable arg0,int errorType) {
		super(arg0);
		setErrorType(errorType);
	
	}
	  
	 
	
	
}
