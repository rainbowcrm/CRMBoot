package com.rainbow.crm.item.service;

import java.util.List;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.item.model.ItemAttribute;
import com.rainbow.crm.item.model.Sku;

public interface IItemService extends IBusinessService{
	
	public Item getByCode(int company, String code) ;
	public Item getByName(int company, String name) ;
	
	public List<Item> getAllByProduct(int company, int productId);
	
	public List<ItemAttribute> getAllItemAttributes(Item item, CRMContext conteext);
	

}
