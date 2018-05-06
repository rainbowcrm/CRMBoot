package com.rainbow.crm.reasoncode.validator;

import com.rainbow.crm.reasoncode.model.ReasonCode;
import com.rainbow.crm.reasoncode.service.IReasonCodeService;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonErrorCodes;
import com.rainbow.crm.common.SpringObjectFactory;
import com.techtrade.rads.framework.model.abstracts.ModelObject;

public class ReasonCodeValidator extends CRMValidator {

	ReasonCode  reasonCode ;
	
	@Override
	protected void checkforCreateErrors(ModelObject object) {
		checkforErrors(object);
		IReasonCodeService service =(IReasonCodeService)SpringObjectFactory.INSTANCE.getInstance("IReasonCodeService");
		ReasonCode exist = (ReasonCode)service.getByName(reasonCode.getCompany().getId(), reasonCode.getReason());
		if(exist != null) {
				errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Reason"))) ;
		}
	}

	@Override
	protected void checkforUpdateErrors(ModelObject object) {
		checkforErrors(object);
		IReasonCodeService service =(IReasonCodeService)SpringObjectFactory.INSTANCE.getInstance("IReasonCodeService");
		ReasonCode exist = (ReasonCode)service.getByName(reasonCode.getCompany().getId(), reasonCode.getReason());
		if(exist != null && exist.getId() != reasonCode.getId()) {
				errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Reason"))) ;
		}
	}
	
	protected void checkforErrors(ModelObject object) {
		reasonCode =  (ReasonCode) object;
		if (reasonCode.getCompany() == null){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Company"))) ;
		}
		if (reasonCode.getReason() == null){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Reason"))) ;
		}
		if (reasonCode.getReasonType() == null){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Reason_Type"))) ;
		}
	}

	public ReasonCodeValidator(CRMContext context) {
		super(context);
	}
	public ReasonCodeValidator(){
		
	}
	
}
