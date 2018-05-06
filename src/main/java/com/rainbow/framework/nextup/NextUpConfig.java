package com.rainbow.framework.nextup;

import com.techtrade.rads.framework.utils.Utils;

public class NextUpConfig {

	class NextUpComponent  {
		String fieldType;
		int fieldWidth;
		String fieldValue;
		
		public boolean isNull() {
			return Utils.isNull(fieldType);
		}
		public String getFieldType() {
			return fieldType;
		}
		public void setFieldType(String fieldType) {
			this.fieldType = fieldType;
		}
		public int getFieldWidth() {
			return fieldWidth;
		}
		public void setFieldWidth(int fieldWidth) {
			this.fieldWidth = fieldWidth;
		}
		public String getFieldValue() {
			return fieldValue;
		}
		public void setFieldValue(String fieldValue) {
			this.fieldValue = fieldValue;
		}
		
		
	}
	
	int company ;
	String program;
	NextUpComponent component1, component2, component3,component4;
	public int getCompany() {
		return company;
	}
	public void setCompany(int company) {
		this.company = company;
	}
	public String getProgram() {
		return program;
	}
	public void setProgram(String program) {
		this.program = program;
	}
	public NextUpComponent getComponent1() {
		return component1;
	}
	public void setComponent1(NextUpComponent component1) {
		this.component1 = component1;
	}
	public NextUpComponent getComponent2() {
		return component2;
	}
	public void setComponent2(NextUpComponent component2) {
		this.component2 = component2;
	}
	public NextUpComponent getComponent3() {
		return component3;
	}
	public void setComponent3(NextUpComponent component3) {
		this.component3 = component3;
	}
	public NextUpComponent getComponent4() {
		return component4;
	}
	public void setComponent4(NextUpComponent component4) {
		this.component4 = component4;
	}
}
