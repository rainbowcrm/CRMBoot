
package com.rainbow.crm.common;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.rainbow.crm.company.dao.CompanyDAO;

public class SpringObjectFactory {

	ApplicationContext context  = null	;
	public static SpringObjectFactory INSTANCE = new SpringObjectFactory();
	
	public void instantiate(ServletContext ctx) {
		if (context == null) {
			 context = WebApplicationContextUtils.getWebApplicationContext(ctx);
		}
	}
	
	public  Object getInstance(String className) {
	/*	if("CompanyDAO".equals(className))
			  return CompanyDAO.INSTANCE ;*/
		return context.getBean(className);
	}
	
	
}
