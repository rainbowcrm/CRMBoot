package com.rainbow.crm.distributionorder.service;

import java.util.Date;
import java.util.List;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.ITransactionService;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.distributionorder.model.DistributionOrder;
import com.techtrade.rads.framework.model.abstracts.RadsError;

public interface IDistributionOrderService extends ITransactionService{

	public DistributionOrder createDOfromSalesOrder(Sales sales, CRMContext context);
	
	public List<RadsError> pick(DistributionOrder order, CRMContext context);
	
	public List<RadsError> pack(DistributionOrder order, CRMContext context);
	
	public List<RadsError> startShipping(DistributionOrder order, CRMContext context);
	
	public List<RadsError> endShipping(DistributionOrder order, CRMContext context);
	
	public String generateShippingLabel(DistributionOrder order, CRMContext context);

}
