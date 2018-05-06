package com.rainbow.crm.product.service;

import java.util.List;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMDBException;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.product.model.Product;
import com.techtrade.rads.framework.model.transaction.TransactionResult;

public interface IProductService extends IBusinessService{
	
	public Product getByName(int company, String name) ;
	
	public TransactionResult batchCreate(List<CRMModelObject> objects, CRMContext context) throws CRMDBException;

}
