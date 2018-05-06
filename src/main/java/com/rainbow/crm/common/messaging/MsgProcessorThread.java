package com.rainbow.crm.common.messaging;

import javax.jms.ObjectMessage;

public class MsgProcessorThread extends Thread {

	ObjectMessage objMsg ;
	
	
	public void run( ) 
	{
		
	}


	public ObjectMessage getObjMsg() {
		return objMsg;
	}


	public void setObjMsg(ObjectMessage objMsg) {
		this.objMsg = objMsg;
	}
	
	
	
}
