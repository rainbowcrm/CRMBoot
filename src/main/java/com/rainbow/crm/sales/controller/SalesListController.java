package com.rainbow.crm.sales.controller;

import java.util.List;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMListController;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.sales.service.ISalesService;
import com.rainbow.crm.sales.validator.SalesValidator;
import com.techtrade.rads.framework.filter.Filter;
import com.techtrade.rads.framework.filter.FilterNode;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

public class SalesListController extends CRMListController{

	@Override
	protected String getFilter(Filter filterData) {
		if (filterData == null)
			filterData  = new Filter();
		FilterNode node =new FilterNode();
		node.setField("return");
		node.setOperater(FilterNode.Operator.EQUALS);
		node.setValue(0);
		filterData.addNode(node);
		return super.getFilter(filterData);
	}

	@Override
	public IBusinessService getService() {
		ISalesService serv = (ISalesService) SpringObjectFactory.INSTANCE.getInstance("ISalesService");
		return serv;
	}

	@Override
	public Object getPrimaryKeyValue(ModelObject object) {
		Sales sales = (Sales) object;
		return sales.getId();
				
	}

	@Override
	public PageResult submit(List<ModelObject> objects, String submitAction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RadsError> validateforDelete(List<ModelObject> objects) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RadsError> validateforEdit(List<ModelObject> objects) {
		SalesValidator validator = new SalesValidator((CRMContext) getContext());
		return validator.eligibleForEdit(objects);
	}

	@Override
	public PageResult goToEdit(List<ModelObject> objects) {
		PageResult result = new PageResult();
		result.setNextPageKey("newsales");
		return result;
	}
	
	

}
