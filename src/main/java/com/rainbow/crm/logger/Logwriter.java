package com.rainbow.crm.logger;

import org.apache.log4j.LogManager;

public class Logwriter {

	 public static Logwriter INSTANCE = new Logwriter();

	public  static void logDebug(String message , Class currentClass)
	{
		LogManager.getLogger(currentClass).debug(message);
	}

	public  static void logTrace(String message , Class currentClass)
	{
		LogManager.getLogger(currentClass).trace(message);
	}

	public  static void logException(String message , Class currentClass , Exception ex)
	{
		LogManager.getLogger(currentClass).error(message,ex);
		ex.printStackTrace();
	}

	public  static void error(Exception ex)
	{

		LogManager.getLogger(INSTANCE.getClass()).error(ex);
		ex.printStackTrace();
	}

	public  static void debug(String message)
	{
		LogManager.getLogger(INSTANCE.getClass()).debug(message);
	}
}
