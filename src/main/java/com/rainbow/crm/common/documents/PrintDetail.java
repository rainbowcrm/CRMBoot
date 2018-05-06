package com.rainbow.crm.common.documents;

import java.util.ArrayList;
import java.util.List;

public class PrintDetail {

	List<PrintLine> printLines;
	List<String>  columnHeaders;
	public List<PrintLine> getPrintLines() {
		return printLines;
	}
	public void setPrintLines(List<PrintLine> printLines) {
		this.printLines = printLines;
	}
	public List<String> getColumnHeaders() {
		return columnHeaders;
	}
	public void setColumnHeaders(List<String> columnHeaders) {
		this.columnHeaders = columnHeaders;
	}
	public void addPrintLine(PrintLine printLine) {
		if (printLines == null)
			printLines = new ArrayList<PrintLine>();
		printLines.add(printLine);
	}
	public void addColumnHeader(String columnHeader) {
		if(columnHeaders == null)
			columnHeaders = new ArrayList<String>();
		columnHeaders.add(columnHeader);
	}
	
}
