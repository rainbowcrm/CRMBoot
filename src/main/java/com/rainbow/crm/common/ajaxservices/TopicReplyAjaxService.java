package com.rainbow.crm.common.ajaxservices;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.topic.model.Topic;
import com.rainbow.crm.topic.model.TopicLine;
import com.rainbow.crm.topic.service.ITopicService;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.IAjaxUpdateService;
import com.techtrade.rads.framework.utils.Utils;

public class TopicReplyAjaxService implements IAjaxUpdateService {

	@Override
	public String update(String jsonString, IRadsContext ctx) {
	System.out.println(jsonString) ;
	try  
	{
		
	JSONTokener  tokener = new JSONTokener(jsonString);
	JSONObject root = new JSONObject(tokener);
	JSONObject response = new JSONObject();
	JSONArray array = new JSONArray();
	Integer topicId = root.optInt("selectedTopic");
	Integer repliesRead =  root.optInt("repliesRead");
	String replyPosted = root.optString("reply");
	Topic topic = new Topic();
	topic.setId(topicId);
	ITopicService service = (ITopicService) SpringObjectFactory.INSTANCE.getInstance("ITopicService");
	if(!Utils.isNullString(replyPosted) ) {
		service.addNewReply(topic, replyPosted, (CRMContext)ctx);
	}
	
	StringBuffer replies = new StringBuffer (  " {\n \"updatedReplies\": [  ");
	List<TopicLine> lines =  service.getUpdatedReplies(topic, repliesRead, (CRMContext)ctx);
	if (lines != null) {
		for(int i = 0 ; i  < lines.size() ; i ++ ) { 
			String replyJSON = lines.get(i).toJSON() ;
			replies.append( replyJSON  ) ;
			if (i < lines.size() -1 ) 
				replies.append( ",");
			replies.append("\n");
		}
		
	 replies.append("],\n");
	 replies.append("\"totalreplies\":"+ (repliesRead + lines.size()) );
	 replies.append("}");
	 return replies.toString();
	}
	
	}catch(JSONException ex) {
		
	}
		return null;
	}

	@Override
	public IRadsContext generateContext(HttpServletRequest request) {
		return CommonUtil.generateContext(request,null);
	}

	
}
