package com.rainbow.crm.wishlist.service;

import java.util.Date;
import java.util.List;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.ITransactionService;
import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.inventory.model.InventoryUpdateObject;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.wishlist.model.WishList;
import com.rainbow.crm.wishlist.model.WishListLine;
import com.techtrade.rads.framework.model.abstracts.RadsError;

public interface IWishListService extends ITransactionService{

	
	public int getItemSaleQuantity(Sku item, Date from, Date to,Division division ) ;
	
	public void generateSalesLead(InventoryUpdateObject invObject,String reason);
	
	public List<WishListLine> getWishesforItem(Item item , CRMContext  context , Date fromDate, Date toDate) ;
	
	public List<WishListLine> getWishesforCustomer(Customer customer , CRMContext  context , Date fromDate, Date toDate) ;

}
