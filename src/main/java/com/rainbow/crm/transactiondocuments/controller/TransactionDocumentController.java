package com.rainbow.crm.transactiondocuments.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMGeneralController;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.distributionorder.model.DistributionOrder;
import com.rainbow.crm.distributionorder.service.IDistributionOrderService;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.sales.service.ISalesService;
import com.rainbow.crm.transactiondocuments.model.TransactionDocument;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.GeneralController;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.transaction.TransactionResult.Result;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

public class TransactionDocumentController extends CRMGeneralController{

	@Override
	public PageResult submit(ModelObject object) {
		PageResult result = new PageResult();
		TransactionDocument transDoc= (TransactionDocument)object ;
		if("SalesInvoice".equalsIgnoreCase(transDoc.getDocType())) {
			ISalesService service = (ISalesService)SpringObjectFactory.INSTANCE.getInstance("ISalesService") ;
			Sales sales = (Sales) service.getById(transDoc.getPk());
			String doc = service.generateInvoice(sales, (CRMContext) getContext());
			transDoc.setDocument(doc);		
		}else if("ShippingLabel".equalsIgnoreCase(transDoc.getDocType())) {
			IDistributionOrderService service = (IDistributionOrderService)SpringObjectFactory.INSTANCE.getInstance("IDistributionOrderService") ;
			DistributionOrder order = (DistributionOrder)service.getById(transDoc.getPk());
			String doc = service.generateShippingLabel(order,(CRMContext) getContext());
			transDoc.setDocument(doc);
		}
		result.setResult(Result.SUCCESS);
		return result;
	}
	
	
}
