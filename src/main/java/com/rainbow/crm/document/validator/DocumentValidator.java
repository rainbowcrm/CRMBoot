package com.rainbow.crm.document.validator;

import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.item.service.ISkuService;
import com.rainbow.crm.saleslead.model.SalesLead;
import com.rainbow.crm.saleslead.service.ISalesLeadService;
import com.rainbow.crm.territory.model.Territory;
import com.rainbow.crm.user.validator.UserErrorCodes;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonErrorCodes;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.contact.model.Contact;
import com.rainbow.crm.document.model.Document;
import com.rainbow.crm.document.service.IDocumentService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.utils.Utils;

public class DocumentValidator extends CRMValidator {

	Document document = null;
	
	@Override
	protected void checkforCreateErrors(ModelObject object) {
		
		checkforErrors(object);
		
		IDocumentService  service = (IDocumentService) SpringObjectFactory.INSTANCE.getInstance("IDocumentService");
		Document  exist = (Document)service.getByBusinessKey(document, context)	;
		if(exist != null ) {
			errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Document_Name"))) ;
		}
	}

	@Override
	protected void checkforUpdateErrors(ModelObject object) {
		checkforErrors(object);
		IDocumentService  service = (IDocumentService) SpringObjectFactory.INSTANCE.getInstance("IDocumentService");
		Document  exist = (Document)service.getByBusinessKey(document, context)	;
		if(exist != null && exist.getId() != document.getId() ) {
			errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Document_Name"))) ;
		}
		if(exist != null && !exist.getOwner().getUserId().equals(context.getUser())  && !CommonUtil.isManagerRole(context.getLoggedInUser()) ){
			errors.add(getErrorforCode(DocumentErrorCodes.DOCUMENT_UNEDITABLE)) ;
		}
	}
	
	
	
	@Override
	protected void checkforDeleteEligibility(ModelObject object) {
		IDocumentService  service = (IDocumentService) SpringObjectFactory.INSTANCE.getInstance("IDocumentService");
		Document  exist = (Document)service.getByBusinessKey(document, context)	;
		if(exist != null && !exist.getOwner().getUserId().equals(context.getUser())  && !CommonUtil.isManagerRole(context.getLoggedInUser()) ){
			errors.add(getErrorforCode(DocumentErrorCodes.DOCUMENT_UNEDITABLE)) ;
		}
	}

	protected void checkforDeleteErrors(ModelObject object) {
		checkforErrors(object);
		IDocumentService  service = (IDocumentService) SpringObjectFactory.INSTANCE.getInstance("IDocumentService");
		Document  exist = (Document)service.getByBusinessKey(document, context)	;
		if(exist != null && exist.getId() != document.getId() ) {
			errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Document_Name"))) ;
		}
		if(exist != null && !exist.getOwner().getUserId().equals(context.getUser())  && !CommonUtil.isManagerRole(context.getLoggedInUser()) ){
			errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "DOCUMENT_UNEDITABLE"))) ;
		}
	}
	
	protected void checkforErrors(ModelObject object) {
		document = (Document) object;
		if (Utils.isNull(document.getDocName())) {
			errors.add(getErrorforCode(UserErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Document_Name"))) ;
		}
		if(document.getDocType() == null) {
			errors.add(getErrorforCode(UserErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Document_Type"))) ;
		}
	}
	
	public DocumentValidator(CRMContext context) {
		super(context);
	}
	
	public DocumentValidator(){
		
	}

	
}
