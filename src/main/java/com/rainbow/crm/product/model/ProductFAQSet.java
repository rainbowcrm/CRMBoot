package com.rainbow.crm.product.model;

import java.util.ArrayList;
import java.util.List;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;

public class ProductFAQSet extends CRMBusinessModelObject{
	
	List<ProductFAQ> productFAQs;
	Product product ;
	List<ProductPriceRange> productPriceRanges;
	List<ProductAttribute> productAttributes;
	
	
	
	
	public List<ProductAttribute> getProductAttributes() {
		return productAttributes;
	}

	public void setProductAttributes(List<ProductAttribute> productAttributes) {
		this.productAttributes = productAttributes;
	}

	public List<ProductPriceRange> getProductPriceRanges() {
		return productPriceRanges;
	}

	public void setProductPriceRanges(List<ProductPriceRange> productPriceRanges) {
		this.productPriceRanges = productPriceRanges;
	}

	public List<ProductFAQ> getProductFAQs() {
		return productFAQs;
	}
	
	public void setProductFAQs(List<ProductFAQ> productFAQs) {
		this.productFAQs = productFAQs;
	}
	
	public void addProductFAQ(ProductFAQ productFAQ)
	{
		if(productFAQs == null)
			productFAQs =new ArrayList<ProductFAQ> ();
		productFAQs.add(productFAQ);
		
	}
	
	public void addProductPriceRange(ProductPriceRange productPriceRange)
	{
		if(productPriceRanges == null)
			productPriceRanges =new ArrayList<ProductPriceRange> ();
		productPriceRanges.add(productPriceRange);
		
	}
	
	public void addProductAttribute(ProductAttribute productAttribute)
	{
		if(productAttributes == null)
			productAttributes =new ArrayList<ProductAttribute> ();
		productAttributes.add(productAttribute);
		
	}
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
	

}
