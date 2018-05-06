package com.rainbow.framework.query.model;

import com.techtrade.rads.framework.model.abstracts.ModelObject;

public class AggregationFields extends ModelObject {
	
	String groupByField;
	
	String aggredatedField;

	String aggregationType;
	
	public String getGroupByField() {
		return groupByField;
	}

	public void setGroupByField(String groupByField) {
		this.groupByField = groupByField;
	}

	public String getAggredatedField() {
		return aggredatedField;
	}

	public void setAggredatedField(String aggredatedField) {
		this.aggredatedField = aggredatedField;
	}

	public String getAggregationType() {
		return aggregationType;
	}

	public void setAggregationType(String aggregationType) {
		this.aggregationType = aggregationType;
	}
	
	
	
	
	
	

}
