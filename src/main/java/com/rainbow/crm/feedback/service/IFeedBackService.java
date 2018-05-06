package com.rainbow.crm.feedback.service;

import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.ITransactionService;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.feedback.model.FeedBack;
import com.rainbow.crm.feedback.model.FeedBackLine;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.graphdata.BarChartData;
import com.techtrade.rads.framework.model.graphdata.GaugeChartData;
import com.techtrade.rads.framework.model.graphdata.PieChartData;

public interface IFeedBackService extends ITransactionService{

	
	public PieChartData getPositiveFeedBacksByReason(Division division,Date fromDate, Date toDate, CRMContext context,FiniteValue finiteValue);
	
	
	public PieChartData getNegativeFeedBacksByReason(Division division,Date fromDate, Date toDate, CRMContext context,FiniteValue finiteValue);
	
	public GaugeChartData getCustomerSatisfactionIndex(Division division,Date fromDate, Date toDate, CRMContext context,FiniteValue finiteValue);
	
	public FeedBack getBySale(String docNo, CRMContext context) ;
	
	public BarChartData getFeedBackValue(Division division,Date fromDate, Date toDate, CRMContext context);
	
	public List<FeedBackLine> getLinesforItem(Item item, CRMContext  context, Date fromDate, Date toDate);
	
	public List<FeedBackLine> getLinesforCustomer(Customer customer, CRMContext  context, Date fromDate, Date toDate);
	

}
