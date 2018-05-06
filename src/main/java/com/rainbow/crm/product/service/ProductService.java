package com.rainbow.crm.product.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.category.model.Category;
import com.rainbow.crm.category.validator.CategoryValidator;
import com.rainbow.crm.common.AbstractService;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMDBException;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.hibernate.ORMDAO;
import com.rainbow.crm.product.dao.ProductDAO;
import com.rainbow.crm.product.model.Product;
import com.rainbow.crm.product.validator.ProductValidator;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.model.transaction.TransactionResult.Result;
import com.techtrade.rads.framework.ui.components.SortCriteria;
import com.techtrade.rads.framework.utils.Utils;

public class ProductService extends AbstractService implements  IProductService{
	
	protected ORMDAO getDAO() {
		return (ProductDAO) SpringObjectFactory.INSTANCE.getInstance("ProductDAO");
	}
		
	@Override
	protected String getTableName() {
		return "Product";
	}
	
	

	@Override
	public Object getById(Object PK) {
		return (Product)getDAO().getById(PK);
	}

	@Override
	public List<CRMModelObject> listData(int from, int to,
			String whereCondition, CRMContext context, SortCriteria sortCriteria) {
		return super.listData("Product",from, to, whereCondition, context,sortCriteria);
	}

	@Override
	@Transactional
	public TransactionResult batchCreate(List<CRMModelObject> objects,
			CRMContext context) throws CRMDBException {
		return super.batchCreate(objects,context);
			
	}

	@Override
	public List<RadsError> validateforCreate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((Product)object).setCompany(company);
		ProductValidator validator = new ProductValidator(context);
		return validator.validateforCreate(object);
	}

	@Override
	public List<RadsError> validateforUpdate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((Product)object).setCompany(company);
		ProductValidator validator = new ProductValidator(context);
		return validator.validateforUpdate(object);
	}

	@Override
	public Product getByName(int company, String name) {
		return ((ProductDAO)getDAO()).findByName(company,name);
	}


}
