package com.rainbow.crm.filter.model;

import java.util.List;

public class CRMFilterDetails {
	int id;
	String field;
	String value;
	CRMFilter parent;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public CRMFilter getParent() {
		return parent;
	}
	public void setParent(CRMFilter parent) {
		this.parent = parent;
	}
	
	

		
}
