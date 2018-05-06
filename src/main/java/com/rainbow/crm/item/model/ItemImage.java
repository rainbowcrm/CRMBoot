package com.rainbow.crm.item.model;

import com.rainbow.crm.abstratcs.model.CRMModelObject;

public class ItemImage extends CRMModelObject{
	int id;
	byte[] image ;
	Sku item ;
	char suffix;
	String fileName ;
	String filePath;
	
	@Override
	public Object getPK() {
		return id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public Sku getSku() {
		return item;
	}
	public void setSku(Sku item) {
		this.item = item;
	}
	public char getSuffix() {
		return suffix;
	}
	public void setSuffix(char suffix) {
		this.suffix = suffix;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	

	
	
	
}
