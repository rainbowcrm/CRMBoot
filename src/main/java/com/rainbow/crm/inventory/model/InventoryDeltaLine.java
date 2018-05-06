package com.rainbow.crm.inventory.model;

import com.rainbow.crm.abstratcs.model.CRMItemLine;
import com.rainbow.crm.division.model.Division;

public class InventoryDeltaLine  extends CRMItemLine
{
	Division division;
	boolean reserve ;
	boolean fulFill ;

	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}

	public boolean isReserve() {
		return reserve;
	}

	public void setReserve(boolean reserve) {
		this.reserve = reserve;
	}

	public boolean isFulFill() {
		return fulFill;
	}

	public void setFulFill(boolean fulFill) {
		this.fulFill = fulFill;
	}
	
	
	
	

}
