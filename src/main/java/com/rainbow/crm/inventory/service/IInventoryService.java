package com.rainbow.crm.inventory.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMDBException;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.inventory.model.Inventory;
import com.rainbow.crm.inventory.model.InventoryDelta;
import com.rainbow.crm.inventory.model.InventoryUpdateObject;
import com.rainbow.crm.item.model.Sku;
import com.techtrade.rads.framework.model.transaction.TransactionResult;

@Transactional
public interface IInventoryService extends IBusinessService{
	
	public TransactionResult batchCreate(List<CRMModelObject> objects, CRMContext context) throws CRMDBException;
	
	public Inventory getByItemandDivision(Sku item, Division division);
	
	@Transactional
	public void updateInventory(InventoryUpdateObject inventoryObject) ;
	
	public List<Inventory> getByItem(Sku item);
	
	@Transactional
	public void updateInventory(InventoryDelta inventoryDelta) ;
	

}
