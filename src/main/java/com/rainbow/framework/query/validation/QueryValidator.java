package com.rainbow.framework.query.validation;

import java.util.concurrent.atomic.AtomicInteger;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.user.validator.UserErrorCodes;
import com.rainbow.framework.query.model.Query;
import com.rainbow.framework.query.model.QueryCondition;
import com.rainbow.framework.setup.model.EntityField;
import com.rainbow.framework.setup.sql.MetadataSQL;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.utils.Utils;

public class QueryValidator extends CRMValidator {

	@Override
	protected void checkforCreateErrors(ModelObject object) {
		Query query  = ( Query) object;
		if(Utils.isNullString(query.getEntity()))
				errors.add(getErrorforCode(UserErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Transaction"))) ;
		if (Utils.isNullSet(query.getConditions()))
			errors.add(getErrorforCode(UserErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Conditions"))) ;
		if(Utils.isNullString(query.getDateValueType()))
			errors.add(getErrorforCode(UserErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Date_Criteria"))) ;
		if(Utils.isNullString(query.getResultType())) 
			errors.add(getErrorforCode(UserErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Result_Display"))) ;
		if("LIST".equalsIgnoreCase(query.getResultType())){
			if (query.getSelectedFields() == null)
				errors.add(getErrorforCode(UserErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Display_Fields"))) ;
			if(Utils.isNullString(query.getSortDesc()))
				errors.add(getErrorforCode(UserErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Sort_Desc"))) ;
		}
		
		if ("REL".equals(query.getDateValueType())) {
			if (query.getFromCriteria() == null || query.getFromCriteria().getCode() ==null)
				errors.add(getErrorforCode(UserErrorCodes.FIELD_EMPTY,externalize.externalize(context, "From"))) ;
			if (query.getToCriteria() == null || query.getToCriteria().getCode() ==null)
				errors.add(getErrorforCode(UserErrorCodes.FIELD_EMPTY,externalize.externalize(context, "To"))) ;	
		}
		if ("ABS".equals(query.getDateValueType())) {
			if (query.getFromDate() == null)
				errors.add(getErrorforCode(UserErrorCodes.FIELD_EMPTY,externalize.externalize(context, "From"))) ;
			if (query.getToDate() == null)
				errors.add(getErrorforCode(UserErrorCodes.FIELD_EMPTY,externalize.externalize(context, "To"))) ;	
		}
				
		
		if (!Utils.isNullSet(query.getConditions())) {
			AtomicInteger openBrackets = new AtomicInteger (0);
			AtomicInteger closeBrackets = new AtomicInteger (0);
			AtomicInteger lineNumber  = new AtomicInteger(1);
			query.getConditions().forEach( condition ->  { 
				EntityField field = MetadataSQL.getEntityField(query.getEntity(),condition.getField());
				condition.setDataType(field.getDataType());
				condition.setCompany(query.getCompany());
				condition.setQuery(query);
				condition.setLineNumber(lineNumber.getAndIncrement());
				int  opCount  = (condition.getOpenBrackets()!= null)?condition.getOpenBrackets().trim().length():0;
				condition.setNoOpenBrackets(opCount);
				int cloCount =  (condition.getCloseBrackets()!= null)?condition.getCloseBrackets().trim().length():0;
				condition.setNoCloseBrackets(cloCount);
				openBrackets.getAndAdd(opCount);
				closeBrackets.getAndAdd(cloCount);
			});
			if(openBrackets.get() != closeBrackets.get()) {
				errors.add(getErrorforCode(QueryErrorCodes.OPEN_CLOSE_BRACKETMISMATCH));
			}
		}
		
	}

	@Override
	protected void checkforUpdateErrors(ModelObject object) {
		// TODO Auto-generated method stub
		
	}

	public QueryValidator() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QueryValidator(CRMContext context) {
		super(context);
	}
	
	

}
