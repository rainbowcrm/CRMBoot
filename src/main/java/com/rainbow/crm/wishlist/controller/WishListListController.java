package com.rainbow.crm.wishlist.controller;

import java.util.List;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMListController;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.wishlist.model.WishList;
import com.rainbow.crm.wishlist.service.IWishListService;
import com.rainbow.crm.wishlist.validator.WishListValidator;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

public class WishListListController extends CRMListController{

	@Override
	public IBusinessService getService() {
		IWishListService serv = (IWishListService) SpringObjectFactory.INSTANCE.getInstance("IWishListService");
		return serv;
	}

	@Override
	public Object getPrimaryKeyValue(ModelObject object) {
		WishList wishList = (WishList) object;
		return wishList.getId();
				
	}

	@Override
	public PageResult submit(List<ModelObject> objects, String submitAction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RadsError> validateforDelete(List<ModelObject> objects) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RadsError> validateforEdit(List<ModelObject> objects) {
		WishListValidator validator = new WishListValidator((CRMContext) getContext());
		return validator.eligibleForEdit(objects);
	}

	@Override
	public PageResult goToEdit(List<ModelObject> objects) {
		PageResult result = new PageResult();
		result.setNextPageKey("newwishlist");
		return result;
	}
	
	

}
