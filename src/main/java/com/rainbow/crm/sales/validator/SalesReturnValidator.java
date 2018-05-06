package com.rainbow.crm.sales.validator;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonErrorCodes;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.config.service.ConfigurationManager;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.sales.model.SalesLine;
import com.rainbow.crm.sales.service.ISalesService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.utils.Utils;

public class SalesReturnValidator extends CRMValidator {
	
	Sales sales ;
	 
	public SalesReturnValidator() {
		super();
	}

	public SalesReturnValidator(CRMContext context) {
		super(context);
	}

	@Override
	protected void checkforCreateErrors(ModelObject object) {
		checkforErrors(object);
		ISalesService service =(ISalesService)SpringObjectFactory.INSTANCE.getInstance("ISalesService");
		Sales exist = (Sales)service.getByBusinessKey(sales, context);
		if(exist != null) {
				errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Bill_No"))) ;
		}
		
	}

	@Override
	protected void checkforUpdateErrors(ModelObject object) {
		checkforErrors(object);
		ISalesService service =(ISalesService)SpringObjectFactory.INSTANCE.getInstance("ISalesService");
		Sales exist = (Sales)service.getByBusinessKey(sales, context);
		if(exist != null && exist.getId() != sales.getId()) {
				errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Bill_No"))) ;
		}
		
	}
	protected void checkforErrors(ModelObject object) {
		
		String allowReturnStr= ConfigurationManager.getConfig(ConfigurationManager.ALLOW_RETURNS, context);
		boolean allowReturns  = Utils.getBooleanValue(allowReturnStr);
		if(! allowReturns) {
			errors.add(CRMValidator.getErrorforCode(SalesErrorCodes.NOT_ALLOWED_TODO_RETURNS));
			return;
		}
		sales = (Sales) object ;
		if (sales.getCompany() == null){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Company"))) ;
		}
		if(Utils.isNull(sales.getSalesDate()) ){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Date"))) ;
		}
		if (Utils.isNullSet(sales.getSalesLines())) {
		//	errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Line_Items"))) ;
		}else {
			double saleTotal =0;
			for (SalesLine line : sales.getSalesLines()) {
				if (line.getSku() == null ) {
					errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Item"))) ;
				}else if (line.getSku().isDeleted() ) {
					errors.add(getErrorforCode(CommonErrorCodes.OBJECT_DELETED,externalize.externalize(context, "Item") + line.getSku().getCode())) ;
				}else if (line.getQty() <=0 ) {
					errors.add(getErrorforCode(CommonErrorCodes.SHOULD_BE_GREATER_THAN,externalize.externalize(context, "Return_Qty") + line.getSku().getCode(),"0") ) ;
				}else if (line.getReturnPrice() <=0 ) {
					errors.add(getErrorforCode(CommonErrorCodes.SHOULD_BE_GREATER_THAN,externalize.externalize(context, "Return_Price") + line.getSku().getCode(),"0") ) ;
				}
				if (line.getQty() > line.getOriginalQty()){
					errors.add(getErrorforCode(SalesErrorCodes.RETURN_QTY_EXCEEDS_ORIGINAL));
				}
				if (line.getReturnPrice() > line.getOriginalPrice()){
					errors.add(getErrorforCode(SalesErrorCodes.RETURN_PRICE_EXCEEDS_ORIGINAL));
				}	
				if (Utils.isNullList(errors)) {
					line.setQty(line.getQty() * -1 );
					line.setLineTotal(line.getQty() * line.getReturnPrice());
					saleTotal += line.getQty() * line.getReturnPrice();
				}
			}
			
			sales.setNetAmount(saleTotal +  sales.getTotalDisc() - sales.getTaxAmount());
		}
	}


}
