package com.rainbow.crm.reasoncode.model;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.common.finitevalue.FiniteValueManager;
import com.rainbow.crm.company.model.Company;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

public class ReasonCode extends CRMBusinessModelObject {

	String reason;
	String description;
	FiniteValue reasonType;
	FiniteValue factorType;
	FiniteValue accessibleBy;
	FiniteValue orientation;
	Boolean useForSWOTAnalysis ;
	
	@RadsPropertySet(isBK=true)
	public String getReason() {
		return reason;
	}
	@RadsPropertySet(isBK=true)
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@RadsPropertySet(useBKForJSON=true, useBKForMap =true, useBKForXML=true)
	public FiniteValue getOrientation() {
		return orientation;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForMap =true, useBKForXML=true)
	public void setOrientation(FiniteValue orientation) {
		this.orientation = orientation;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForMap =true, useBKForXML=true)
	public FiniteValue getReasonType() {
		return reasonType;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForMap =true, useBKForXML=true)
	public void setReasonType(FiniteValue reasonType) {
		this.reasonType = reasonType;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForMap =true, useBKForXML=true)
	public FiniteValue getFactorType() {
		return factorType;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForMap =true, useBKForXML=true)
	public void setFactorType(FiniteValue factorType) {
		this.factorType = factorType;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForMap =true, useBKForXML=true)
	public FiniteValue getAccessibleBy() {
		return accessibleBy;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForMap =true, useBKForXML=true)
	public void setAccessibleBy(FiniteValue accessibleBy) {
		this.accessibleBy = accessibleBy;
	}
	public Boolean getUseForSWOTAnalysis() {
		return useForSWOTAnalysis;
	}
	public void setUseForSWOTAnalysis(Boolean useForSWOTAnalysis) {
		this.useForSWOTAnalysis = useForSWOTAnalysis;
	}
	
	
	
	
}
