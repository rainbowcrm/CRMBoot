package com.rainbow.crm.address.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.AbstractService;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMDBException;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.DatabaseException;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.hibernate.ORMDAO;
import com.rainbow.crm.address.dao.AddressDAO;
import com.rainbow.crm.address.model.Address;
import com.rainbow.crm.address.validator.AddressValidator;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.ui.components.SortCriteria;

public class AddressService extends AbstractService implements IAddressService{

	@Override
	protected String getTableName() {
		return "Address";
	}
	
	

	@Override
	public Object getById(Object PK) {
		return getDAO().getById(PK);
	}

	@Override
	public List<CRMModelObject> listData(int from, int to,
			String whereCondition, CRMContext context, SortCriteria sortCriteria) {
		return super.listData("Address", from, to, whereCondition, context,sortCriteria);
	}

	@Override
	public List<RadsError> validateforCreate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((Address)object).setCompany(company);
		AddressValidator validator = new AddressValidator(context);
		return validator.validateforCreate(object);
	}

	@Override
	public List<RadsError> validateforUpdate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((Address)object).setCompany(company);
		AddressValidator validator = new AddressValidator(context);
		return validator.validateforUpdate(object);
	}

	
	@Transactional 
	public TransactionResult create(CRMModelObject object,CRMContext context)  {
		List<RadsError> errors  = new ArrayList<RadsError>(); 
		TransactionResult.Result result = TransactionResult.Result.SUCCESS;
		try {
			int pk = GeneralSQLs.getNextPKValue("Addresses") ;
			((Address)object).setId(pk);
			object.setObjectVersion(1);
			object.setCreatedDate(new java.sql.Timestamp(new java.util.Date().getTime()));
			object.setCreatedUser(context.getUser());
			getDAO().create(object);
		}catch(DatabaseException ex) {
			RadsError error = CRMValidator.getErrorforCode(context.getLocale(),CRMDBException.ERROR_UNABLE_TO_CREATE);
			errors.add(error);
			result = TransactionResult.Result.FAILURE ;
			throw new RuntimeException(ex) ;
		}
		return new TransactionResult(result,errors);
	}
	
	

	@Override
	protected ORMDAO getDAO() {
		return (AddressDAO) SpringObjectFactory.INSTANCE.getInstance("AddressDAO");
	}

	
	
	

}
