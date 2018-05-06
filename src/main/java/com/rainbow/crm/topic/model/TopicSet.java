package com.rainbow.crm.topic.model;

import java.util.ArrayList;
import java.util.List;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;

public class TopicSet  extends CRMBusinessModelObject{
	
	List<Topic> openTopics ;
	
	int readReply;
	
	int currentTopic ;
	
	List<TopicLine> newReplies;

	Topic newTopic;
	
	String completeDiscussion;
	
	String postedReply;

	public List<Topic> getOpenTopics() {
		return openTopics;
	}

	public void setOpenTopics(List<Topic> openTopics) {
		this.openTopics = openTopics;
	}

	public void addOpenTopic(Topic openTopic) {
		if(openTopics == null)
			openTopics = new ArrayList<Topic>();
		openTopics.add(openTopic);
	}
	public int getReadReply() {
		return readReply;
	}

	public void setReadReply(int readReply) {
		this.readReply = readReply;
	}

	public int getCurrentTopic() {
		return currentTopic;
	}

	public void setCurrentTopic(int currentTopic) {
		this.currentTopic = currentTopic;
	}

	public List<TopicLine> getNewReplies() {
		return newReplies;
	}

	public void setNewReplies(List<TopicLine> newReplies) {
		this.newReplies = newReplies;
	} 

	public void addNewReply(TopicLine newReply) {
		if(newReplies == null)
			newReplies = new ArrayList<TopicLine>();
		newReplies.add(newReply);
	}

	public Topic getNewTopic() {
		return newTopic;
	}

	public void setNewTopic(Topic newTopic) {
		this.newTopic = newTopic;
	}

	public String getCompleteDiscussion() {
		return completeDiscussion;
	}

	public void setCompleteDiscussion(String completeDiscussion) {
		this.completeDiscussion = completeDiscussion;
	}

	public String getPostedReply() {
		return postedReply;
	}

	public void setPostedReply(String postedReply) {
		this.postedReply = postedReply;
	}
	
	
	
}
