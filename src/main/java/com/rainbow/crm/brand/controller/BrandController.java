package com.rainbow.crm.brand.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.brand.model.Brand;
import com.rainbow.crm.brand.service.IBrandService;
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


public class BrandController extends CRMDataSheetController{

	@Override
	public IBrandService getService() {
		return (IBrandService) SpringObjectFactory.INSTANCE.getInstance("IBrandService");
	}

	@Override
	public Object getPrimaryKeyValue(ModelObject object) {
		Brand brand = (Brand) object;
		return brand.getId();
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
		IBrandService service = getService();
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
