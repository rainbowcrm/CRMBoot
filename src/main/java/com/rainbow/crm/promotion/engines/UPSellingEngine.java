package com.rainbow.crm.promotion.engines;

import java.util.List;

import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.promotion.model.Promotion;
import com.rainbow.crm.promotion.model.PromotionLine;
import com.rainbow.crm.promotion.service.IPromotionService;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.sales.model.SalesLine;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.utils.Utils;

public class UPSellingEngine extends AbstractPromotionEngine{

	@Override
	public RadsError searchForUnConsumedPromotions(Sales sales,
			CRMContext context) {
		return null;
	}

	private boolean matches (SalesLine line, Promotion promotion)
	{
		
		if (promotion.getForAll())
			return true;
		for (PromotionLine promoLine :  promotion.getPromotionLines()) {
			if(promoLine.getMasterPortFolioType().equals(CRMConstants.SALESPFTYPE.BRAND) && 
					line.getSku().getItem().getItemClass().equals(promotion.getItemClass()) &&
					line.getSku().getItem().getBrand().getId() ==  Integer.parseInt(promoLine.getMasterPortFolioKey())) {
				line.setPromotion(promotion);
				line.setIsMasterLine(true);
				return true;
			}
			if(promoLine.getMasterPortFolioType().equals(CRMConstants.SALESPFTYPE.PRODUCT) &&
					line.getSku().getItem().getItemClass().equals(promotion.getItemClass().getCode()) &&
					line.getSku().getItem().getProduct().getId() ==  Integer.parseInt(promoLine.getMasterPortFolioKey())) {
				line.setPromotion(promotion);
				line.setIsMasterLine(true);
				return true;
			}
			if(promoLine.getMasterPortFolioType().equals(CRMConstants.SALESPFTYPE.CATEGORY) &&
					line.getSku().getItem().getItemClass().equals(promotion.getItemClass()) &&
					line.getSku().getItem().getProduct().getCategory().getId() ==  Integer.parseInt(promoLine.getMasterPortFolioKey())) {
				line.setPromotion(promotion);
				line.setIsMasterLine(true);
				return true;
			}
			if(promoLine.getMasterPortFolioType().equals(CRMConstants.SALESPFTYPE.ITEM) &&
					line.getSku().getItem().getItemClass().equals(promotion.getItemClass()) &&
					line.getSku().getItem().getId() ==  Integer.parseInt(promoLine.getMasterPortFolioKey())) {
				line.setPromotion(promotion);
				line.setIsMasterLine(true);
				return true;
			}
		}
		return false; 
	}
	
	
	@Override
	public void applyPromotions(Sales sales, CRMContext context) {
		IPromotionService promotionService = (IPromotionService)SpringObjectFactory.INSTANCE.getInstance("IPromotionService");
		List<Promotion> promotions = promotionService.getAllPromotionsforType(new FiniteValue(CRMConstants.PROMOTYPE.UPSELLING), sales.getSalesDate(), context);
		if(Utils.isNullList(promotions ) ) return ;
		promotions.forEach(promotion ->    {
			if(isApplicable(promotion,sales))  {
				for (SalesLine line : sales.getSalesLines()) {
					if (matches(line, promotion)){
						if (line.getDiscPercent() < promotion.getPromotedDiscPercent())
							line.setDiscPercent(promotion.getPromotedDiscPercent());
					}
				}
			}
		 });
		
	}
	
	

	
	
}
