package com.rainbow.crm.vendor.service;

import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.vendor.model.Vendor;

public interface IVendorService extends IBusinessService{
	
	public Vendor getByCode(int company, String code) ;
	public Vendor getByName(int company, String name) ;

}
