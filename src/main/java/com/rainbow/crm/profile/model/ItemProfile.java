package com.rainbow.crm.profile.model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.config.service.ConfigurationManager;
import com.rainbow.crm.document.model.Document;
import com.rainbow.crm.feedback.model.FeedBackLine;
import com.rainbow.crm.inventory.model.Inventory;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.item.model.ItemAttribute;
import com.rainbow.crm.sales.model.SalesLine;
import com.rainbow.crm.wishlist.model.WishListLine;
import com.techtrade.rads.framework.annotations.RadsPropertySet;
import com.techtrade.rads.framework.model.graphdata.GaugeChartData;

public class ItemProfile extends CRMModelObject{
	Item item;
	double avgRating;
	String itemImage;
	
	List<FeedBackLine> customerFeedBacks; 
	List <SalesLine> pastSales;
	List <SalesLine> pastReturns;
	List<Inventory> inventory;
	List<Document> documents;
	List<WishListLine> wishList;
	List<ItemAttribute> itemAttributes;
	
	GaugeChartData satisfactionIndex;
	
	double unitsSold;
	double unitsReturned;
	double totalAmountsSold;
	int skuVariants;
	
	
	
	
	public double getUnitsSold() {
		return unitsSold;
	}
	public void setUnitsSold(double unitsSold) {
		this.unitsSold = unitsSold;
	}
	public double getUnitsReturned() {
		return unitsReturned;
	}
	public void setUnitsReturned(double unitsReturned) {
		this.unitsReturned = unitsReturned;
	}
	
	public String getTotalAmountsSoldInCurrency() {
		DecimalFormat decFormat = new DecimalFormat("#,##0.00");
		String currency = ConfigurationManager.getConfig(ConfigurationManager.CURRENCY, item.getCompany().getId());
		return decFormat.format(totalAmountsSold) + " " + currency;
	}
	
	public double getTotalAmountsSold() {
		return totalAmountsSold;
	}
	public void setTotalAmountsSold(double totalAmountsSold) {
		this.totalAmountsSold = totalAmountsSold;
	}
	public int getSkuVariants() {
		return skuVariants;
	}
	public void setSkuVariants(int skuVariants) {
		this.skuVariants = skuVariants;
	}
	@RadsPropertySet(isBK =true, isPK=true)
	public Item getItem() {
		return item;
	}
	@RadsPropertySet(isBK =true, isPK=true)
	public void setItem(Item item) {
		this.item = item;
	}
	public double getAvgRating() {
		return avgRating;
	}
	public void setAvgRating(double avgRating) {
		this.avgRating = avgRating;
	}
	
	public List<FeedBackLine> getCustomerFeedBacks() {
		return customerFeedBacks;
	}
	public void setCustomerFeedBacks(List<FeedBackLine> customerFeedBacks) {
		this.customerFeedBacks = customerFeedBacks;
	}
	public List<SalesLine> getPastSales() {
		return pastSales;
	}
	public void setPastSales(List<SalesLine> pastSales) {
		this.pastSales = pastSales;
	}
	public List<SalesLine> getPastReturns() {
		return pastReturns;
	}
	public void setPastReturns(List<SalesLine> pastReturns) {
		this.pastReturns = pastReturns;
	}
	public List<Inventory> getInventory() {
		return inventory;
	}
	public void setInventory(List<Inventory> inventory) {
		this.inventory = inventory;
	}
	public List<Document> getDocuments() {
		return documents;
	}
	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}
	
	public List<WishListLine> getWishList() {
		return wishList;
	}
	public void setWishList(List<WishListLine> wishList) {
		this.wishList = wishList;
	}
	
	
	
	public List<ItemAttribute> getItemAttributes() {
		return itemAttributes;
	}
	public void setItemAttributes(List<ItemAttribute> itemAttributes) {
		this.itemAttributes = itemAttributes;
	}
	public void addCustomerFeedBack(FeedBackLine document) {
		if (customerFeedBacks == null)
			customerFeedBacks = new ArrayList<FeedBackLine>();
		customerFeedBacks.add(document);
		
	}
	public void addPastSale(SalesLine document) {
		if (pastSales == null)
			pastSales = new ArrayList<SalesLine>();
		pastSales.add(document);
		
	}
	public void addPastReturn(SalesLine document) {
		if (pastReturns == null)
			pastReturns = new ArrayList<SalesLine>();
		pastReturns.add(document);
		
	}
	public void addDocument(Document document) {
		if (documents == null)
			documents = new ArrayList<Document>();
		documents.add(document);
		
	}
	public void addInventory(Inventory document) {
		if (inventory == null)
			inventory = new ArrayList<Inventory>();
		inventory.add(document);
	}
	public void addWishList(WishListLine document) {
		if (wishList == null)
			wishList = new ArrayList<WishListLine>();
		wishList.add(document);
	}
	public String getItemImage() {
		return itemImage;
	}
	public void setItemImage(String itemImage) {
		this.itemImage = itemImage;
	}
	public GaugeChartData getSatisfactionIndex() {
		return satisfactionIndex;
	}
	public void setSatisfactionIndex(GaugeChartData satisfactionIndex) {
		this.satisfactionIndex = satisfactionIndex;
	}
	
	

}
