package com.rainbow.crm.uom.model;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.company.model.Company;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

public class UOM extends CRMBusinessModelObject{

	String code;
	String primaryUOM;
	String description;
	@RadsPropertySet(isBK=true)
	public String getCode() {
		return code;
	}
	@RadsPropertySet(isBK=true)
	public void setCode(String code) {
		this.code = code;
	}
	@RadsPropertySet(isBK=true)
	public String getPrimaryUOM() {
		return primaryUOM;
	}
	@RadsPropertySet(isBK=true)
	public void setPrimaryUOM(String primaryUOM) {
		this.primaryUOM = primaryUOM;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
