package com.rainbow.crm.company.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.CRMCRUDController;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMDBException;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.common.ajaxservices.CountryStateAjaxService;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.model.GuestCompany;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.database.LoginSQLs;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.context.RadsContext;
import com.techtrade.rads.framework.controller.abstracts.CRUDController;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult.Result;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.ui.abstracts.UIPage;
import com.techtrade.rads.framework.utils.Utils;

public class CompanyController extends CRMCRUDController{

	public IBusinessService getService() {
		ICompanyService serv = (ICompanyService) SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		return serv;
	}
	
	
	public Map <String, String > getBusinessTypes() {
		Map<String, String> ans = GeneralSQLs.getFiniteValues(CRMConstants.FV_BUSINESS_TYPE);
		return ans;
	}
	
	public Map <String, String > getIndustryTypes() {
		Map<String, String> ans = GeneralSQLs.getFiniteValues(CRMConstants.FV_INDUSTRY_TYPE);
		return ans;
	}
	
	
	
	@Override
	public IRadsContext generateContext(HttpServletRequest request,
			HttpServletResponse response,UIPage page) {
		CRMContext context = new CRMContext();
		context.setGuestLogin(true);
		return context;
	}


	public Map <String, String > getStates() {
		Map<String, String> ans = new HashMap<String, String> ();
		if(object != null  && (!Utils.isNullString(((Company)object).getCountry()))){
			CountryStateAjaxService serv = new CountryStateAjaxService();
			return serv.findStates(((Company)object).getCountry());
		}
		return ans; 
	}
	
	public Map <String, String > getCountries() {
		Map <String, String > ans = new HashMap();
		String[] locales = Locale.getISOCountries();
		for (String countryCode : locales) {
		    Locale obj = new Locale("", countryCode);
		    //if("IN".equalsIgnoreCase(obj.getCountry()) || "US".equalsIgnoreCase(obj.getCountry())) {
		    ans.put(obj.getCountry(), obj.getDisplayCountry()) ;
		    //}
		}
		return ans ;
	}


	@Override
	public PageResult submit(ModelObject object, String actionParam) {
		if("checkcode".equalsIgnoreCase(actionParam))
		{
			
			
		}  
			return super.submit(object, actionParam);
	}


	@Override
	public PageResult create() {
		if (object instanceof GuestCompany){
			String guestjson =  object.toJSON();
			try {
			 object = Company.instantiateObjectfromJSON(guestjson, Company.class.getName(), getContext());
			}catch(Exception ex) {
				ex.printStackTrace();
			}
					
		}
		return super.create();
	}
	
	
}
