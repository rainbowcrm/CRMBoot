package com.rainbow.crm.common;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.context.ISettings;

public class CRMDefaultSettings implements ISettings {

	private static CRMDefaultSettings INSTANCE = new CRMDefaultSettings();
	
	@Override
	public ISettings getDefaultInstance() {
		// TODO Auto-generated method stub
		return INSTANCE;
	}

	@Override
	public SimpleDateFormat getDateFormat(IRadsContext ctx) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyy");
		return dateFormat;
	}

	@Override
	public NumberFormat getCurrencyFormat(IRadsContext ctx) {
		NumberFormat numFormat = NumberFormat.getCurrencyInstance(new Locale("en", "US"));
		return numFormat;
	}

	@Override
	public String getCurrencySymbol(IRadsContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Locale getLocale(IRadsContext ctx) {
		return Locale.US;
	}

	
	

}
