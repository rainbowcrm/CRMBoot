package com.rainbow.crm.item.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.CRMAppConfig;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.config.service.ConfigurationManager;
import com.rainbow.crm.document.model.Document;
import com.rainbow.crm.document.service.IDocumentService;
import com.rainbow.crm.feedback.model.FeedBackLine;
import com.rainbow.crm.feedback.service.IFeedBackService;
import com.rainbow.crm.inventory.model.Inventory;
import com.rainbow.crm.inventory.service.IInventoryService;
import com.rainbow.crm.item.dao.ItemImageSQL;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.item.model.ItemAttribute;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.item.model.SkuComplete;
import com.rainbow.crm.item.service.IItemService;
import com.rainbow.crm.logger.Logwriter;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.utils.Utils;

public class SkuCompleteController  extends SkuController{
	
	
	@Override
	public void read() {
		try {
		ModelObject thisObject = getService().getByBusinessKey((CRMModelObject)object, (CRMContext)getContext());
		SkuComplete itemComplete = new SkuComplete((Sku) thisObject);
		List<String > imageURLs = ItemImageSQL.getAllItemImages(itemComplete.getId());
		if(!Utils.isNullList(imageURLs)) {
			String path = CRMAppConfig.INSTANCE.getProperty("doc_server");
			String companyCode = ((CRMContext)getContext()).getLoggedinCompanyCode();
			itemComplete.setImage1URL(path +"/" + companyCode+ "/" + "itemimages/"  + imageURLs.get(0).toString());
			if(imageURLs.size() > 1) 
				itemComplete.setImage2URL(path +"/" + companyCode+ "/" + "itemimages/"  + imageURLs.get(1).toString());
			if(imageURLs.size() > 2) 
				itemComplete.setImage3URL(path +"/" + companyCode+ "/" + "itemimages/"  + imageURLs.get(2).toString());
		}
		IInventoryService inventoryService =(IInventoryService) SpringObjectFactory.INSTANCE.getInstance("IInventoryService");
		CRMContext context =(CRMContext)getContext();
		boolean allowAllDiv = CommonUtil.allowAllDivisionAccess(context);
		List<Inventory> inventoryList ;
		if  (allowAllDiv) {
			inventoryList = inventoryService.getByItem((Sku) thisObject) ;
		} else {
			Inventory inventory = inventoryService.getByItemandDivision((Sku) thisObject, context.getLoggedInUser().getDivision());
			inventoryList = new ArrayList<Inventory>();
			inventoryList.add(inventory);
		}
		itemComplete.setInventory(inventoryList);
		
		IDocumentService docService = (IDocumentService)SpringObjectFactory.INSTANCE.getInstance("IDocumentService");
		List<Document> documents = docService.findAllByItem(((Sku) thisObject).getItem());
		itemComplete.setDocuments(documents);
		
		String profDataHist = ConfigurationManager.getConfig(ConfigurationManager.PROF_DATAHISTORY, context);
		Date fromDate = CommonUtil.getRelativeDate(new FiniteValue(profDataHist));
		IFeedBackService feedBackService =(IFeedBackService)SpringObjectFactory.INSTANCE.getInstance("IFeedBackService");
		List<FeedBackLine> feedBackLines =feedBackService.getLinesforItem(itemComplete.getItem(), context, fromDate, new java.util.Date());
		itemComplete.setFeedBackLines(feedBackLines);
		
		IItemService itemService = (IItemService)SpringObjectFactory.INSTANCE.getInstance("IItemService");
		List<ItemAttribute> itemAttributes = itemService.getAllItemAttributes(((Sku) thisObject).getItem(), context);
		itemComplete.setItemAttributes(itemAttributes);
		
		setObject(itemComplete);
		}catch(Exception ex) {
			Logwriter.INSTANCE.error(ex);
		}
	}

	@Override
	public PageResult submit(ModelObject object, String actionParam) {
		// TODO Auto-generated method stub
		return super.submit(object, actionParam);
	}
	
	
	
}
