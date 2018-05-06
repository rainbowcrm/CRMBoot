package com.rainbow.framework.query.model;

import java.util.List;

import com.techtrade.rads.framework.utils.Utils;

public class QueryRecord {

	int fieldCount;
	
	String [] fields = new String[10];

	public int getFieldCount() {
		return fieldCount;
	}

	public void setFieldCount(int fieldCount) {
		this.fieldCount = fieldCount;
	}

	public String[] getFields() {
		return fields;
	}

	public void setFields(String[] fields) {
		this.fields = fields;
	}
	
	private String fieldValue(int index)
	{
		if (fields.length > index)
			return Utils.isNullString(fields[index])?"":fields[index];
		else
			return "";
	}
	
	public String getField1( ){
		return fieldValue(0);
	}
	public String getField2( ){
		return fieldValue(1);
	}
	public String getField3( ){
		return fieldValue(2);
	}
	public String getField4( ){
		return fieldValue(3);
	}
	public String getField5( ){
		return fieldValue(4);
	}
	public String getField6( ){
		return fieldValue(5);
	}
	public String getField7( ){
		return fieldValue(6);
	}
	
	
	
	
	
}
