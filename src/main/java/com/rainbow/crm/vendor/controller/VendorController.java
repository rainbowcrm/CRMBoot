package com.rainbow.crm.vendor.controller;


import com.rainbow.crm.common.CRMCRUDController;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.vendor.service.IVendorService;

public class VendorController extends CRMCRUDController{
	
	public IBusinessService getService() {
		IVendorService serv = (IVendorService) SpringObjectFactory.INSTANCE.getInstance("IVendorService");
		return serv;
	}

}
