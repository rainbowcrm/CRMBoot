package com.rainbow.framework.setup.model;

import com.rainbow.crm.abstratcs.model.CRMModelObject;

public class DataLoader extends CRMModelObject{
	
	String exportmetadataClass ;
	String exportFormat;
	String importMetadataClasss;
	String importFormat;
	String action;
	byte[] importData ;
	String importFile;
	

	private boolean excludeDeleted;

	public boolean isExcludeDeleted() {
		return excludeDeleted;
	}

	public void setExcludeDeleted(boolean excludeDeleted) {
		this.excludeDeleted = excludeDeleted;
	}

	
	private boolean deleteOtherEntries;
	private boolean updateEntries ;
	
	public boolean isDeleteOtherEntries() {

		return deleteOtherEntries;
	}
	public void setDeleteOtherEntries(boolean deleteOtherEntries) {
		this.deleteOtherEntries = deleteOtherEntries;
	}
	public boolean isUpdateEntries() {
		return updateEntries;
	}
	public void setUpdateEntries(boolean updateEntries) {
		this.updateEntries = updateEntries;
	}
	
	public String getExportmetadataClass() {
		return exportmetadataClass;
	}
	public void setExportmetadataClass(String exportmetadataClass) {
		this.exportmetadataClass = exportmetadataClass;
	}
	public String getExportFormat() {
		return exportFormat;
	}
	public void setExportFormat(String exportFormat) {
		this.exportFormat = exportFormat;
	}
	public String getImportMetadataClasss() {
		return importMetadataClasss;
	}
	public void setImportMetadataClasss(String importMetadataClasss) {
		this.importMetadataClasss = importMetadataClasss;
	}
	public String getImportFormat() {
		return importFormat;
	}
	public void setImportFormat(String importFormat) {
		this.importFormat = importFormat;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public byte[] getImportData() {
		return importData;
	}
	public void setImportData(byte[] importData) {
		this.importData = importData;
	}
	public String getImportFile() {
		return importFile;
	}
	public void setImportFile(String importFile) {
		this.importFile = importFile;
	}
	
	

}
