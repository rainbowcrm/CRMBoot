package com.rainbow.crm.login.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.hibernate.HibernateDAO;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.crm.login.model.Login;
import com.rainbow.crm.user.dao.UserDAO;
import com.rainbow.crm.user.model.User;
import com.rainbow.crm.user.service.IUserService;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.context.RadsContext;
import com.techtrade.rads.framework.controller.abstracts.GeneralController;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.ui.abstracts.UIPage;
import com.techtrade.rads.framework.utils.Utils;

public class LoginController extends  GeneralController{

	String sessionId ;
	@Override
	public IRadsContext generateContext(HttpServletRequest request,HttpServletResponse response,UIPage page) {
		//Logwriter.INSTANCE.instantiate(request.getServletContext());
		HibernateDAO.instantiate(request.getServletContext());
		SpringObjectFactory.INSTANCE.instantiate(request.getServletContext());
		sessionId = request.getSession().getId();
		CRMContext existingSession = LoginSQLs.loggedInUser(sessionId);
		if  (existingSession != null)
			 return existingSession;
		return new RadsContext();
	}
	
	@Override
	public IRadsContext generateContext(String authToken,UIPage page) {
		return LoginSQLs.loggedInUser(authToken);
	}

	public void generateLoginRecord(String user,boolean isMobileLogin) {
		CRMContext context = new CRMContext();
		context.setUser(user);
		context.setAuthenticated(true);
		context.setAuthenticationToken(sessionId);
		context.setMobileLogin(isMobileLogin);
		setContext(context);
		LoginSQLs.registerLogin(context);
	}
	

	
	
	
	@Override
	public PageResult submit(ModelObject object, String actionParam) {
		if (Utils.isNullString(actionParam))
			return submit(object); 
		else if("regMobileNotificationID".equalsIgnoreCase(actionParam)) {
			PageResult result = new PageResult();
			Login login=(Login) object;
			login.setMobileLogin(true);
			LoginSQLs.updateMobileLogin(login.getUsername(), login.getMobileNotificationId(),login.getAuthToken());
			result.setObject(login);
			return result;
		}else if("logout".equalsIgnoreCase(actionParam)) {
			PageResult result = new PageResult();
			Login login=(Login) object;
			if (!Utils.isNullString(login.getUsername())) {
				LoginSQLs.updateLogout(login.getUsername());
				login.setLoggedOut(true);
				result.setObject(login);
			}else if (getContext() instanceof CRMContext )  {
				String user  = ((CRMContext)getContext()).getUser();
				LoginSQLs.updateLogout(user);
				login.setLoggedOut(true);
				result.setObject(login);
			}
			
			return result;
		}
		return super.submit(object, actionParam);
	}

	@Override
	public PageResult submit(ModelObject object) {
		PageResult res = new PageResult(); 
		Login login = (Login)object;
		if (login == null || Utils.isNull(login.getUsername())){
			return res;
		}
		
		//boolean valid = GeneralSQLs.isUserValid(login.getUsername(), login.getPassword());
		//Object obj= UserDAO.INSTANCE.getById(login.getUsername()) ;
		IUserService service = (IUserService) SpringObjectFactory.INSTANCE.getInstance("IUserService") ;
		Object obj = service.getById(login.getUsername());
		if (obj == null ) {
			Logwriter.INSTANCE.debug("Not a valid user");
			RadsError error=  new RadsError("101","UnauthorizedAccess");
			res.addError(error);
			return res;
		}else {
			User user =(User) obj ;
			if (user.getPassword().equals(login.getPassword())) {
				Logwriter.INSTANCE.debug("Login Successfull");
				login.setLoggedInDivision(user.getDivision());
				if (user.getCompany().getId() == 1)
					res.setNextPageKey("sysadmin"); // newdivision
				else
					res.setNextPageKey("alerts"); // newdivision
				generateLoginRecord(user.getUserId(),login.isMobileLogin());
			}else {
				Logwriter.INSTANCE.debug("Wrong password");
				RadsError error=  new RadsError("102","Wrong password");
				res.addError(error);
				return res;
			}
		}
		CRMContext context =(CRMContext) getContext();
		login.setAuthToken(context.getAuthenticationToken());
		res.setObject(login);
		return res;
		
	}
	
	

	
	
}
