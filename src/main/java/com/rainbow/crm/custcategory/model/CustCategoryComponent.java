package com.rainbow.crm.custcategory.model;

public class CustCategoryComponent {

	String entity;
	String keyField;
	String hqlKeyField;
	String dataType;
	String joinHqlClause;
	boolean isAggregated ;
	String preCondition;
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
	public String getKeyField() {
		return keyField;
	}
	public void setKeyField(String keyField) {
		this.keyField = keyField;
	}
	public String getHqlKeyField() {
		return hqlKeyField;
	}
	public void setHqlKeyField(String hqlKeyField) {
		this.hqlKeyField = hqlKeyField;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getJoinHqlClause() {
		return joinHqlClause;
	}
	public void setJoinHqlClause(String joinHqlClause) {
		this.joinHqlClause = joinHqlClause;
	}
	public boolean isAggregated() {
		return isAggregated;
	}
	public void setAggregated(boolean isAggregated) {
		this.isAggregated = isAggregated;
	}
	public CustCategoryComponent(String entity, String keyField,
			String hqlKeyField, String dataType, String joinHqlClause,
			boolean isAggregated, String preCondition) {
		super();
		this.entity = entity;
		this.keyField = keyField;
		this.hqlKeyField = hqlKeyField;
		this.dataType = dataType;
		this.joinHqlClause = joinHqlClause;
		this.isAggregated = isAggregated;
		this.preCondition = preCondition;
	}
	
	
	public CustCategoryComponent() {
		super();
	}

	public String getPreCondition() {
		return preCondition;
	}
	public void setPreCondition(String preCondition) {
		this.preCondition = preCondition;
	}
	
	
	
	
}
