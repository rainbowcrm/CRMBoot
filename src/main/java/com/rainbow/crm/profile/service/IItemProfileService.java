package com.rainbow.crm.profile.service;


import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.profile.model.CustomerProfile;
import com.rainbow.crm.profile.model.ItemProfile;

public interface IItemProfileService {

	public ItemProfile getItemProfile(Item item, CRMContext context);
	
	public CustomerProfile getCustomerProfile(Customer customer,CRMContext context);
	
	
}
