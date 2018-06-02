package com.rainbow.crm.saleslead.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rainbow.crm.document.model.Document;
import com.rainbow.crm.followup.model.Followup;

public class SalesLeadExtended extends SalesLead {

	List<Followup> followups;
	List<Document > documents;

	Date nextFollowupDate;

	public List<Followup> getFollowups() {
		return followups;
	}
	public void setFollowups(List<Followup> followups) {
		this.followups = followups;
	}
	public List<Document> getDocuments() {
		return documents;
	}
	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}
	
	public void addFollowup(Followup followup) {
		if (followups == null)
			followups = new ArrayList<Followup> ();
		followups.add(followup);
	}
	
	public void addDocument(Document document) {
		if (documents == null)
			documents = new ArrayList<Document> ();
		documents.add(document);
	}

	public static  SalesLeadExtended create (SalesLead lead)
	{
		SalesLeadExtended extended = new SalesLeadExtended();
		extended.setId(lead.getId());
		extended.setDocNumber(lead.getDocNumber());
		extended.setCompany(lead.getCompany());
		extended.setDivision(lead.getDivision());
		extended.setCustomer(lead.getCustomer());
		extended.setClosureDate(lead.getClosureDate());
		extended.setReleasedDate(lead.getReleasedDate());
		extended.setRefDate(lead.getRefDate());
		extended.setComments(lead.getComments());
		extended.setSales(lead.getSales());
		extended.setDocNumber(lead.getDocNumber());
		extended.setMgrReason(lead.getMgrReason());
		extended.setSalesAssReason(lead.getSalesAssReason());
		extended.setSalesAssociate(lead.getSalesAssociate());
		extended.setRefNo(lead.getRefNo());
		extended.setReferall(lead.getReferall());
		extended.setSalesLeadLines(lead.getSalesLeadLines());
		extended.setStatus(lead.getStatus());
		extended.setAlerted(lead.isAlerted());
		extended.setSalesWon(lead.isSalesWon());
		return extended;
	}

	public Date getNextFollowupDate() {
		return nextFollowupDate;
	}

	public void setNextFollowupDate(Date nextFollowupDate) {
		this.nextFollowupDate = nextFollowupDate;
	}
}
