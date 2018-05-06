package com.rainbow.crm.customer.service;

import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.customer.model.Customer;

public interface ICustomerService extends IBusinessService{
	
	public Customer getByEmail(int company, String email) ;
	public Customer getByPhone(int company, String Phone) ;

}
