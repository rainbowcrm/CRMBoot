package com.rainbow.crm.common;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.user.model.User;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.ui.abstracts.UIPage;

public class CRMContext implements IRadsContext,Serializable{
	String user;
	boolean authenticated ;
	boolean authorized =true;
	String authenticationToken;
	int loggedinCompany;
	Company company ;
	Timestamp logginTime;
	Timestamp logoffTime;
	String loggedinCompanyCode;
	Locale locale = Locale.US;
	User loggedInUser; 
	
	boolean fetchDeletedToo;
	boolean backgroundProcess; 
	boolean guestLogin;
	boolean reFetchAfterWrite; 
	
	boolean mobileLogin;
    Map<String, String> properties;
    
    String pageAccessCode; 
	
	@Override
	public String getUser() {
		return user;
	}

	@Override
	public void setUser(String user) {
		this.user = user;
	}

	@Override
	public Map getProperties() {
		return properties;
	}

	@Override
	public void setProperties(Map properties) {
	 this.properties = properties;
	}

	@Override
	public void addProperty(String key, String value) {
	  if(properties == null)
		  properties =  new HashMap<String,String>();
	  properties.put(key, value);	  
	}
	
	public String getProperty(String key) 
	{
		if (properties == null)
			 return null;
		else
			 return properties.get(key);
	}
	
	@Override
	public boolean isAuthenticated() {
		return authenticated;
	}

	@Override
	public void setAuthenticated(boolean isAuth) {
		authenticated = isAuth;
	}

	public String getAuthenticationToken() {
		return authenticationToken;
	}

	public void setAuthenticationToken(String authenticationToken) {
		this.authenticationToken = authenticationToken;
	}

	public int getLoggedinCompany() {
		return loggedinCompany;
	}

	public void setLoggedinCompany(int loggedinCompany) {
		this.loggedinCompany = loggedinCompany;
	}

	public Timestamp getLogginTime() {
		return logginTime;
	}

	public void setLogginTime(Timestamp logginTime) {
		this.logginTime = logginTime;
	}

	public Timestamp getLogoffTime() {
		return logoffTime;
	}

	public void setLogoffTime(Timestamp logoffTime) {
		this.logoffTime = logoffTime;
	}

	public String getLoggedinCompanyCode() {
		return loggedinCompanyCode;
	}

	public void setLoggedinCompanyCode(String loggedinCompanyCode) {
		this.loggedinCompanyCode = loggedinCompanyCode;
	}

	@Override
	public Locale getLocale() {
		return locale;
	}

	@Override
	public void setLocale(Locale locale) {
		this.locale=locale;
		
	}

	public User getLoggedInUser() {
		return loggedInUser;
	}

	public void setLoggedInUser(User loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

	public boolean isFetchDeletedToo() {
		return fetchDeletedToo;
	}

	public void setFetchDeletedToo(boolean fetchDeletedToo) {
		this.fetchDeletedToo = fetchDeletedToo;
	}

	public boolean isBackgroundProcess() {
		return backgroundProcess;
	}

	public void setBackgroundProcess(boolean backgroundProcess) {
		this.backgroundProcess = backgroundProcess;
	}

	public boolean isGuestLogin() {
		return guestLogin;
	}

	public void setGuestLogin(boolean guestLogin) {
		this.guestLogin = guestLogin;
	}

	@Override
	public boolean isAuthorized() {
		return authorized;
	}

	public void setAuthorized(boolean authorized) {
		this.authorized = authorized;
	}

	public boolean isMobileLogin() {
		return mobileLogin;
	}

	public void setMobileLogin(boolean mobileLogin) {
		this.mobileLogin = mobileLogin;
	}

	public String getPageAccessCode() {
		return pageAccessCode;
	}

	public void setPageAccessCode(String pageAccessCode) {
		this.pageAccessCode = pageAccessCode;
	}

	public boolean isReFetchAfterWrite() {
		return reFetchAfterWrite;
	}

	public void setReFetchAfterWrite(boolean reFetchAfterWrite) {
		this.reFetchAfterWrite = reFetchAfterWrite;
	}


	public Company getCompany() {
		if (company == null )
		{
			ICompanyService companyService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
			company = (Company)companyService.getById(loggedinCompany);
		}
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
}
