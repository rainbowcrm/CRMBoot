package com.rainbow.crm.contact.service;

import java.util.List;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.AbstractService;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.hibernate.ORMDAO;
import com.rainbow.crm.contact.dao.ContactDAO;
import com.rainbow.crm.contact.model.Contact;
import com.rainbow.crm.contact.validator.ContactValidator;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.ui.components.SortCriteria;
import com.techtrade.rads.framework.utils.Utils;

public class ContactService extends AbstractService implements IContactService{

	
	@Override
	protected String getTableName() {
		return "Contact";
	}
	
	
	@Override
	public Object getById(Object PK) {
		return getDAO().getById(PK);
	}

	@Override
	public List<CRMModelObject> listData(int from, int to,
			String whereCondition, CRMContext context, SortCriteria sortCriteria) {
		return super.listData("Contact", from, to, whereCondition, context,sortCriteria);
	}

	@Override
	public List<RadsError> validateforCreate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((Contact)object).setCompany(company);
		ContactValidator validator = new ContactValidator(context);
		return validator.validateforCreate(object);
	}

	@Override
	public List<RadsError> validateforUpdate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((Contact)object).setCompany(company);
		ContactValidator validator = new ContactValidator(context);
		return validator.validateforUpdate(object);
	}

	
	
	
	@Override
	public Contact getByFullName(int company, String fullName) {
		ContactDAO dao = (ContactDAO) getDAO();
		if( fullName.contains(" - ")) {
			String firstName = fullName.substring(0, fullName.indexOf(' '));
			String lastName   =  fullName.substring(fullName.indexOf(' ')+1, fullName.indexOf('-')-1);
			String phone =  fullName.substring(fullName.indexOf('-')+2,fullName.length());
			Contact contact = dao.findByfullNameAndPhone(company, firstName, lastName, phone);
			return contact;
		}else
		{
			String firstName = fullName.substring(0, fullName.indexOf(' '));
			String lastName   = fullName.substring(fullName.indexOf(' ')+1,fullName.length());
			Contact contact = dao.findByfullName(company, firstName, lastName);
			return contact;
		}
			
	}

	/**
	@Override
	public Contact getByCode(int company, String code) {
		return ((ContactDAO)getDAO()).findByCode(company, code);
	}

	@Override
	public Contact getByName(int company, String name) {
		return ((ContactDAO)getDAO()).findByName(company, name);
	}**/
	
	
	

	@Override
	protected ORMDAO getDAO() {
		return (ContactDAO) SpringObjectFactory.INSTANCE.getInstance("ContactDAO");
	}

	@Override
	public Contact getByEmail(int company, String email) {
		return ((ContactDAO)getDAO()).findByEmail(company, email);
	}

	@Override
	public Contact getByPhone(int company, String phone) {
		return ((ContactDAO)getDAO()).findByPhone(company, phone);
	}
	
	
	

}
