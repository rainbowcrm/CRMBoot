package com.rainbow.framework.nextup;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.division.model.Division;
import com.techtrade.rads.framework.utils.Utils;

public class NextUpGenerator {

	public static final String CONSTANT= "CONSTANT";
	public static final String YEARCOMP= "YEARCOMP";
	public static final String DIVCODE= "DIVCODE";
	public static final String MONTHCOMP= "MONTHCOMP";
	public static final String DAYCOMP= "DAYCOMP";
	public static final String DATECOMP= "DATECOMP";
	public static final String SEQCOMP= "SEQCOMP";
	
	public static String getNextNumber(String program, CRMContext context , Division division  ) {
		int companyCode = context.getLoggedinCompany() ;
		StringBuffer ansString = new StringBuffer();
		NextUpConfig config = NextUPSQL.getNextUpConfig(companyCode, program);
		
		int seq =NextUPSQL.getNextPKValue(program,(division == null)?-1: division.getId(), companyCode);
		String part1 = makeCompString(config.getComponent1(), division, seq);
		if (!Utils.isNull(part1)) {
			ansString.append(part1);
		}
		String part2 = makeCompString(config.getComponent2(), division, seq);
		if (!Utils.isNull(part2)) {
			ansString.append(part2);
		}
		String part3 = makeCompString(config.getComponent3(), division, seq);
		if (!Utils.isNull(part3)) {
			ansString.append(part3);
		}
		String part4 = makeCompString(config.getComponent4(), division, seq);
		if (!Utils.isNull(part4)) {
			ansString.append(part4);
		}
		return ansString.toString();
	}
	
	private static String makeCompString(NextUpConfig.NextUpComponent component ,Division division,int sequence) {
		StringBuffer buff = new StringBuffer();
		if (component == null || component.isNull()) return null ;
		if(component.getFieldType().equals(CONSTANT)) {
			return component.getFieldValue();
		} else if(component.getFieldType().equals(YEARCOMP)) {
			return String.valueOf(new java.util.Date().getYear() );
		} else if(component.getFieldType().equals(MONTHCOMP)) {
			return String.valueOf(new java.util.Date().getMonth() );
		} else if(component.getFieldType().equals(DAYCOMP)) {
			return String.valueOf(new java.util.Date().getMonth() );
		} else if(component.getFieldType().equals(DATECOMP)) {
			return String.valueOf(new java.util.Date().toLocaleString());
		} else if(component.getFieldType().equals(SEQCOMP)) {
			return String.valueOf(sequence);
		} else if(component.getFieldType().equals(DIVCODE)) {
			return String.valueOf(division.getCode());
		} 
		
		return null;
	}
	
	
	
	
}
