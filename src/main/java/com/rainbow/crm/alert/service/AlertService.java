
package com.rainbow.crm.alert.service;

import java.util.Iterator;
import java.util.List;













import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.AbstractService;
import com.rainbow.crm.common.CRMAppConfig;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.hibernate.ORMDAO;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.crm.saleslead.model.SalesLead;
import com.rainbow.crm.saleslead.model.SalesLeadLine;
import com.rainbow.crm.saleslead.service.ISalesLeadService;
import com.rainbow.crm.user.model.User;
import com.rainbow.crm.user.service.IUserService;
import com.rainbow.crm.alert.dao.AlertDAO;
import com.rainbow.crm.alert.model.Alert;
import com.rainbow.crm.alert.validator.AlertValidator;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.ui.components.SortCriteria;
import com.techtrade.rads.framework.utils.Utils;

public class AlertService extends AbstractService implements IAlertService{

	
	
	@Override
	protected String getTableName() {
		return "Alert";
	}
	

	@Override
	public Object getById(Object PK) {
		return getDAO().getById(PK);
	}

	@Override
	public List<CRMModelObject> listData(int from, int to,
			String whereCondition, CRMContext context, SortCriteria sortCriteria) {
		return super.listData("Alert", from, to, whereCondition, context,sortCriteria);
	}

	@Override
	public List<RadsError> validateforCreate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((Alert)object).setCompany(company);
		AlertValidator validator = new AlertValidator(context);
		return validator.validateforCreate(object);
	}

	@Override
	public List<RadsError> validateforUpdate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((Alert)object).setCompany(company);
		AlertValidator validator = new AlertValidator(context);
		return validator.validateforUpdate(object);
	}


	@Override
	public TransactionResult create(CRMModelObject object, CRMContext context) {
		TransactionResult result=  super.create(object, context);
		pushAlertNotifictions((Alert) object,context);
		return result; 
	}

	@Override
	public TransactionResult update(CRMModelObject object, CRMContext context) {
		TransactionResult result= super.update(object, context);
		return result; 
	}

	@Override
	protected ORMDAO getDAO() {
		return (AlertDAO) SpringObjectFactory.INSTANCE.getInstance("AlertDAO");
	}

	@Override
	public List<RadsError> acknowledgeAlert(Alert alert, CRMContext context) {
		IUserService userService = (IUserService) SpringObjectFactory.INSTANCE.getInstance("IUserService") ;
		alert.setStatus(new FiniteValue(CRMConstants.ALERT_STATUS.ACKNOWLEDGED));
		User owner = (User)userService.getById(context.getUser());
 		alert.setOwner(owner);
		alert.setAcknowDate(new java.util.Date());
		getDAO().update(alert);
		return null;
	}

	@Override
	public List<RadsError> pushAlertNotifictions(Alert alert, CRMContext context) {
		if(alert.getOwner() != null) {
			String notificationID= LoginSQLs.getNotificationIDforUser(alert.getOwner().getUserId());
			pushNotification(alert, notificationID);
		}else {
			IUserService  userService = (IUserService)SpringObjectFactory.INSTANCE.getInstance("IUserService");
			List<User> users = userService.getByDivision(alert.getDivision(), context);
			if(!Utils.isNullList(users)) {
				users.forEach( user ->  {  
					if(alert.getOwner() != null ) {
					String notificationID= LoginSQLs.getNotificationIDforUser(alert.getOwner().getUserId());
					pushNotification(alert, notificationID);
					}
				});
			}
		}
		
		return null;
	}
	
	public void pushNotification(Alert alert, String notificationID )
	{
		try {
		String mobileAppId= 	CRMAppConfig.INSTANCE.getProperty("mobile_app_id");
		Sender sender = new  Sender(mobileAppId);
		Message message = new Message.Builder()
          .collapseKey("message")
          .timeToLive(3)
          .delayWhileIdle(true)
          .addData("message",alert.getData()) 
          .build();  
		Result result = sender.send(message,notificationID, 1);
		}catch(Exception ex)
		{
			Logwriter.INSTANCE.error(ex);
		}
		
		
	}
	
	


	
	

}
