package com.rainbow.crm.config.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMGeneralController;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.config.model.ConfigSet;
import com.rainbow.crm.config.service.IConfigService;
import com.rainbow.crm.database.LoginSQLs;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.GeneralController;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

public class ConfigController extends CRMGeneralController {

	@Override
	public PageResult submit(ModelObject object) {
		ConfigSet configSet = (ConfigSet) object;
		PageResult result = new PageResult();
		IConfigService service = getService();
		if (!configSet.isNull()) {
			service.saveConfig(configSet, (CRMContext) getContext());
		}
		configSet = service.getConfig((CRMContext) getContext());
		setObject(configSet);

		result.setObject(configSet);
		return result;
	}

	public String getCompanyName() {
		ICompanyService service = (ICompanyService) SpringObjectFactory.INSTANCE
				.getInstance("ICompanyService");
		Company company = (Company) service.getById(((CRMContext) getContext())
				.getLoggedinCompany());
		return company.getName();
	}


	public IConfigService getService() {
		return (IConfigService) SpringObjectFactory.INSTANCE
				.getInstance("IConfigService");
	}

}
