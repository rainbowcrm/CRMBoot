package com.rainbow.crm.common.messaging;

import org.springframework.jms.core.JmsTemplate;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.inventory.model.InventoryUpdateObject;

public class CRMMessageSender {

	public static void sendMessage(CRMModelObject object) {
		JmsTemplate template = (JmsTemplate)SpringObjectFactory.INSTANCE.getInstance("jmsTemplate") ;
		template.convertAndSend(object);
	}
}
