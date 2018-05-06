package com.rainbow.crm.user.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMListController;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.user.model.User;
import com.rainbow.crm.user.service.IUserService;
import com.rainbow.crm.user.validator.UserValidator;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.filter.Filter;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.utils.Utils;

public class UserListController extends CRMListController{

	@Override
	public IBusinessService getService() {
		return (IUserService) SpringObjectFactory.INSTANCE.getInstance("IUserService");
	}
	@Override
	public Object getPrimaryKeyValue(ModelObject object) {
		User user = (User) object ;
		return user.getUserId();
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
		UserValidator validator = new UserValidator(((CRMContext)getContext()));
		return validator.eligibleForEdit(objects);
	}

	@Override
	public PageResult goToEdit(List<ModelObject> objects) {
		PageResult result = new PageResult();
		CRMContext context = (CRMContext)getContext();
		if (context.getLoggedinCompany() == 1) {
			result.setNextPageKey("newuser");
		}else {
			result.setNextPageKey("Appnewuser");
		}
		return result;
	}
	
	public Map <String, String > getRoleTypesWithSelect() {
		Map<String, String> ans = new LinkedHashMap<String, String> ();
		ans.put("null", "---Select one---") ;
		ans.putAll(GeneralSQLs.getFiniteValues(CRMConstants.FV_ROLE_TYPE));
		return ans;
	}
	

}
