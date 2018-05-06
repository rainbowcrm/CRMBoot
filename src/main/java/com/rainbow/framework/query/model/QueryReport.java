package com.rainbow.framework.query.model;

import java.util.ArrayList;
import java.util.List;

public class QueryReport {
	
	List<QueryRecord>  records;
	
	String [] titles;
	
	String from;
	String to;
	

	public List<QueryRecord> getRecords() {
		return records;
	}

	public void setRecords(List<QueryRecord> records) {
		this.records = records;
	}
	
	public void addRecord(QueryRecord record) {
		if(records == null)
			records = new ArrayList<> ();
		records.add(record);
	}

	public String[] getTitles() {
		return titles;
	}

	public void setTitles(String[] titles) {
		this.titles = titles;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}
	
	
	
	

}
