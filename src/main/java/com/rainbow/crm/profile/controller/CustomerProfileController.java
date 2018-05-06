package com.rainbow.crm.profile.controller;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMTransactionController;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.ITransactionService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.profile.model.CustomerProfile;
import com.rainbow.crm.profile.service.IItemProfileService;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

public class CustomerProfileController extends CRMTransactionController {

	@Override
	public ITransactionService getService() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageResult read() {
		CustomerProfile profile  = (CustomerProfile) object;
		PageResult result = new PageResult();
		
		if(profile != null && profile.getCustomer() != null ) {
			Customer customer = CommonUtil.getCustomerObect((CRMContext) getContext(),  profile.getCustomer());
			IItemProfileService profileService = (IItemProfileService)SpringObjectFactory.INSTANCE.getInstance("IItemProfileService");
			profile = profileService.getCustomerProfile(customer, (CRMContext) getContext());
			result.setObject(profile);
			setObject(profile);
			return result; 
		}
				
		return super.read();
	}
	
	

}
