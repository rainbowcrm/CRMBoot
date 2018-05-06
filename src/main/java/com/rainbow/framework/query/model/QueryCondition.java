package com.rainbow.framework.query.model;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.techtrade.rads.framework.utils.Utils;

public class QueryCondition  extends CRMBusinessModelObject{

	int noOpenBrackets;
	int noCloseBrackets;

	int lineNumber ;
	
	String openBrackets;
	String closeBrackets;
	
	FiniteValue dataType;

	String field;
	String operator;
	String value;

	
	String postCondition;

	Query query;
	
	@Override
	public String toString() 
	{
		StringBuffer condition  =new StringBuffer(" ");
		if(!Utils.isNullString(openBrackets))
			condition.append(openBrackets );
		
		condition.append( field).append(operator);
		if("NUMER".equalsIgnoreCase(dataType.getCode()) ||   "BOOL".equalsIgnoreCase(dataType.getCode())) {
			condition.append(value) ; 
		}
		if("STR".equalsIgnoreCase(dataType.getCode()) ||  "FINVAL".equalsIgnoreCase(dataType.getCode())   || "DATE".equalsIgnoreCase(dataType.getCode())) {
			condition.append("'"+ value + "'") ; 
		}
		if(!Utils.isNullString(closeBrackets))
			condition.append(closeBrackets );
		
		if(!Utils.isNullString(postCondition))
			condition.append(" " + postCondition + " " );
		
		return condition.toString();
	}
	public int getNoOpenBrackets() {
		return noOpenBrackets;
	}

	public void setNoOpenBrackets(int noOpenBrackets) {
		this.noOpenBrackets = noOpenBrackets;
	}

	public int getNoCloseBrackets() {
		return noCloseBrackets;
	}

	public void setNoCloseBrackets(int noCloseBrackets) {
		this.noCloseBrackets = noCloseBrackets;
	}

	public FiniteValue getDataType() {
		return dataType;
	}

	public void setDataType(FiniteValue dataType) {
		this.dataType = dataType;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getPostCondition() {
		return postCondition;
	}

	public void setPostCondition(String postCondition) {
		this.postCondition = postCondition;
	}

	public String getOpenBrackets() {
		return openBrackets;
	}

	public void setOpenBrackets(String openBrackets) {
		this.openBrackets = openBrackets;
	}

	public String getCloseBrackets() {
		return closeBrackets;
	}

	public void setCloseBrackets(String closeBrackets) {
		this.closeBrackets = closeBrackets;
	}
	public Query getQuery() {
		return query;
	}
	public void setQuery(Query query) {
		this.query = query;
	}
	public int getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	
	
	

}
