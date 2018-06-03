
package com.rainbow.crm.common;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.rainbow.crm.followup.model.Followup;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.velocity.app.VelocityEngine;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.brand.model.Brand;
import com.rainbow.crm.brand.service.IBrandService;
import com.rainbow.crm.category.model.Category;
import com.rainbow.crm.category.service.ICategoryService;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.config.service.ConfigurationManager;
import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.customer.service.ICustomerService;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.division.service.IDivisionService;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.item.service.IItemService;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.crm.product.model.Product;
import com.rainbow.crm.product.service.IProductService;
import com.rainbow.crm.reasoncode.model.ReasonCode;
import com.rainbow.crm.reasoncode.service.IReasonCodeService;
import com.rainbow.crm.user.model.User;
import com.rainbow.crm.user.service.IUserService;
import com.rainbow.framework.setup.model.Metadata;
import com.rainbow.framework.setup.sql.MetadataSQL;
import com.rainbow.framework.utils.EmailComponent;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.ui.abstracts.UIPage;
import com.techtrade.rads.framework.utils.Utils;

public class CommonUtil {
	
	private static Map <String,Metadata> metadataMap = new HashMap<String,Metadata> ();

	private static String [] colors = { "Red","Blue" ,"Green" , "Violet" , "Indigo" , "Majenta" ,"Brown" ,"Yellow" , "Orange", 
			"Salmon","Gray","SandyBrown","Ivory","CadetBlue","OrangeRed","SeaGreen","rosy brown","chocolate","lemon chiffon","wheat","deep pink","orchid",
			"purple","medium purple"} ;
	
	public static String [] getGraphColors() {
		return colors;
	}

	public static String getFileExtn(String fullName) {
		if (fullName.contains(".")) {
			String laterPart = fullName.substring(fullName.indexOf(".")+1,fullName.length());
			return laterPart;
		}
		return "";
	}

	public static String getTokenfromSession(String sessionId)
	{
		return sessionId;
	}
	
	public static String getSessionfromToken(String token)
	{
		return token;
	}

	public static EmailComponent getEmailSession(String to) throws Exception 
	{
		EmailComponent emailComponent = new EmailComponent();
		String from  = "noresponse@primussol.com";
		String host = CRMAppConfig.INSTANCE.getProperty("smtp_provider");
		String port =CRMAppConfig.INSTANCE.getProperty("smtp_port");
		String authuser =CRMAppConfig.INSTANCE.getProperty("smtp_authuser");
		String authpwd =CRMAppConfig.INSTANCE.getProperty("smtp_password");
		Properties properties = System.getProperties();
		properties.put("mail.transport.protocol", "smtp");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		
		Session session = Session.getInstance(properties,
		        new javax.mail.Authenticator() {
			
		            protected PasswordAuthentication getPasswordAuthentication() {
		                return new PasswordAuthentication(authuser, authpwd);
		            }
		        });
		emailComponent.setSession(session);
		emailComponent.setPort(port);
		emailComponent.setHost(host);
		emailComponent.setAuthUser(authuser);
		emailComponent.setAuthPassword(authpwd);
		emailComponent.setProperties(properties);
		emailComponent.setFrom(from);
		emailComponent.setTo(to);
		
		return emailComponent;
	}
	
	private static boolean changeFolder(FTPClient ftpClient, String companyCode, String subFolder) throws Exception
	{
		  ftpClient.changeWorkingDirectory(companyCode);
		  int  returnCode = ftpClient.getReplyCode();
		    if (returnCode == 550) {
		    	ftpClient.makeDirectory(companyCode) ;
		    	 ftpClient.changeWorkingDirectory(companyCode);
		    }
		    ftpClient.changeWorkingDirectory(subFolder); 
		  returnCode = ftpClient.getReplyCode();
		    if (returnCode == 550) {
		    	ftpClient.makeDirectory(subFolder) ;
		    	 ftpClient.changeWorkingDirectory(subFolder);
		    	
		    }
		    return true ;
	}
	
	public static VelocityEngine getVelocityEngine()
	{
		VelocityEngine ve = new VelocityEngine();
		try {
		
		 String path = CRMAppConfig.INSTANCE.getProperty("VelocityTemplatePath");
        ve.setProperty("file.resource.loader.path", path);
        ve.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.SimpleLog4JLogSystem");
        ve.setProperty("runtime.log.logsystem.log4j.category", "velocity");
        ve.setProperty("runtime.log.logsystem.log4j.logger", "velocity");
        ve.init();
		}catch(Exception ex)
		{
			Logwriter.INSTANCE.error(ex);
		}
	    return ve;     
		
	}
	
