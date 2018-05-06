package com.rainbow.crm.common;

import java.util.ResourceBundle;

import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.IExternalizeFacade;
import com.techtrade.rads.framework.utils.Utils;

public class Externalize implements IExternalizeFacade {

	public static ResourceBundle  resourceBundle = null;
	
	@Override
	public String externalize(IRadsContext context, String label) {
		if (resourceBundle == null) {
			initialize(context);
		}
		String trimLabel = trimmedLabel(label); 
		if  (resourceBundle.containsKey(trimLabel)) {
			String property = (String)resourceBundle.getObject(trimLabel);
			return property;
		}
		else
			return label; 
	}
	
	private void initialize(IRadsContext context) {
		resourceBundle = ResourceBundle.getBundle("com.rainbow.crm.common.Label",context.getLocale());
	}
	
	private String trimmedLabel(String label) {
		if (!Utils.isNull(label))
			return label.replace(' ', '_');
		return  "";
				
	}
	
	public String getDateFormat( ) {
		return "dd-MMM-yyyy";
	}
	

}

