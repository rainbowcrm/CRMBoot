package com.rainbow.crm.servicerequest.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMListController;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.servicerequest.model.ServiceRequest;
import com.rainbow.crm.servicerequest.service.IServiceRequestService;
import com.rainbow.crm.servicerequest.validator.ServiceRequestValidator;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.filter.Filter;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.ui.abstracts.UIPage;
import com.techtrade.rads.framework.ui.components.SortCriteria;

public class ServiceRequestListController extends CRMListController{

	String realPath ;
	
	@Override
	public IRadsContext generateContext(HttpServletRequest request,HttpServletResponse response,UIPage page) {
		realPath = request.getServletContext().getRealPath(".");
		return super.generateContext(request, response,page);
	}
	
	
	
	
	
	@Override
	public void init(HttpServletRequest request) {
		realPath = request.getServletContext().getRealPath(".");
		super.init(request);
	}





	@Override
	public IBusinessService getService() {
		IServiceRequestService serv = (IServiceRequestService) SpringObjectFactory.INSTANCE.getInstance("IServiceRequestService");
		return serv;
	}

	@Override
	public Object getPrimaryKeyValue(ModelObject object) {
		ServiceRequest serviceRequest = (ServiceRequest) object;
		return serviceRequest.getId();
				
	}
	
	
	

	@Override
	public PageResult submit(List<ModelObject> objects, String submitAction) {
		PageResult result = new PageResult();
		return result;
	}

	@Override
	public List<RadsError> validateforDelete(List<ModelObject> objects) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RadsError> validateforEdit(List<ModelObject> objects) {
		ServiceRequestValidator validator = new ServiceRequestValidator((CRMContext) getContext());
		return validator.eligibleForEdit(objects);
	}

	@Override
	public PageResult goToEdit(List<ModelObject> objects) {
		PageResult result = new PageResult();
		result.setNextPageKey("newservicerequest");
		return result;
	}
	
	

}
