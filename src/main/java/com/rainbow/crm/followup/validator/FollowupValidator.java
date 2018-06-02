package com.rainbow.crm.followup.validator;

import com.rainbow.crm.reasoncode.model.ReasonCode;
import com.rainbow.crm.reasoncode.service.IReasonCodeService;
import com.rainbow.crm.saleslead.model.SalesLead;
import com.rainbow.crm.saleslead.service.ISalesLeadService;
import com.rainbow.crm.user.model.User;
import com.rainbow.crm.user.service.IUserService;
import com.rainbow.crm.user.validator.UserErrorCodes;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonErrorCodes;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.followup.model.Followup;
import com.rainbow.crm.followup.service.IFollowupService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.utils.Utils;

public class FollowupValidator extends CRMValidator {

	Followup followup = null;
	
	@Override
	protected void checkforCreateErrors(ModelObject object) {
		checkforErrors(object);
		IFollowupService  service = (IFollowupService) SpringObjectFactory.INSTANCE.getInstance("IFollowupService");
		
	}

	@Override
	protected void checkforUpdateErrors(ModelObject object) {
		checkforErrors(object);
		IFollowupService  service = (IFollowupService) SpringObjectFactory.INSTANCE.getInstance("IFollowupService");
		
		
	}
	
	protected void checkforErrors(ModelObject object) {
		followup = (Followup) object;
		if(followup.getLead()==null) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Sales_Lead"))) ;
			return ;
		}else {
			ISalesLeadService leadService =(ISalesLeadService) SpringObjectFactory.INSTANCE.getInstance("ISalesLeadService");
			SalesLead lead= (SalesLead)leadService.getByBusinessKey(followup.getLead(), context);
			if (lead == null) {
				errors.add(getErrorforCode(CommonErrorCodes.FIELD_NOT_VALID,externalize.externalize(context, "Sales_Lead"))) ;
				return ;
			}else {
				followup.setLead(lead);
				followup.setDivision(lead.getDivision());
			}
			if (lead.getStatus() != null && ( lead.getStatus().equals(CRMConstants.SALESCYCLE_STATUS.CLOSED) || 
					lead.getStatus().equals(CRMConstants.SALESCYCLE_STATUS.FAILED))){
				errors.add(getErrorforCode(CommonErrorCodes.INVALID_STATUS,externalize.externalize(context, "Sales_Lead"))) ;
				return ;
			}
		}
		if(followup.getDivision() == null) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Division"))) ;
		} else if (followup.getDivision().getId() !=  followup.getLead().getDivision().getId()) {
			errors.add(getErrorforCode(FollowupErrorCodes.SALESLEAD_DIVISON_MISMATCH));
		}
		
		if(Utils.isNullString(followup.getSalesAssociate())) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Sales_Associate"))) ;
		}else {
			IUserService userService = (IUserService)SpringObjectFactory.INSTANCE.getInstance("IUserService");
			User user = new User();
			user.setUserId(followup.getSalesAssociate());
			user = (User)userService.getByBusinessKey(user, context);
			if (user == null) {
				errors.add(getErrorforCode(CommonErrorCodes.FIELD_NOT_VALID,externalize.externalize(context, "Sales_Associate"))) ;
			}else if (followup.getDivision() != null && user.getDivision().getId() != followup.getDivision().getId())  {
				errors.add(getErrorforCode(FollowupErrorCodes.ASSOCIATE_DIVISON_MISMATCH));
			}
			
			if (followup.getLead().getSalesAssociate() !=null && !followup.getLead().getSalesAssociate().equalsIgnoreCase(followup.getSalesAssociate())) {
				errors.add(getErrorforCode(FollowupErrorCodes.SALESLEAD_DIFFERENT_OWNER));
			}
			
		}
		if (Utils.isNull(followup.getCommunicationMode())) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Communication_Mode"))) ;
		}
		if (Utils.isNull(followup.getConfidenceLevel())) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Confidence_Level"))) ;
		}
		
		if(followup.isFinalFollowup()) {
			if(followup.getResultReason() != null) {
				IReasonCodeService reasonService = (IReasonCodeService)SpringObjectFactory.INSTANCE.getInstance("IReasonCodeService");
				ReasonCode reasonCode = (ReasonCode)reasonService.getById(followup.getResultReason().getId());
				if (reasonCode != null)
					followup.setResultReason(reasonCode);
				else
					errors.add(getErrorforCode(CommonErrorCodes.FIELD_NOT_VALID,externalize.externalize(context, "Result_Reason"))) ;
			}
			if(followup.getResultReason() == null) {
				errors.add(getErrorforCode(UserErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Result_Reason"))) ;
			}
			
		}
		
	}
	
	public FollowupValidator(CRMContext context) {
		super(context);
	}
	
	public FollowupValidator(){
		
	}

	
}
