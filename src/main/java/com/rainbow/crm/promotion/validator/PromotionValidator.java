package com.rainbow.crm.promotion.validator;

import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonErrorCodes;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.promotion.model.Promotion;
import com.rainbow.crm.promotion.service.IPromotionService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.utils.Utils;

public class PromotionValidator extends CRMValidator {

	Promotion promotion = null;
	
	@Override
	protected void checkforCreateErrors(ModelObject object) {
		checkforErrors(object);
		IPromotionService  service = (IPromotionService) SpringObjectFactory.INSTANCE.getInstance("IPromotionService");
		Promotion  exist = (Promotion)service.getByBusinessKey(promotion, context);
		if(exist != null ) {
			errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Name"))) ;
		}
		exist = (Promotion)service.getByBusinessKey(promotion, context);
		if(exist != null ) {
			errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Name"))) ;
		}
		
	}

	@Override
	protected void checkforUpdateErrors(ModelObject object) {
		checkforErrors(object);
		IPromotionService  service = (IPromotionService) SpringObjectFactory.INSTANCE.getInstance("IPromotionService");
		Promotion  exist = (Promotion)service.getByBusinessKey(promotion, context);
		if(exist != null && exist.getId() !=  promotion.getId()) {
			errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Name"))) ;
		}
		exist = (Promotion)service.getByBusinessKey(promotion, context);
		if(exist != null  && exist.getId() != promotion.getId()) {
			errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Name"))) ;
		}
		
	}
	
	private void validate()
	{
		if(promotion.getForAll())  {
			forAllValidations();
			
		}
		
	}
	
	private void forAllValidations() {
		if (promotion.getPromoType() == null)
		{
			errors.add( getErrorforCode(CommonErrorCodes.FIELD_EMPTY,"Promo_Type"));
		}
		if(!promotion.getPromoType().equals(CRMConstants.PROMOTYPE.PLAINDISCOUNT) ) {
			errors.add( getErrorforCode(PromotionErrorCodes.INVALID_PROMO_FOR_ALLITEMS));
		}
		if(!promotion.getPromoType().equals(CRMConstants.PROMOTYPE.BUNDLING) ) {
			if (promotion.getBundlePricing() != null ) {
				errors.add( getErrorforCode(PromotionErrorCodes.BUNDLING_PRICING_ONLYFORBUNDLING));
			}
			if(promotion.getBundlePrice() > 0 && ! CRMConstants.BUNDLE_PRICING.FIXED_PRICE.equals(promotion.getPromoType()))
			{
				errors.add( getErrorforCode(PromotionErrorCodes.BUNDLE_PRICE_ONLYFORFIXEDBUNDLING));
			}
		}
		if(promotion.getPromoType().equals(CRMConstants.PROMOTYPE.BUNDLING))
		{
			validateBundlingConditions() ;
		}
		if(promotion.getPromoType().equals(CRMConstants.PROMOTYPE.UPSELLING))
		{
			validateUpSellingConditions();
		}
		if(promotion.getPromoType().equals(CRMConstants.PROMOTYPE.CROSSSELL))
		{
			validateCrossSellingConditions();
		}
	}
	private void validateBundlingConditions() 
	{
		 if(Utils.isNullSet(promotion.getPromotionLines())) {
			 errors.add( getErrorforCode(CommonErrorCodes.FIELD_EMPTY,"Promotion_Lines"));
			 return ; 
		 }
		 if(promotion.getItemClass() != null ) {
			 errors.add( getErrorforCode(PromotionErrorCodes.ITEMCLASS_NOTFOR_UPSELLING));
			 return ;
		 }
		 promotion.getPromotionLines().forEach(promotionLine ->  {
			 if (promotionLine.getMasterPortFolioType() == null ) {
				 errors.add( getErrorforCode(PromotionErrorCodes.CANNOT_BE_BLANK_FOR,"Master_Type","Bundling"));
			 }
			 if (promotionLine.getMasterPortFolioKey() == null ) {
				 errors.add( getErrorforCode(PromotionErrorCodes.CANNOT_BE_BLANK_FOR,"Master_Value","Bundling"));
			 }
			 if (promotionLine.getChildPortFolioType() == null ) {
				 errors.add( getErrorforCode(PromotionErrorCodes.CANNOT_BE_BLANK_FOR,"Incentive_Type","Bundling"));
			 }
			 if (promotionLine.getChildPortFolioKey() == null ) {
				 errors.add( getErrorforCode(PromotionErrorCodes.CANNOT_BE_BLANK_FOR,"Incentive_Value","Bundling"));
			 }
			 if (promotionLine.getRequiredQty() == 0 &&  promotionLine.getRequiredAmount() == 0 ) {
				 errors.add( getErrorforCode(PromotionErrorCodes.CANNOT_BE_BLANK_FOR,"Required_Qty","Bundling"));
			 }
		 } );
	}
	
