package com.rainbow.crm.alert.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMListController;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.alert.model.Alert;
import com.rainbow.crm.alert.service.IAlertService;
import com.rainbow.crm.alert.validator.AlertValidator;
import com.techtrade.rads.framework.filter.Filter;
import com.techtrade.rads.framework.filter.FilterNode;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult.Result;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.utils.Utils;

public class AlertListController extends CRMListController{

	@Override
	public IBusinessService getService() {
		IAlertService serv = (IAlertService) SpringObjectFactory.INSTANCE.getInstance("IAlertService");
		return serv;
	}

	@Override
	public Object getPrimaryKeyValue(ModelObject object) {
		Alert alert = (Alert) object ;
		return alert.getId();
	}

	@Override
	public PageResult submit(List<ModelObject> objects, String submitAction) {
		if("acknowledge".equalsIgnoreCase(submitAction)){
			IAlertService serv =(IAlertService)getService();
			for ( ModelObject obj :  objects) {
				serv.acknowledgeAlert((Alert)obj, (CRMContext)getContext());
			}
		}
		PageResult resut = new 	PageResult();
		resut.setResult(Result.SUCCESS);
		return resut;
	}

	@Override
	public List<RadsError> validateforDelete(List<ModelObject> objects) {
		return null;
	}

	@Override
	public List<RadsError> validateforEdit(List<ModelObject> objects) {
		AlertValidator validator = new AlertValidator(((CRMContext)getContext()));
		return validator.eligibleForEdit(objects);
	}

	@Override
	public PageResult goToEdit(List<ModelObject> objects) {
		PageResult result = new PageResult();
		result.setNextPageKey("newalert");
		return result;
	}
	
	public Map <String, String > getTypes() {
		Map<String, String> ans = new LinkedHashMap<String, String> ();
		ans.put("null", "---Select one---") ;
		ans.putAll(GeneralSQLs.getFiniteValues(CRMConstants.FV_ALERT_TYPE));
		return ans;
	}
	
	public Map <String, String > getStatuses() {
		Map<String, String> ans = new LinkedHashMap<String, String> ();
		ans.put("null", "---Select one---") ;
		ans.putAll(GeneralSQLs.getFiniteValues(CRMConstants.FV_ALERT_STATUS));
		return ans;
	}

	
	
	protected String getFilter(Filter filterData ) {
		StringBuffer whereCondition = new  StringBuffer();
		if (filterData!=null  && !Utils.isNullList(filterData.getNodeList()) ) {
			for (FilterNode node : filterData.getNodeList()) {
				if (!Utils.isNullString(String.valueOf(node.getValue())) && !"FilterName".equals(node.getField())) {
					if (whereCondition.length() < 1)
						whereCondition.append( " where  "  + Utils.initlower(node.getField())  + getOperator(node) +  "'" +  node.getValue() + "'");
					else
						whereCondition.append( " and  "  + Utils.initlower(node.getField())  +  getOperator(node) + "'" +  node.getValue() + "'");
				}
			}
		}
			CRMContext ctx = (CRMContext)getContext();
			int division = (ctx.getLoggedInUser().getDivision()!= null)?ctx.getLoggedInUser().getDivision().getId():-1;
			StringBuffer mandateCondition =  new StringBuffer(" (owner is null or owner ='" + getContext().getUser() + "') and "+
		   " ( division is null or division.id = "+ division +") ");
			if (whereCondition.toString().trim().equals("") )
				whereCondition.append( " where status='" + CRMConstants.ALERT_STATUS.OPEN + "' and  " +  mandateCondition) ;
			else
				whereCondition.append( " and  " +  mandateCondition) ;
		
		return whereCondition.toString();
	}
	
}
