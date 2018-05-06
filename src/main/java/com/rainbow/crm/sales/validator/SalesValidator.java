package com.rainbow.crm.sales.validator;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonErrorCodes;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.config.service.ConfigurationManager;
import com.rainbow.crm.inventory.model.Inventory;
import com.rainbow.crm.inventory.service.IInventoryService;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.sales.model.SalesLine;
import com.rainbow.crm.sales.service.ISalesService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.utils.Utils;

public class SalesValidator extends CRMValidator {

	Sales sales ;
	 
	

	@Override
	protected void checkforCreateErrors(ModelObject object) {
		checkforErrors(object);
		ISalesService service =(ISalesService)SpringObjectFactory.INSTANCE.getInstance("ISalesService");
		Sales exist = (Sales)service.getByBusinessKey(sales, context);
		if(exist != null) {
				errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Bill_No"))) ;
		}
		String trackString = ConfigurationManager.getConfig(ConfigurationManager.TRACK_INVENTORY, context);
		Boolean track = Utils.getBooleanValue(trackString) ;
		if (track != false )
			checkforInventory();
	}

	@Override
	protected void checkforUpdateErrors(ModelObject object) {
		checkforErrors(object);
		ISalesService service =(ISalesService)SpringObjectFactory.INSTANCE.getInstance("ISalesService");
		Sales exist = (Sales)service.getByBusinessKey(sales, context);
		if(exist != null && exist.getId() != sales.getId()) {
				errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Bill_No"))) ;
		}
		String trackString = ConfigurationManager.getConfig(ConfigurationManager.TRACK_INVENTORY, context);
		Boolean track = Utils.getBooleanValue(trackString) ;
		if (track != false )
			inventoryUpdation(exist);
	}
	protected void checkforErrors(ModelObject object) {
		sales = (Sales) object ;
		if (sales.getCompany() == null){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Company"))) ;
		}
		if(Utils.isNull(sales.getSalesDate()) ){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Date"))) ;
		}
		if (Utils.isNullSet(sales.getSalesLines())) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Line_Items"))) ;
		}else {
			for (SalesLine line : sales.getSalesLines()) {
				if (line.getSku() == null ) {
					errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Item"))) ;
				}else if (line.getSku().isDeleted() ) {
					errors.add(getErrorforCode(CommonErrorCodes.OBJECT_DELETED,externalize.externalize(context, "Item") + line.getSku().getCode())) ;
				}else if (line.getQty() <=0 ) {
					errors.add(getErrorforCode(CommonErrorCodes.SHOULD_BE_GREATER_THAN,externalize.externalize(context, "Qty") + line.getSku().getCode(),"0") ) ;
				}else if (line.getUnitPrice() <=0 ) {
					errors.add(getErrorforCode(CommonErrorCodes.SHOULD_BE_GREATER_THAN,externalize.externalize(context, "Unit_Price") + line.getSku().getCode(),"0") ) ;
				}
			}
		}
	}

	protected void checkforInventory()
	{
		String ignoreINVavailStr = ConfigurationManager.getConfig(ConfigurationManager.ALLOW_SALE_WITHNO_INVENTORY, context);
		Boolean ignoreINavail = Utils.getBooleanValue(ignoreINVavailStr) ;
		if(ignoreINavail || Utils.isNullSet(sales.getSalesLines()) || sales.isFutureDelivery() || sales.isReturn()) return ;
		for (SalesLine line : sales.getSalesLines()) {
			IInventoryService inventoryService = (IInventoryService)SpringObjectFactory.INSTANCE.getInstance("IInventoryService");
			Inventory inventory = inventoryService.getByItemandDivision(line.getSku(), sales.getDivision());
			if( inventory == null || inventory.getCurrentQty()  < line.getQty()  ) {
				errors.add(getErrorforCode(SalesErrorCodes.INVENTORY_NOT_AVLBLE,line.getSku().getName())); 
			}
		}
		
				
	}
	protected void inventoryUpdation(Sales oldObject)
	{
		String ignoreINVavailStr = ConfigurationManager.getConfig(ConfigurationManager.ALLOW_SALE_WITHNO_INVENTORY, context);
		Boolean ignoreINavail = Utils.getBooleanValue(ignoreINVavailStr) ;
		if(ignoreINavail || CommonUtil.isManagerRole(context.getLoggedInUser()) || Utils.isNullSet(sales.getSalesLines()) ) return ;
		if (sales.getSalesLines().size() != oldObject.getSalesLines().size()) {
			errors.add(getErrorforCode(SalesErrorCodes.MGR_CAN_EDIT_LINES));
			return ;
		}
		Object [] newLineArray = sales.getSalesLines().toArray();
		Object [] oldLineArray = oldObject.getSalesLines().toArray();
		for (int i =0 ; i < sales.getSalesLines().size() ; i ++) {
			SalesLine newLine = (SalesLine) newLineArray[i];
			SalesLine oldLine = (SalesLine) oldLineArray[i];
			if(newLine.getQty() != oldLine.getQty() ||  newLine.getSku().getId() != oldLine.getSku().getId()){
				errors.add(getErrorforCode(SalesErrorCodes.MGR_CAN_EDIT_LINES));
				return ;
			}
				
		}
	}
	
	public SalesValidator(CRMContext context) {
		super(context);
	}
	public SalesValidator(){
		
	}

	
}
