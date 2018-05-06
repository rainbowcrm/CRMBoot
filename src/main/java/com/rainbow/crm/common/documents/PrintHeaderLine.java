package com.rainbow.crm.common.documents;

import java.util.ArrayList;
import java.util.List;

public class PrintHeaderLine {
 
	List<PrintField> printFields;

	public List<PrintField> getPrintFields() {
		return printFields;
	}

	public void setPrintFields(List<PrintField> printFields) {
		this.printFields = printFields;
	}
	
	public void addPrintField(PrintField printField) {
		if(printFields == null )
			printFields  = new ArrayList<PrintField>();
		printFields.add(printField);
	}
	
	public String toString() {
		StringBuffer buff = new StringBuffer();
		printFields.forEach(printField -> 
		{
			buff.append(printField.getField() +":" + printField.getValue());
			
		});
		return buff.toString();
	}
}
