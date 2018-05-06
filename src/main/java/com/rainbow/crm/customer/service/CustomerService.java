package com.rainbow.crm.customer.service;

import java.io.ByteArrayInputStream;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.transaction.annotation.Transactional;

import sun.misc.BASE64Decoder;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.AbstractService;
import com.rainbow.crm.common.CRMAppConfig;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.hibernate.ORMDAO;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.crm.customer.dao.CustomerDAO;
import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.customer.validator.CustomerValidator;
import com.rainbow.crm.document.model.Document;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.ui.components.SortCriteria;
import com.techtrade.rads.framework.utils.Utils;

public class CustomerService extends AbstractService implements ICustomerService{

	
	@Override
	protected String getTableName() {
		return "Customer";
	}
	
	

	@Override
	public Object getById(Object PK) {
		Customer customer = (Customer) getDAO().getById(PK);
		loadSupplymentoryURL(customer);
		return customer;
		
	}

	private boolean uploadFile(Customer customer, CRMContext context)
	{
		if(Utils.isNullString(customer.getFileName())) return false;
		String fileExtn = CommonUtil.getFileExtn(customer.getFileName());
		String fileName =  new String("cs" + customer.getId());
		fileName.replace(" ", "_")    ; 
	//	doc.setDocName(fileName +  "."  + fileExtn);
		customer.setPhotoFile( "//" +  context.getLoggedinCompanyCode() +  "//custs//" + fileName +  "."  + fileExtn );
		//customer.setPhotoFile(fileName +  "."  + fileExtn );
		if(customer.getImage() != null) 
			CommonUtil.uploadFile(customer.getImage(), fileName +  "."  + fileExtn  , context, "custs");
		else{
			byte[] imageByte;
			try  {
				BASE64Decoder decoder = new BASE64Decoder();
				imageByte = decoder.decodeBuffer(customer.getBase64Image());
				ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
				bis.close();
				CommonUtil.uploadFile(imageByte, fileName +  "."  + fileExtn  , context, "custs");
			}catch(Exception ex) {
				Logwriter.INSTANCE.error(ex);
			}
		}
			
		return true;
	}
	private void loadSupplymentoryURL(Customer customer)
	{
		try { 
			String serverURL = CRMAppConfig.INSTANCE.getProperty("doc_server");
			customer.setFileWithLink(serverURL + customer.getPhotoFile());
			customer.setTempPhotoFile(customer.getPhotoFile());
		}catch(Exception ex) 
		{
		  Logwriter.INSTANCE.error(ex);	
		}
	}
	
	@Override
	public List<CRMModelObject> listData(int from, int to,
			String whereCondition, CRMContext context, SortCriteria sortCriteria) {
		List<CRMModelObject> customers = super.listData("Customer", from, to, whereCondition, context,sortCriteria);
		customers.forEach( customer ->  {  
			if (!Utils.isNullString(((Customer)customer).getPhotoFile()))  {
			loadSupplymentoryURL((Customer)customer);
			}
		});
		
		return customers;
	}

	
	@Override
	@Transactional 
	public TransactionResult create(CRMModelObject object, CRMContext context) {
		TransactionResult result  = super.create(object, context);
		if (((Customer)object).getImage()  != null || !Utils.isNullString(((Customer)object).getBase64Image()))  {
			Customer loadedCust = (Customer) getByBusinessKey(object, context);
			((Customer)object).setId(loadedCust.getId());
			uploadFile(((Customer)object), context); 
			loadedCust.setPhotoFile(((Customer)object).getPhotoFile());
			super.update(loadedCust, context);
		}
		return result;
	}

	@Override
	@Transactional 
	public TransactionResult update(CRMModelObject object, CRMContext context) {
		if (((Customer)object).getImage()  != null || !Utils.isNullString(((Customer)object).getBase64Image()) ) {
			  boolean fileuploaded = uploadFile(((Customer)object), context);
			  if(!fileuploaded &&  !Utils.isNullString(((Customer)object).getTempPhotoFile()) ) 
			  {
				  ((Customer)object).setPhotoFile(((Customer)object).getTempPhotoFile());
			  }
		}
		return super.update(object, context);
	}

	@Override
	public List<RadsError> validateforCreate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((Customer)object).setCompany(company);
		CustomerValidator validator = new CustomerValidator(context);
		return validator.validateforCreate(object);
	}

	@Override
	public List<RadsError> validateforUpdate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((Customer)object).setCompany(company);
		CustomerValidator validator = new CustomerValidator(context);
		return validator.validateforUpdate(object);
	}

	/**
	@Override
	public Customer getByCode(int company, String code) {
		return ((CustomerDAO)getDAO()).findByCode(company, code);
	}

	@Override
	public Customer getByName(int company, String name) {
		return ((CustomerDAO)getDAO()).findByName(company, name);
	}**/
	
	
	

	@Override
	protected ORMDAO getDAO() {
		return (CustomerDAO) SpringObjectFactory.INSTANCE.getInstance("CustomerDAO");
	}

	@Override
	public Customer getByEmail(int company, String email) {
		return ((CustomerDAO)getDAO()).findByEmail(company, email);
	}

	@Override
	public Customer getByPhone(int company, String phone) {
		return ((CustomerDAO)getDAO()).findByPhone(company, phone);
	}
	
	
	

}
