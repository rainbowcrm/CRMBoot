package com.rainbow.crm.item.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rainbow.crm.brand.model.Brand;
import com.rainbow.crm.brand.service.IBrandService;
import com.rainbow.crm.common.CRMCRUDController;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.item.model.ItemAttributeSet;
import com.rainbow.crm.item.service.IItemService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.transaction.TransactionResult.Result;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.ui.abstracts.PageResult.ResponseAction;

public class ItemController extends CRMCRUDController{

	public IBusinessService getService() {
		IItemService serv = (IItemService) SpringObjectFactory.INSTANCE.getInstance("IItemService");
		return serv;
	}
	
	
	public Map <String, String > getUOMs() {
		Map<String, String> ans = GeneralSQLs.getDefaultUOMs();
		return ans;
	}
	
	public Map <String, String > getAllBrands() {
		Map <String, String >  map = new HashMap<String,String>();
		IBrandService service = (IBrandService)SpringObjectFactory.INSTANCE.getInstance("IBrandService");
		List<Brand> brandList= service.getAllBrands(((CRMContext)getContext()).getLoggedinCompany());
		brandList.forEach( brand ->  { 
			map.put(String.valueOf( brand.getId()), brand.getName());
		});
		return map;
	}


	@Override
	public PageResult submit(ModelObject object, String actionParam) {
		if("Variants".equalsIgnoreCase(actionParam)) {
			PageResult result = new PageResult ();
			Item item = (Item) object;
			ItemAttributeSet itrSet = new ItemAttributeSet();
			itrSet.setCompany(item.getCompany());
			itrSet.setItem(item);
			result.setNextPageKey("itemattributes");
			result.setObject(itrSet);
			result.setResult(Result.SUCCESS);
			result.setResponseAction(ResponseAction.NEWPAGE);
			return result;
		}
		return super.submit(object, actionParam);
	}
	
	
}
