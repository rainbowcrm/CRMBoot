package com.rainbow.crm.common.documents;

import java.util.ArrayList;
import java.util.List;

public class PrintLine {

	List<String>  columnValues;

	public List<String> getColumnValues() {
		return columnValues;
	}

	public void setColumnValues(List<String> columnValues) {
		this.columnValues = columnValues;
	}
	
	public void addColumnValue(String columnValue) {
		if (columnValues == null)
			columnValues = new ArrayList<String> ();
		columnValues.add(columnValue);
	}
	
	
	public String toString() 
	{
		StringBuffer buff = new StringBuffer();
		columnValues.forEach(columnValue -> 
		{
			buff.append(columnValue);
			
		});
		return buff.toString();
	}
	
}