	public static void uploadFile (byte[] bytes, String fileName, CRMContext context, String subFolder )
	{
		if (Utils.isNullString(fileName))
			return;
		
		FTPClient  ftpClient = new FTPClient ();
		try {
			String host = CRMAppConfig.INSTANCE.getProperty("doc_server_host");
			String user = CRMAppConfig.INSTANCE.getProperty("doc_server_user");
			String pass = CRMAppConfig.INSTANCE.getProperty("doc_server_password");
			String port = CRMAppConfig.INSTANCE.getProperty("doc_server_port");
			ftpClient.setDefaultTimeout(100000);
			if (Integer.parseInt(port) > 0)
				ftpClient.connect(host,Integer.parseInt(port));
			else
				ftpClient.connect(host);
			ftpClient.enterLocalPassiveMode();
			int reply = ftpClient.getReplyCode();
			if (FTPReply.isPositiveCompletion(reply)) {
				
				ftpClient.login(user, pass);
				//FTPFile[] files = ftpClient.listFiles(directory);
				 
				//ftpClient.enterLocalPassiveMode();
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
				ByteArrayInputStream inputStream = new ByteArrayInputStream(
						bytes);
				System.out.println("Start uploading first file");
				changeFolder(ftpClient, context.getLoggedinCompanyCode(),subFolder);
				boolean done = ftpClient.storeFile(fileName, inputStream);
				inputStream.close();
/*				InputStream st2 = ftpClient.retrieveFileStream("/public_html/pics/MP003-a.jpg");
				if (done) {
					System.out
							.println("The first file is uploaded successfully.");
				}
*/			}

		} catch (Exception ex) {
			Logwriter.INSTANCE.error(ex);
		} finally {
			try {
				if (ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
				}
			} catch (IOException ex) {
				Logwriter.INSTANCE.error(ex);
			}
		}
	}
	
	public static Date getRelativeDate(FiniteValue dateValue) {
			if(CRMConstants.RELDATE.TODAY.equals(dateValue.getCode())) {
				return new java.util.Date();
			}else if(CRMConstants.RELDATE.LASTWEEK.equals(dateValue.getCode())) {
				return new java.util.Date (new java.util.Date().getTime() -  (7l * 24l  * 3600l * 1000l));
			}else if(CRMConstants.RELDATE.LASTWEEK.equals(dateValue.getCode())) {
				return new java.util.Date (new java.util.Date().getTime() -  (7l * 24l   * 3600l * 1000l));
			}else if(CRMConstants.RELDATE.LASTMONTH.equals(dateValue.getCode())) {
				return new java.util.Date (new java.util.Date().getTime() -  (30l * 24l   * 3600l * 1000l));
			}else if(CRMConstants.RELDATE.LASTTWOMONTH.equals(dateValue.getCode())) {
				return new java.util.Date (new java.util.Date().getTime() -  (60l * 24l   * 3600l * 1000l));
			}else if(CRMConstants.RELDATE.LASTTHREEMONTH.equals(dateValue.getCode())) {
				return new java.util.Date (new java.util.Date().getTime() -  (91l * 24l   * 3600l * 1000l));
			}else if(CRMConstants.RELDATE.LASTSIXMONTH.equals(dateValue.getCode())) {
				return new java.util.Date (new java.util.Date().getTime() -  (182l * 24l   * 3600l * 1000l));
			}else if(CRMConstants.RELDATE.LASTYEAR.equals(dateValue.getCode())) {
				return new java.util.Date (new java.util.Date().getTime() -  (365l * 24l   * 3600l * 1000l));
			}else if(CRMConstants.RELDATE.LASTTWOYEAR.equals(dateValue.getCode())) {
				return new java.util.Date (new java.util.Date().getTime() -  (365l * 2l * 24l   * 3600l * 1000l));
			}else if(CRMConstants.RELDATE.LASTTHREEYEAR.equals(dateValue.getCode())) {
				return new java.util.Date (new java.util.Date().getTime() -  (365l * 3l * 24l   * 3600l * 1000l));
			}else
				return null;
	}
	

