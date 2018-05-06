package com.rainbow.crm.custcategory.model;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.framework.query.model.QueryCondition;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

public class CustCategoryCondition extends QueryCondition{
	
	CustCategory category ;

	@RadsPropertySet(excludeFromJSON = true, excludeFromMap = true, excludeFromXML = true)
	public CustCategory getCategory() {
		return category;
	}

	@RadsPropertySet(excludeFromJSON = true, excludeFromMap = true, excludeFromXML = true)
	public void setCategory(CustCategory category) {
		this.category = category;
	}
	
	

}
