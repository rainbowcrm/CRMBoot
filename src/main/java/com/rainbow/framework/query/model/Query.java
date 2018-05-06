package com.rainbow.framework.query.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.user.model.User;

public class Query extends CRMBusinessModelObject{

	String name ;
	String entity;
	Division division;
	Date fromDate;
	Date toDate;
	
	FiniteValue fromCriteria;
	FiniteValue toCriteria;
	
	String dateValueType;
	String resultType; 
	
	String selectedFields[];
	String selectedFiedsBlob;
	String sortField;
	String sortDesc;
	
	String reportData;
	AggregationFields aggregationFields = new AggregationFields();
	
	User owner;


	Set<QueryCondition> conditions;
	boolean isDateExcluded ;

	
	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Set<QueryCondition> getConditions() {
		return conditions;
	}

	public void setConditions(Set<QueryCondition> conditions) {
		this.conditions = conditions;
	}

	public void addCondition(QueryCondition condition) {
		if (conditions == null)
			conditions = new LinkedHashSet<QueryCondition>();
		this.conditions.add(condition);
	}

	public FiniteValue getFromCriteria() {
		return fromCriteria;
	}

	public void setFromCriteria(FiniteValue fromCriteria) {
		this.fromCriteria = fromCriteria;
	}

	public FiniteValue getToCriteria() {
		return toCriteria;
	}

	public void setToCriteria(FiniteValue toCriteria) {
		this.toCriteria = toCriteria;
	}

	public String getDateValueType() {
		return dateValueType;
	}

	public void setDateValueType(String dateValueType) {
		this.dateValueType = dateValueType;
	}

	public String[] getSelectedFields() {
		return selectedFields;
	}

	public void setSelectedFields(String[] selectedFields) {
		this.selectedFields = selectedFields;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getSortDesc() {
		return sortDesc;
	}

	public void setSortDesc(String sortDesc) {
		this.sortDesc = sortDesc;
	}

	public String getReportData() {
		return reportData;
	}

	public void setReportData(String reportData) {
		this.reportData = reportData;
	}

	public String getResultType() {
		return resultType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	public AggregationFields getAggregationFields() {
		return aggregationFields;
	}

	public void setAggregationFields(AggregationFields aggregationFields) {
		this.aggregationFields = aggregationFields;
	}

	public boolean isDateExcluded() {
		return isDateExcluded;
	}

	public void setDateExcluded(boolean isDateExcluded) {
		this.isDateExcluded = isDateExcluded;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getSelectedFiedsBlob() {
		return selectedFiedsBlob;
	}

	public void setSelectedFiedsBlob(String selectedFiedsBlob) {
		this.selectedFiedsBlob = selectedFiedsBlob;
	}
	
	
	public String getGroupByField() {
		return aggregationFields.getGroupByField();
	}

	public void setGroupByField(String groupByField) {
		aggregationFields.setGroupByField(groupByField);
	}

	public String getAggredatedField() {
		return aggregationFields.getAggredatedField();
	}

	public void setAggredatedField(String aggredatedField) {
		aggregationFields.setAggredatedField(aggredatedField);
	}

	public String getAggregationType() {
		return aggregationFields.getAggregationType();
	}

	public void setAggregationType(String aggregationType) {
		aggregationFields.setAggregationType(aggregationType);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
	
}
