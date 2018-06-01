package com.rainbow.crm.common;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.division.service.IDivisionService;
import com.rainbow.crm.user.model.User;
import com.rainbow.util.ServiceLibrary;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.GeneralController;
import com.techtrade.rads.framework.ui.abstracts.UIPage;
import com.techtrade.rads.framework.utils.Utils;

public abstract class CRMGeneralController  extends GeneralController{
	
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
	
	public Map <String, String > getAllDivisions() {
		CRMContext ctx = ((CRMContext) getContext());
		boolean allowAll =CommonUtil.allowAllDivisionAccess(ctx);
		Map<String, String> ans = new LinkedHashMap<String, String>();
		IDivisionService service = (IDivisionService) SpringObjectFactory.INSTANCE
				.getInstance("IDivisionService");
		List<Division> divisions = service.getAllDivisions(ctx
				.getLoggedinCompany());
		if (!Utils.isNullList(divisions)) {
			for (Division division : divisions) {
				if (allowAll || division.getId() == ctx.getLoggedInUser().getDivision().getId())
					ans.put(String.valueOf(division.getId()), division.getName());
			}
		}
		return ans;
	}

	public String  getLogo() {
		if(((CRMContext) getContext()).getLoggedinCompany() >0 ) {
			Company company  = CommonUtil.getCompany(((CRMContext) getContext()).getLoggedinCompany());
			String logo =  company.getLogo() ;  //./images/logo1.gif
			if (Utils.isNull(logo))
				return "./images/logo1.gif";
			else  {
				String serverURL = ServiceLibrary.services().getApplicationManager().getDocServer();
				return serverURL + logo;
			}
		}
		return "./images/logo1.gif";

	}

	public String getUserPhoto()
	{
		String serverURL = ServiceLibrary.services().getApplicationManager().getDocServer();
		String photo =  ((CRMContext) getContext()).getLoggedInUser().getPhoto();
		return serverURL  + photo ;
	}

}
