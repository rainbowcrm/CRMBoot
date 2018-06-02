package com.rainbow.crm.followup.model;

import java.util.Date;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.reasoncode.model.ReasonCode;
import com.rainbow.crm.saleslead.model.SalesLead;
import com.techtrade.rads.framework.annotations.RadsPropertySet;
import com.techtrade.rads.framework.utils.Utils;

@RadsPropertySet(jsonTag="Followup",xmlTag="Followup")
public class Followup extends CRMBusinessModelObject{
   

	SalesLead lead;
	Division division;
	String conversation;
	Date followupDate ;
	FiniteValue confidenceLevel;
	
	FiniteValue communicationMode;

	FiniteValue status;

	Double offeredPrice;
	String salesAssociate ;
	Date nextFollwup;
	boolean finalFollowup;
	FiniteValue result;
	ReasonCode resultReason ;
	String comments;
	boolean alerted; 
	
	
	@RadsPropertySet(excludeFromJSON =true, excludeFromMap = true, excludeFromXML = true)
	public Division getDivision() {
		return division;
	}
	@RadsPropertySet(excludeFromJSON =true, excludeFromMap = true, excludeFromXML = true)
	public void setDivision(Division division) {
		this.division = division;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForXML=true,useBKForMap=true)
	public SalesLead getLead() {
		return lead;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForXML=true,useBKForMap=true)
	public void setLead(SalesLead lead) {
		this.lead = lead;
	}
	public String getConversation() {
		return conversation;
	}
	public void setConversation(String conversation) {
		this.conversation = conversation;
	}
	@RadsPropertySet(usePKForJSON=true,usePKForMap=true,usePKForXML=true)
	public FiniteValue getConfidenceLevel() {
		return confidenceLevel;
	}
	@RadsPropertySet(usePKForJSON=true,usePKForMap=true,usePKForXML=true)
	public void setConfidenceLevel(FiniteValue confidenceLevel) {
		this.confidenceLevel = confidenceLevel;
	}
	@RadsPropertySet(usePKForJSON=true,usePKForMap=true,usePKForXML=true)
	public FiniteValue getCommunicationMode() {
		return communicationMode;
	}
	@RadsPropertySet(usePKForJSON=true,usePKForMap=true,usePKForXML=true)
	public void setCommunicationMode(FiniteValue communicationMode) {
		this.communicationMode = communicationMode;
	}

	@RadsPropertySet(usePKForJSON=true,usePKForMap=true,usePKForXML=true)
	public FiniteValue getStatus() {
		return status;
	}

	@RadsPropertySet(usePKForJSON=true,usePKForMap=true,usePKForXML=true)
	public void setStatus(FiniteValue status) {
		this.status = status;
	}

	public Double getOfferedPrice() {
		return offeredPrice;
	}
	public void setOfferedPrice(Double offeredPrice) {
		this.offeredPrice = offeredPrice;
	}
	public String getSalesAssociate() {
		return salesAssociate;
	}
	public void setSalesAssociate(String salesAssociate) {
		this.salesAssociate = salesAssociate;
	}
	public Date getNextFollwup() {
		return nextFollwup;
	}
	public void setNextFollwup(Date nextFollwup) {
		this.nextFollwup = nextFollwup;
	}
	public boolean isFinalFollowup() {
		return finalFollowup;
	}
	public void setFinalFollowup(boolean finalFollowup) {
		this.finalFollowup = finalFollowup;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForMap=true,useBKForXML=true)
	public FiniteValue getResult() {
		return result;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForMap=true,useBKForXML=true)
	public void setResult(FiniteValue result) {
		this.result = result;
	}
	public String getComments() {
		return comments;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForMap=true,useBKForXML=true)
	public ReasonCode getResultReason() {
		return resultReason;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForMap=true,useBKForXML=true)
	public void setResultReason(ReasonCode resultReason) {
		this.resultReason = resultReason;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public boolean isAlerted() {
		return alerted;
	}
	public void setAlerted(boolean alerted) {
		this.alerted = alerted;
	}
	public Date getFollowupDate() {
		return followupDate;
	}
	public void setFollowupDate(Date followupDate) {
		this.followupDate = followupDate;
	}
	

}
