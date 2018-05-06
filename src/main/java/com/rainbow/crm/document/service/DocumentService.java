package com.rainbow.crm.document.service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
















import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.AbstractService;
import com.rainbow.crm.common.CRMAppConfig;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.customer.service.ICustomerService;
import com.rainbow.crm.followup.model.Followup;
import com.rainbow.crm.hibernate.ORMDAO;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.item.service.IItemService;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.sales.service.ISalesService;
import com.rainbow.crm.saleslead.model.SalesLead;
import com.rainbow.crm.saleslead.model.SalesLeadLine;
import com.rainbow.crm.saleslead.service.ISalesLeadService;
import com.rainbow.crm.document.dao.DocumentDAO;
import com.rainbow.crm.document.model.Document;
import com.rainbow.crm.document.validator.DocumentValidator;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.ui.components.SortCriteria;
import com.techtrade.rads.framework.utils.Utils;

public class DocumentService extends AbstractService implements IDocumentService{

	@Override
	protected String getTableName() {
		return "Document";
	}
	
	

	@Override
	public Object getById(Object PK) {
		Document document = (Document)getDAO().getById(PK);
		loadSupplymentoryURL(document);
		return document; 
	}
	
	private void loadSupplymentoryURL(Document document)
	{
		try { 
			String serverURL = CRMAppConfig.INSTANCE.getProperty("doc_server");
			document.setFileWithLink(serverURL + document.getDocPath());
		
		}catch(Exception ex) 
		{
		  Logwriter.INSTANCE.error(ex);	
		}
	}

	@Override
	public List<CRMModelObject> listData(int from, int to,
			String whereCondition, CRMContext context, SortCriteria sortCriteria) {
		return super.listData("Document", from, to, whereCondition, context,sortCriteria);
	}

	private void loadDependentObjects(Document document,CRMContext context)
	{
		if(document.getCustomer() != null )  {
		ICustomerService customerService = 	(ICustomerService) SpringObjectFactory.INSTANCE.getInstance("ICustomerService");
		Customer customer =(Customer) customerService.getByBusinessKey(document.getCustomer(), context);
		document.setCustomer(customer);
		}
		
		if(document.getItem() != null )  {
			IItemService itemService = 	(IItemService) SpringObjectFactory.INSTANCE.getInstance("IItemService");
			Item item =(Item) itemService.getByBusinessKey(document.getItem(), context);
			document.setItem(item);
		}
		
		if(document.getLead() != null )  {
			ISalesLeadService leadService = 	(ISalesLeadService) SpringObjectFactory.INSTANCE.getInstance("ISalesLeadService");
			SalesLead lead =(SalesLead) leadService.getByBusinessKey(document.getLead(), context);
			document.setLead(lead);
		}
		
		if(document.getSales() != null )  {
			ISalesService salesService = 	(ISalesService) SpringObjectFactory.INSTANCE.getInstance("ISalesService");
			Sales sales =(Sales) salesService.getByBusinessKey(document.getLead(), context);
			document.setSales(sales);
		}	
	}
	
	@Override
	public List<RadsError> validateforCreate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((Document)object).setCompany(company);
		((Document)object).setOwner(context.getLoggedInUser());
		DocumentValidator validator = new DocumentValidator(context);
		loadDependentObjects(((Document)object),context);
		return validator.validateforCreate(object);
	}

	@Override
	public List<RadsError> validateforUpdate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((Document)object).setCompany(company);
		loadDependentObjects(((Document)object),context);
		DocumentValidator validator = new DocumentValidator(context);
		return validator.validateforUpdate(object);
	}

	private boolean uploadFile(Document doc, CRMContext context)
	{
		String fileExtn = CommonUtil.getFileExtn(doc.getFileName1());
		String fileName =  new String(doc.getDocName());
		fileName.replace(" ", "_")    ; 
	//	doc.setDocName(fileName +  "."  + fileExtn);
		doc.setDocPath( "//" +  context.getLoggedinCompanyCode() +  "//docs//" + fileName +  "."  + fileExtn );
		CommonUtil.uploadFile(doc.getDocData(), fileName +  "."  + fileExtn  , context, "docs");
		return true;
	}
	

	@Override
	public TransactionResult create(CRMModelObject object, CRMContext context) {
		Document document = (Document) object ;
		uploadFile(document, context);
		
		TransactionResult result=  super.create(object, context);
		return result; 
	}

	@Override
	public TransactionResult update(CRMModelObject object, CRMContext context) {
		Document document = (Document) object ;
		uploadFile(document, context);
		TransactionResult result= super.update(object, context);
		return result; 
	}

	@Override
	protected ORMDAO getDAO() {
		return (DocumentDAO) SpringObjectFactory.INSTANCE.getInstance("DocumentDAO");
	}

	@Override
	public List<Document> findAllBySalesLead(SalesLead lead) {
		DocumentDAO dao = (DocumentDAO) getDAO();
		List<Document> docs = dao.getDocumentsforSalesLead(lead.getId());
		if(!Utils.isNullList(docs)) {
			docs.forEach(doc ->  {   
				loadSupplymentoryURL(doc);
			} );
		}
		return docs;
	}



	@Override
	public List<Document> findAllByItem(Item item) {
		DocumentDAO dao = (DocumentDAO) getDAO();
		List<Document> docs =  dao.getDocumentsforItem(item.getId());
		if(!Utils.isNullList(docs)) {
			docs.forEach(doc ->  {   
				loadSupplymentoryURL(doc);
			} );
		}
		return docs;
	}



	@Override
	public List<Document> findAllByCustomer(Customer customer) {
		DocumentDAO dao = (DocumentDAO) getDAO();
		List<Document> docs =  dao.getDocumentsforCustomer(customer.getId());
		if(!Utils.isNullList(docs)) {
			docs.forEach(doc ->  {   
				loadSupplymentoryURL(doc);
			} );
		}
		return docs;
	}

	

	
   

	
	

}
