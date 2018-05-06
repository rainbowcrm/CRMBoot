package com.rainbow.crm.contact.service;

import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.contact.model.Contact;

public interface IContactService extends IBusinessService{
	
	public Contact getByEmail(int company, String email) ;
	public Contact getByPhone(int company, String Phone) ;
	
	public Contact getByFullName(int company, String fullName) ;

}
