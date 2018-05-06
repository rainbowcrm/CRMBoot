package com.rainbow.crm.common.documents;

import java.util.ArrayList;
import java.util.List;

public class PrintDocument {

	String title;
	String subTitle;
	

	List<PrintHeaderLine> headerLines;
	
	List<PrintDetail> printDetails;
	
	List<PrintSummaryLine> summaryLines;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public List<PrintHeaderLine> getHeaderLines() {
		return headerLines;
	}

	public void setHeaderLines(List<PrintHeaderLine> headerLines) {
		this.headerLines = headerLines;
	}
	
	public void addHeaderLine(PrintHeaderLine headerLine) {
		if (headerLines == null)
			headerLines = new ArrayList<PrintHeaderLine>();
			
		headerLines.add(headerLine);

	}
	

	public List<PrintDetail> getPrintDetails() {
		return printDetails;
	}

	public void setPrintDetails(List<PrintDetail> printDetails) {
		this.printDetails = printDetails;
	}

	public List<PrintSummaryLine> getSummaryLines() {
		return summaryLines;
	}

	public void setSummaryLines(List<PrintSummaryLine> summaryLines) {
		this.summaryLines = summaryLines;
	}
	
	public void addSummaryLine(PrintSummaryLine summaryLine) {
		if(summaryLines == null)
			summaryLines = new ArrayList<PrintSummaryLine> ();
		summaryLines.add(summaryLine);
	}
	
	
	
	
	
}
