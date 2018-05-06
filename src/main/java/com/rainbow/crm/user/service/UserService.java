package com.rainbow.crm.user.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.AbstractService;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMDBException;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.division.validator.DivisionValidator;
import com.rainbow.crm.hibernate.ORMDAO;
import com.rainbow.crm.user.dao.UserDAO;
import com.rainbow.crm.user.model.User;
import com.rainbow.crm.user.validator.UserValidator;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.ui.components.SortCriteria;
import com.techtrade.rads.framework.utils.Utils;

public class UserService  extends AbstractService implements IUserService{
	
	
	protected ORMDAO getDAO() {
		return (UserDAO) SpringObjectFactory.INSTANCE.getInstance("UserDAO");
	}
	@Override
	public Object getById(Object PK) {
		return (User)getDAO().getById(PK);
	}

	@Override
	public List<CRMModelObject> listData(int from, int to, String whereCondition,CRMContext context, SortCriteria sortCriteria) {
		if(context.getLoggedinCompany() != 1) {
			return super.listData("User", from, to, whereCondition, context,sortCriteria);
		}else
			return  getDAO().listData("User" ,from, to, whereCondition);
	}

	

	@Override
	@Transactional 
	public TransactionResult update(CRMModelObject user, CRMContext context) throws CRMDBException{
		ICompanyService companyService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = companyService.findByName(((User)user).getCompany().getName());
		((User)user).setCompany(company);
		return super.update(user,context);
	}
	
	@Override
	@Transactional 
	public TransactionResult batchUpdate(List<CRMModelObject> users, CRMContext context) throws CRMDBException {
		return super.batchUpdate(users,context);
	}
	
	@Override
	protected String getTableName() {
		return "User";
	}
	
	
	@Override
	public User getByEmail(String email) {
		return((UserDAO)getDAO()).getByEmail(email);
	}
	@Override
	public User getByPhone(String phone) {
		return ((UserDAO)getDAO()).getByPhone(phone);
	}

	
	
	@Override
	public List<User> getByDivision(Division division, CRMContext context) {
		return ((UserDAO)getDAO()).getAllUsersByDivision(division.getId(), false);
	}
	@Override
	public List<RadsError> validateforCreate(CRMModelObject object,
			CRMContext context) {
		UserValidator validator = new UserValidator(context);
		return validator.validateforCreate(object);
		
	}
	
	@Override
	public List<RadsError> validateforUpdate(CRMModelObject object,
			CRMContext context) {
		UserValidator validator = new UserValidator(context);
		return validator.validateforUpdate(object);
		
	}
	
	
	

}
