package com.rainbow.crm.topic.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMGeneralController;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.topic.model.Topic;
import com.rainbow.crm.topic.model.TopicLine;
import com.rainbow.crm.topic.model.TopicSet;
import com.rainbow.crm.topic.service.ITopicService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult.Result;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.utils.Utils;

public class TopicSetController extends CRMGeneralController{

	@Override
	public PageResult submit(ModelObject object) {
		return new PageResult();
	}

	private List<RadsError> createNewTopic(Topic newTopic, CRMContext context)
	{
		ITopicService service  =  getService() ;
		List errors = service.validateforCreate(newTopic, context);
		if(!Utils.isNullList(errors))
			return errors;
		service.create(newTopic, context);
		return null;			
				
	}
	
	@Override
	public PageResult submit(ModelObject object, String actionParam) {
		TopicSet topicSet = (TopicSet) object;
		ITopicService service  =  getService() ;
		PageResult result  = new PageResult();
		if  ("Refresh".equalsIgnoreCase(actionParam)) { 
			List<Topic> topics = service.getOpenTopics((CRMContext)getContext());
			topicSet.setOpenTopics(topics);
			result.setObject(topicSet);
		} else if ("fetchLatest".equalsIgnoreCase(actionParam)) {
			int topicId = topicSet.getCurrentTopic() ;
			if (topicId > 0 ) {
				int readReply = topicSet.getReadReply();
				Topic topic = new Topic();
				topic.setId(topicId);
				List<TopicLine> updatedReplies=  service.getUpdatedReplies(topic, readReply, (CRMContext)getContext());
				topicSet.setNewReplies(updatedReplies);
			}
			result.setObject(topicSet);
		}else if("newTopic".equalsIgnoreCase(actionParam)) {
			List<RadsError>  errors = createNewTopic(topicSet.getNewTopic(),(CRMContext) getContext());
			if (errors != null) {
				result.setErrors(errors);
				result.setResult(Result.FAILURE);
				return result;
			}
		}else if ("closeTopic".equalsIgnoreCase(actionParam)) {
			int topicId = topicSet.getCurrentTopic() ;
			if (topicId > 0 ) {
				Topic topic = new Topic();
				topic.setId(topicId);
				List<RadsError>  errors = service.closeTopic(topic, (CRMContext) getContext());
				if (errors != null) {
					result.setErrors(errors);
					result.setResult(Result.FAILURE);
					return result;
				}
				
			}
		}
		return result;
	}

	
	public Map <String, String > getAllOpenTopics() {
		ITopicService service  =  getService() ;
		List<Topic> topics = service.getOpenTopics((CRMContext)getContext());
		Map<String, String> ans = new LinkedHashMap<String,String> ();
		if (topics != null ) {
			topics.forEach( topic -> { 
				ans.put(String.valueOf( topic.getId()),topic.getTitle());
			} ); 
		}
		return ans;
	}
	public ITopicService getService() {
		ITopicService serv = (ITopicService) SpringObjectFactory.INSTANCE.getInstance("ITopicService");
		return serv;
	}
	
	public Map <String, String > getAllTypes() {
		Map<String, String> ans = GeneralSQLs.getFiniteValues(CRMConstants.FV_SPFTYPE);
		return ans;
	}

}
