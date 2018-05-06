package com.rainbow.crm.topic.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.user.model.User;
import com.rainbow.crm.vendor.model.Vendor;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

public class Topic extends CRMBusinessModelObject {

	Division division;
	User owner;
	String refNo;
	Date topicDate;
	FiniteValue portfolioType;
	String portfolioKey;
	String portfolioValue;
	String title;
	String question;
	String comments;
	Boolean closed;
	boolean salesMade;
	String salesBill;
	Sales sales;

	Set<TopicLine> topicLines;

	@RadsPropertySet(useBKForJSON = true, useBKForXML = true, useBKForMap = true)
	public Division getDivision() {
		return division;
	}

	@RadsPropertySet(useBKForJSON = true, useBKForXML = true, useBKForMap = true)
	public void setDivision(Division division) {
		this.division = division;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Set<TopicLine> getTopicLines() {
		return topicLines;
	}

	public void setTopicLines(Set<TopicLine> topicLines) {
		this.topicLines = topicLines;
	}

	public void addTopicLine(TopicLine line) {
		if (topicLines == null)
			topicLines = new LinkedHashSet<TopicLine>();
		topicLines.add(line);
	}

	@RadsPropertySet(useBKForJSON = true, useBKForXML = true, useBKForMap = true)
	public User getOwner() {
		return owner;
	}

	@RadsPropertySet(useBKForJSON = true, useBKForXML = true, useBKForMap = true)
	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public Date getTopicDate() {
		return topicDate;
	}

	public void setTopicDate(Date topicDate) {
		this.topicDate = topicDate;
	}

	@RadsPropertySet(usePKForJSON = true, usePKForXML = true, usePKForMap = true)
	public FiniteValue getPortfolioType() {
		return portfolioType;
	}

	@RadsPropertySet(usePKForJSON = true, usePKForXML = true, usePKForMap = true)
	public void setPortfolioType(FiniteValue portfolioType) {
		this.portfolioType = portfolioType;
	}

	public String getPortfolioKey() {
		return portfolioKey;
	}

	public void setPortfolioKey(String portfolioKey) {
		this.portfolioKey = portfolioKey;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public boolean isSalesMade() {
		return salesMade;
	}

	public void setSalesMade(boolean salesMade) {
		this.salesMade = salesMade;
	}

	public String getSalesBill() {
		return salesBill;
	}

	public void setSalesBill(String salesBill) {
		this.salesBill = salesBill;
	}

	public Sales getSales() {
		return sales;
	}

	public void setSales(Sales sales) {
		this.sales = sales;
	}

	public Boolean getClosed() {
		return closed;
	}

	public void setClosed(Boolean closed) {
		this.closed = closed;
	}

	public String getPortfolioValue() {
		return portfolioValue;
	}

	public void setPortfolioValue(String portfolioValue) {
		this.portfolioValue = portfolioValue;
	}

	
}
