package com.rainbow.crm.topic.service;

import java.util.Date;
import java.util.List;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.ITransactionService;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.topic.model.Topic;
import com.rainbow.crm.topic.model.TopicLine;
import com.techtrade.rads.framework.model.abstracts.RadsError;

public interface ITopicService extends ITransactionService{

	
	public List<Topic> getUsersforItem (Sku sku, int divisionId, Date date) ;

	public List<Topic> getOpenTopics(CRMContext context) ;
	
	public List<TopicLine> getUpdatedReplies(Topic topic, int replyRead , CRMContext context);

	public List<RadsError> addNewReply(Topic topic, String reply,  CRMContext context );
	
	public List<RadsError> closeTopic(Topic topic, CRMContext context);
  
}
