package com.rainbow.crm.config.service;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.config.model.ConfigSet;

public interface IConfigService {

	public ConfigSet getConfig(CRMContext context) ;
	
	public void saveConfig(ConfigSet set, CRMContext context);
	
	
}
