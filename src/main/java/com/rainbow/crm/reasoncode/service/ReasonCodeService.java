package com.rainbow.crm.reasoncode.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.reasoncode.dao.ReasonCodeDAO;
import com.rainbow.crm.reasoncode.model.ReasonCode;
import com.rainbow.crm.reasoncode.validator.ReasonCodeValidator;
import com.rainbow.crm.common.AbstractService;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMDBException;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.hibernate.ORMDAO;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.model.transaction.TransactionResult.Result;
import com.techtrade.rads.framework.ui.components.SortCriteria;
import com.techtrade.rads.framework.utils.Utils;

public class ReasonCodeService extends AbstractService implements IReasonCodeService{

	
	protected ORMDAO getDAO() {
		return (ReasonCodeDAO) SpringObjectFactory.INSTANCE.getInstance("ReasonCodeDAO");
	}
		
	@Override
	protected String getTableName() {
		return "ReasonCode";
	}
	
	

	@Override
	public Object getById(Object PK) {
		return (ReasonCode)getDAO().getById(PK);
	}

	@Override
	public List<CRMModelObject> listData(int from, int to,
			String whereCondition, CRMContext context, SortCriteria sortCriteria) {
		return super.listData("ReasonCode", from, to, whereCondition, context,sortCriteria);
	}

	@Override
	@Transactional
	public TransactionResult create(CRMModelObject object, CRMContext context) throws CRMDBException {
			return super.create((ReasonCode)object,context);
	}
		
	
	

	@Override
	@Transactional
	public TransactionResult update(CRMModelObject object, CRMContext context) {
		try {
			return super.update((ReasonCode)object,context);
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
		((ReasonCode)object).setCompany(company);
		ReasonCodeValidator validator = new ReasonCodeValidator(context);
		return validator.validateforCreate(object);
	}

	@Override
	public List<RadsError> validateforUpdate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((ReasonCode)object).setCompany(company);
		ReasonCodeValidator validator = new ReasonCodeValidator(context);
		return validator.validateforUpdate(object);
	}

	@Override
	public ReasonCode getByName(int company, String reason) {
		return ((ReasonCodeDAO)getDAO()).findByReason(company,reason);
	}

	@Override
	public List<ReasonCode> getAllReasonsforType(FiniteValue value,
			CRMContext context) {
		ReasonCodeDAO dao = ((ReasonCodeDAO)getDAO()) ;
		return dao.getAllReasonsByType(context.getLoggedinCompany(), value.getCode()) ;
	}

	@Override
	public List<ReasonCode> getAllReasonsforTypeAndOrientation(
			FiniteValue value, CRMContext context, FiniteValue orientation) {
		ReasonCodeDAO dao = ((ReasonCodeDAO)getDAO()) ;
		return dao.getAllReasonsByTypeAndOrientation(context.getLoggedinCompany(), value.getCode(),orientation.getCode()) ;
	}
	
	

		
	
}
