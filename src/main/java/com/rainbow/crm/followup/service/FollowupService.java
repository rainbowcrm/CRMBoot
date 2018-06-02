package com.rainbow.crm.followup.service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;









import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.AbstractService;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.hibernate.ORMDAO;
import com.rainbow.crm.reasoncode.model.ReasonCode;
import com.rainbow.crm.saleslead.model.SalesLead;
import com.rainbow.crm.saleslead.model.SalesLeadExtended;
import com.rainbow.crm.saleslead.model.SalesLeadLine;
import com.rainbow.crm.saleslead.service.ISalesLeadService;
import com.rainbow.crm.followup.dao.FollowupDAO;
import com.rainbow.crm.followup.model.Followup;
import com.rainbow.crm.followup.validator.FollowupValidator;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.ui.components.SortCriteria;
import com.techtrade.rads.framework.utils.Utils;

public class FollowupService extends AbstractService implements IFollowupService{

	@Override
	protected String getTableName() {
		return "Followup";
	}
	
	@Override
	public Object getById(Object PK) {
		return getDAO().getById(PK);
	}

	@Override
	public TransactionResult createFollowup(SalesLeadExtended salesLeadExtended, SalesLead lead, CRMContext context) {
		Followup followup = new Followup();
		followup.setCompany(context.getCompany());
		followup.setLead(lead);
		followup.setFollowupDate(salesLeadExtended.getNextFollowupDate());
		return null;
	}

	@Override
	public List<CRMModelObject> listData(int from, int to,
			String whereCondition, CRMContext context, SortCriteria sortCriteria) {
		return super.listData("Followup", from, to, whereCondition, context,sortCriteria);
	}

	@Override
	public List<RadsError> validateforCreate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((Followup)object).setCompany(company);
		massageData((Followup)object,context);
		FollowupValidator validator = new FollowupValidator(context);
		return validator.validateforCreate(object);
	}

	@Override
	public List<RadsError> validateforUpdate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((Followup)object).setCompany(company);
		massageData((Followup)object,context);
		FollowupValidator validator = new FollowupValidator(context);
		return validator.validateforUpdate(object);
	}


	private void updateSalesLead(Followup followup,CRMContext context) {
		SalesLead lead= followup.getLead();
		if (followup.isFinalFollowup()) {
			if (followup.getResult().equals(CRMConstants.FOLLOWUP_RESULT.SOLD) || followup.getResult().equals(CRMConstants.FOLLOWUP_RESULT.PARTSALE)) {
				lead.setStatus(new FiniteValue(CRMConstants.SALESCYCLE_STATUS.CLOSED));
				lead.setSalesWon(true);
			}else 
				lead.setStatus(new FiniteValue(CRMConstants.SALESCYCLE_STATUS.FAILED));
			lead.setClosureDate(followup.getFollowupDate());
			lead.setSalesAssReason(followup.getResultReason());
			lead.setMgrReason(followup.getResultReason());
		} else {
			lead.setStatus(new FiniteValue(CRMConstants.SALESCYCLE_STATUS.IN_PROGRESS));
			if (followup.getOfferedPrice() != null && followup.getOfferedPrice() >0 ) {
				lead.setStatus(new FiniteValue(CRMConstants.SALESCYCLE_STATUS.NEGOTIATING));
				if (!Utils.isNullSet(lead.getSalesLeadLines()) &&  lead.getSalesLeadLines().size() == 1) {
					Iterator<SalesLeadLine> it = lead.getSalesLeadLines().iterator();
					while(it.hasNext()) {
						SalesLeadLine line = it.next() ;
						line.setNegotiatedPrice(followup.getOfferedPrice());
					}
				}
			}
		}
		ISalesLeadService leadService = (ISalesLeadService)SpringObjectFactory.INSTANCE.getInstance("ISalesLeadService");
		leadService.update(lead, context);
	}
	
	public void massageData(Followup followup,CRMContext context)
	{
		if(followup.getResultReason() != null) {
			ReasonCode reasonCode= CommonUtil.getReasonCode(followup.getResultReason(), context) ;
			followup.setResultReason(reasonCode);
		}
		if (followup.getDivision() != null) {
			Division division  = CommonUtil.getDivisionObect(context, followup.getDivision());
			followup.setDivision(division);
		}
	}
	

	@Override
	public TransactionResult create(CRMModelObject object, CRMContext context) {
		Followup followup = (Followup) object ;
		System.out.println("followup json =" + followup.toJSON());
		TransactionResult result=  super.create(object, context);
		updateSalesLead(followup,context);
		return result; 
	}

	@Override
	public TransactionResult update(CRMModelObject object, CRMContext context) {
		Followup followup = (Followup) object ;
		TransactionResult result= super.update(object, context);
		updateSalesLead(followup,context);
		return result; 
	}

	@Override
	protected ORMDAO getDAO() {
		return (FollowupDAO) SpringObjectFactory.INSTANCE.getInstance("FollowupDAO");
	}

	@Override
	public List<Followup> getFollowupsforDayforAlerts(Date startDt) {
		FollowupDAO dao = (FollowupDAO)getDAO();
		
		return dao.getFollowupsforDayforAlerts(startDt, 1000 * 60 * 60 * 12);
	}

	@Override
	public List<Followup> findBySalesLead(SalesLead lead) {
		FollowupDAO dao = (FollowupDAO)getDAO();
		return		dao.getFollowupsforSalesLead(lead.getId());
		
	}
	
	


	
	

}
