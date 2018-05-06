package com.rainbow.crm.promotion.engines;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.custcategory.service.ICustCategoryService;
import com.rainbow.crm.promotion.model.Promotion;
import com.rainbow.crm.sales.model.Sales;
import com.techtrade.rads.framework.model.abstracts.RadsError;

public abstract class AbstractPromotionEngine {

	public abstract RadsError searchForUnConsumedPromotions(Sales sales, CRMContext context);
	
	public abstract void applyPromotions(Sales sales, CRMContext context);
	
	protected boolean isApplicable(Promotion promotion, Sales sales) {
		if((promotion.getDivision() ==null || promotion.getDivision().getId() == sales.getDivision().getId()))  {
			if(promotion.getCustCategory() == null ) {
				return true;
			}else {
				if(sales.getCustomer() == null ) return false; 
				ICustCategoryService service = (ICustCategoryService)SpringObjectFactory.INSTANCE.getInstance("ICustCategoryService");
				boolean isCustomerEligible = service.isCustomerInCategory(promotion.getCustCategory(), sales.getCustomer()) ;
				return isCustomerEligible ;
			}
		}
		
		return false;
	}
	
}
