package com.rainbow.crm.inventory.model;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.abstratcs.model.CRMItemLine;
import com.rainbow.crm.common.BusinessAction;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.division.model.Division;

@Deprecated
public class InventoryUpdateObject extends CRMBusinessModelObject implements Serializable{

	Division division ;
	boolean addition ;
	CRMContext context;
	
	boolean reserve ;
	boolean fulFilll ;
	
	boolean addLoyalty;
	String  salesDoc;
	
	BusinessAction action ;
	
	Set <? extends CRMItemLine> itemLines;
	public Division getDivision() {
		return division;
	}
	public void setDivision(Division division) {
		this.division = division;
	}
	public Set<? extends CRMItemLine> getItemLines() {
		return itemLines;
	}
	public void setItemLines(Set<? extends CRMItemLine> itemLines) {
		this.itemLines = itemLines;
	} 
/*	public void addItemLine( CRMItemLine itemLine) {
		if (itemLines == null) {
			itemLines = new LinkedHashSet<CRMItemLine>();
		}
		this.itemLines.add(itemLine);
	}*/
	public boolean isAddition() {
		return addition;
	}
	public void setAddition(boolean addition) {
		this.addition = addition;
	}
	public CRMContext getContext() {
		return context;
	}
	public void setContext(CRMContext context) {
		this.context = context;
	}
	public boolean isReserve() {
		return reserve;
	}
	public void setReserve(boolean reserve) {
		this.reserve = reserve;
	}
	public boolean isFulFilll() {
		return fulFilll;
	}
	public void setFulFilll(boolean fulFilll) {
		this.fulFilll = fulFilll;
	}
	public boolean isAddLoyalty() {
		return addLoyalty;
	}
	public void setAddLoyalty(boolean addLoyalty) {
		this.addLoyalty = addLoyalty;
	}
	public String getSalesDoc() {
		return salesDoc;
	}
	public void setSalesDoc(String salesDoc) {
		this.salesDoc = salesDoc;
	}
	public BusinessAction getAction() {
		return action;
	}
	public void setAction(BusinessAction action) {
		this.action = action;
	}
	
	
	
	
	
	
	
	
	
}
