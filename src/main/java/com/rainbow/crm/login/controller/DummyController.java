package com.rainbow.crm.login.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonErrorCodes;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.GeneralController;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.ui.abstracts.UIPage;

public class DummyController extends  GeneralController{

	@Override
	public PageResult submit(ModelObject object) {
		PageResult pageResult = new PageResult();
		pageResult.addError(CRMValidator.getErrorforCode(CommonErrorCodes.UNAUTHORIZED_ACCESS));
		return pageResult;
	}

	@Override
	public IRadsContext generateContext(HttpServletRequest request,
			HttpServletResponse response, UIPage page) {
		return CommonUtil.generateContext(request, page);
	}

	@Override
	public IRadsContext generateContext(String authToken, UIPage page) {
		return CommonUtil.generateContext(authToken, page);
	}
	
	public String getCompanyName() {
		ICompanyService service = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company =(Company) service.getById(((CRMContext)getContext()).getLoggedinCompany());
		return company.getName();
	}
}
