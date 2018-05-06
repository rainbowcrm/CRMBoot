package com.rainbow.crm.abstratcs.model;

import java.util.Arrays;
import java.util.Base64;

import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.item.model.Sku;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

public  class CRMItemLine extends CRMBusinessModelObject {
	
	Sku sku;
	Item item;
	int qty;
	boolean voided;
	
	private byte [] imgBytes1 ;
	private byte [] imgBytes2 ;
	private byte [] imgBytes3 ;
	
	
	String image1URL;
	String image2URL;
	String image3URL;
	
	
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public Item getItem() {
		return item;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public void setItem(Item item) {
		this.item = item;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public Sku getSku() {
		return sku;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public void setSku(Sku sku) {
		this.sku = sku;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	
	public boolean getVoided() {
		return voided;
	}
	public void setVoided(boolean isVoided) {
		this.voided = isVoided;
	}
	
	@RadsPropertySet(excludeFromJSON=true,excludeFromXML=true,excludeFromMap=true)
	public byte[] getImgBytes1() {
		return imgBytes1;
	}
	@RadsPropertySet(excludeFromJSON=true,excludeFromXML=true,excludeFromMap=true)
	public String getImgBytes1Str() {
		Base64.Encoder base64 = Base64.getEncoder();
		return base64.encodeToString(imgBytes1);
	}
	@RadsPropertySet(excludeFromJSON=true,excludeFromXML=true,excludeFromMap=true)
	public void setImgBytes1(byte[] imgBytes1) {
		this.imgBytes1 = imgBytes1;
	}
	@RadsPropertySet(excludeFromJSON=true,excludeFromXML=true,excludeFromMap=true)
	public String getImgBytes2Str() {
		Base64.Encoder base64 = Base64.getEncoder();
		return base64.encodeToString(imgBytes2);
	}
	@RadsPropertySet(excludeFromJSON=true,excludeFromXML=true,excludeFromMap=true)
	public byte[] getImgBytes2() {
		return imgBytes2;
	}
	@RadsPropertySet(excludeFromJSON=true,excludeFromXML=true,excludeFromMap=true)
	public void setImgBytes2(byte[] imgBytes2) {
		this.imgBytes2 = imgBytes2;
	}
	@RadsPropertySet(excludeFromJSON=true,excludeFromXML=true,excludeFromMap=true)
	public byte[] getImgBytes3() {
		return imgBytes3;
	}
	@RadsPropertySet(excludeFromJSON=true,excludeFromXML=true,excludeFromMap=true)
	public String getImgBytes3Str() {
		Base64.Encoder base64 = Base64.getEncoder();
		return base64.encodeToString(imgBytes3);
	}
	@RadsPropertySet(excludeFromJSON=true,excludeFromXML=true,excludeFromMap=true)
	public void setImgBytes3(byte[] imgBytes3) {
		this.imgBytes3 = imgBytes3;
	}
	public String getImage1URL() {
		return image1URL;
	}
	public void setImage1URL(String image1url) {
		image1URL = image1url;
	}
	public String getImage2URL() {
		return image2URL;
	}
	public void setImage2URL(String image2url) {
		image2URL = image2url;
	}
	public String getImage3URL() {
		return image3URL;
	}
	public void setImage3URL(String image3url) {
		image3URL = image3url;
	}
	
	
	

}
