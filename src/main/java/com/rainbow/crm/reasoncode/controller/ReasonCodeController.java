package com.rainbow.crm.reasoncode.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.reasoncode.model.ReasonCode;
import com.rainbow.crm.reasoncode.service.IReasonCodeService;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMDBException;
import com.rainbow.crm.common.CRMDataSheetController;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.logger.Logwriter;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.model.transaction.TransactionResult.Result;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.utils.Utils;


public class ReasonCodeController extends CRMDataSheetController{

	@Override
	public IReasonCodeService getService() {
		return (IReasonCodeService) SpringObjectFactory.INSTANCE.getInstance("IReasonCodeService");
	}

	@Override
	public Object getPrimaryKeyValue(ModelObject object) {
		ReasonCode reasonCode = (ReasonCode) object;
		return reasonCode.getId();
	}

	@Override
	public PageResult submit(List<ModelObject> objects, String submitAction) {
		return null;
	}

	@Override
	public List<RadsError> validateforEdit(List<ModelObject> objects) {
		return null;
	}

	
	@Override
	public List<RadsError> validateforDelete(List<ModelObject> objects) {
		return null;
	}

	@Override
	public PageResult goToEdit(List<ModelObject> objects) {
		return null;
	}
	
	public Map <String, String > getAllReasonCodeTypesWithSelect() {
		Map<String, String> ans = new LinkedHashMap<String, String> ();
		ans.put("null", "---Select one---") ;
		ans.putAll(GeneralSQLs.getFiniteValues(CRMConstants.FV_REASON_TYPE));
		return ans;
	}
	
	public Map <String, String > getAllReasonCodeTypes() {
		 return  GeneralSQLs.getFiniteValues(CRMConstants.FV_REASON_TYPE);
	}

	public Map <String, String > getAllFactors() {
		return  GeneralSQLs.getFiniteValues(CRMConstants.FV_FACTOR_TYPE);
	}
	
	public Map <String, String > getAllOrientation() {
		return  GeneralSQLs.getFiniteValues(CRMConstants.FV_ORIENTATION);
	}
	public Map <String, String > getAllAccessibleRoles() {
		 return  GeneralSQLs.getFiniteValues(CRMConstants.FV_USRPRIV_TYPE);
	}
	
	@Override
	public List<RadsError> validateforCreate() {
		List<RadsError> ans = new ArrayList<RadsError>();
		IBusinessService service = getService() ;
		for (ModelObject object: objects) {
			List<RadsError> err = service.validateforCreate((CRMModelObject)object, (CRMContext)getContext());
			if(!Utils.isNullList(err))
				ans.addAll(err);
		}
		return ans;
	}

	@Override
	public List<RadsError> validateforUpdate() {
		List<RadsError> ans = new ArrayList<RadsError>();
		IBusinessService service = getService() ;
		for (ModelObject object: objects) {
			List<RadsError> err = service.validateforUpdate((CRMModelObject)object, (CRMContext)getContext());
			if(!Utils.isNullList(err))
				ans.addAll(err);
		}
		return ans;
	}
	

	@Override
	public PageResult create() {
		IReasonCodeService service = getService();
		try {
			return new PageResult( service.batchCreate((List)objects, (CRMContext)getContext()));
		}catch(CRMDBException ex) {
			return new PageResult(Result.FAILURE,ex.getErrors(),null);
		}
	}

	@Override
	public PageResult delete() {
		return null;
	}

	@Override
	public void read() {
		
	}

	

	
		
}
