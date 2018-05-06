package com.rainbow.crm.category.model;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.finitevalue.FiniteValueManager;
import com.rainbow.crm.company.model.Company;
import com.techtrade.rads.framework.annotations.RadsPropertySet;
import com.techtrade.rads.framework.utils.Utils;

public class Category extends CRMBusinessModelObject {

	String name;
	String primaryUOM;
	String description;
	
	
	@RadsPropertySet(isBK=true)
	public String getName() {
		return name;
	}
	@RadsPropertySet(isBK=true)
	public void setName(String name) {
		this.name = name;
	}
	public String getPrimaryUOM() {
		return primaryUOM;
	}
	public void setPrimaryUOM(String primaryUOM) {
		this.primaryUOM = primaryUOM;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@RadsPropertySet(excludeFromJSON=true,excludeFromMap=true,excludeFromXML=true)
	public String getPrimaryUOMDesc() {
		return FiniteValueManager.INSTANCE.getFiniteValueDesc(primaryUOM);
	}
	
	@Override
	@RadsPropertySet(excludeFromJSON=true,excludeFromMap=true,excludeFromXML=true)
	public boolean isNullContent() {
		if( id <= 0 && Utils.isNullString(name))
			return true ;
		else
			return false;
	}
	
	
}
