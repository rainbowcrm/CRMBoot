package com.rainbow.crm.territory.model;

import java.util.Date;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;

import com.techtrade.rads.framework.annotations.RadsPropertySet;

public class TerritoryLine extends CRMBusinessModelObject{
	int lineNumber;
	String zipCode ;
	String keyLocations;
	
	Territory territoryDoc;
	
	public TerritoryLine() {
	
	}
	@RadsPropertySet(isBK=true)
	public int getLineNumber() {
		return lineNumber;
	}
	@RadsPropertySet(isBK=true)
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	
	@RadsPropertySet(excludeFromJSON=true,excludeFromMap=true,excludeFromXML=true)
	public Territory getTerritoryDoc() {
		return territoryDoc;
	}
	@RadsPropertySet(excludeFromJSON=true,excludeFromMap=true,excludeFromXML=true)
	public void setTerritoryDoc(Territory territoryDoc) {
		this.territoryDoc = territoryDoc;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getKeyLocations() {
		return keyLocations;
	}
	public void setKeyLocations(String keyLocations) {
		this.keyLocations = keyLocations;
	}
	
}
