package com.rainbow.crm.item.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMTransactionController;
import com.rainbow.crm.common.ITransactionService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.item.model.ItemAttribute;
import com.rainbow.crm.item.model.ItemAttributeSet;
import com.rainbow.crm.item.service.IItemAttributeService;
import com.rainbow.crm.item.service.IItemService;
import com.rainbow.crm.product.model.ProductAttribute;
import com.rainbow.crm.product.service.IProductFAQService;
import com.rainbow.crm.product.service.IProductService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.transaction.TransactionResult.Result;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

public class ItemAttributeController extends CRMTransactionController {

	@Override
	public ITransactionService getService() {
		IItemAttributeService itemAttributeService  =(IItemAttributeService) SpringObjectFactory.INSTANCE.getInstance("IItemAttributeService");
		return itemAttributeService;
	}

	public Map<String,String> getAttributes() {
		ItemAttributeSet attributeSet =  (ItemAttributeSet)getObject();
		Map<String, String> ans  =new HashMap();
		if (attributeSet != null) {
			CRMContext context = (CRMContext)getContext();
			IItemService itemService = (IItemService)SpringObjectFactory.INSTANCE.getInstance("IItemService");
			Item item = itemService.getByName(context.getLoggedinCompany(), attributeSet.getItem().getName());
			if (item != null )  {
				IProductFAQService productService = (IProductFAQService)SpringObjectFactory.INSTANCE.getInstance("IProductFAQService");
				List<ProductAttribute> attributes = productService.getAllAttributes(item.getProduct(), context) ;
				attributes.forEach(attribute ->  { 
					ans.put(String.valueOf( attribute.getId()),attribute.getAttribute());
				});
			}
		}
		return ans;
	}
	
	@Override
	public PageResult read() {
		PageResult result = new PageResult();
		ItemAttributeSet attributeSet =  (ItemAttributeSet)getObject();
		/*IItemService itemService = (IItemService)SpringObjectFactory.INSTANCE.getInstance("IItemService");
		Item item = itemService.getByName(((CRMContext) getContext()).getLoggedinCompany(), attributeSet.getItem().getName()) ;
		if (item !=null) {
			List<ItemAttribute> attributes = itemService.getAllItemAttributes(item, (CRMContext) getContext());
			attributeSet.setAttributes(attributes);
			attributeSet.setCompany(item.getCompany());
			setObject(attributeSet);
			result.setResult(Result.SUCCESS);
			result.setObject(attributeSet);
		}*/
		IItemAttributeService itemService = (IItemAttributeService)SpringObjectFactory.INSTANCE.getInstance("IItemAttributeService");
		attributeSet = itemService.getByItem(attributeSet.getItem(), (CRMContext) getContext());
		setObject(attributeSet);
		result.setResult(Result.SUCCESS);
		result.setObject(attributeSet);
		return result;
	}

	@Override
	public PageResult submit(ModelObject object, String actionParam) {
		// TODO Auto-generated method stub
		return super.submit(object, actionParam);
	}
	
	
	
	

}
