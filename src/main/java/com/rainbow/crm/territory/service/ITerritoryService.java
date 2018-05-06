package com.rainbow.crm.territory.service;

import java.util.Date;
import java.util.List;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.ITransactionService;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.territory.model.Territory;
import com.techtrade.rads.framework.model.abstracts.RadsError;

public interface ITerritoryService extends ITransactionService{

	
	public List<Territory> getAllTerritoriesforDivision(int divisionId);
	
}
