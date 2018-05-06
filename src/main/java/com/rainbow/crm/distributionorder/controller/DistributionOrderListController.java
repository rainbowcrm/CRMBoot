package com.rainbow.crm.distributionorder.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMListController;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.distributionorder.model.DistributionOrder;
import com.rainbow.crm.distributionorder.service.IDistributionOrderService;
import com.rainbow.crm.distributionorder.validator.DistributionOrderValidator;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.ui.abstracts.UIPage;

public class DistributionOrderListController extends CRMListController{

	String realPath ;
	
	@Override
	public IRadsContext generateContext(HttpServletRequest request,HttpServletResponse response,UIPage page) {
		realPath = request.getServletContext().getRealPath(".");
		return CommonUtil.generateContext(request,page);
	}
	
	@Override
	public IBusinessService getService() {
		IDistributionOrderService serv = (IDistributionOrderService) SpringObjectFactory.INSTANCE.getInstance("IDistributionOrderService");
		return serv;
	}

	@Override
	public Object getPrimaryKeyValue(ModelObject object) {
		DistributionOrder distributionOrder = (DistributionOrder) object;
		return distributionOrder.getId();
				
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
		DistributionOrderValidator validator = new DistributionOrderValidator((CRMContext) getContext());
		return validator.eligibleForEdit(objects);
	}

	@Override
	public PageResult goToEdit(List<ModelObject> objects) {
		PageResult result = new PageResult();
		result.setNextPageKey("newdistributionorder");
		return result;
	}
	
	

}
