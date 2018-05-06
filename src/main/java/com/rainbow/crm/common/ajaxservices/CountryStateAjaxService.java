package com.rainbow.crm.common.ajaxservices;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rainbow.crm.common.CRMAppConfig;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.logger.Logwriter;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.context.RadsContext;
import com.techtrade.rads.framework.controller.abstracts.IAjaxLookupService;

public class CountryStateAjaxService implements IAjaxLookupService{
    public static final String COUNTRY_CODE  = "countryCode" ;
	private static JSONObject countryCaps  ;
    static {
    	try {
    		readFile();
    	}catch(Exception ex) {
    		
    	}
    }
	@Override
	public String lookupValues(Map<String, String> searchFields,IRadsContext ctx) {
		try {
			String country = searchFields.get(COUNTRY_CODE);
			return "{\"stateList\":" + getStates(country) + "}" ;
			/*if("IN".equalsIgnoreCase(country)) {
				return "{\"stateList\":" + getIndianStates().toString() + "}" ;
			}else if("US".equalsIgnoreCase(country)) {
				return "{\"stateList\":" + getAmerican().toString() + "}" ;
			}*/
		
		}catch(Exception ex) {
			Logwriter.INSTANCE.error(ex);
		}
		return "";
		
	}
	
	public Map<String,String> findStates(String countryCode){
		Map<String,String> ans = new HashMap<String,String> ();
		try { 
			if (countryCaps.opt(countryCode) != null ) {
				JSONObject json = countryCaps.getJSONObject(countryCode);
				Iterator it = json.keys() ;
				while(it.hasNext() ) {
					String code = String.valueOf(it.next()) ;
					String name = json.getString(code) ;
					ans.put(code, name);
				}
			}else {
				ans.put("UNKN", "Unknown");
			}
		}catch(JSONException ex) {
			Logwriter.INSTANCE.error(ex);
		}
		return ans;
	}
	
	public static String readFile(String filename) {
	    String result = "";
	    try {
	        BufferedReader br = new BufferedReader(new FileReader(filename));
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();
	        while (line != null) {
	            sb.append(line);
	            line = br.readLine();
	        }
	        result = sb.toString();
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}
	
	
	private static void readFile()  throws Exception{
		String filePath = CRMAppConfig.INSTANCE.getProperty("Country_State_JSON_Path");
		 String jsonData = readFile(filePath + "//" + "CountryStates.json");
		 countryCaps = new JSONObject(jsonData);
	}
	
	private JSONArray getStates(String countryCode) throws Exception{
		JSONArray ans = new JSONArray();
		if (countryCaps.opt(countryCode) != null ) {
			JSONObject json = countryCaps.getJSONObject(countryCode);
			Iterator it = json.keys() ;
			while(it.hasNext() ) {
				JSONObject state = new JSONObject();
				String code = String.valueOf(it.next()) ;
				String name = json.getString(code) ;
				state.put("value", code) ;
				state.put("text", name) ;
				ans.put(state);
			}
		}else {
			JSONObject state = new JSONObject();
			state.put("value", "unknown") ;
			state.put("text", "unknown") ;
			ans.put(state);
		}
		
		return ans; 
	}
	
	private JSONArray getIndianStates() throws Exception{
		JSONArray ans = new JSONArray();
		JSONObject kr = new JSONObject();
		kr.put("value", "KL");
		kr.put("text", "Kerala");
		ans.put(kr);
		JSONObject tn = new JSONObject();
		tn.put("value", "TN");
		tn.put("text", "TamilNadu");
		ans.put(tn);
		return ans;
	}
	
	private JSONArray getAmerican() throws Exception{
		JSONArray ans = new JSONArray();
		JSONObject kr = new JSONObject();
		
		kr.put("value", "GA");
		kr.put("text", "Georgia");
		ans.put(kr);
		JSONObject tn = new JSONObject();
		tn.put("value", "NJ");
		tn.put("text", "New Jersey");
		ans.put(tn);
		return ans;
	}

	@Override
	public IRadsContext generateContext(HttpServletRequest request) {
		return new RadsContext();
	}


	
	
}
