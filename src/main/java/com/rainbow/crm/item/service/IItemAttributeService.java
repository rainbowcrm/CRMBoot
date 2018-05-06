package com.rainbow.crm.item.service;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.ITransactionService;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.item.model.ItemAttributeSet;

public interface IItemAttributeService extends ITransactionService{

	public ItemAttributeSet getByItem(Item item, CRMContext context) ;
}
