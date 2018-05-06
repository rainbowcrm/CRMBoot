package com.rainbow.crm.topic.model;

import java.util.Date;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.abstratcs.model.CRMItemLine;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.user.model.User;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

public class TopicLine extends CRMBusinessModelObject {

	int lineNumber;

	boolean deleted;
	String comments;
	String reply;
	User repliedBy;
	Boolean replyUseful;
	Date replyDate;

	Topic topic;

	public TopicLine() {
		id = 1;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@RadsPropertySet(excludeFromJSON = true, excludeFromMap = true, excludeFromXML = true)
	public Topic getTopic() {
		return topic;
	}

	@RadsPropertySet(excludeFromJSON = true, excludeFromMap = true, excludeFromXML = true)
	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	@RadsPropertySet(useBKForJSON =true, useBKForXML= true, useBKForMap=true)
	public User getRepliedBy() {
		return repliedBy;
	}

	@RadsPropertySet(useBKForJSON =true, useBKForXML= true, useBKForMap=true)
	public void setRepliedBy(User repliedBy) {
		this.repliedBy = repliedBy;
	}

	
	public Boolean getReplyUseful() {
		return replyUseful;
	}

	public void setReplyUseful(Boolean replyUseful) {
		this.replyUseful = replyUseful;
	}

	public Date getReplyDate() {
		return replyDate;
	}

	public void setReplyDate(Date replyDate) {
		this.replyDate = replyDate;
	}

}
