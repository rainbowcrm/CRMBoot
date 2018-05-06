package com.rainbow.crm.company.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.AbstractService;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.company.dao.CompanyDAO;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.validator.CompanyValidator;
import com.rainbow.crm.hibernate.ORMDAO;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.ui.components.SortCriteria;

public class CompanyService  extends AbstractService implements ICompanyService{


	protected ORMDAO getDAO() {
		return (CompanyDAO) SpringObjectFactory.INSTANCE.getInstance("CompanyDAO");
		//return CompanyDAO.INSTANCE; 
	}
	@Override
	public Object getById(Object PK) {
		return (Company)getDAO().getById(PK);
	}
	
	

	@Override
	public List<Company> findAllActiveCompanies() {
		// TODO Auto-generated method stub
		return ((CompanyDAO)getDAO()).getAllActiveCompanies();
	}
	@Override
	public List<CRMModelObject> listData(int from, int to, String whereCondition,CRMContext context, SortCriteria sortCriteria) {
		 return  getDAO().listData("Company" ,from, to, whereCondition);
	}

	@Override
	protected String getTableName() {
		return "Company";
	}
	
	
	@Override
	public Company findByCode(String code) {
		return ((CompanyDAO)getDAO()).findByCode(code);
	}
	@Override
	public Company findByName(String name) {
		return ((CompanyDAO)getDAO()).findByName(name);
	}
	@Override
	public List<RadsError> validateforCreate(CRMModelObject object,
			CRMContext context) {
		CompanyValidator validator = new CompanyValidator(context);
		return validator.validateforCreate(object);
	}
	@Override
	public List<RadsError> validateforUpdate(CRMModelObject object,
			CRMContext context) {
		CompanyValidator validator = new CompanyValidator(context);
		return validator.validateforUpdate(object);
	}
	
	
	

}