	public static Division getDefaultDivision(CRMContext context)
	{
		int company  = context.getLoggedinCompany() ;
		IDivisionService service =(IDivisionService)SpringObjectFactory.INSTANCE.getInstance("IDivisionService");
		return service.getDefaultDivision(company);
	}
	
	public static User getDivisionManager(Division division,CRMContext context) 
	{
		IUserService userService= (IUserService) SpringObjectFactory.INSTANCE.getInstance("IUserService");
		List<User> users =  userService.getByDivision(division, context);
		for (User user : users) {
			if ( isManagerRole(user) ) return user;
		}
		return null;		
	}
	
	public static User getUser(CRMContext context, String userId){
		IUserService service = (IUserService) SpringObjectFactory.INSTANCE.getInstance("IUserService");
		User user  = (User) service.getById(userId);
		return user;
	}
	
	public static Company getCompany(int company){
		ICompanyService service = (ICompanyService) SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company comp  = (Company) service.getById(company);
		return comp;
	}
	
	public static boolean isManagerRole(User user) {
		if (CRMConstants.ROLETYPE.CORPADMIN.equals(user.getRoleType())  ||  CRMConstants.ROLETYPE.SYSADMIN.equals(user.getRoleType()) 
				|| CRMConstants.ROLETYPE.MANAGER.equals(user.getRoleType()) )
			return true;
		else
			return false;
	}
	
	public static boolean isCorporateUser(User user) {
		
		if (CRMConstants.ROLETYPE.CORPADMIN.equals(user.getRoleType())  ||  CRMConstants.ROLETYPE.SYSADMIN.equals(user.getRoleType()))
				return true;
		else
			return false;
	}
	
	public static boolean allowAllDivisionAccess(CRMContext ctx)  {
		if (ctx.isBackgroundProcess() ) return true;
		User user = ctx.getLoggedInUser();
		if (isCorporateUser(user)) return true;
		String allow = isManagerRole(user) ? ConfigurationManager
				.getConfig(ConfigurationManager.MANAGER_ACC_ALLDIV, ctx)
				: ConfigurationManager.getConfig(ConfigurationManager.ASSOCIATE_ACC_ALLDIV, ctx);
		boolean allowAll = Boolean.parseBoolean(allow);
		return allowAll;
	}
	
	public static IRadsContext generateContext(HttpServletRequest request,
			HttpServletResponse response,UIPage page ) {
		String authToken  =  request.getParameter("authToken");
		if(Utils.isNullString(authToken))
			authToken = getTokenfromSession(request.getSession().getId());
		CRMContext context=  LoginSQLs.loggedInUser(authToken);
		User user = CommonUtil.getUser(context, context.getUser());
		context.setLoggedInUser(user);
		if (page != null && !Utils.isNull(page.getAccessCode()) ) {
			context.setAuthorized(AuthorizationUtil.isAccessAllowed(page.getAccessCode(), user));
		}else  {
			context.setAuthorized(true);
		}
		if(page != null)
			context.setPageAccessCode(page.getAccessCode());
		return context;
	}
	
	public static IRadsContext generateContext(String authToken,UIPage page) {
		CRMContext context=  LoginSQLs.loggedInUser(authToken);
		if (context == null ) {
			CRMContext contextUnAuth = new CRMContext();
			contextUnAuth.setAuthenticated(false);
			return contextUnAuth;
		}
		User user = CommonUtil.getUser(context, context.getUser());
		context.setLoggedInUser(user);
		if (page != null && !Utils.isNull(page.getAccessCode()) ) {
			context.setAuthorized(AuthorizationUtil.isAccessAllowed(page.getAccessCode(), user));
		}else  {
			context.setAuthorized(true);
		}
		if(page != null)
			context.setPageAccessCode(page.getAccessCode());
		return context;
	}
	
	public static IRadsContext generateContext(HttpServletRequest request,UIPage page) {
		String authToken  =  String.valueOf(request.getAttribute("authToken"));
		if(Utils.isNullString(authToken))
			authToken = getTokenfromSession(request.getSession().getId());
		CRMContext context=  LoginSQLs.loggedInUser(authToken);
		if (context == null ) {
			CRMContext contextUnAuth = new CRMContext();
			contextUnAuth.setAuthenticated(false);
			return contextUnAuth;
		}
		User user = CommonUtil.getUser(context, context.getUser());
		context.setLoggedInUser(user);
		if (page != null && !Utils.isNull(page.getAccessCode()) ) {
			context.setAuthorized(AuthorizationUtil.isAccessAllowed(page.getAccessCode(), user));
		}else  {
			context.setAuthorized(true);
		}
		if(page != null)
			context.setPageAccessCode(page.getAccessCode());
		return context;
	}
	