	private void validateCrossSellingConditions() 
	{
		if(Utils.isNullSet(promotion.getPromotionLines())) {
			 errors.add( getErrorforCode(CommonErrorCodes.FIELD_EMPTY,"Promotion_Lines"));
			 return ; 
		 }
		if(promotion.getItemClass() != null ) {
			 errors.add( getErrorforCode(PromotionErrorCodes.ITEMCLASS_NOTFOR_UPSELLING));
			 return ;
		 }
		promotion.getPromotionLines().forEach(promotionLine ->  {
			 if (promotionLine.getMasterPortFolioType() == null ) {
				 errors.add( getErrorforCode(PromotionErrorCodes.CANNOT_BE_BLANK_FOR,"Master_Type","Cross_Selling"));
			 }
			 if (promotionLine.getMasterPortFolioKey() == null ) {
				 errors.add( getErrorforCode(PromotionErrorCodes.CANNOT_BE_BLANK_FOR,"Master_Value","Cross_Selling"));
			 }
			 if (promotionLine.getChildPortFolioType() == null ) {
				 errors.add( getErrorforCode(PromotionErrorCodes.CANNOT_BE_BLANK_FOR,"Incentive_Type","Cross_Selling"));
			 }
			 if (promotionLine.getChildPortFolioKey() == null ) {
				 errors.add( getErrorforCode(PromotionErrorCodes.CANNOT_BE_BLANK_FOR,"Incentive_Value","Cross_Selling"));
			 }
			 if (promotionLine.getRequiredQty() == 0 &&  promotionLine.getRequiredAmount() == 0 ) {
				 errors.add( getErrorforCode(PromotionErrorCodes.CANNOT_BE_BLANK_FOR,"Required_Amount","Cross_Selling"));
			 }
		 } );
		
	}

	private void validateUpSellingConditions() 
	{
	
		 if(Utils.isNullSet(promotion.getPromotionLines())) {
			 errors.add( getErrorforCode(CommonErrorCodes.FIELD_EMPTY,"Promotion_Lines"));
			 return ; 
		 }
		 if(promotion.getItemClass() == null ) {
			 errors.add( getErrorforCode(PromotionErrorCodes.ITEMCLASS_FOR_UPSELLING));
			 return ;
		 }
		 promotion.getPromotionLines().forEach(promotionLine ->  {
			 if (promotionLine.getMasterPortFolioType() == null ) {
				 errors.add( getErrorforCode(PromotionErrorCodes.CANNOT_BE_BLANK_FOR,"Master_Type","Up_Selling"));
			 }
			 if (promotionLine.getMasterPortFolioKey() == null ) {
				 errors.add( getErrorforCode(PromotionErrorCodes.CANNOT_BE_BLANK_FOR,"Master_Value","Up_Selling"));
			 }
			/* if (promotionLine.getRequiredQty() == 0 &&  promotionLine.getRequiredAmount() == 0 ) {
				 errors.add( getErrorforCode(PromotionErrorCodes.CANNOT_BE_BLANK_FOR,"Required_Amount","Required_Amount"));
			 }*/
			 if(promotionLine.getPromotedDiscPercent() == 0) {
				 errors.add( getErrorforCode(PromotionErrorCodes.CANNOT_BE_BLANK_FOR,"Discount_Percent","Up_Selling"));
			 }
		 } );
	}
	
	protected void checkforErrors(ModelObject object) {
		promotion = (Promotion) object;
		
	}
	
	public PromotionValidator(CRMContext context) {
		super(context);
	}
	
	public PromotionValidator(){
		
	}

	
}
