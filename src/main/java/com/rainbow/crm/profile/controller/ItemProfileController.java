package com.rainbow.crm.profile.controller;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMGeneralController;
import com.rainbow.crm.common.CRMTransactionController;
import com.rainbow.crm.common.ITransactionService;
import com.rainbow.crm.common.ItemUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.profile.model.ItemProfile;
import com.rainbow.crm.profile.service.IItemProfileService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

public class ItemProfileController extends CRMTransactionController {

	@Override
	public PageResult submit(ModelObject object) {
		// TODO Auto-generated method stub
		return new PageResult();
	}
	
	@Override
	public ITransactionService getService() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageResult read() {
		ItemProfile profile = (ItemProfile) object;
		PageResult result = new PageResult();
		if(profile != null && profile.getItem() != null  ) {
			Item item = ItemUtil.getItem((CRMContext) getContext(), profile.getItem());
			profile.setItem(item);
			IItemProfileService service = (IItemProfileService)SpringObjectFactory.INSTANCE.getInstance("IItemProfileService");
			profile = service.getItemProfile(profile.getItem(), (CRMContext) getContext());
			result.setObject(profile);
			setObject(profile);
			return result; 
		}
		return super.read();
	}

	
	
}
