package com.rainbow.crm.profile.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rainbow.crm.common.CRMAppConfig;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.config.service.ConfigurationManager;
import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.document.model.Document;
import com.rainbow.crm.document.service.IDocumentService;
import com.rainbow.crm.feedback.model.FeedBackLine;
import com.rainbow.crm.feedback.service.IFeedBackService;
import com.rainbow.crm.feedback.sql.FeedbackSQLs;
import com.rainbow.crm.inventory.model.Inventory;
import com.rainbow.crm.inventory.service.IInventoryService;
import com.rainbow.crm.item.dao.ItemImageSQL;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.item.model.ItemAttributeSet;
import com.rainbow.crm.item.model.ItemImage;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.item.service.IItemAttributeService;
import com.rainbow.crm.item.service.ISkuService;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.crm.profile.model.CustomerProfile;
import com.rainbow.crm.profile.model.ItemProfile;
import com.rainbow.crm.sales.model.SalesLine;
import com.rainbow.crm.sales.service.ISalesService;
import com.rainbow.crm.saleslead.model.SalesLeadLine;
import com.rainbow.crm.saleslead.service.ISalesLeadService;
import com.rainbow.crm.wishlist.model.WishListLine;
import com.rainbow.crm.wishlist.service.IWishListService;
import com.techtrade.rads.framework.model.graphdata.GaugeChartData;
import com.techtrade.rads.framework.model.graphdata.GaugeChartData.ColorRange;
import com.techtrade.rads.framework.utils.Utils;

public class ItemProfileService implements IItemProfileService{

	 private void loadImage(ItemProfile itemProfile,CRMContext context )
	 {
		 try {
		 	String imgPath = "./resources/images/not-available.png";
			String filePath = CRMAppConfig.INSTANCE.getProperty("doc_server");
			String code = context.getLoggedinCompanyCode();
			ItemImage dbRecord1 = ItemImageSQL.getItemImage(itemProfile.getItem().getId(), 'a');
			if (dbRecord1 != null ) {
				imgPath = filePath + "\\" +  code  + "\\itemimages\\" +  dbRecord1.getFileName();
			}
			itemProfile.setItemImage(imgPath);
		 }catch(Exception ex)
		 {
			 Logwriter.INSTANCE.error(ex);
		 }
	 }
	 
	 public GaugeChartData getItemRatingIndex(Item item,
				Date fromDate, Date toDate, CRMContext context) {
			GaugeChartData chartData = new GaugeChartData();
			String benchMark = ConfigurationManager.getConfig(
					ConfigurationManager.FEEDBACK_RATING_BENCHMARK, context);
			double avgRating = FeedbackSQLs.getItemRatingIndex(
					Utils.getSQLDate(fromDate), Utils.getSQLDate(toDate),
					context.getLoggedinCompany(), item.getId());
			chartData.setTitle("Item Rating Index");
			chartData.setLabel("IRI");
			chartData.setMaxValue(100);
			chartData.setGraphValue((int) (avgRating * 10));
			chartData.setMinorTicks(10);

			int dangerZone = Integer.parseInt(benchMark) / 2 * 10;
			int yellowZone = Integer.parseInt(benchMark) * 10;
			int greenZone = 100;
			ColorRange redRange = chartData.new ColorRange();
			redRange.setColor("red");
			redRange.setFrom(0);
			redRange.setTo(dangerZone);
			chartData.addColorRange(redRange);

			ColorRange yellowRange = chartData.new ColorRange();
			yellowRange.setColor("yellow");
			yellowRange.setFrom(dangerZone);
			yellowRange.setTo(yellowZone);
			chartData.addColorRange(yellowRange);

			ColorRange greenRange = chartData.new ColorRange();
			greenRange.setColor("green");
			greenRange.setFrom(yellowZone);
			greenRange.setTo(greenZone);
			chartData.addColorRange(greenRange);

			return chartData;
		}
	 

	 
	 
