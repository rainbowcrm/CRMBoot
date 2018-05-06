package com.rainbow.crm.carrier.service;

import java.util.List;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.AbstractService;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.hibernate.ORMDAO;
import com.rainbow.crm.carrier.dao.CarrierDAO;
import com.rainbow.crm.carrier.model.Carrier;
import com.rainbow.crm.carrier.validator.CarrierValidator;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.ui.components.SortCriteria;
import com.techtrade.rads.framework.utils.Utils;

public class CarrierService extends AbstractService implements ICarrierService{

	@Override
	protected String getTableName() {
		return "Carrier";
	}
	
	

	@Override
	public Object getById(Object PK) {
		return getDAO().getById(PK);
	}

	@Override
	public List<CRMModelObject> listData(int from, int to,
			String whereCondition, CRMContext context, SortCriteria sortCriteria) {
		return super.listData("Carrier", from, to, whereCondition, context,sortCriteria);
	}

	@Override
	public List<RadsError> validateforCreate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((Carrier)object).setCompany(company);
		CarrierValidator validator = new CarrierValidator(context);
		return validator.validateforCreate(object);
	}

	@Override
	public List<RadsError> validateforUpdate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((Carrier)object).setCompany(company);
		CarrierValidator validator = new CarrierValidator(context);
		return validator.validateforUpdate(object);
	}

	/**
	@Override
	public Carrier getByCode(int company, String code) {
		return ((CarrierDAO)getDAO()).findByCode(company, code);
	}

	@Override
	public Carrier getByName(int company, String name) {
		return ((CarrierDAO)getDAO()).findByName(company, name);
	}**/
	
	
	

	@Override
	protected ORMDAO getDAO() {
		return (CarrierDAO) SpringObjectFactory.INSTANCE.getInstance("CarrierDAO");
	}

	@Override
	public List<Carrier> getAllCarriers(int company) {
		return ((CarrierDAO)getDAO()).getAllCarriers(company);
	}

	@Override
	public Carrier getByEmail(int company, String email) {
		return ((CarrierDAO)getDAO()).findByEmail(company, email);
	}

	@Override
	public Carrier getByPhone(int company, String phone) {
		return ((CarrierDAO)getDAO()).findByPhone(company, phone);
	}
	
	@Override
	public Carrier getByCode(int company, String code) {
		return ((CarrierDAO)getDAO()).findByPhone(company, code);
	}
	

}
