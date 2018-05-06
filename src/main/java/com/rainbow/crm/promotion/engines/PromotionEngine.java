package com.rainbow.crm.promotion.engines;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.promotion.model.Promotion;
import com.rainbow.crm.promotion.model.PromotionLine;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.sales.model.SalesLine;
import com.rainbow.crm.sales.service.ISalesService;

public class PromotionEngine {

		
	private static List<AbstractPromotionEngine> availablePromoEngines()
	{
		List<AbstractPromotionEngine> engines = new ArrayList<AbstractPromotionEngine>();
		engines.add( new UPSellingEngine());
		engines.add( new DiscountEngine());
		engines.add( new CrossSellingEngine());
		engines.add( new BundlingEngine());
		return engines;
		
	}
	
	public static void applyPromotions(Sales sales,CRMContext context)
	{
		List<AbstractPromotionEngine> engines = availablePromoEngines();
		engines.forEach( engine ->  { 
			engine.applyPromotions(sales, context);
		} );
		
		ISalesService service = (ISalesService) SpringObjectFactory.INSTANCE.getInstance("ISalesService");
		service.reCalculateTotal(sales, context);
	}
	
	
	
	
}
