package com.rainbow.framework.setup.model;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.finitevalue.FiniteValue;

public class Metadata  extends CRMModelObject{

	String objectName;
	String className;
	FiniteValue  metaDataType;
	boolean isDivisionSpecific;
	String hqlClass;
	String description;
	String dateField;
	
	
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public FiniteValue getMetaDataType() {
		return metaDataType;
	}
	public void setMetaDataType(FiniteValue metaDataType) {
		this.metaDataType = metaDataType;
	}
	public boolean isDivisionSpecific() {
		return isDivisionSpecific;
	}
	public void setDivisionSpecific(boolean isDivisionSpecific) {
		this.isDivisionSpecific = isDivisionSpecific;
	}
	public Metadata(String objectName, String className,
			FiniteValue metaDataType, boolean isDivisionSpecific) {
		super();
		this.objectName = objectName;
		this.className = className;
		this.metaDataType = metaDataType;
		this.isDivisionSpecific = isDivisionSpecific;
	}
	
	
	
	public Metadata(String objectName, String className,
			FiniteValue metaDataType, boolean isDivisionSpecific,
			String hqlClass, String description, String dateField) {
		super();
		this.objectName = objectName;
		this.className = className;
		this.metaDataType = metaDataType;
		this.isDivisionSpecific = isDivisionSpecific;
		this.hqlClass = hqlClass;
		this.description = description;
		this.dateField = dateField;
	}
	public Metadata(){
		
	}
	public String getHqlClass() {
		return hqlClass;
	}
	public void setHqlClass(String hqlClass) {
		this.hqlClass = hqlClass;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDateField() {
		return dateField;
	}
	public void setDateField(String dateField) {
		this.dateField = dateField;
	}
	
	
	
	
}
