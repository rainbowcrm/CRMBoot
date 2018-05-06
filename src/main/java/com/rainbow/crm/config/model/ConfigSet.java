package com.rainbow.crm.config.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.utils.Utils;

public class ConfigSet extends ModelObject{

	public final static String GENERAL = "General" ;
	public final static String CUSTOMER_MANAGEMENT = "Customer Management" ;
	public final static String PAYMENT = "Payment" ;
	public final static String INVENTORY = "Inventory" ;
	public final static String ORDER_MANAGEMENT = "Order Management" ;
	public final static String WORKFORCE_MANAGEMENT = "Workforce Management" ;
	public final static String AUTHORIZATIONS = "Authorizations" ;
	
	int company ;

	public List<ConfigLine> getAuthorizationConfigLines() {
		return configMap.get(AUTHORIZATIONS) ;
	}
	public void setAuthorizationConfigLines(List<ConfigLine>  configLines) {
		configMap.put(AUTHORIZATIONS, configLines);
	}
	
	
	public List<ConfigLine> getOrderConfigLines() {
		return configMap.get(ORDER_MANAGEMENT) ;
	}
	public void setOrderConfigLines(List<ConfigLine>  configLines) {
		configMap.put(ORDER_MANAGEMENT, configLines);
	}
	public List<ConfigLine> getWorkforceConfigLines() {
		return configMap.get(WORKFORCE_MANAGEMENT) ;
	}
	public void setWorkforceConfigLines(List<ConfigLine>  configLines) {
		configMap.put(WORKFORCE_MANAGEMENT, configLines);
	}
	
	public List<ConfigLine> getPaymentConfigLines() {
		return configMap.get(PAYMENT) ;
	}
	public void setPaymentConfigLines(List<ConfigLine>  configLines) {
		configMap.put(PAYMENT, configLines);
	}
	
	public List<ConfigLine> getInventoryConfigLines() {
		return configMap.get(INVENTORY) ;
	}
	public void setInventoryConfigLines(List<ConfigLine>  configLines) {
		configMap.put(INVENTORY, configLines);
	}
	
	public List<ConfigLine> getGeneralConfigLines() {
		return configMap.get(GENERAL) ;
	}
	public void setGeneralConfigLines(List<ConfigLine>  configLines) {
		configMap.put(GENERAL, configLines);
	}
	
	public List<ConfigLine> getCustomerConfigLines() {
		return configMap.get(CUSTOMER_MANAGEMENT) ;
	}
	public void setCustomerConfigLines(List<ConfigLine>  configLines) {
		configMap.put(CUSTOMER_MANAGEMENT, configLines);
	}
	
	Map<String, List<ConfigLine>> configMap = new HashMap<String, List<ConfigLine>>();

	public Map<String, List<ConfigLine>> getConfigMap() {
		return configMap;
	}

	public void setConfigMap(Map<String, List<ConfigLine>> configMap) {
		this.configMap = configMap;
	}

	public int getCompany() {
		return company;
	}

	public void setCompany(int company) {
		this.company = company;
	}
	
	public boolean isNull () {
		return Utils.isNullMap(configMap); 
	}

}
