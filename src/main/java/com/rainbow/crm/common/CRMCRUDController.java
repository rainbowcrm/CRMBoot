package com.rainbow.crm.common;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.division.service.IDivisionService;
import com.rainbow.crm.user.model.User;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.CRUDController;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult.Result;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.ui.abstracts.UIPage;
import com.techtrade.rads.framework.utils.Utils;

public  abstract class CRMCRUDController  extends CRUDController{

	public abstract IBusinessService getService() ;
	@Override
	public List<RadsError> validateforCreate() {
		return getService().validateforCreate((CRMModelObject)object,(CRMContext)getContext());
		
	}
	@Override
	public void init(HttpServletRequest request) {
		Object obj  = request.getParameter("id") ;
		if (obj != null && Utils.isPositiveInt(String.valueOf(obj)))  {
			int id = Integer.parseInt(String.valueOf(obj));
			((CRMBusinessModelObject)object).setId(id);
		}
	}

	

	@Override
	public ModelObject populateFullObjectfromPK(ModelObject object) {
		
		return (ModelObject)getService().getById(object.getPK());
	}
	@Override
	public List<RadsError> validateforUpdate() {
		return getService().validateforUpdate((CRMModelObject)object,(CRMContext)getContext());
	}

	@Override
	public List<RadsError> validateforDelete() {
		return null;
	}

	@Override
	public PageResult create() {
		try {
			 PageResult  result = new PageResult(getService().create((CRMModelObject)object, (CRMContext)getContext()));
			 result.setObject(object);
			 return result;
		} catch(CRMDBException ex) {
			return new PageResult(Result.FAILURE,ex.getErrors(),object);
		}
	}

	@Override
	public PageResult delete() {
		return null;
		
	}

	@Override
	public void read() {
		ModelObject thisObject = getService().getByBusinessKey((CRMModelObject)object, (CRMContext)getContext());
		setObject(thisObject);
	}

	@Override
	public PageResult update() {
		try {
			PageResult  result = new PageResult(getService().update((CRMModelObject)object, (CRMContext)getContext()));
			 result.setObject(object);
			 return result;
		} catch(CRMDBException ex) {
			return new PageResult(Result.FAILURE,ex.getErrors(),object);
		}
		
	}

	public Map <String, String > getAllDivisions() {
		return CommonUtil.getAllDivisions((CRMContext)getContext());
	}

	
	@Override
	public IRadsContext generateContext(HttpServletRequest request,
			HttpServletResponse response, UIPage page) {
		return CommonUtil.generateContext(request,page);
	}
	
	@Override
	public IRadsContext generateContext(String authToken, UIPage page) {
		return CommonUtil.generateContext(authToken,page);
	}
	
	
	public String getCompanyName() {
		ICompanyService service = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company =(Company) service.getById(((CRMContext)getContext()).getLoggedinCompany());
		return company.getName();
	}
	
	public Map <String, String > getItemClassTypes() {
		Map<String, String> ans = GeneralSQLs.getFiniteValues(CRMConstants.FV_ITEMCLASS_TYPE);
		return ans;
	}



}
