package com.rainbow.crm.distributionorder.controller;

import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.carrier.model.Carrier;
import com.rainbow.crm.carrier.service.ICarrierService;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMDBException;
import com.rainbow.crm.common.CRMTransactionController;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.division.service.IDivisionService;
import com.rainbow.crm.distributionorder.model.DistributionOrder;
import com.rainbow.crm.distributionorder.service.IDistributionOrderService;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.sales.service.ISalesService;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.TransactionController;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult.Result;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.ui.abstracts.UIPage;
import com.techtrade.rads.framework.utils.Utils;

public class DistributionOrderController extends CRMTransactionController{
	
	ServletContext ctx;
	HttpServletResponse resp;
	

	public IDistributionOrderService getService() {
		IDistributionOrderService serv = (IDistributionOrderService) SpringObjectFactory.INSTANCE.getInstance("IDistributionOrderService");
		return serv;
	}

	@Override
	public IRadsContext generateContext(HttpServletRequest request,HttpServletResponse response,UIPage page) {
		ctx =  request.getServletContext() ;
		resp = response ;
		return CommonUtil.generateContext(request, response,page);
	}
	
	public boolean isReleased() {
		DistributionOrder dOrder = (DistributionOrder)object ;
		return ( dOrder.getStatus().equals(CRMConstants.DO_STATUS.RELEASED) || dOrder.getStatus().equals(CRMConstants.DO_STATUS.PICKING)) ;
	}
	public boolean isPicked() {
		DistributionOrder dOrder = (DistributionOrder)object ;
		return ( dOrder.getStatus().equals(CRMConstants.DO_STATUS.PICKED)) ;
	}
	
	public boolean isPacking() {
		DistributionOrder dOrder = (DistributionOrder)object ;
		return ( dOrder.getStatus().equals(CRMConstants.DO_STATUS.PACKING) || dOrder.getStatus().equals(CRMConstants.DO_STATUS.PACKD) ) ;
	}
	
	public boolean isShipping() {
		DistributionOrder dOrder = (DistributionOrder)object ;
		return ( dOrder.getStatus().equals(CRMConstants.DO_STATUS.SHIPPING) ) ;
	}
	

	public Map <String, String > getAllCarriers() {
		Map<String, String> ans = new LinkedHashMap<String, String> ();
		ICarrierService service =(ICarrierService) SpringObjectFactory.INSTANCE.getInstance("ICarrierService");
		List<Carrier> carriers = service.getAllCarriers(((CRMContext)getContext()).getLoggedinCompany());
		if (!Utils.isNullList(carriers)) {
			for (Carrier carrier : carriers) {
				ans.put(String.valueOf(carrier.getId()), carrier.getCode() + " " + carrier.getName());
			}
		}
		return ans;
	}
	
	
	@Override
	public PageResult submit(ModelObject object, String actionParam) {
		DistributionOrder order = (DistributionOrder)object;
		IDistributionOrderService  service = getService() ;
		service.adaptfromUI((CRMContext) getContext(), order);
		List<RadsError> errors = null;
		if ("pick".equals(actionParam))
			errors =service.pick(order, (CRMContext) getContext());
		else if ("pack".equals(actionParam))
			errors = service.pack(order, (CRMContext) getContext());
		else if ("ship".equals(actionParam))
			errors =service.startShipping(order, (CRMContext) getContext());
		else if ("shipped".equals(actionParam))
			errors = service.endShipping(order, (CRMContext) getContext());

		DistributionOrder	savedOrder = (DistributionOrder)populateFullObjectfromPK(object);
		if (!Utils.isNullList(errors)) {
			savedOrder.setCarrier(order.getCarrier());
			savedOrder.setShipmentRefNumber(order.getShipmentRefNumber());
			savedOrder.setShippingDate(order.getShippingDate());
			savedOrder.setShippingCharges(order.getShippingCharges());
		}
		PageResult newResult = new PageResult();
		newResult.setObject(savedOrder);
		newResult.setErrors(errors);
		return newResult;
	}

	@Override
	public PageResult print() {
		PageResult result  = new PageResult();
		try {
		IDistributionOrderService doService = getService();
		String htmlData = doService.generateShippingLabel((DistributionOrder) object,(CRMContext)getContext());
        OutputStream responseOutputStream = resp.getOutputStream();
        
        resp.setContentType("application/html");
		resp.setHeader("Content-Disposition","attachment; filename=lbl.html" );
        responseOutputStream.write(htmlData.getBytes());
        responseOutputStream.close();
        result.setResponseAction(PageResult.ResponseAction.FILEDOWNLOAD);
		}catch(Exception ex) {
			Logwriter.INSTANCE.error(ex);
		}
		return result;
	}
	
	
	

	
	

}
