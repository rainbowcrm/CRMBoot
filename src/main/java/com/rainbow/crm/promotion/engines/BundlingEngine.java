package com.rainbow.crm.promotion.engines;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

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

public class BundlingEngine extends AbstractPromotionEngine{
	
	Map<PromotionLine , List<SalesLine>> bundles = new HashMap<> (); 

	@Override
	public RadsError searchForUnConsumedPromotions(Sales sales,
			CRMContext context) {
		// TODO Auto-generated method stub
		return null;
	}
	private boolean matches (SalesLine line, PromotionLine promoLine)
	{
			if(line.getPromotion() != null) return false; 
			if(promoLine.getMasterPortFolioType().equals(CRMConstants.SALESPFTYPE.BRAND) && 
					line.getSku().getItem().getBrand().getId() ==  Integer.parseInt(promoLine.getMasterPortFolioKey())) {
				
				return true;
			}
			if(promoLine.getMasterPortFolioType().equals(CRMConstants.SALESPFTYPE.PRODUCT) &&
					line.getSku().getItem().getProduct().getId() ==  Integer.parseInt(promoLine.getMasterPortFolioKey())) {
				
				return true;
			}
			if(promoLine.getMasterPortFolioType().equals(CRMConstants.SALESPFTYPE.CATEGORY) &&
					line.getSku().getItem().getProduct().getCategory().getId() ==  Integer.parseInt(promoLine.getMasterPortFolioKey())) {
				
				return true;
			}
			if(promoLine.getMasterPortFolioType().equals(CRMConstants.SALESPFTYPE.ITEM) &&
					line.getSku().getItem().getId() ==  Integer.parseInt(promoLine.getMasterPortFolioKey())) {
				
				return true;
			}
		return false; 
	}
	
	private void addToBundle(PromotionLine promotionLine, SalesLine line)
	{
		List<SalesLine> lineList = bundles.get(promotionLine) ;
		if (lineList == null)
			lineList = new ArrayList<>();
		if(promotionLine.getRequiredQty() > lineList.size()) {
			line.setPromotion(promotionLine.getPromotion());
			line.setIsMasterLine(true);
			lineList.add(line);
			bundles.put(promotionLine, lineList) ;
		}
		
	}
	
	private boolean canBeApplied(PromotionLine promoLine, List<SalesLine> lines)
	{
		AtomicInteger qty = new AtomicInteger();
		lines.forEach( line ->   {  
			qty.addAndGet(line.getQty());
		} );
		
		if (promoLine.getRequiredQty() <= qty.get()) return true ;
		return false; 
	}
	
	private void applyPromotion(PromotionLine promoLine ,List<SalesLine> lines ) 
	{
		DecimalFormat df = new DecimalFormat("#.###");
		Promotion promotion =  promoLine.getPromotion();
		double totalPrice = 0;
		for (SalesLine line : lines) {  
			totalPrice += line.getUnitPrice();
		} 
		
		if(promotion.getBundlePricing().equals(CRMConstants.BUNDLE_PRICING.FIXED_PRICE))  {
			double fixedPrice = promotion.getBundlePrice();
			double totalPricePer = (100  * fixedPrice) / totalPrice ;
			double indDiscPer = (100 - totalPricePer) ;
			lines.forEach( line ->   {  
					line.setDiscPercent(Double.valueOf(df.format(indDiscPer)));
			} );
		}
	}
	
	private void checkAndApply() {
		Iterator it = bundles.keySet().iterator() ;
		while(it.hasNext()) {
			PromotionLine key = (PromotionLine) it.next() ;
			List<SalesLine> lineList = bundles.get(key);
			if(canBeApplied(key, lineList)) {
				applyPromotion(key, lineList);
			}
		}
	}
	
	@Override
	public void applyPromotions(Sales sales, CRMContext context) {
		IPromotionService promotionService = (IPromotionService)SpringObjectFactory.INSTANCE.getInstance("IPromotionService");
		List<Promotion> promotions = promotionService.getAllPromotionsforType(new FiniteValue(CRMConstants.PROMOTYPE.BUNDLING), sales.getSalesDate(), context);
		if(Utils.isNullList(promotions ) ) return ;
		promotions.forEach(promotion ->    {
			if(isApplicable(promotion,sales))  {
				promotion.getPromotionLines().forEach( promotionLine ->  { 
					for (SalesLine line : sales.getSalesLines()) {
						if (matches( line,promotionLine) ) {
							 addToBundle(promotionLine,line);
						}
					}
				}  );
				
			}
		 });		
		checkAndApply();
	}
	
}
