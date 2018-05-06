package com.rainbow.crm.abstratcs.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.rainbow.crm.company.model.Company;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

public abstract class CRMBusinessModelObject  extends CRMModelObject{
	
	protected Company company;
	
	protected int id;
	
	protected Map<String,Object> temporaryProperties = new HashMap<String,Object>() ;
	
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	//@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	@RadsPropertySet(excludeFromJSON=true,excludeFromMap=true,excludeFromXML=true)
	public Company getCompany() {
		return company;
	}
	
	//@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	@RadsPropertySet(excludeFromJSON=true,excludeFromMap=true,excludeFromXML=true)
	public void setCompany(Company company) {
		this.company = company;
	}
	
	@Override
	@RadsPropertySet(excludeFromJSON=true,excludeFromMap=true,excludeFromXML=true)
	public Object getPK() {
		return id;
	}
	@RadsPropertySet(excludeFromJSON=true,excludeFromMap=true,excludeFromXML=true)
	public Map<String, Object> getTemporaryProperties() {
		return temporaryProperties;
	}
	@RadsPropertySet(excludeFromJSON=true,excludeFromMap=true,excludeFromXML=true)
	public void setTemporaryProperties(Map<String, Object> temporaryProperties) {
		this.temporaryProperties = temporaryProperties;
	}
	
	@RadsPropertySet(excludeFromJSON=true,excludeFromMap=true,excludeFromXML=true)
	public boolean isNullContent() {
		Map<String, Object> keys =  this.getBK();
		Iterator it = keys.keySet().iterator() ;
		while(it.hasNext()) {
			Object ob = keys.get(it.next());
			if (ob != null) {
				if (ob instanceof CRMBusinessModelObject)
					return ((CRMBusinessModelObject)ob).isNullContent();
				return false;
			}
		}
		return true;
	}

}
