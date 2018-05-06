
package com.rainbow.crm.company.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMListController;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.company.dao.CompanyDAO;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.company.validator.CompanyErrorCodes;
import com.rainbow.crm.company.validator.CompanyValidator;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.filter.model.CRMFilter;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.context.RadsContext;
import com.techtrade.rads.framework.controller.abstracts.ListController;
import com.techtrade.rads.framework.filter.Filter;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.utils.Utils;

public class CompanyListController extends CRMListController{

	@Override
	public IBusinessService getService() {
		return (ICompanyService) SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
	}
	

	@Override
	public Object getPrimaryKeyValue(ModelObject object) {
		Company company = (Company) object;
		return company.getId();
	}


	public Map <String, String > getBusinessTypes() {
		Map<String, String> ans = GeneralSQLs.getFiniteValues(CRMConstants.FV_BUSINESS_TYPE);
		return ans;
	}
	
	public Map <String, String > getIndustryTypes() {
		Map<String, String> ans = GeneralSQLs.getFiniteValues(CRMConstants.FV_INDUSTRY_TYPE);
		return ans;
	}
	
	public Map <String, String > getBusinessTypesWithSelect() {
		Map<String, String> ans = new LinkedHashMap<String, String> ();
		ans.put("null", "---Select one---") ;
		ans.putAll(GeneralSQLs.getFiniteValues(CRMConstants.FV_BUSINESS_TYPE));
		return ans;
	}
	
	public Map <String, String > getIndustryTypesWithSelect() {
		Map<String, String> ans = new LinkedHashMap<String, String> ();
		ans.put("null", "---Select one---") ;
		ans.putAll(GeneralSQLs.getFiniteValues(CRMConstants.FV_INDUSTRY_TYPE));
		return ans;
	}

	

	@Override
	public PageResult submit(List<ModelObject> objects, String submitAction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RadsError> validateforDelete(List<ModelObject> objects) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	@Override
	public List<RadsError> validateforEdit(List<ModelObject> objects) {
			CompanyValidator validator = new CompanyValidator(((CRMContext)getContext()));
			return validator.eligibleForEdit(objects);
	}

	@Override
	public PageResult goToEdit(List<ModelObject> objects) {
		PageResult result = new PageResult();
		result.setNextPageKey("compcreate");
		return result;
	}

	

	
	

}