	public static Metadata getMetaDataforClass(String classId)
	{
		if(metadataMap.containsKey(classId)) {
			return metadataMap.get(classId) ;
		}else {
			List<Metadata> metadataList = MetadataSQL.getMetadata();
			metadataList.forEach( metadata -> {   
				String className  =metadata.getClassName() ;
				StringTokenizer tokenizer = new StringTokenizer(className,".");
				int countTokens =  tokenizer.countTokens();
				String lastToken = null ;
				while(tokenizer.hasMoreElements()) {
					lastToken = String.valueOf(tokenizer.nextElement());
				}
				metadataMap.put(lastToken, metadata);
			});
			return metadataMap.get(classId) ;
		}
	}
	
	
	public static Customer getCustomerObect(CRMContext context , Customer customer) {
		ICustomerService customerService = (ICustomerService) SpringObjectFactory.INSTANCE.getInstance("ICustomerService");
		Customer retdivision = null ;
		if(customer.getId() > 0 ) {
			retdivision =(Customer) customerService.getById(customer.getId());
		} else if (!Utils.isNullString(customer.getPhone())) {
			retdivision =(Customer) customerService.getByBusinessKey(customer, context);
		}
		return retdivision;
	}
	
	public static Division getDivisionObect(CRMContext context , Division division) {
		IDivisionService divisionService = (IDivisionService) SpringObjectFactory.INSTANCE.getInstance("IDivisionService");
		Division retdivision = null ;
		if(division.getId() > 0 ) {
			retdivision =(Division) divisionService.getById(division.getId());
		} else if (!Utils.isNullString(division.getName())) {
			retdivision =(Division) divisionService.getByBusinessKey(division, context);
		}
		return retdivision;
	}
	
	public static User getUser(CRMContext context , User user) {
		IUserService userService = (IUserService) SpringObjectFactory.INSTANCE.getInstance("IUserService");
		User retUser =(User) userService.getById(user.getUserId());
		return retUser;
	}
	
	public static CRMModelObject getCRMModelObject(CRMContext context,CRMBusinessModelObject modelObject, String interfaceName ) {
		IBusinessService  businessService =(IBusinessService)  SpringObjectFactory.INSTANCE.getInstance(interfaceName);
		CRMBusinessModelObject retObject = null;
		if (modelObject == null)
			return null;
		if (  modelObject.getPK() != null ) {
			retObject = (CRMBusinessModelObject) businessService.getById(modelObject.getPK());
		}else if  (!Utils.isNullMap(modelObject.getBK())) {
			retObject = (CRMBusinessModelObject) businessService.getByBusinessKey(modelObject, context);
		}
		return retObject;
	}

	
	public static String getSalesPortfolioKey(FiniteValue type, String value,
			CRMContext context) {
		if (CRMConstants.SALESPFTYPE.CATEGORY.equals(type.getCode())) {
			ICategoryService service = (ICategoryService) SpringObjectFactory.INSTANCE
					.getInstance("ICategoryService");
			Category object = new Category();
			object.setName(value);
			object = (Category) service.getByBusinessKey(object, context);
			if (object != null)
				return String.valueOf(object.getId());
			else
				return "-1";
		}
		if (CRMConstants.SALESPFTYPE.BRAND.equals(type.getCode())) {
			IBrandService service = (IBrandService) SpringObjectFactory.INSTANCE
					.getInstance("IBrandService");
			Brand object = new Brand();
			object.setName(value);
			object = (Brand) service.getByBusinessKey(object, context);
			if (object != null)
				return String.valueOf(object.getId());
			else
				return "-1";
		}
		if (CRMConstants.SALESPFTYPE.PRODUCT.equals(type.getCode())) {
			IProductService service = (IProductService) SpringObjectFactory.INSTANCE
					.getInstance("IProductService");
			Product object = new Product();
			object.setName(value);
			object = (Product) service.getByBusinessKey(object, context);
			if (object != null)
				return String.valueOf(object.getId());
			else
				return "-1";
		}
		if (CRMConstants.SALESPFTYPE.ITEM.equals(type.getCode())) {
			IItemService service = (IItemService) SpringObjectFactory.INSTANCE
					.getInstance("IItemService");
			Item object = new Item();
			object.setName(value);
			object = (Item) service.getByBusinessKey(object, context);
			if (object != null)
				return String.valueOf(object.getId());
			else
				return "-1";

		}
		return "-1";

	}
	

