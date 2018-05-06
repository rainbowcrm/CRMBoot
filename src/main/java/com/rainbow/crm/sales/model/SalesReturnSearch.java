package com.rainbow.crm.sales.model;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.division.model.Division;

public class SalesReturnSearch  extends CRMBusinessModelObject{

	Division division ; 
	String originalBilllNumber;
	public Division getDivision() {
		return division;
	}
	public void setDivision(Division division) {
		this.division = division;
	}
	public String getOriginalBilllNumber() {
		return originalBilllNumber;
	}
	public void setOriginalBilllNumber(String originalBilllNumber) {
		this.originalBilllNumber = originalBilllNumber;
	}
	
	
}
