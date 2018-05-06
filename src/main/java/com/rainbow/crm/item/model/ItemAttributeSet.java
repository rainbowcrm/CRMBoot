package com.rainbow.crm.item.model;

import java.util.ArrayList;
import java.util.List;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;

public class ItemAttributeSet extends CRMBusinessModelObject
{

	Item item ;
	List<ItemAttribute> attributes;
	List<Sku> skuVariants;
	

	public List<Sku> getSkuVariants() {
		return skuVariants;
	}

	public void setSkuVariants(List<Sku> skuVariants) {
		this.skuVariants = skuVariants;
	}
	
	public void addSkuVariant(Sku skuVariant) {
		if(skuVariants == null)
			skuVariants = new ArrayList<Sku> ();
		
		this.skuVariants.add(skuVariant);
	}

	public List<ItemAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<ItemAttribute> attributes) {
		this.attributes = attributes;
	}
	
	public void addAttribute(ItemAttribute attribute) {
		if (attributes == null)
			attributes = new ArrayList<ItemAttribute> ();
		attributes.add(attribute);
		
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	
	
	
	
	
	
}
