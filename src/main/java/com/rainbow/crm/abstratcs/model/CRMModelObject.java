package com.rainbow.crm.abstratcs.model;

import java.io.Serializable;
import java.sql.Timestamp;

import com.techtrade.rads.framework.annotations.RadsPropertySet;
import com.techtrade.rads.framework.model.abstracts.ModelObject;

public abstract class CRMModelObject extends ModelObject implements Serializable,Cloneable {
	
	Timestamp createdDate;
	Timestamp lastUpdateDate ;
	String createdUser;
	String lastUpdateUser;
	boolean isDeleted ;
	Integer objectVersion;
	
	
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	@RadsPropertySet(excludeFromJSON=true, excludeFromXML=true,excludeFromMap=true)
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	@RadsPropertySet(excludeFromJSON=true, excludeFromXML=true,excludeFromMap=true)
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	@RadsPropertySet(excludeFromJSON=true, excludeFromXML=true,excludeFromMap=true)
	public Timestamp getLastUpdateDate() {
		return lastUpdateDate;
	}
	@RadsPropertySet(excludeFromJSON=true, excludeFromXML=true,excludeFromMap=true)
	public void setLastUpdateDate(Timestamp lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	@RadsPropertySet(excludeFromJSON=true, excludeFromXML=true,excludeFromMap=true)
	public String getCreatedUser() {
		return createdUser;
	}
	@RadsPropertySet(excludeFromJSON=true, excludeFromXML=true,excludeFromMap=true)
	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}
	@RadsPropertySet(excludeFromJSON=true, excludeFromXML=true,excludeFromMap=true)
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	@RadsPropertySet(excludeFromJSON=true, excludeFromXML=true,excludeFromMap=true)
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
	
	public boolean isDeleted() {
		return isDeleted;
	}
	
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Integer getObjectVersion() {
		return objectVersion;
	}

	public void setObjectVersion(Integer objectVersion) {
		this.objectVersion = objectVersion;
	}
	
	
	

}
