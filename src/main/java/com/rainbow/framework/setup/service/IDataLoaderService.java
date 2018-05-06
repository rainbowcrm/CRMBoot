package com.rainbow.framework.setup.service;

import java.io.File;
import java.io.OutputStream;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.framework.setup.model.DataLoader;

public interface IDataLoaderService {

	public void importXML(DataLoader dataLoader, CRMContext context) ;
	
	public void importJSON(DataLoader dataLoader, CRMContext context);
	
	public String exportXML(DataLoader dataLoader, CRMContext context) ;
	
	public String exportJSON(DataLoader dataLoader, CRMContext context) ;
	
	public File exportXLS(DataLoader dataLoader, CRMContext context) ;
	
	public void importXLS(DataLoader dataLoader, CRMContext context) ;
	
}
