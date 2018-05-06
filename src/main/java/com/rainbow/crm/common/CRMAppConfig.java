package com.rainbow.crm.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CRMAppConfig {
	protected static Properties properties;

	public static CRMAppConfig INSTANCE = new CRMAppConfig(); 
	
	public  void initialize() throws IOException {
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("com/rainbow/crm/common/app.properties") ;
		if (inputStream != null ) {
			properties = new Properties();
			properties.load(inputStream);
		}
	}
	
	public  String getProperty(String prop) throws IOException{
		if (properties == null) {
			initialize();
		}
		return properties.getProperty(prop);
	}
	
	

}
