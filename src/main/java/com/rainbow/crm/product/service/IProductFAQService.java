package com.rainbow.crm.product.service;

import java.util.List;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.ITransactionService;
import com.rainbow.crm.product.model.Product;
import com.rainbow.crm.product.model.ProductAttribute;
import com.rainbow.crm.product.model.ProductFAQSet;

public interface IProductFAQService extends ITransactionService{
	
	public ProductFAQSet getByProduct(Product product, CRMContext context);
	
	public List<ProductAttribute> getAllAttributes(Product product, CRMContext context);
	
	public ProductAttribute getAttribute (ProductAttribute prodAttribute, CRMContext context );

}
