package com.rainbow.crm.config.model;

import java.util.Map;

import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.database.GeneralSQLs;
import com.techtrade.rads.framework.model.abstracts.ModelObject;

public class ConfigLine  extends ModelObject{
	
	String configCode; 
	int company ;
	String description ;
	String group;
	FiniteValue valueType;
	String valueGenerator;
	String defaultValue; 
	String configValue ;
	String value; 
	
	public String getConfigCode() {
		return configCode;
	}
	public void setConfigCode(String configCode) {
		this.configCode = configCode;
	}
	

	public int getCompany() {
		return company;
	}
	public void setCompany(int company) {
		this.company = company;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public FiniteValue getValueType() {
		return valueType;
	}
	public void setValueType(FiniteValue valueType) {
		this.valueType = valueType;
	}
	public String getValueGenerator() {
		return valueGenerator;
	}
	public void setValueGenerator(String valueGenerator) {
		this.valueGenerator = valueGenerator;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getConfigValue() {
		return configValue;
	}
	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public Map <String, String > getOptions() {
		if (  CRMConstants.VALUE_TYPE.FINITE_VALUE.equals(valueType.getCode()) ) {
			Map<String, String> ans = GeneralSQLs.getFiniteValues(valueGenerator);
			return ans;
		}
		return null;
	}
	
	public boolean isTextField() {
		return CRMConstants.VALUE_TYPE.STRING.equals(valueType.getCode());
	}
	
	public boolean isFVField() {
		return CRMConstants.VALUE_TYPE.FINITE_VALUE.equals(valueType.getCode());
	}
	
	public boolean isNumericField() {
		return CRMConstants.VALUE_TYPE.NUMERIC.equals(valueType.getCode());
	}
	public boolean isBooleanField() {
		return CRMConstants.VALUE_TYPE.BOOLEAN.equals(valueType.getCode());
	}
	

}
