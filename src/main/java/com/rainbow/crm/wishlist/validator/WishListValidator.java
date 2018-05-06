package com.rainbow.crm.wishlist.validator;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonErrorCodes;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.wishlist.model.WishList;
import com.rainbow.crm.wishlist.model.WishListLine;
import com.rainbow.crm.wishlist.service.IWishListService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.utils.Utils;

public class WishListValidator extends CRMValidator {

	WishList wishList ;
	 
	@Override
	protected void checkforCreateErrors(ModelObject object) {
		checkforErrors(object);
		IWishListService service =(IWishListService)SpringObjectFactory.INSTANCE.getInstance("IWishListService");
		WishList exist = (WishList)service.getByBusinessKey(wishList, context);
		if(exist != null) {
				errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Bill_No"))) ;
		}
		
	}

	@Override
	protected void checkforUpdateErrors(ModelObject object) {
		checkforErrors(object);
		IWishListService service =(IWishListService)SpringObjectFactory.INSTANCE.getInstance("IWishListService");
		WishList exist = (WishList)service.getByBusinessKey(wishList, context);
		if(exist != null && exist.getId() != wishList.getId()) {
				errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Bill_No"))) ;
		}
		
	}
	protected void checkforErrors(ModelObject object) {
		wishList = (WishList) object ;
		if (wishList.getCompany() == null){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Company"))) ;
		}
		if(Utils.isNull(wishList.getWishListDate()) ){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Date"))) ;
		}
		if (Utils.isNullSet(wishList.getWishListLines())) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Line_Items"))) ;
		}else {
			for (WishListLine line : wishList.getWishListLines()) {
				if (line.getSku() == null ) {
					errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Item"))) ;
				}else if (line.getSku().isDeleted() ) {
					errors.add(getErrorforCode(CommonErrorCodes.OBJECT_DELETED,externalize.externalize(context, "Item") + line.getSku().getCode())) ;
				}else if (line.getQty() <=0 ) {
					errors.add(getErrorforCode(CommonErrorCodes.SHOULD_BE_GREATER_THAN,externalize.externalize(context, "Qty") + line.getSku().getCode(),"0") ) ;
				}else if (line.getDesiredPrice() <=0 ) {
					errors.add(getErrorforCode(CommonErrorCodes.SHOULD_BE_GREATER_THAN,externalize.externalize(context, "Desired_Price") + line.getSku().getCode(),"0") ) ;
				}
			}
		}
	}
	
	public WishListValidator(CRMContext context) {
		super(context);
	}
	public WishListValidator(){
		
	}

	
}
