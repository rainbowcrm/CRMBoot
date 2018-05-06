package com.rainbow.crm.config.service;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.config.model.ConfigSet;
import com.rainbow.crm.config.sql.ConfigSQL;

public class ConfigService implements IConfigService{

	@Override
	public ConfigSet getConfig(CRMContext context) {
		return ConfigSQL.getConfigSet(context.getLoggedinCompany());
	}

	@Override
	public void saveConfig(ConfigSet set, CRMContext context) {
		ConfigSQL.saveConfigSet(set, context.getLoggedinCompany());
		
	}

	
}
