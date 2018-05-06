package com.rainbow.crm.category.service;

import java.util.List;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.category.model.Category;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMDBException;
import com.rainbow.crm.common.IBusinessService;
import com.techtrade.rads.framework.model.transaction.TransactionResult;

public interface ICategoryService extends IBusinessService{
	
	public Category getByName(int company, String name) ;
	
	public TransactionResult batchCreate(List<CRMModelObject> objects, CRMContext context) throws CRMDBException;

	public List<Category>  getAllCategories(int company);
}
