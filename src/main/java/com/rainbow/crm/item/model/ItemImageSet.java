package com.rainbow.crm.item.model;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.CRMAppConfig;
import com.rainbow.crm.logger.Logwriter;
import com.techtrade.rads.framework.utils.Utils;

public class ItemImageSet extends CRMModelObject{

	Sku item;
	byte[] image1 ;
	byte[] image2 ;
	byte[] image3 ;
	
	String fileName1;
	String fileName2;
	String fileName3;
	
	String filewithPath1;
	String filewithPath2;
	String filewithPath3;
	
	@Override
	public Object getPK() {
		return null;
	}
	public Sku getSku() {
		return item;
	}
	public void setSku(Sku item) {
		this.item = item;
	}
	public byte[] getImage1() {
		return image1;
	}
	public void setImage1(byte[] image1) {
		this.image1 = image1;
	}
	public byte[] getImage2() {
		return image2;
	}
	public void setImage2(byte[] image2) {
		this.image2 = image2;
	}
	public byte[] getImage3() {
		return image3;
	}
	public void setImage3(byte[] image3) {
		this.image3 = image3;
	}
	public String getFileName1() {
		return fileName1;
	}
	public void setFileName1(String fileName1) {
		this.fileName1 = fileName1;
	}
	public String getFileName2() {
		return fileName2;
	}
	public void setFileName2(String fileName2) {
		this.fileName2 = fileName2;
	}
	public String getFileName3() {
		return fileName3;
	}
	public void setFileName3(String fileName3) {
		this.fileName3 = fileName3;
	}
	public String getFilewithPath1() {
		return filewithPath1;
	}
	public void setFilewithPath1(String filewithPath1) {
		this.filewithPath1 = filewithPath1;
	}
	public String getFilewithPath2() {
		return filewithPath2;
	}
	public void setFilewithPath2(String filewithPath2) {
		this.filewithPath2 = filewithPath2;
	}
	public String getFilewithPath3() {
		return filewithPath3;
	}
	public void setFilewithPath3(String filewithPath3) {
		this.filewithPath3 = filewithPath3;
	}
	
	
	
	
	
	
	
}
