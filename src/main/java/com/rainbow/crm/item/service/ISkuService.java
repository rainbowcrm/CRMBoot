package com.rainbow.crm.item.service;

import java.util.List;

import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.product.model.Product;

public interface ISkuService extends IBusinessService{

	public Sku getByCode(int company, String code) ;
	public Sku getByBarCode(int company, String barcode) ;
	public Sku getByName(int company, String name) ;
	
	public List<Sku> getAllByProduct(int company, int productId);
	public List<Sku> getAllByItem(int company, int itemId);

	

}
