package com.rainbow.crm.sales.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.ITransactionService;
import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.product.model.Product;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.sales.model.SalesLine;
import com.rainbow.crm.user.model.User;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

public interface ISalesService extends ITransactionService{
	
	public TransactionResult  emailInvoice(Sales sales,CRMContext context) ;
	
	public byte[] printInvoice(Sales sales,CRMContext context) ;
	
	public int getItemSaleQuantity(Item item, Date from, Date to,Division division ) ;
	
	public Map getItemSoldQtyByProduct(Product product, Date from, Date to, Division division , String itemClass ) ;
	
	public String generateInvoice(Sales  sales,CRMContext context) ;
	
	public int getSalesManSaleQuantity(User user, Date from, Date to,Division division ) ;
	
	public int getTotalSaleQuantity(Date from, Date to,Division division ) ;
	
	public int getTerritorySaleQuantity(int territory, Date from, Date to,Division division ) ;
	
	public int getBrandSaleQuantity(int brandId, Date from, Date to,Division division ) ;
	
	public int getProductSaleQuantity(int productId, Date from, Date to,Division division ) ;
	
	public int getCategorySaleQuantity(int categoryId, Date from, Date to,Division division ) ;
	
	public int getDivisionSaleQuantity(Date from, Date to,Division division ) ;
	
	public Sales getByBillNumberforReturn(Division division , String billNumber) ;
	
	public List<Sales> getNonAlertedSalesFeedBack(int company, int interval);
	
	public void reCalculateTotal (Sales sales, CRMContext contex) ;
	
	public List<SalesLine> getSalesForItem(Item item, CRMContext context, boolean returns, Date from, Date to);
	
	public Double getTotalSalesAmountForItem(Item item, CRMContext context, boolean returns, Date from, Date to);
	
	public Long getUnitsSoldForItem(Item item, CRMContext context, boolean returns, Date from, Date to);
	
	public List<SalesLine> getSalesForCustomer(Customer customer, CRMContext context, boolean isReturn, Date from, Date to);
	
	public Date getLastSaleDateForCustomer(Customer customer, CRMContext context, boolean isReturn, Date from, Date to);

	public Double getSalesAmountForCustomer(Customer customer, CRMContext context, boolean isReturn, Date from, Date to);
}
