package com.rainbow.crm.territory.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.vendor.model.Vendor;
import com.techtrade.rads.framework.annotations.RadsPropertySet;
import com.techtrade.rads.framework.utils.Utils;

public class Territory extends CRMBusinessModelObject{

	Division division;
	String territory;
	String description;
	Set<TerritoryLine> territoryLines;
	
	
	
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public Division getDivision() {
		return division;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public void setDivision(Division division) {
		this.division = division;
	}
	
	@RadsPropertySet(isBK =true)
	public String getTerritory() {
		return territory;
	}
	
	@RadsPropertySet(isBK =true)
	public void setTerritory(String territory) {
		this.territory = territory;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Set<TerritoryLine> getTerritoryLines() {
		return territoryLines;
	}
	public void setTerritoryLines(Set<TerritoryLine> territoryLines) {
		this.territoryLines = territoryLines;
	}
	public void addTerritoryLine(TerritoryLine territoryLine) {
		if (territoryLines  == null)
			territoryLines = new LinkedHashSet<TerritoryLine> ();
		this.territoryLines.add(territoryLine);
	}
	
	@Override
	@RadsPropertySet(excludeFromJSON=true,excludeFromMap=true,excludeFromXML=true)
	public boolean isNullContent() {
		if( id <= 0 && Utils.isNullString(territory))
			return true ;
		else
			return false;
	}
	

	
	
	
}
