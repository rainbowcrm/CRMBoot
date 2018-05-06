package com.rainbow.crm.promotion.engines;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

public class CrossSellingEngine extends AbstractPromotionEngine{

	@Override
	public RadsError searchForUnConsumedPromotions(Sales sales,
			CRMContext context) {
		// TODO Auto-generated method stub
		return null;
	}

	private boolean matchesChild (SalesLine line, PromotionLine promoLine)
	{
			if(promoLine.getChildPortFolioType().equals(CRMConstants.SALESPFTYPE.BRAND) && 
					line.getSku().getItem().getBrand().getId() ==  Integer.parseInt(promoLine.getChildPortFolioKey())) {
				line.setPromotion(promoLine.getPromotion());
				line.setIsChildLine(true);
				return true;
			}
			if(promoLine.getChildPortFolioType().equals(CRMConstants.SALESPFTYPE.PRODUCT) &&
					line.getSku().getItem().getProduct().getId() ==  Integer.parseInt(promoLine.getChildPortFolioKey())) {
				line.setPromotion(promoLine.getPromotion());
				line.setIsChildLine(true);
				return true;
			}
			if(promoLine.getChildPortFolioType().equals(CRMConstants.SALESPFTYPE.CATEGORY) &&
					line.getSku().getItem().getProduct().getCategory().getId() ==  Integer.parseInt(promoLine.getChildPortFolioKey())) {
				line.setPromotion(promoLine.getPromotion());
				line.setIsChildLine(true);
				return true;
			}
			if(promoLine.getChildPortFolioType().equals(CRMConstants.SALESPFTYPE.ITEM) &&
					line.getSku().getItem().getId() ==  Integer.parseInt(promoLine.getChildPortFolioKey())) {
				line.setPromotion(promoLine.getPromotion());
				line.setIsChildLine(true);
				return true;
			}
		return false; 
	}
	
	private PromotionLine matchesMaster(SalesLine line, Promotion promotion)
	{
		
	
		for (PromotionLine promoLine :  promotion.getPromotionLines()) {
			if(promoLine.getMasterPortFolioType().equals(CRMConstants.SALESPFTYPE.BRAND) && 
					line.getSku().getItem().getBrand().getId() ==  Integer.parseInt(promoLine.getMasterPortFolioKey())) {
				line.setPromotion(promotion);
				line.setIsMasterLine(true);
				return promoLine;
			}
			if(promoLine.getMasterPortFolioType().equals(CRMConstants.SALESPFTYPE.PRODUCT) &&
					line.getSku().getItem().getProduct().getId() ==  Integer.parseInt(promoLine.getMasterPortFolioKey())) {
				line.setPromotion(promotion);
				line.setIsMasterLine(true);
				return promoLine;
			}
			if(promoLine.getMasterPortFolioType().equals(CRMConstants.SALESPFTYPE.CATEGORY) &&
					line.getSku().getItem().getProduct().getCategory().getId() ==  Integer.parseInt(promoLine.getMasterPortFolioKey())) {
				line.setPromotion(promotion);
				line.setIsMasterLine(true);
				return promoLine;
			}
			if(promoLine.getMasterPortFolioType().equals(CRMConstants.SALESPFTYPE.ITEM) &&
					line.getSku().getItem().getId() ==  Integer.parseInt(promoLine.getMasterPortFolioKey())) {
				line.setPromotion(promotion);
				line.setIsMasterLine(true);
				return promoLine;
			}
		}
		return null; 
	}
	
	@Override
	public void applyPromotions(Sales sales, CRMContext context) {
		IPromotionService promotionService = (IPromotionService)SpringObjectFactory.INSTANCE.getInstance("IPromotionService");
		List<Promotion> promotions = promotionService.getAllPromotionsforType(new FiniteValue(CRMConstants.PROMOTYPE.CROSSSELL), sales.getSalesDate(), context);
		if(Utils.isNullList(promotions ) ) return ;
		promotions.forEach(promotion ->    {
			if(isApplicable(promotion,sales))  {
				for (SalesLine line : sales.getSalesLines()) {
					PromotionLine promoLine = matchesMaster(line, promotion); 
					if (promoLine != null ){
						for (SalesLine innerLine : sales.getSalesLines()) {
								if (innerLine.getPromotion() == null && matchesChild(innerLine,promoLine)) {
									if (innerLine.getDiscPercent() < promoLine.getPromotedDiscPercent())
										innerLine.setDiscPercent(promoLine.getPromotedDiscPercent());
								}
						}
					}
				}
			}
		 });
	}
	
	

}
