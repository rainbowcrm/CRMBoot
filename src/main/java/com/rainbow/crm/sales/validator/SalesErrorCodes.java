package com.rainbow.crm.sales.validator;

import com.rainbow.crm.common.CommonErrorCodes;

public class SalesErrorCodes extends CommonErrorCodes{
	
	public static final int SALESBILL_NOT_FOUND= 2000301	;
	public static final int RETURN_QTY_EXCEEDS_ORIGINAL = 2000302;
	public static final int RETURN_PRICE_EXCEEDS_ORIGINAL = 2000303;
	public static final int ITEM_ALREADY_RETURNED_EARLIER = 2000304;
	public static final int REDEEM_LOYALTY_GREATER_THAN_AVLBLE = 2000305;
	public static final int INVENTORY_NOT_AVLBLE = 2000306;
	public static final int MGR_CAN_EDIT_LINES = 2000307;
	public static final int NOT_ALLOWED_TODO_RETURNS = 2000308 ;
	public static final int CUSTOMER_REQUIRED_FOREMAIL   =2000309;
	public static final int EMAIL_FAILED   =2000310;


}
