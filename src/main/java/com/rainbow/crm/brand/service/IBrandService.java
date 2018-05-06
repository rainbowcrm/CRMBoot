package com.rainbow.crm.brand.service;

import java.util.List;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.brand.model.Brand;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMDBException;
import com.rainbow.crm.common.IBusinessService;
import com.techtrade.rads.framework.model.transaction.TransactionResult;

public interface IBrandService extends IBusinessService{
	
	public Brand getByName(int company, String name) ;
	
	public TransactionResult batchCreate(List<CRMModelObject> objects, CRMContext context) throws CRMDBException;

	public List<Brand>  getAllBrands(int company);
}
