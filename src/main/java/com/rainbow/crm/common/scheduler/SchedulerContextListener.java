package com.rainbow.crm.common.scheduler;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.rainbow.crm.common.SpringObjectFactory;

public class SchedulerContextListener   implements ServletContextListener {


	SchedulerThread stThread  =null;
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		try {
			stThread.stop();
        } catch (Exception ex) {
        	ex.printStackTrace();	
        }
	}

	@Override

	public void contextInitialized(ServletContextEvent arg0) {
		if (stThread == null) {
			stThread = new SchedulerThread();
		}
		SpringObjectFactory.INSTANCE.instantiate(arg0.getServletContext());
		stThread.start();
		
	}
	
	

}
