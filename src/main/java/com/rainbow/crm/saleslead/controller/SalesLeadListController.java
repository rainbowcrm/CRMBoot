package com.rainbow.crm.saleslead.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMListController;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.saleslead.model.SalesLead;
import com.rainbow.crm.saleslead.model.SalesLeadExtended;
import com.rainbow.crm.saleslead.service.ISalesLeadService;
import com.rainbow.crm.saleslead.validator.SalesLeadValidator;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.filter.Filter;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.ui.abstracts.UIPage;
import com.techtrade.rads.framework.ui.components.SortCriteria;

public class SalesLeadListController extends CRMListController{

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
		ISalesLeadService serv = (ISalesLeadService) SpringObjectFactory.INSTANCE.getInstance("ISalesLeadService");
		return serv;
	}

	@Override
	public Object getPrimaryKeyValue(ModelObject object) {
		SalesLead salesLead = (SalesLead) object;
		return salesLead.getId();
				
	}
	
	
	

	@Override
	public PageResult submit(List<ModelObject> objects, String submitAction) {
		PageResult result = new PageResult();
		ISalesLeadService service = (ISalesLeadService) getService();
		for (ModelObject lead : objects) {
			if("promote".equals(submitAction))
				service.startSalesCycle((SalesLead)lead);
			else   if ("viewConsole".equalsIgnoreCase(submitAction)) { 
				result.setNextPageKey("salesleadconsole");
				SalesLeadExtended leadExtended = service.getSalesLeadWithExtension(((SalesLead)lead).getId(), (CRMContext)getContext());
				result.setObject(leadExtended);
				return result;
			}else  if("sendemail".equalsIgnoreCase(submitAction)){
				service.sendEmail((SalesLead)lead,(CRMContext) getContext(),realPath);
			}
				
		}
		return result;
	}

	@Override
	public List<RadsError> validateforDelete(List<ModelObject> objects) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RadsError> validateforEdit(List<ModelObject> objects) {
		SalesLeadValidator validator = new SalesLeadValidator((CRMContext) getContext());
		return validator.eligibleForEdit(objects);
	}

	@Override
	public PageResult goToEdit(List<ModelObject> objects) {
		PageResult result = new PageResult();
		result.setNextPageKey("newsaleslead");
		return result;
	}
	
	

}
