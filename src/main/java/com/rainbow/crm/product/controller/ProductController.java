package com.rainbow.crm.product.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.category.model.Category;
import com.rainbow.crm.category.service.ICategoryService;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMDBException;
import com.rainbow.crm.common.CRMDataSheetController;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.crm.product.model.Product;
import com.rainbow.crm.product.service.IProductService;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.ui.abstracts.UIPage;
import com.techtrade.rads.framework.utils.Utils;

public class ProductController extends CRMDataSheetController{
	@Override
	public IProductService getService() {
		return (IProductService) SpringObjectFactory.INSTANCE.getInstance("IProductService");
	}

	@Override
	public Object getPrimaryKeyValue(ModelObject object) {
		Product category = (Product) object;
		return category.getId();
	}
	
	

	@Override
	public IRadsContext generateContext(String authToken, UIPage page) {
		CRMContext ctx = (CRMContext)CommonUtil.generateContext(authToken,page);
		if(ctx.isMobileLogin())
			ctx.setAuthorized(true);
		return ctx;
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
		IProductService service = getService();
		try {
			return new PageResult( service.batchCreate((List)objects, (CRMContext)getContext()));
		}catch(CRMDBException ex) {
			Logwriter.INSTANCE.error(ex);
			return new PageResult( TransactionResult.Result.FAILURE, ex.getErrors(),null);
		}
	}

	@Override
	public PageResult delete() {
		return null;
	}

	@Override
	public void read() {
		
	}

	public Map <String, String > getAllCategories() {
		Map<String, String> ans = new LinkedHashMap<String, String> ();
		ICategoryService service =(ICategoryService) SpringObjectFactory.INSTANCE.getInstance("ICategoryService");
		List<Category> categories = service.getAllCategories(((CRMContext)getContext()).getLoggedinCompany());
		if (!Utils.isNullList(categories)) {
			for (Category category : categories) {
				ans.put(String.valueOf(category.getId()), category.getName());
			}
		}
		ans.putAll(GeneralSQLs.getFiniteValues(CRMConstants.FV_UOM_TYPE));
		return ans;
	}
	
	public Map <String, String > getAllCategorieswithSelect() {
		Map<String, String> ans = new LinkedHashMap<String, String> ();
		ans.put("null", "--Select One--");
		ICategoryService service =(ICategoryService) SpringObjectFactory.INSTANCE.getInstance("ICategoryService");
		List<Category> categories = service.getAllCategories(((CRMContext)getContext()).getLoggedinCompany());
		if (!Utils.isNullList(categories)) {
			for (Category category : categories) {
				ans.put(String.valueOf(category.getId()), category.getName());
			}
		}
		ans.putAll(GeneralSQLs.getFiniteValues(CRMConstants.FV_UOM_TYPE));
		return ans;
	}

	
}
