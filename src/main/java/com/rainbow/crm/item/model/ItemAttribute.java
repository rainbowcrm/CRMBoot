package com.rainbow.crm.item.model;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.product.model.ProductAttribute;

public class ItemAttribute extends CRMBusinessModelObject
{
	Item item;
	ProductAttribute attribute;
	String value;
	String comments;
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public ProductAttribute getAttribute() {
		return attribute;
	}
	public void setAttribute(ProductAttribute attribute) {
		this.attribute = attribute;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
	

}
