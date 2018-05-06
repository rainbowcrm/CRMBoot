package com.rainbow.crm.common.messaging;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.alert.model.Alert;
import com.rainbow.crm.alert.service.IAlertService;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.inventory.model.InventoryDelta;
import com.rainbow.crm.inventory.model.InventoryUpdateObject;
import com.rainbow.crm.inventory.service.IInventoryService;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.crm.loyalty.service.ILoyaltyService;
import com.rainbow.crm.wishlist.service.IWishListService;
import com.techtrade.rads.framework.utils.Utils;

public class CRMMessageReceiver implements MessageListener {

	@Override
	public void onMessage(Message message) {
		Logwriter.INSTANCE.debug("message=" + message);
		ObjectMessage objMsg = (ObjectMessage)message ;
		try {
			if (objMsg.getObject() instanceof Alert) {
				createAlert((Alert)objMsg.getObject() );
				
			}
			if (objMsg.getObject() instanceof InventoryUpdateObject) {
				InventoryUpdateObject inventoryObject = (InventoryUpdateObject)objMsg.getObject() ; 
				IInventoryService service = (IInventoryService)SpringObjectFactory.INSTANCE.getInstance("IInventoryService");
				if (inventoryObject.getDivision() != null )
					service.updateInventory(inventoryObject);
				if (inventoryObject.isAddition()) {
					IWishListService wishService = (IWishListService)SpringObjectFactory.INSTANCE.getInstance("IWishListService");
					if (inventoryObject.getDivision() != null )
						wishService.generateSalesLead(inventoryObject, "AVLBLTY");
					else
						wishService.generateSalesLead(inventoryObject, "LOWPRICE");
				}
				if (!Utils.isNullString( inventoryObject.getSalesDoc())){
					ILoyaltyService loyaltyService = (ILoyaltyService)SpringObjectFactory.INSTANCE.getInstance("ILoyaltyService");
					loyaltyService.addToLoyalty(inventoryObject.getSalesDoc(), makeContext(inventoryObject));
				}
				
			}else if(objMsg.getObject() instanceof InventoryDelta) {
				InventoryDelta inventoryObject = (InventoryDelta)objMsg.getObject() ;
				IInventoryService service = (IInventoryService)SpringObjectFactory.INSTANCE.getInstance("IInventoryService");
				service.updateInventory(inventoryObject);
				
			}
		}catch(Exception ex) {
			Logwriter.INSTANCE.error(ex);
		}
		
	}
	
	
	
	
	private CRMContext makeContext(CRMBusinessModelObject model) {
		CRMContext context = new CRMContext();
		context.setLoggedinCompany(model.getCompany().getId());
		context.setAuthenticated(true);
		context.setUser(model.getLastUpdateUser());
		context.setBackgroundProcess(true);
		return context ;
	}
	
	private void createAlert(Alert alert) {
		IAlertService service = (IAlertService)SpringObjectFactory.INSTANCE.getInstance("IAlertService");
		CRMContext context  = makeContext(alert) ;
		alert.setStatus(new FiniteValue(CRMConstants.ALERT_STATUS.OPEN));
		service.create(alert, context);
	}
	
	

}
