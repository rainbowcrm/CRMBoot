package com.rainbow.crm.promotion.validator;

import com.rainbow.crm.common.CommonErrorCodes;

public class PromotionErrorCodes extends CommonErrorCodes{
	
	public static final int INVALID_PROMO_FOR_ALLITEMS= 20000601	;
	public static final int CANNOT_BE_BLANK_FOR= 20000602	;
	public static final int SHOULD_NOT_BEENTERED = 20000603 ;
	public static final int ITEMCLASS_FOR_UPSELLING = 20000604 ;
	public static final int ITEMCLASS_NOTFOR_UPSELLING = 20000605 ;
	public static final int BUNDLING_PRICING_ONLYFORBUNDLING = 20000606 ;
	public static final int BUNDLE_PRICE_ONLYFORFIXEDBUNDLING = 20000607 ;

}
