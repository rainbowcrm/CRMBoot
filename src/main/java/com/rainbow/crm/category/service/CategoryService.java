package com.rainbow.crm.category.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.category.dao.CategoryDAO;
import com.rainbow.crm.category.model.Category;
import com.rainbow.crm.category.validator.CategoryValidator;
import com.rainbow.crm.common.AbstractService;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMDBException;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.hibernate.ORMDAO;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.model.transaction.TransactionResult.Result;
import com.techtrade.rads.framework.ui.components.SortCriteria;
import com.techtrade.rads.framework.utils.Utils;

public class CategoryService extends AbstractService implements ICategoryService{

	
	protected ORMDAO getDAO() {
		return (CategoryDAO) SpringObjectFactory.INSTANCE.getInstance("CategoryDAO");
	}
		
	@Override
	protected String getTableName() {
		return "Category";
	}
	
	

	@Override
	public Object getById(Object PK) {
		return (Category)getDAO().getById(PK);
	}

	@Override
	public List<CRMModelObject> listData(int from, int to,
			String whereCondition, CRMContext context, SortCriteria sortCriteria) {
		return super.listData("Category", from, to, whereCondition, context,sortCriteria);
	}

	@Override
	@Transactional
	public TransactionResult create(CRMModelObject object, CRMContext context) throws CRMDBException {
			return super.create((Category)object,context);
	}
		
	
	

	@Override
	@Transactional
	public TransactionResult update(CRMModelObject object, CRMContext context) {
		try {
			return super.update((Category)object,context);
		}catch(CRMDBException ex) {
			TransactionResult result=  new TransactionResult(Result.FAILURE);
			result.setErrors(ex.getErrors());
			return result;
		}
	}

	@Override
	@Transactional
	public TransactionResult batchUpdate(List<CRMModelObject> objects,
			CRMContext context) {
		try {
			return super.batchUpdate(objects,context);
		}catch(CRMDBException ex) {
			TransactionResult result=  new TransactionResult(Result.FAILURE);
			result.setErrors(ex.getErrors());
			return result;
		}
	}

	@Override
	@Transactional
	public TransactionResult batchCreate(List<CRMModelObject> objects,
			CRMContext context) {
			try {
				return super.batchCreate(objects,context);
			}catch(CRMDBException ex) {
				TransactionResult result=  new TransactionResult(Result.FAILURE);
				result.setErrors(ex.getErrors());
				return result;
			}
	}

	@Override
	public List<RadsError> validateforCreate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((Category)object).setCompany(company);
		CategoryValidator validator = new CategoryValidator(context);
		return validator.validateforCreate(object);
	}

	@Override
	public List<RadsError> validateforUpdate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((Category)object).setCompany(company);
		CategoryValidator validator = new CategoryValidator(context);
		return validator.validateforUpdate(object);
	}

	@Override
	public Category getByName(int company, String name) {
		return ((CategoryDAO)getDAO()).findByName(company,name);
	}

	@Override
	public List<Category> getAllCategories(int company) {
		return ((CategoryDAO)getDAO()).getAllCategories(company);
	}
	
	
}
