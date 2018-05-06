package com.rainbow.crm.saleslead.service;

import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.activation.FileDataSource;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.ITransactionService;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.saleslead.model.SalesLead;
import com.rainbow.crm.saleslead.model.SalesLeadExtended;
import com.rainbow.crm.saleslead.model.SalesLeadLine;
import com.rainbow.crm.user.model.User;
import com.rainbow.crm.wishlist.model.WishListLine;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.graphdata.BarChartData;
import com.techtrade.rads.framework.model.graphdata.PieChartData;
import com.techtrade.rads.framework.model.transaction.TransactionResult;

public interface ISalesLeadService extends ITransactionService{

	public int getItemSaleQuantity(Sku item, Date from, Date to,Division division ) ;
	
	public List<RadsError> startSalesCycle(SalesLead salesLead) ;
	
	public List<RadsError> sendEmail(SalesLead salesLead,CRMContext context,String realPath) ;
	
	public List<RadsError> sendEmailWithQuote(SalesLead salesLead,CRMContext context,String realPath,FileDataSource dataSource) ;
	
	public SalesLeadExtended getSalesLeadWithExtension( int leadId,CRMContext context) ;
	
	public byte[] printQuotation(SalesLead lead) ;
	
	public List<SalesLeadLine> getSalesLeadLinesforCustomer(Customer customer , CRMContext  context , Date fromDate, Date toDate) ;
	
	public TransactionResult generateSalesOrder(SalesLead lead, CRMContext context);
	
	
}
