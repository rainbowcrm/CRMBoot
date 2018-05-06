package com.rainbow.crm.common.documents;

public class PrintField {

	String field;
	String value;
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
	public PrintField(String field, String value) {
		super();
		this.field = field;
		this.value = value;
	}
	
	
	
}
