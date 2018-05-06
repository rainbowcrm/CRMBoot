package com.rainbow.crm.topic.validator;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonErrorCodes;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.topic.model.Topic;
import com.rainbow.crm.topic.model.TopicLine;
import com.rainbow.crm.topic.service.ITopicService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.utils.Utils;

public class TopicValidator extends CRMValidator {

	Topic topic ;
	 
	@Override
	protected void checkforCreateErrors(ModelObject object) {
		checkforErrors(object);
		ITopicService service =(ITopicService)SpringObjectFactory.INSTANCE.getInstance("ITopicService");
		Topic exist = (Topic)service.getByBusinessKey(topic, context);
		if(exist != null) {
				errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "User"))) ;
		}
		
	}

	@Override
	protected void checkforUpdateErrors(ModelObject object) {
		checkforErrors(object);
		ITopicService service =(ITopicService)SpringObjectFactory.INSTANCE.getInstance("ITopicService");
		Topic exist = (Topic)service.getByBusinessKey(topic, context);
		if(exist != null && exist.getId() != topic.getId()) {
				errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "User"))) ;
		}
		
	}
	protected void checkforErrors(ModelObject object) {
		topic = (Topic) object ;
		if (topic.getCompany() == null){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Company"))) ;
		}
		
		if(Utils.isNull(topic.getTopicDate()) ){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Date"))) ;
		}
		
		if(Utils.isNullString(topic.getTitle()) ){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Title"))) ;
		}
		
		if(Utils.isNullString(topic.getQuestion()) ){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Question"))) ;
		}
	}
	public TopicValidator(CRMContext context) {
		super(context);
	}
	public TopicValidator(){
		
	}

	
}