	public GaugeChartData getCustomerSatisfactionIndex(Customer customer,
			Date fromDate, Date toDate, CRMContext context) {
		GaugeChartData chartData = new GaugeChartData();
		String benchMark = ConfigurationManager.getConfig(
				ConfigurationManager.FEEDBACK_RATING_BENCHMARK, context);
		double avgRating = FeedbackSQLs.getCustSatisfactionRatingIndex(
				Utils.getSQLDate(fromDate), Utils.getSQLDate(toDate),
				context.getLoggedinCompany(), customer.getId());
		chartData.setTitle("Customer Satisfaction Index");
		chartData.setLabel("CSI");
		chartData.setMaxValue(100);
		chartData.setGraphValue((int) (avgRating * 10));
		chartData.setMinorTicks(10);

		int dangerZone = Integer.parseInt(benchMark) / 2 * 10;
		int yellowZone = Integer.parseInt(benchMark) * 10;
		int greenZone = 100;
		ColorRange redRange = chartData.new ColorRange();
		redRange.setColor("red");
		redRange.setFrom(0);
		redRange.setTo(dangerZone);
		chartData.addColorRange(redRange);

		ColorRange yellowRange = chartData.new ColorRange();
		yellowRange.setColor("yellow");
		yellowRange.setFrom(dangerZone);
		yellowRange.setTo(yellowZone);
		chartData.addColorRange(yellowRange);

		ColorRange greenRange = chartData.new ColorRange();
		greenRange.setColor("green");
		greenRange.setFrom(yellowZone);
		greenRange.setTo(greenZone);
		chartData.addColorRange(greenRange);

		return chartData;
	}
 
	 
	@Override
	public CustomerProfile getCustomerProfile(Customer customer,
			CRMContext context) {
		CustomerProfile custProfile = new CustomerProfile();
		custProfile.setCustomer(customer);
		String profDataHist = ConfigurationManager.getConfig(ConfigurationManager.PROF_DATAHISTORY, context);
		Date fromDate = CommonUtil.getRelativeDate(new FiniteValue(profDataHist));
		Date toDate = new java.util.Date();
		
		IFeedBackService  service = (IFeedBackService) SpringObjectFactory.INSTANCE.getInstance("IFeedBackService");
		List<FeedBackLine> feedBackLines =service.getLinesforCustomer(customer, context, fromDate, toDate);
		custProfile.setFeedBackLines(feedBackLines);
		
		IDocumentService docService = (IDocumentService) SpringObjectFactory.INSTANCE.getInstance("IDocumentService");
		List<Document> docLines = docService.findAllByCustomer(customer);
		custProfile.setDocuments(docLines);
		
		IWishListService wishListService = (IWishListService) SpringObjectFactory.INSTANCE.getInstance("IWishListService");
		List<WishListLine> wishesList = wishListService.getWishesforCustomer(customer, context, fromDate, new java.util.Date()) ;
		custProfile.setOpenWishes(wishesList);
		
		ISalesService  salesService =(ISalesService) SpringObjectFactory.INSTANCE.getInstance("ISalesService");
		
		List<SalesLine> sales = salesService.getSalesForCustomer(customer, context, false, fromDate, new java.util.Date());
		custProfile.setPastSales(sales);
		if(sales != null)
			custProfile.setNoSalesLines(sales.size());
		
		List<SalesLine> returns = salesService.getSalesForCustomer(customer, context, true, fromDate, new java.util.Date());
		if(returns != null)
			  custProfile.setNoReturnLines(returns.size());
		
		Double salesAmount = salesService.getSalesAmountForCustomer(customer, context, false, fromDate, new java.util.Date());
		custProfile.setTotalSales(salesAmount);
		
		Date lastDate = salesService.getLastSaleDateForCustomer(customer, context, false, fromDate,toDate);
		custProfile.setLastSaleOn(lastDate);
		
		ISalesLeadService  salesLeadService =(ISalesLeadService) SpringObjectFactory.INSTANCE.getInstance("ISalesLeadService");
		List<SalesLeadLine> salesLeadLines =  salesLeadService.getSalesLeadLinesforCustomer(customer, context, fromDate, toDate);
		custProfile.setSalesLeadLines(salesLeadLines);
		
		GaugeChartData data = getCustomerSatisfactionIndex(customer, fromDate, toDate, context);
		custProfile.setSatisfactionIndex(data);
		
		
		return custProfile;
		
	}



