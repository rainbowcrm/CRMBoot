package com.rainbow.crm.promotion.service;

import java.util.Date;
import java.util.List;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.ITransactionService;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.promotion.model.Promotion;
import com.rainbow.crm.promotion.model.PromotionLine;
import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.framework.query.model.QueryReport;

public interface IPromotionService extends ITransactionService{
	
	public PromotionLine getPromotionforSKU(Sku sku, Date date);
	
	public Promotion getAllItemPromotion(Date date, Division division);
	
	public PromotionLine isIncentivizedSku(Sku sku, Date date);
	
	public List<Promotion> getAllPromotionsforType(FiniteValue promoType,Date date, CRMContext context);
}
