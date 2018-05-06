package com.rainbow.crm.custcategory.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;










import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;




import org.springframework.transaction.annotation.Transactional;




import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.AbstractService;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMDBException;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.DatabaseException;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.hibernate.ORMDAO;
import com.rainbow.crm.saleslead.model.SalesLead;
import com.rainbow.crm.saleslead.model.SalesLeadLine;
import com.rainbow.crm.saleslead.service.ISalesLeadService;
import com.rainbow.crm.custcategory.dao.CustCategoryDAO;
import com.rainbow.crm.custcategory.model.CustCategory;
import com.rainbow.crm.custcategory.model.CustCategoryComponent;
import com.rainbow.crm.custcategory.model.CustCategoryCondition;
import com.rainbow.crm.custcategory.sql.CustCategorySQLs;
import com.rainbow.crm.custcategory.validator.CustCategoryValidator;
import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.division.model.Division;
import com.rainbow.framework.query.model.Query;
import com.rainbow.framework.query.model.QueryCondition;
import com.rainbow.framework.query.model.QueryRecord;
import com.rainbow.framework.query.model.QueryReport;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.ui.components.SortCriteria;
import com.techtrade.rads.framework.utils.Utils;

public class CustCategoryService extends AbstractService implements ICustCategoryService{

	
	@Override
	protected String getTableName() {
		return "CustCategory";
	}
	
	
	
	
	@Override
	public boolean isCustomerInCategory(CustCategory custCategory,
			Customer customer) {
		// TODO Auto-generated method stub
		return true;
	}




	@Override
	public Object getById(Object PK) {
		CustCategory category =(CustCategory) getDAO().getById(PK);
		splitConditions(category);		
		return category ;
	}

	@Override
	public List<CRMModelObject> listData(int from, int to,
			String whereCondition, CRMContext context, SortCriteria sortCriteria) {
		return super.listData("CustCategory", from, to, whereCondition, context,sortCriteria);
	}
	
	@Transactional 
	public TransactionResult create(CRMModelObject object,CRMContext context)  {
		List<RadsError> errors  = new ArrayList<RadsError>(); 
		TransactionResult.Result result = TransactionResult.Result.SUCCESS;
		try {
			CustCategory custCategory = (CustCategory) object ;
			int pk = GeneralSQLs.getNextPKValue("CUSTOMER_CATEGORIES") ;
			custCategory.setId(pk);
			
			custCategory.getConditions().forEach( condition ->  {
				int linePK = GeneralSQLs.getNextPKValue( "CUSTOMER_CATEGORY_CONDITION") ;
				condition.setId(linePK ++ );
				condition.setCategory(custCategory);
				
			});
			
			
			object.setObjectVersion(1);
			object.setCreatedDate(new java.sql.Timestamp(new java.util.Date().getTime()));
			object.setCreatedUser(context.getUser());
			getDAO().create(object);
		}catch(DatabaseException ex) {
			RadsError error = CRMValidator.getErrorforCode(context.getLocale(),CRMDBException.ERROR_UNABLE_TO_CREATE);
			errors.add(error);
			result = TransactionResult.Result.FAILURE ;
			throw new RuntimeException(ex) ;
		}
		return new TransactionResult(result,errors);
	}

