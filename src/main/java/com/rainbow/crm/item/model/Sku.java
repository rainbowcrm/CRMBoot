package com.rainbow.crm.item.model;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.finitevalue.FiniteValueManager;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.product.model.Product;
import com.rainbow.crm.uom.model.UOM;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

public class Sku  extends CRMBusinessModelObject{
	String code;
	String barcode;
	String name; 
	String description;
	Product product;
	String color;
	String size;
	String manufacturer;
	UOM uom; // unused .. 
	int uomId;
	Double purchasePrice;
	Double maxPrice;
	Double retailPrice;
	Double wholeSalePrice;
	Double breakEvenPrice;
	Double promotionPrice;
	Double maxDiscount;
	String specification;
	boolean onPromotion;
	String itemClass; 
	String itemClassDesc; 
	
	Item item;
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	@RadsPropertySet(isBK=true)
	public String getName() {
		return name;
	}
	@RadsPropertySet(isBK=true)
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public Product getProduct() {
		return product;
	}
	
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public void setProduct(Product product) {
		this.product = product;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public UOM getUom() {
		return uom;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public void setUom(UOM uom) {
		this.uom = uom;
	}
	
	
	public Double getPurchasePrice() {
		return purchasePrice;
	}
	public void setPurchasePrice(Double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	public Double getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(Double maxPrice) {
		this.maxPrice = maxPrice;
	}
	public Double getRetailPrice() {
		return retailPrice;
	}
	public void setRetailPrice(Double retailPrice) {
		this.retailPrice = retailPrice;
	}
	public Double getWholeSalePrice() {
		return wholeSalePrice;
	}
	public void setWholeSalePrice(Double wholeSalePrice) {
		this.wholeSalePrice = wholeSalePrice;
	}
	public Double getBreakEvenPrice() {
		return breakEvenPrice;
	}
	public void setBreakEvenPrice(Double breakEvenPrice) {
		this.breakEvenPrice = breakEvenPrice;
	}
	public Double getMaxDiscount() {
		return maxDiscount;
	}
	public void setMaxDiscount(Double maxDiscount) {
		this.maxDiscount = maxDiscount;
	}
	public void setPromotionPrice(Double promotionPrice) {
		this.promotionPrice = promotionPrice;
	}
	@RadsPropertySet(excludeFromJSON=true)
	public String getSpecification() {
		return specification;
	}
	@RadsPropertySet(excludeFromJSON=true)
	public void setSpecification(String specification) {
		this.specification = specification;
	}
	public Double getPromotionPrice() {
		return promotionPrice;
	}
	public boolean isOnPromotion() {
		return onPromotion;
	}
	public void setOnPromotion(boolean onPromotion) {
		this.onPromotion = onPromotion;
	}
	
	public int getUomId() {
		return uomId;
	}
	public void setUomId(int uomId) {
		this.uomId = uomId;
	}
	public String getItemClass() {
		return itemClass;
	}
	public void setItemClass(String itemClass) {
		this.itemClass = itemClass;
	}

	public String getItemClassDesc() {
		return FiniteValueManager.INSTANCE.getFiniteValueDesc(itemClass);
	}

	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public Item getItem() {
		return item;
	}

	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public void setItem(Item item) {
		this.item = item;
	}
	
	
	

}
