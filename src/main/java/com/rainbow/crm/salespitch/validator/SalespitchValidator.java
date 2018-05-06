package com.rainbow.crm.salespitch.validator;

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
import com.rainbow.crm.salespitch.model.Salespitch;
import com.rainbow.crm.salespitch.service.ISalespitchService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.utils.Utils;

public class SalespitchValidator extends CRMValidator {

	Salespitch salespitch = null;
	
	@Override
	protected void checkforCreateErrors(ModelObject object) {
		checkforErrors(object);
		ISalespitchService  service = (ISalespitchService) SpringObjectFactory.INSTANCE.getInstance("ISalespitchService");
		
	}

	@Override
	protected void checkforUpdateErrors(ModelObject object) {
		checkforErrors(object);
	}
	
	protected void checkforErrors(ModelObject object) {
		salespitch = (Salespitch) object;
		if(salespitch.getDivision()==null) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Division"))) ;
		}else {
			salespitch.setDivision(CommonUtil.getDivisionObect(context, salespitch.getDivision()));
		}
		if(salespitch.getSalesAssociate() == null) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Sales_Associate"))) ;
		}else {
			salespitch.setSalesAssociate(CommonUtil.getUser(context, salespitch.getSalesAssociate().getUserId()));
		}
		if (salespitch.getContact() != null){
			salespitch.setContact((Contact)CommonUtil.getCRMModelObject(context, salespitch.getContact(), "IContactService"));
		}
		if (salespitch.getTerritory() != null){
			salespitch.setTerritory((Territory)CommonUtil.getCRMModelObject(context, salespitch.getTerritory(), "ITerritoryService"));
		}
		if (salespitch.getItem() != null){
			salespitch.setItem((Item)CommonUtil.getCRMModelObject(context, salespitch.getItem(), "IItemService"));
		}	
		
		
		
	}
	
	public SalespitchValidator(CRMContext context) {
		super(context);
	}
	
	public SalespitchValidator(){
		
	}

	
}