	@Override
	public QueryReport checCustomers(CustCategory custCategory,
			CRMContext context) {
		StringBuffer havingFields = new StringBuffer("");
		StringBuffer whereFields = new StringBuffer("");
		Set<String> joins= new LinkedHashSet<String>();
		
		if(!Utils.isNullSet(custCategory.getAggregateConditions())) {
		custCategory.getAggregateConditions().forEach(condition -> {
			if (! Utils.isNullString( condition.getValue())) {
			CustCategoryComponent component = CustCategorySQLs.getComponentByKey("SALES", condition.getField());
			condition.setDataType(new FiniteValue(component.getDataType()));
			if  ( component.isAggregated()) {
				havingFields.append(condition.toString());
			} 
			}
		});
		}
		
		if(!Utils.isNullSet(custCategory.getWhereConditions())) { 
		custCategory.getWhereConditions().forEach(condition -> {
			if (! Utils.isNullString( condition.getValue())) {
			CustCategoryComponent component = CustCategorySQLs.getComponentByKey("SALES", condition.getField());
			condition.setDataType(new FiniteValue(component.getDataType()));
			whereFields.append(condition.toString());
			if( !Utils.isNullString( component.getJoinHqlClause()))  {
				joins.add(component.getJoinHqlClause());
			}
			}
		});
		}
		
		/*custCategory.getConditions().forEach(condition -> {
			CustCategoryComponent component = CustCategorySQLs.getComponentByKey("SALES", condition.getField());
			condition.setDataType(new FiniteValue(component.getDataType()));
			if  ( component.isAggregated()) {
				havingFields.append(condition.toString());
			} else  {
				whereFields.append(condition.toString());
				if( !Utils.isNullString( component.getJoinHqlClause()))  {
					joins.add(component.getJoinHqlClause());
				}
						
			}
		});*/
		
		StringBuffer joinClause = new StringBuffer("") ;
		joins.forEach( hqlClause  ->  { 
			joinClause.append(" " +  hqlClause + " ");
		} );
		String hqlPrefix = " select Sales.customer from Sales Sales " + joinClause  +  " where Sales.voided = false and Sales.salesDate>= :fromDate and Sales.salesDate <= :toDate and Sales.company.id= "
				+ context.getLoggedinCompany() + "    ";
		Date fromDate   = CommonUtil.getRelativeDate(custCategory.getEvalFrom());
		Date toDate   = CommonUtil.getRelativeDate(custCategory.getEvalTo());
		
		CustCategoryDAO dao = (CustCategoryDAO)getDAO();
		String whereClause =  whereFields.length()>1?" and " + whereFields.toString()  : " ";
		String havingClause =  havingFields.length()>1?" having  " + havingFields.toString()  : " ";
		//String groupByClause = havingFields.length()>1?" group by Sales.customer.id ": " ";
		String groupByClause = " group by Sales.customer.id ";
		List<Customer > customers = dao.getCustomersforRule(hqlPrefix + whereClause +  groupByClause +    havingClause  ,fromDate,toDate );
		QueryReport report = generateReport(custCategory, customers);
		return report;
	}
	
	private QueryReport generateReport(CustCategory category, List  list) {
		QueryReport report = new QueryReport();
		String [] titles=   {"First Name" , "Last Name" , "Phone" , "Email"  } ;
		report.setTitles(titles);
		/*if(getQuerySelDate(query,"From")!=null)  {
		report.setFrom(getQuerySelDate(query,"From").toLocaleString());
		report.setTo(getQuerySelDate(query,"To").toLocaleString());
		}*/
		if(!Utils.isNullList(list)) {
			for(int i = 0 ; i < list.size() ; i ++ ) {
				QueryRecord record= new QueryRecord();
				Customer cust =(Customer) list.get(i);
				String  []actValues = new String[4];
				actValues[0] = cust.getFirstName();
				actValues[1] = cust.getLastName();
				actValues[2] = cust.getPhone() ;
				actValues[3] = cust.getEmail();
				record.setFields(actValues);
				record.setFieldCount(4);
				report.addRecord(record);
			}
		}
		return report;
	}

	@Override
	public List<RadsError> validateforCreate(CRMModelObject object,
			CRMContext context) {
		CustCategoryValidator validator = new CustCategoryValidator(context);
		return validator.validateforCreate(object);
	}

