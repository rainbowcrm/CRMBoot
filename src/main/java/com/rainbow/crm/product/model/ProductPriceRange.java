package com.rainbow.crm.product.model;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

public class ProductPriceRange extends CRMBusinessModelObject{

	Product product ;
	FiniteValue itemClass;
	Double minPrice;
	Double maxPrice;
	String comments;
	
	@RadsPropertySet(useBKForJSON =true, useBKForMap =true, useBKForXML =true)
	public Product getProduct() {
		return product;
	}
	@RadsPropertySet(useBKForJSON =true, useBKForMap =true, useBKForXML =true)
	public void setProduct(Product product) {
		this.product = product;
	}
	@RadsPropertySet(useBKForJSON =true, useBKForMap =true, useBKForXML =true)
	public FiniteValue getItemClass() {
		return itemClass;
	}
	@RadsPropertySet(useBKForJSON =true, useBKForMap =true, useBKForXML =true)
	public void setItemClass(FiniteValue itemClass) {
		this.itemClass = itemClass;
	}
	public Double getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
	}
	public Double getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(Double maxPrice) {
		this.maxPrice = maxPrice;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
	
}
