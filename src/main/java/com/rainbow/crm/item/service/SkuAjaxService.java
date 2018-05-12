package com.rainbow.crm.item.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.config.service.ConfigurationManager;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.crm.salesportfolio.model.SalesPortfolio;
import com.rainbow.crm.salesportfolio.service.ISalesPortfolioService;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.IAjaxLookupService;
import com.techtrade.rads.framework.utils.Utils;

public class SkuAjaxService implements IAjaxLookupService {

	@Override
	public String lookupValues(Map<String, String> searchFields,
			IRadsContext ctx) {
		CRMContext context = (CRMContext) ctx;String code = searchFields.get("Code");
		String barCode = searchFields.get("Barcode");
		String itName = searchFields.get("Name");
		String divisionId = searchFields.get("Division");
		String priceSource = ConfigurationManager.getConfig(ConfigurationManager.FETCH_PRICESFROM, ((CRMContext)ctx).getLoggedinCompany());
		
		ISkuService service = (ISkuService) SpringObjectFactory.INSTANCE
				.getInstance("ISkuService");
		Sku item = null;
		if (!Utils.isNull(code)) {
			item = service.getByCode(context.getLoggedinCompany(), code);
		} else if (!Utils.isNull(barCode)) {
			item = service.getByBarCode(context.getLoggedinCompany(), barCode);
		}else if (!Utils.isNull(itName)) {
			item = service.getByName(context.getLoggedinCompany(), itName);
		}  
		try {
			if (item != null) {
				JSONObject json = new JSONObject();
				json.put("Code", item.getCode());
				json.put("Barcode", item.getBarcode());
				json.put("Name", item.getName());
				json.put("Color", item.getColor());
				json.put("Size", item.getSize());
				json.put("Specification", item.getSpecification());
				if(item.getPurchasePrice()!= null &&  item.getPurchasePrice().doubleValue() > 0)
					json.put("PurchasePrice", item.getPurchasePrice());
				else
					json.put("RetailPrice", item.getItem().getPurchasePrice());
				if(item.getRetailPrice()!= null &&  item.getRetailPrice().doubleValue() > 0 && CRMConstants.PRICE_SOURCES.SKU.equals(priceSource))
					json.put("RetailPrice", item.getRetailPrice());
				else
					json.put("RetailPrice", item.getItem().getRetailPrice());
				if (Utils.isPositiveInt(divisionId)) {
					JSONArray ar = getSalesMenforSKU(
							Integer.parseInt(divisionId), item, ctx);
					json.put("SalesAssociates", ar);
				}
				return json.toString();
			}
		} catch (Exception ex) {
			Logwriter.INSTANCE.error(ex);
		}

		return null;
	}

	private JSONArray getSalesMenforSKU(int divisionId, Sku sku,
			IRadsContext ctx) throws Exception {
		JSONArray result = new JSONArray();
		ISalesPortfolioService service = (ISalesPortfolioService) SpringObjectFactory.INSTANCE
				.getInstance("ISalesPortfolioService");
		List<SalesPortfolio> portfolios = service.getUsersforItem(sku,
				divisionId, new java.util.Date());
		portfolios.forEach((portfolio) -> {
			try {
				JSONObject state = new JSONObject();
				state.put("value", portfolio.getUser().getUserId());
				state.put("text", portfolio.getUser().getUserId());
				result.put(state);
			} catch (Exception ex) {
				Logwriter.INSTANCE.error(ex);
			}
		});
		return result;
	}

	@Override
	public IRadsContext generateContext(HttpServletRequest request) {
		return CommonUtil.generateContext(request,null);
	}

}
