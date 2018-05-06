package com.rainbow.crm.lookups;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.database.LoginSQLs;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.ui.abstracts.ILookupService;
import com.techtrade.rads.framework.ui.abstracts.UIPage;

public class LookupSalesPortfolioKeys implements ILookupService{

	String keyType;
	
	@Override
	public Map<String,String> lookupData(IRadsContext ctx, String searchString,
			int from, int noRecords, String lookupParam,List<String > additionalFields) {
		Map<String,String> ans = new LinkedHashMap<String,String> ();
		ILookupService lookup ;
		if(CRMConstants.SALESPFTYPE.CATEGORY.equals(lookupParam)) {
			lookup = new LookupCategories();
			return lookup.lookupData(ctx, searchString, from, noRecords, lookupParam,additionalFields) ;
		}
		if(CRMConstants.SALESPFTYPE.BRAND.equals(lookupParam)) {
			lookup = new LookupBrands();
			return lookup.lookupData(ctx, searchString, from, noRecords, lookupParam,additionalFields) ;
		}
		if(CRMConstants.SALESPFTYPE.PRODUCT.equals(lookupParam)) {
			lookup = new LookupProducts();
			return lookup.lookupData(ctx, searchString, from, noRecords, lookupParam,additionalFields) ;
			
		}if(CRMConstants.SALESPFTYPE.ITEM.equals(lookupParam)) {
			lookup = new LookupItems();
			return lookup.lookupData(ctx, searchString, from, noRecords, lookupParam,additionalFields) ;
		}
		return null;
	}

	@Override
	public IRadsContext generateContext(HttpServletRequest request,UIPage page) {
		return CommonUtil.generateContext(request,page);
	}
	
	

}
