package com.rainbow.crm.expensehead.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.expensehead.model.ExpenseHead;
import com.rainbow.crm.expensehead.service.IExpenseHeadService;
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


public class ExpenseHeadController extends CRMDataSheetController{

	@Override
	public IExpenseHeadService getService() {
		return (IExpenseHeadService) SpringObjectFactory.INSTANCE.getInstance("IExpenseHeadService");
	}

	@Override
	public Object getPrimaryKeyValue(ModelObject object) {
		ExpenseHead expensehead = (ExpenseHead) object;
		return expensehead.getId();
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
	
	public Map <String, String > getFVUOMswithSelect() {
		Map<String, String> ans = new LinkedHashMap<String, String> ();
		ans.put("null", "---Select one---") ;
		ans.putAll(GeneralSQLs.getFiniteValues(CRMConstants.FV_UOM_TYPE));
		return ans;
	}
	
	public Map <String, String > getFVUOMs() {
		 return  GeneralSQLs.getFiniteValues(CRMConstants.FV_UOM_TYPE);
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
		IExpenseHeadService service = getService();
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
