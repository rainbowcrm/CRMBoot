package com.rainbow.crm.servicerequest.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMDBException;
import com.rainbow.crm.common.CRMTransactionController;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.division.service.IDivisionService;
import com.rainbow.crm.servicerequest.model.ServiceRequest;
import com.rainbow.crm.servicerequest.service.IServiceRequestService;
import com.rainbow.crm.reasoncode.model.ReasonCode;
import com.rainbow.crm.reasoncode.service.IReasonCodeService;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.sales.model.SalesLine;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.TransactionController;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult.Result;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.utils.Utils;

public class ServiceRequestController extends CRMTransactionController{

	public IServiceRequestService getService() {
		IServiceRequestService serv = (IServiceRequestService) SpringObjectFactory.INSTANCE.getInstance("IServiceRequestService");
		return serv;
	}

	public Map <String, String > getAllObjectTypes() {
		Map<String, String> ans = GeneralSQLs.getFiniteValues(CRMConstants.FV_FEEDBACK_ON);
		return ans;
	}
	
	public Map <String, String > getAllCommunicationModes() {
		Map<String, String> ans = GeneralSQLs.getFiniteValues(CRMConstants.FV_COMMUNICATION_MODE);
		return ans;
	}
	
	public Map <String, String > getAllObjects() {
		Map<String, String> ans = new HashMap<String,String>();
		if (getObject() != null ) {
			ServiceRequest serviceRequest = (ServiceRequest) getObject();
			Sales sales = serviceRequest.getSales(); 
			if (sales != null) {		

				for(SalesLine line : sales.getSalesLines()) {

					if(line.getSku() != null ) {
						ans.put(String.valueOf(line.getSku().getId()), line.getSku().getName());
					}
				}
			}
		}
		return ans;
	
	}
	


	
	

	
	

}
