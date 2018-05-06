package com.rainbow.crm.common.finitevalue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rainbow.crm.database.GeneralSQLs;

public class FiniteValueManager {

	Map <String,FiniteValue> finiteValueMap = null; 
	public static FiniteValueManager INSTANCE = new FiniteValueManager();
	
	private void initialize(){
		finiteValueMap = new HashMap<String,FiniteValue> ();
		List<FiniteValue> values =  GeneralSQLs.getAllFiniteValues();
		for (FiniteValue val : values) {
			finiteValueMap.put(val.getCode(),val) ;
		}
	}
	
	public FiniteValue getFiniteValue(String code) {
		if (finiteValueMap == null)  {
			initialize();
		}
		return finiteValueMap.get(code); 
	}
	
	public String getFiniteValueDesc(String code) {
		if (finiteValueMap == null)  {
			initialize();
		}
		if (finiteValueMap.get(code) != null)
			return finiteValueMap.get(code).getDescription() ;
		else
			return "";
	}
	
	
	
	
}
