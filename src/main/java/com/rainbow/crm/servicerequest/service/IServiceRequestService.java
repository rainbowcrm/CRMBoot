package com.rainbow.crm.servicerequest.service;

import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.ITransactionService;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.feedback.model.FeedBackLine;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.servicerequest.model.ServiceRequest;
import com.rainbow.crm.servicerequest.model.ServiceRequestLine;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.graphdata.BarChartData;
import com.techtrade.rads.framework.model.graphdata.GaugeChartData;
import com.techtrade.rads.framework.model.graphdata.PieChartData;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

public interface IServiceRequestService extends ITransactionService{

	


	public ServiceRequest getBySale(String docNo, CRMContext context) ;

	public List<ServiceRequestLine> getLinesforItem(Item item, CRMContext  context, Date fromDate, Date toDate);

	public List<ServiceRequestLine> getLinesforCustomer(Customer customer, CRMContext  context, Date fromDate, Date toDate);

	public PageResult completeServiceRequest( ServiceRequest request, CRMContext context);

	public PageResult rejectServiceRequest( ServiceRequest request, CRMContext context);

}