	@Override
	public List<RadsError> validateforUpdate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((CustCategory)object).setCompany(company);
		CustCategoryValidator validator = new CustCategoryValidator(context);
		return validator.validateforUpdate(object);
	}


	@Override
	public List<RadsError> adaptfromUI(CRMContext context, ModelObject object) {
		CustCategory custCategory  = (CustCategory)object ;
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		Division division = CommonUtil.getDivisionObect(context, custCategory.getDivision());
		custCategory.setDivision(division);
		((CustCategory)object).setCompany(company);
		combineConditions(custCategory);
		AtomicInteger lineNumber = new AtomicInteger(1);
		custCategory.getConditions().forEach( condition ->  {  
			condition.setCategory(custCategory);
			condition.setCompany(custCategory.getCompany());
			condition.setLineNumber(lineNumber.getAndIncrement());
		});
		return null;
	}

	@Override
	public List<RadsError> adaptToUI(CRMContext context, ModelObject object) {
		splitConditions((CustCategory) object);
		return null;
	}

	@Override
	protected ORMDAO getDAO() {
		return (CustCategoryDAO) SpringObjectFactory.INSTANCE.getInstance("CustCategoryDAO");
	}
	
	@Transactional
	public TransactionResult update(CRMModelObject object,CRMContext context) {
		List<RadsError> errors  = new ArrayList<RadsError>(); 
		TransactionResult.Result result = TransactionResult.Result.SUCCESS;
		try {
			resetCategoryCondition((CustCategory)object, context);
			object.setLastUpdateDate(new java.sql.Timestamp(new java.util.Date().getTime()));
			object.setLastUpdateUser(context.getUser());
			((CustCategoryDAO)getDAO()).deleteOrphanedRecords((CustCategory)object);
			getDAO().update(object); 
		}catch(DatabaseException ex) {
			RadsError error = CRMValidator.getErrorforCode(context.getLocale(),CRMDBException.ERROR_DIRTY_READ);
			errors.add(error);
			result = TransactionResult.Result.FAILURE ;
			throw new CRMDBException(error) ;
		}
		return new TransactionResult(result,errors);
	}

	private void resetCategoryCondition (CustCategory category, CRMContext context)
	{
		CustCategory oldObject = (CustCategory)getById(category.getId());
		if (!Utils.isNullSet(category.getConditions())) {
			int  ct = 0;
			Iterator it = oldObject.getConditions().iterator() ;
			for (CustCategoryCondition  line : category.getConditions()) {
				CustCategoryCondition oldLine = null ;
				if (it.hasNext()) {
					oldLine= (CustCategoryCondition) it.next() ;
				}
				line.setCategory(category);
				if (oldLine != null) {
					line.setId(oldLine.getId());
					
				}else {
					int linePK = GeneralSQLs.getNextPKValue( "CUSTOMER_CATEGORY_CONDITION") ;
					line.setId(linePK);
				}
			}
			while (it.hasNext()) {
				CustCategoryCondition oldLine= (CustCategoryCondition) it.next() ;
				oldLine.setDeleted(true);
				category.addCondition(oldLine);
			}
		}
		
	}
	
	private void combineConditions(CustCategory custCategory)
	{
		custCategory.setConditions(null);
		if(!Utils.isNullSet(custCategory.getAggregateConditions())) {
			custCategory.getAggregateConditions().forEach(condition -> {
				if (! Utils.isNullString( condition.getValue())) {
				custCategory.addCondition(condition);
				}
			});
			}
			
			if(!Utils.isNullSet(custCategory.getWhereConditions())) { 
			custCategory.getWhereConditions().forEach(condition -> {
				if (! Utils.isNullString( condition.getValue())) {
					custCategory.addCondition(condition);
				}
			});
			}
		
	}
	
	private  void splitConditions(CustCategory custCategory)
	{
		custCategory.getConditions().forEach(condition -> {
			CustCategoryComponent component = CustCategorySQLs.getComponentByKey("SALES", condition.getField());
			condition.setDataType(new FiniteValue(component.getDataType()));
			if  ( component.isAggregated()) {
				custCategory.addAggregateCondition(condition);
			} else  {
				custCategory.addWhereCondition(condition);
			}
		});
	}
	

}