	@Override
	public ItemProfile getItemProfile(Item item, CRMContext context) {
		ItemProfile itemProfile = new ItemProfile();
		itemProfile.setItem(item);
		
		loadImage(itemProfile, context);
		
		String profDataHist = ConfigurationManager.getConfig(ConfigurationManager.PROF_DATAHISTORY, context);
		Date fromDate = CommonUtil.getRelativeDate(new FiniteValue(profDataHist));
		
		IFeedBackService  service = (IFeedBackService) SpringObjectFactory.INSTANCE.getInstance("IFeedBackService");
		List<FeedBackLine> feedBackLines = service.getLinesforItem(item,context,fromDate,new java.util.Date());
		itemProfile.setCustomerFeedBacks(feedBackLines);

		IDocumentService docService = (IDocumentService) SpringObjectFactory.INSTANCE.getInstance("IDocumentService");
		List<Document> docLines = docService.findAllByItem(item);
		itemProfile.setDocuments(docLines);
		
		ISkuService skuService = (ISkuService) SpringObjectFactory.INSTANCE.getInstance("ISkuService");
		List<Sku> skuList = skuService.getAllByItem(context.getLoggedinCompany(), item.getId());
		if (skuList != null)
		itemProfile.setSkuVariants(skuList.size());
		
		IInventoryService inventoryService = (IInventoryService) SpringObjectFactory.INSTANCE.getInstance("IInventoryService");
		
		List<Inventory> inventoryList = new ArrayList<Inventory> ();
		
		if ( skuList != null ) {
			skuList.forEach(sku ->  {   
				List<Inventory> skuInventory = inventoryService.getByItem(sku);
				inventoryList.addAll(skuInventory);
				
				
			});
		}
		
		itemProfile.setInventory(inventoryList);
		IWishListService wishListService = (IWishListService) SpringObjectFactory.INSTANCE.getInstance("IWishListService");
		List<WishListLine> wishesList = wishListService.getWishesforItem(item, context, fromDate, new java.util.Date()) ;
		itemProfile.setWishList(wishesList);
		
		ISalesService  salesService =(ISalesService) SpringObjectFactory.INSTANCE.getInstance("ISalesService");
		List<SalesLine> sales = salesService.getSalesForItem(item, context, false, fromDate, new java.util.Date());
		itemProfile.setPastSales(sales);
		
		Double totSales = salesService.getTotalSalesAmountForItem(item, context, false, fromDate, new java.util.Date());
		itemProfile.setTotalAmountsSold(totSales != null?totSales.doubleValue():0d);
		
		Long unitsSold = salesService.getUnitsSoldForItem(item, context, false, fromDate, new java.util.Date());
		itemProfile.setUnitsSold(unitsSold!= null ? unitsSold.intValue():0);
		
		Long unitsReturned = salesService.getUnitsSoldForItem(item, context, true, fromDate, new java.util.Date());
		itemProfile.setUnitsReturned(unitsReturned!= null ? unitsReturned.intValue():0);
		
		GaugeChartData data = getItemRatingIndex(item, fromDate, new java.util.Date(), context);
		itemProfile.setSatisfactionIndex(data);
		
		IItemAttributeService attributeService = (IItemAttributeService)SpringObjectFactory.INSTANCE.getInstance("IItemAttributeService");
		ItemAttributeSet  attrSet = attributeService.getByItem(item, context) ;
		itemProfile.setItemAttributes(attrSet.getAttributes());	
		
		return itemProfile;
	}

	public List<FeedBackLine> getCustomerFeedBacks(Item item, CRMContext context) {
		IFeedBackService  service = (IFeedBackService) SpringObjectFactory.INSTANCE.getInstance("IFeedBackService");
		service.getLinesforItem(item,context,null,null);
		return null;
	}

	public List<SalesLine> getPastSales(Item item, CRMContext context) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<SalesLine> getPastReturns(Item item, CRMContext context) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Inventory> getInventory(Item item, CRMContext context) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Document> getDocument(Item item, CRMContext context) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<WishListLine> getWishList(Item item, CRMContext context) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
