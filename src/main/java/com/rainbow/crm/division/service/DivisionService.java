package com.rainbow.crm.division.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.AbstractService;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.division.dao.DivisionDAO;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.division.validator.DivisionValidator;
import com.rainbow.crm.hibernate.ORMDAO;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.ui.components.SortCriteria;
import com.techtrade.rads.framework.utils.Utils;

public class DivisionService extends AbstractService implements IDivisionService {

	
	
	@Override
	public Division getDefaultDivision(int company) {
		return ((DivisionDAO)getDAO()).getDefaultDivision(company);
	}

	@Override
	protected ORMDAO getDAO() {
		return  DivisionDAO.INSTANCE;
	}
	
	@Override
	protected String getTableName() {
		return "Division";
	}

	

	@Override
	public Object getById(Object PK) {
		return (Division)getDAO().getById(PK);
	}

	@Override
	public List<CRMModelObject> listData(int from, int to, String whereCondition,CRMContext context, SortCriteria sortCriteria) {
		return super.listData("Division", from, to, whereCondition, context,sortCriteria);
	}

	

	@Override
	public List<RadsError> validateforCreate(CRMModelObject object,CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((Division)object).setCompany(company);
		DivisionValidator validator = new DivisionValidator(context);
		return validator.validateforCreate(object);
	}

	@Override
	public List<RadsError> validateforUpdate(CRMModelObject object,CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((Division)object).setCompany(company);
		DivisionValidator validator = new DivisionValidator(context);
		return validator.validateforUpdate(object);
	}

	@Override
	public Division getByCode(int company, String code) {
		return ((DivisionDAO)getDAO()).getByCode(code, company);
	}

	@Override
	public Division getByName(int company, String name) {
		return ((DivisionDAO)getDAO()).getByName(name, company);
	}

	@Override
	public List<Division> getAllDivisions(int company) {
		return ((DivisionDAO)getDAO()).getAllDivisions(company);
	}
	
	
	

}