	public static String getSalesPortfolioValue(FiniteValue type, String id) {
		if (CRMConstants.SALESPFTYPE.CATEGORY.equals(type.getCode())) {
			ICategoryService service = (ICategoryService) SpringObjectFactory.INSTANCE
					.getInstance("ICategoryService");
			Category object = new Category();
			object.setId(Integer.parseInt(id));
			object = (Category) service.getById(object.getId());
			if (object != null)
				return String.valueOf(object.getName());
			else
				return "-1";
		}
		if (CRMConstants.SALESPFTYPE.BRAND.equals(type.getCode())) {
			IBrandService service = (IBrandService) SpringObjectFactory.INSTANCE
					.getInstance("IBrandService");
			Brand object = new Brand();
			object.setId(Integer.parseInt(id));
			object = (Brand) service.getById(object.getId());
			if (object != null)
				return String.valueOf(object.getName());
			else
				return "-1";
		}
		if (CRMConstants.SALESPFTYPE.PRODUCT.equals(type.getCode())) {
			IProductService service = (IProductService) SpringObjectFactory.INSTANCE
					.getInstance("IProductService");
			Product object = new Product();
			object.setId(Integer.parseInt(id));
			object = (Product) service.getById(object.getId());
			if (object != null)
				return String.valueOf(object.getName());
			else
				return "-1";
		}
		if (CRMConstants.SALESPFTYPE.ITEM.equals(type.getCode())) {
			IItemService service = (IItemService) SpringObjectFactory.INSTANCE
					.getInstance("IItemService");
			Item object = new Item();
			object.setId(Integer.parseInt(id));
			object = (Item) service.getById(object.getId());
			if (object != null)
				return String.valueOf(object.getName());
			else
				return "-1";

		}
		return "-1";

	}

	public static Map <String, String > getAllDivisions(CRMContext ctx) {
		boolean allowAll =CommonUtil.allowAllDivisionAccess(ctx);
		Map<String, String> ans = new LinkedHashMap<String, String>();
		IDivisionService service = (IDivisionService) SpringObjectFactory.INSTANCE
				.getInstance("IDivisionService");
		List<Division> divisions = service.getAllDivisions(ctx
				.getLoggedinCompany());
		if (!Utils.isNullList(divisions)) {
			for (Division division : divisions) {
				if (allowAll || division.getId() == ctx.getLoggedInUser().getDivision().getId())
					ans.put(String.valueOf(division.getId()), division.getName());
			}
		}
		return ans;
	}
	
	public static ReasonCode getReasonCode(ReasonCode selectedReason,CRMContext context)
	{
		IReasonCodeService reasonCodeService = (IReasonCodeService)SpringObjectFactory.INSTANCE.getInstance("IReasonCodeService");
		if(selectedReason != null && selectedReason.getId() > 0 ) {
			ReasonCode reason =  (ReasonCode)reasonCodeService.getById(selectedReason.getId());
			return reason; 
		} else if (selectedReason != null && !Utils.isNullString(selectedReason.getReason())) {
			ReasonCode reason =  (ReasonCode)reasonCodeService.getByBusinessKey(selectedReason, context);
			return reason;
		}
		
		return null ;
	}

	public static String getFollowupTimeAsString(Followup followup, String dateFormat, String timeFormat)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
		String timeString = sdf.format(followup.getFollowupDate()) ;
		/*String hhPart = timeString.substring(0,2);
		String mmPart = timeString.substring(3,5);
		appointment.setHh(Integer.parseInt(hhPart));
		appointment.setMm(Integer.parseInt(mmPart));*/

		SimpleDateFormat dateFormatObj = new SimpleDateFormat(dateFormat);
		String dateString = dateFormatObj.format(followup.getFollowupDate()) ;
		return dateString + " " +  timeString ;


	}
}
