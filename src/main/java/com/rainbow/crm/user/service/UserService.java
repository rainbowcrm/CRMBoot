package com.rainbow.crm.user.service;

import java.io.ByteArrayInputStream;
import java.util.List;

import com.rainbow.crm.common.*;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.util.ServiceLibrary;
import org.springframework.transaction.annotation.Transactional;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.division.validator.DivisionValidator;
import com.rainbow.crm.hibernate.ORMDAO;
import com.rainbow.crm.user.dao.UserDAO;
import com.rainbow.crm.user.model.User;
import com.rainbow.crm.user.validator.UserValidator;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.ui.components.SortCriteria;
import com.techtrade.rads.framework.utils.Utils;
import sun.misc.BASE64Decoder;

public class UserService  extends AbstractService implements IUserService{
	
	
	protected ORMDAO getDAO() {
		return (UserDAO) SpringObjectFactory.INSTANCE.getInstance("UserDAO");
	}
	@Override
	public Object getById(Object PK) {
		User  user=  (User)getDAO().getById(PK);
		adaptToUI(user,null);
		return user ;
	}

	public List<RadsError> adaptToUI(ModelObject model, CRMContext context) {
		User user = (User) model ;
		String serverURL = ServiceLibrary.services().getApplicationManager().getDocServer();
		user.setFileWithLink(serverURL + user.getPhoto());
		user.setFileWithoutLink(user.getPhoto());
		return null;


	}

	@Override
	public List<CRMModelObject> listData(int from, int to, String whereCondition,CRMContext context, SortCriteria sortCriteria) {
		if(context.getLoggedinCompany() != 1) {
			return super.listData("User", from, to, whereCondition, context,sortCriteria);
		}else
			return  getDAO().listData("User" ,from, to, whereCondition);
	}


	private boolean uploadFile(User object, CRMContext context)
	{
		if(Utils.isNullString(object.getFileName())) {
			if(!Utils.isNullString(object.getFileWithoutLink()))
				object.setPhoto(object.getFileWithoutLink());
			return false;
		}
		String fileExtn = CommonUtil.getFileExtn(object.getFileName());
		String fileName =  new String("usr" + object.getPrefix());
		fileName.replace(" ", "_")    ;
		//	doc.setDocName(fileName +  "."  + fileExtn);
		object.setPhoto( "//" +  context.getLoggedinCompanyCode() +  "//usr//" + fileName +  "."  + fileExtn );
		//customer.setPhotoFile(fileName +  "."  + fileExtn );
		if(object.getImage() != null)
			CommonUtil.uploadFile(object.getImage(), fileName +  "."  + fileExtn  , context, "usr");
		else{
			byte[] imageByte;
			try  {
				BASE64Decoder decoder = new BASE64Decoder();
				imageByte = decoder.decodeBuffer(object.getBase64Image());
				ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
				bis.close();
				CommonUtil.uploadFile(imageByte, fileName +  "."  + fileExtn  , context, "usr");
			}catch(Exception ex) {
				Logwriter.error(ex);
			}
		}

		return true;
	}


	@Override
	@Transactional 
	public TransactionResult update(CRMModelObject user, CRMContext context) throws CRMDBException{
		ICompanyService companyService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = companyService.findByName(((User)user).getCompany().getName());
		((User)user).setCompany(company);
		uploadFile((User) user,context);
		return super.update(user,context);
	}

	@Override
	@Transactional
	public TransactionResult create(CRMModelObject object, CRMContext context) {
		uploadFile((User) object,context);
		return super.create(object, context);
	}

	@Override
	@Transactional 
	public TransactionResult batchUpdate(List<CRMModelObject> users, CRMContext context) throws CRMDBException {
		return super.batchUpdate(users,context);
	}
	
	@Override
	protected String getTableName() {
		return "User";
	}
	
	
	@Override
	public User getByEmail(String email) {
		return((UserDAO)getDAO()).getByEmail(email);
	}
	@Override
	public User getByPhone(String phone) {
		return ((UserDAO)getDAO()).getByPhone(phone);
	}

	
	
	@Override
	public List<User> getByDivision(Division division, CRMContext context) {
		return ((UserDAO)getDAO()).getAllUsersByDivision(division.getId(), false);
	}
	@Override
	public List<RadsError> validateforCreate(CRMModelObject object,
			CRMContext context) {
		UserValidator validator = new UserValidator(context);
		return validator.validateforCreate(object);
		
	}
	
	@Override
	public List<RadsError> validateforUpdate(CRMModelObject object,
			CRMContext context) {
		UserValidator validator = new UserValidator(context);
		return validator.validateforUpdate(object);
		
	}
	
	
	

}
