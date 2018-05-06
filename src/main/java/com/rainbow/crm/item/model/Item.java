package com.rainbow.crm.item.model;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.brand.model.Brand;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.product.model.Product;
import com.rainbow.crm.uom.model.UOM;
import com.techtrade.rads.framework.annotations.RadsPropertySet;
import com.techtrade.rads.framework.utils.Utils;

public class Item extends CRMBusinessModelObject{
	
	String code;
	String name; 
	String description;
	Product product;
	Brand brand; 
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
	
	
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public Brand getBrand() {
		return brand;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public UOM getUom() {
		return uom;
	}
	public void setUom(UOM uom) {
		this.uom = uom;
	}
	public int getUomId() {
		return uomId;
	}
	public void setUomId(int uomId) {
		this.uomId = uomId;
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
	public Double getPromotionPrice() {
		return promotionPrice;
	}
	public void setPromotionPrice(Double promotionPrice) {
		this.promotionPrice = promotionPrice;
	}
	public Double getMaxDiscount() {
		return maxDiscount;
	}
	public void setMaxDiscount(Double maxDiscount) {
		this.maxDiscount = maxDiscount;
	}
	@RadsPropertySet(excludeFromJSON =true)
	public String getSpecification() {
		return specification;
	}
	@RadsPropertySet(excludeFromJSON =true)
	public void setSpecification(String specification) {
		this.specification = specification;
	}
	public boolean isOnPromotion() {
		return onPromotion;
	}
	public void setOnPromotion(boolean onPromotion) {
		this.onPromotion = onPromotion;
	}
	public String getItemClass() {
		return itemClass;
	}
	public void setItemClass(String itemClass) {
		this.itemClass = itemClass;
	}
	public String getItemClassDesc() {
		if(Utils.isNullString(itemClassDesc)) {
			FiniteValue fvalue= GeneralSQLs.getFiniteValue(itemClass);
			itemClassDesc = fvalue!=null?fvalue.getDescription():"";
		}
		return itemClassDesc;
	}
	public void setItemClassDesc(String itemClassDesc) {
		this.itemClassDesc = itemClassDesc;
	} 

	@Override
	@RadsPropertySet(excludeFromJSON=true,excludeFromMap=true,excludeFromXML=true)
	public boolean isNullContent() {
		if( id <= 0 && Utils.isNullString(name))
			return true ;
		else
			return false;
	}
	
}
