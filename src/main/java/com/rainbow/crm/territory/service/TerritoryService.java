
package com.rainbow.crm.territory.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rainbow.crm.abstratcs.model.CRMItemLine;
import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.alert.model.Alert;
import com.rainbow.crm.common.AbstractService;
import com.rainbow.crm.common.AbstractionTransactionService;
import com.rainbow.crm.common.CRMAppConfig;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMDBException;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonErrorCodes;
import com.rainbow.crm.common.Externalize;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.common.messaging.CRMMessageSender;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.customer.service.ICustomerService;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.division.service.IDivisionService;
import com.rainbow.crm.hibernate.ORMDAO;
import com.rainbow.crm.inventory.model.InventoryUpdateObject;
import com.rainbow.crm.item.dao.ItemImageSQL;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.item.model.ItemImage;
import com.rainbow.crm.item.service.ISkuService;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.crm.product.validator.ProductValidator;
import com.rainbow.crm.saleslead.model.SalesLeadLine;
import com.rainbow.crm.saleslead.validator.SalesLeadErrorCodes;
import com.rainbow.crm.territory.dao.TerritoryDAO;
import com.rainbow.crm.territory.model.Territory;
import com.rainbow.crm.territory.model.TerritoryLine;
import com.rainbow.crm.territory.validator.TerritoryValidator;
import com.rainbow.crm.user.model.User;
import com.rainbow.crm.user.service.IUserService;
import com.rainbow.crm.vendor.model.Vendor;
import com.rainbow.crm.vendor.service.IVendorService;
import com.rainbow.framework.nextup.NextUpGenerator;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.ui.components.SortCriteria;
import com.techtrade.rads.framework.utils.Utils;

@Transactional
public class TerritoryService extends AbstractionTransactionService implements ITerritoryService{

	@Override
	protected String getTableName() {
		return "Territory";
	}
	
	

	@Override
	public Object getById(Object PK) {
		return getDAO().getById(PK);
	}

	@Override
	public List<CRMModelObject> listData(int from, int to,
			String whereCondition, CRMContext context, SortCriteria sortCriteria) {
		return super.listData("Territory", from, to, whereCondition, context,sortCriteria);
	}

	@Override
	public List<RadsError> validateforCreate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((Territory)object).setCompany(company);
		TerritoryValidator validator = new TerritoryValidator(context);
		return validator.validateforCreate(object);
	}

	@Override
	public List<RadsError> validateforUpdate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((Territory)object).setCompany(company);
		TerritoryValidator validator = new TerritoryValidator(context);
		return validator.validateforUpdate(object);
	}

	@Override
	protected ORMDAO getDAO() {
//	return new TerritoryDAO();
	return (TerritoryDAO) SpringObjectFactory.INSTANCE.getInstance("TerritoryDAO");
	}

	
	@Override
	public List<RadsError> adaptfromUI(CRMContext context,ModelObject obj) {
		Territory object = (Territory) obj;
		ICompanyService compService = (ICompanyService) SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		object.setCompany(company);
				
		List<RadsError> ans = new ArrayList<RadsError>();
		
		
		if (object.getDivision() != null) {
			int divisionId  = object.getDivision().getId() ;
			IDivisionService divisionService =(IDivisionService) SpringObjectFactory.INSTANCE.getInstance("IDivisionService");
			Division division = null;
			if (divisionId > 0 )
				division = (Division)divisionService.getById(divisionId);
			else
				division  = (Division)divisionService.getByBusinessKey(object.getDivision(), context);
			if(division == null){
				ans.add(CRMValidator.getErrorforCode(context.getLocale(), CommonErrorCodes.FIELD_NOT_VALID , "Division"));
			}else {
				object.setDivision(division);
			}
		}
		Externalize externalize = new Externalize(); ;
		if(!Utils.isNullSet(object.getTerritoryLines())){
			int lineNo=1;
			for (TerritoryLine line: object.getTerritoryLines()) {
				line.setCompany(company);
				line.setLineNumber(lineNo ++);
			}
		}
		return ans;
	}

	
	
	
	@Override
	public List<Territory> getAllTerritoriesforDivision(int division) {
		return ((TerritoryDAO)getDAO()).getAllTerritoriesforDivision(division);
	}

	@Override
	public TransactionResult create(CRMModelObject object, CRMContext context) {
		Territory territory = (Territory)object ;
		if (!Utils.isNullSet(territory.getTerritoryLines())) {
			int pk = GeneralSQLs.getNextPKValue("Territory") ;
			territory.setId(pk);
			for (TerritoryLine  line : territory.getTerritoryLines()) {
				int linePK = GeneralSQLs.getNextPKValue( "Territory_Lines") ;
				line.setId(linePK);
				line.setTerritoryDoc(territory);
				line.setCompany(territory.getCompany());
			}
		}
		TransactionResult result= super.create(object, context);
		return result; 
	}

	
	@Override
	public TransactionResult update(CRMModelObject object, CRMContext context) {
		Territory territory = (Territory)object ;
		Territory oldObject = (Territory)getById(territory.getPK());
		//Territory oldInvObj = (Territory)oldObject.clone();
		if (!Utils.isNullSet(territory.getTerritoryLines())) {
			int  ct = 0;
			Iterator it = oldObject.getTerritoryLines().iterator() ;
			for (TerritoryLine  line : territory.getTerritoryLines()) {
				TerritoryLine oldLine = null ;
				if (it.hasNext()) {
					oldLine= (TerritoryLine) it.next() ;
				}
				line.setTerritoryDoc(territory);
				if (oldLine != null) {
					line.setId(oldLine.getId());
					line.setObjectVersion(oldLine.getObjectVersion());
				}else {
					int linePK = GeneralSQLs.getNextPKValue( "Territory_Lines") ;
					line.setId(linePK);
				}
			}
			while (it.hasNext()) {
				TerritoryLine oldLine= (TerritoryLine) it.next() ;
				oldLine.setDeleted(true);
				territory.addTerritoryLine(oldLine);
			}
		}
		return super.update(object, context);
	}

	@Override
	public TransactionResult batchUpdate(List<CRMModelObject> objects,
			CRMContext context) throws CRMDBException {
		return super.batchUpdate(objects, context);
	}

	@Override
	public TransactionResult batchCreate(List<CRMModelObject> objects,
			CRMContext context) throws CRMDBException {
		return super.batchCreate(objects, context);
	}

	

/*	@Override
	public List<RadsError> sendEmail(Territory territory, CRMContext context) {
		List<RadsError> errors = new ArrayList<RadsError>();
		String to = territory.getCustomer().getEmail();
		String from  = "noresponse@primussol.com";
		String host = "localhost";
		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "25");
		
		Session session = Session.getInstance(properties);
		try {
			  MimeMessage message = new MimeMessage(session);
		      message.setFrom(new InternetAddress(from));
		      message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
	         message.setSubject("This is the new util tets!");
	         message.setText("Testing email");
	         Transport.send(message);
		}catch(Exception ex){
			Logwriter.INSTANCE.error(ex);
			errors.add(new RadsError(String.valueOf(CommonErrorCodes.COULDNOT_SENDEMAIL),"Could not send email"));
		}
		return errors;

	}

*/	
		
	
	
	
	
}
