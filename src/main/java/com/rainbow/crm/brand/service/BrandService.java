package com.rainbow.crm.brand.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.brand.dao.BrandDAO;
import com.rainbow.crm.brand.model.Brand;
import com.rainbow.crm.brand.validator.BrandValidator;
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

public class BrandService extends AbstractService implements IBrandService{

	
	protected ORMDAO getDAO() {
		return (BrandDAO) SpringObjectFactory.INSTANCE.getInstance("BrandDAO");
	}
		
	@Override
	protected String getTableName() {
		return "Brand";
	}
	
	

	@Override
	public Object getById(Object PK) {
		return (Brand)getDAO().getById(PK);
	}

	@Override
	public List<CRMModelObject> listData(int from, int to,
			String whereCondition, CRMContext context, SortCriteria sortCriteria) {
		return super.listData("Brand", from, to, whereCondition, context,sortCriteria);
	}

	@Override
	@Transactional
	public TransactionResult create(CRMModelObject object, CRMContext context) throws CRMDBException {
			return super.create((Brand)object,context);
	}
		
	
	

	@Override
	@Transactional
	public TransactionResult update(CRMModelObject object, CRMContext context) {
		try {
			return super.update((Brand)object,context);
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
		((Brand)object).setCompany(company);
		BrandValidator validator = new BrandValidator(context);
		return validator.validateforCreate(object);
	}

	@Override
	public List<RadsError> validateforUpdate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((Brand)object).setCompany(company);
		BrandValidator validator = new BrandValidator(context);
		return validator.validateforUpdate(object);
	}

	@Override
	public Brand getByName(int company, String name) {
		return ((BrandDAO)getDAO()).findByName(company,name);
	}

	@Override
	public List<Brand> getAllBrands(int company) {
		return ((BrandDAO)getDAO()).getAllBrands(company);
	}
	
	
}
