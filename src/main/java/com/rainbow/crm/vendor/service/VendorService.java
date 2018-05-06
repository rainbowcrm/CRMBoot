package com.rainbow.crm.vendor.service;

import java.util.List;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.AbstractService;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.hibernate.ORMDAO;
import com.rainbow.crm.vendor.dao.VendorDAO;
import com.rainbow.crm.vendor.model.Vendor;
import com.rainbow.crm.vendor.validator.VendorValidator;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.ui.components.SortCriteria;
import com.techtrade.rads.framework.utils.Utils;

public class VendorService extends AbstractService implements IVendorService{

	
	@Override
	protected String getTableName() {
		return "Vendor";
	}
	
	

	@Override
	public Object getById(Object PK) {
		return getDAO().getById(PK);
	}

	@Override
	public List<CRMModelObject> listData(int from, int to,
			String whereCondition, CRMContext context, SortCriteria sortCriteria) {
		return super.listData("Vendor", from, to, whereCondition, context,sortCriteria);
	}

	@Override
	public List<RadsError> validateforCreate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((Vendor)object).setCompany(company);
		VendorValidator validator = new VendorValidator(context);
		return validator.validateforCreate(object);
	}

	@Override
	public List<RadsError> validateforUpdate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((Vendor)object).setCompany(company);
		VendorValidator validator = new VendorValidator(context);
		return validator.validateforUpdate(object);
	}

	@Override
	public Vendor getByCode(int company, String code) {
		return ((VendorDAO)getDAO()).findByCode(company, code);
	}

	@Override
	public Vendor getByName(int company, String name) {
		return ((VendorDAO)getDAO()).findByName(company, name);
	}

	@Override
	protected ORMDAO getDAO() {
		return (VendorDAO) SpringObjectFactory.INSTANCE.getInstance("VendorDAO");
	}
	
	
	

}
