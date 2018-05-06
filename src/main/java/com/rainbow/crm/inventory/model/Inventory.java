package com.rainbow.crm.inventory.model;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.category.model.Category;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.item.model.Sku;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

public class Inventory extends CRMBusinessModelObject{


	Division division;
	Sku sku;
	int opQty;
	int reservedQty;
	int damagedQty;
	int currentQty;
	
	@Override
	public Object getPK() {
		return id;
	}


	@RadsPropertySet(isBK=true,useBKForJSON=true,useBKForXML=true,useBKForMap=true)
	public Division getDivision() {
		return division;
	}

	@RadsPropertySet(isBK=true,useBKForJSON=true,useBKForXML=true,useBKForMap=true)
	public void setDivision(Division division) {
		this.division = division;
	}

	@RadsPropertySet(isBK=true,useBKForJSON=true,useBKForXML=true,useBKForMap=true)
	public Sku getSku() {
		return sku;
	}

	@RadsPropertySet(isBK=true,useBKForJSON=true,useBKForXML=true,useBKForMap=true)
	public void setSku(Sku item) {
		this.sku = item;
	}

	public int getOpQty() {
		return opQty;
	}

	public void setOpQty(int opQty) {
		this.opQty = opQty;
	}

	public int getCurrentQty() {
		return currentQty;
	}

	public void setCurrentQty(int currentQty) {
		this.currentQty = currentQty;
	}


	public int getReservedQty() {
		return reservedQty;
	}


	public void setReservedQty(int reservedQty) {
		this.reservedQty = reservedQty;
	}


	public int getDamagedQty() {
		return damagedQty;
	}


	public void setDamagedQty(int damagedQty) {
		this.damagedQty = damagedQty;
	}
	
	
	

	
}
