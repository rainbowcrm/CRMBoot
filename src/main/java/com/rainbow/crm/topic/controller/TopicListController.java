package com.rainbow.crm.topic.controller;

import java.util.List;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMListController;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.topic.model.Topic;
import com.rainbow.crm.topic.service.ITopicService;
import com.rainbow.crm.topic.validator.TopicValidator;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

public class TopicListController extends CRMListController{

	@Override
	public IBusinessService getService() {
		ITopicService serv = (ITopicService) SpringObjectFactory.INSTANCE.getInstance("ITopicService");
		return serv;
	}

	@Override
	public Object getPrimaryKeyValue(ModelObject object) {
		Topic topic = (Topic) object;
		return topic.getId();
				
	}

	@Override
	public PageResult submit(List<ModelObject> objects, String submitAction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RadsError> validateforDelete(List<ModelObject> objects) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RadsError> validateforEdit(List<ModelObject> objects) {
		TopicValidator validator = new TopicValidator((CRMContext) getContext());
		return validator.eligibleForEdit(objects);
	}

	@Override
	public PageResult goToEdit(List<ModelObject> objects) {
		PageResult result = new PageResult();
		result.setNextPageKey("newtopic");
		return result;
	}
	
	

}
