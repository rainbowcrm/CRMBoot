package com.rainbow.crm.custcategory.model;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.saleslead.model.SalesLead;
import com.techtrade.rads.framework.annotations.RadsPropertySet;
import com.techtrade.rads.framework.utils.Utils;

@RadsPropertySet(jsonTag="CustCategory",xmlTag="CustCategory")
public class CustCategory extends CRMBusinessModelObject{
   
	String name ;
	Division division ;
	FiniteValue evalFrom;
	FiniteValue evalTo ;
	boolean incudeReturns;
	String comments;
	
	private String reportData;
	
	Set<CustCategoryCondition> conditions;
	
	Set<CustCategoryCondition> aggregateConditions;
	
	Set<CustCategoryCondition> whereConditions;

	@RadsPropertySet(isBK=true)
	public String getName() {
		return name;
	}
	
	@RadsPropertySet(isBK=true)
	public void setName(String name) {
		this.name = name;
	}
	public Division getDivision() {
		return division;
	}
	public void setDivision(Division division) {
		this.division = division;
	}
	public FiniteValue getEvalFrom() {
		return evalFrom;
	}
	public void setEvalFrom(FiniteValue evalFrom) {
		this.evalFrom = evalFrom;
	}
	public FiniteValue getEvalTo() {
		return evalTo;
	}
	public void setEvalTo(FiniteValue evalTo) {
		this.evalTo = evalTo;
	}
	public boolean isIncudeReturns() {
		return incudeReturns;
	}
	public void setIncudeReturns(boolean incudeReturns) {
		this.incudeReturns = incudeReturns;
	}
	public Set<CustCategoryCondition> getConditions() {
		return conditions;
	}
	public void setConditions(Set<CustCategoryCondition> conditions) {
		this.conditions = conditions;
	}
	public void addCondition(CustCategoryCondition condition)
	{
		if(conditions == null)
			 conditions = new LinkedHashSet<CustCategoryCondition> ();
		conditions.add(condition);
	}
	
		
	public Set<CustCategoryCondition> getAggregateConditions() {
		return aggregateConditions;
	}
	public void setAggregateConditions(
			Set<CustCategoryCondition> aggregateConditions) {
		this.aggregateConditions = aggregateConditions;
	}
	public void addAggregateCondition(
			CustCategoryCondition aggregateCondition) {
		if(aggregateConditions == null)
			aggregateConditions = new LinkedHashSet<CustCategoryCondition> ();
		this.aggregateConditions.add(aggregateCondition);
	}
	
	public Set<CustCategoryCondition> getWhereConditions() {
		return whereConditions;
	}
	public void setWhereConditions(Set<CustCategoryCondition> whereConditions) {
		this.whereConditions = whereConditions;
	}
	public void addWhereCondition(
			CustCategoryCondition whereCondition) {
		if(whereConditions == null)
			whereConditions = new LinkedHashSet<CustCategoryCondition> ();
		this.whereConditions.add(whereCondition);
	}
	
	
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getReportData() {
		return reportData;
	}
	public void setReportData(String reportData) {
		this.reportData = reportData;
	}
	

}
