package com.rainbow.crm.common;

import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.config.service.ConfigurationManager;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.item.service.IItemService;
import com.rainbow.crm.product.model.Product;
import com.rainbow.crm.product.model.ProductFAQSet;
import com.rainbow.crm.product.model.ProductPriceRange;
import com.rainbow.crm.product.service.IProductFAQService;
import com.rainbow.crm.product.service.IProductService;
import com.techtrade.rads.framework.utils.Utils;

public class ItemUtil {

	public static double getRetailPrice(Sku sku)
	{
		if(sku == null || sku.getItem() == null) return  0;
		String priceFrom = ConfigurationManager.getConfig(ConfigurationManager.FETCH_PRICESFROM, sku.getCompany().getId());
		if(CRMConstants.PRICE_SOURCES.SKU.equalsIgnoreCase(priceFrom))
			return sku.getRetailPrice();
		else
			return sku.getItem().getRetailPrice();	
	}
	
	public static double getRetailPrice(Item item)
	{
		if(item != null )
			return item.getRetailPrice();
		else
			return  0;
	}
	
	public static Product getProduct(CRMContext context,Product product)
	{
		IProductService prodService = (IProductService)SpringObjectFactory.INSTANCE.getInstance("IProductService");
		if (product == null) return null;
		if(product.getId() > 0 )
			product = (Product)prodService.getById(product.getId());
		else if (product.getBK() != null )
			product = (Product)prodService.getByBusinessKey(product, context);
		
		return product ;
		
	}
	
	public static Item getItem(CRMContext context , Item item)
	{
		IItemService service = (IItemService)SpringObjectFactory.INSTANCE.getInstance("IItemService");
		if (item == null) return null;
		if(item.getId() > 0 )
			item = (Item)service.getById(item.getId());
		else if (item.getBK() != null )
			item = (Item)service.getByBusinessKey(item, context);
		
		return item ;
		
	}
	
	public static FiniteValue getItemClass(CRMContext context, Product product , double retailPrice )
	{
		IProductFAQService productFAQService = (IProductFAQService)SpringObjectFactory.INSTANCE.getInstance("IProductFAQService");
		ProductFAQSet prodFAQ = productFAQService.getByProduct(product, context);
		if ( prodFAQ == null ||  Utils.isNullList(prodFAQ.getProductPriceRanges()))  {
			return null;
		}
		Double economicMax =null;
		Double lowMedMax =null;
		Double upMedMax = null;
		for (ProductPriceRange prodPriceRange :  prodFAQ.getProductPriceRanges() )  { 
			if (prodPriceRange.getItemClass() != null &&
						CRMConstants.ITEM_CLASS.ECONOMIC.equalsIgnoreCase(prodPriceRange.getItemClass().getCode())) {
				economicMax =  prodPriceRange.getMaxPrice();
			}else if (prodPriceRange.getItemClass() != null &&
					CRMConstants.ITEM_CLASS.LOWER_MEDIUM.equalsIgnoreCase(prodPriceRange.getItemClass().getCode())) {
				lowMedMax =  prodPriceRange.getMaxPrice();
		    }else if (prodPriceRange.getItemClass() != null &&
						CRMConstants.ITEM_CLASS.UPPER_MEDIUM.equalsIgnoreCase(prodPriceRange.getItemClass().getCode())) {
		    	upMedMax =  prodPriceRange.getMaxPrice();
			}  
			if(economicMax != null && retailPrice <= economicMax.doubleValue())
				return new FiniteValue(CRMConstants.ITEM_CLASS.ECONOMIC);
			else if (lowMedMax != null && retailPrice <= lowMedMax.doubleValue())
				return new FiniteValue(CRMConstants.ITEM_CLASS.LOWER_MEDIUM);
			else if (upMedMax != null && retailPrice <= upMedMax.doubleValue())
				return new FiniteValue(CRMConstants.ITEM_CLASS.UPPER_MEDIUM);
			else if (upMedMax != null && retailPrice > upMedMax.doubleValue())
				return new FiniteValue(CRMConstants.ITEM_CLASS.TOP_END);
		}
		
		return null;
	}
}
