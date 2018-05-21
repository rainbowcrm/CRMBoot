package com.rainbow.crm.servicerequest.validator;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonErrorCodes;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.servicerequest.model.ServiceRequest;
import com.rainbow.crm.servicerequest.model.ServiceRequestLine;
import com.rainbow.crm.servicerequest.service.IServiceRequestService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.utils.Utils;

public class ServiceRequestValidator extends CRMValidator {

	ServiceRequest serviceRequest ;
	 
	@Override
	protected void checkforCreateErrors(ModelObject object) {
		checkforErrors(object);
		IServiceRequestService service =(IServiceRequestService)SpringObjectFactory.INSTANCE.getInstance("IServiceRequestService");
		ServiceRequest exist = (ServiceRequest)service.getByBusinessKey(serviceRequest, context);
		if(exist != null) {
				errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Doc_Number"))) ;
		}
		
		ServiceRequest existForSale = (ServiceRequest)service.getBySale(serviceRequest.getSales().getBillNumber(), context);
		if(existForSale != null ) {
			errors.add(getErrorforCode(ServiceRequestErrorCodes.FEEDBACK_ALREADY_CAPTURED,serviceRequest.getSales().getBillNumber())) ;
		}
	}

	@Override
	protected void checkforUpdateErrors(ModelObject object) {
		checkforErrors(object);
		IServiceRequestService service =(IServiceRequestService)SpringObjectFactory.INSTANCE.getInstance("IServiceRequestService");
		ServiceRequest exist = (ServiceRequest)service.getByBusinessKey(serviceRequest, context);
		if(exist != null && exist.getId() != serviceRequest.getId()) {
				errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Doc_Number"))) ;
		}
		ServiceRequest existForSale = (ServiceRequest)service.getBySale(serviceRequest.getSales().getBillNumber(), context);
		if(existForSale != null && existForSale.getId() != serviceRequest.getId() ) {
			errors.add(getErrorforCode(ServiceRequestErrorCodes.FEEDBACK_ALREADY_CAPTURED,serviceRequest.getSales().getBillNumber())) ;
		}
	}
	protected void checkforErrors(ModelObject object) {
		serviceRequest = (ServiceRequest) object ;
		if (serviceRequest.getCompany() == null){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Company"))) ;
		}
		if (serviceRequest.getCapturedBy() == null){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Captured_By"))) ;
		}
		if(Utils.isNull(serviceRequest.getServiceRequestDate())) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Date"))) ;
		}
		if (Utils.isNullSet(serviceRequest.getServiceRequestLines())) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Line_Items"))) ;
		}else {
			for (ServiceRequestLine line : serviceRequest.getServiceRequestLines()) {
				if (line.getServiceRequestObjectType() == null ) {
					errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Type"))) ;
				}
				if(line.getRating() <0  || line.getRating() > 10 ) {
					errors.add(getErrorforCode(ServiceRequestErrorCodes.RATING_RANGE_ERROR)) ;
				}
			}
		}
	}
	
	public ServiceRequestValidator(CRMContext context) {
		super(context);
	}
	public ServiceRequestValidator(){
		
	}

	
}
