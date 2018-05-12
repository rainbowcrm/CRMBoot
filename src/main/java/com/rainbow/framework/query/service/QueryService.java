package com.rainbow.framework.query.service;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.util.StringUtils;

import com.rainbow.crm.common.CRMAppConfig;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.Externalize;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.customer.dao.CustomerDAO;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.hibernate.ORMDAO;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.sales.model.SalesLine;
import com.rainbow.crm.user.model.User;
import com.rainbow.crm.user.service.IUserService;
import com.rainbow.framework.query.dao.QueryDAO;
import com.rainbow.framework.query.model.Query;
import com.rainbow.framework.query.model.QueryCondition;
import com.rainbow.framework.query.model.QueryRecord;
import com.rainbow.framework.query.model.QueryReport;
import com.rainbow.framework.query.validation.QueryValidator;
import com.rainbow.framework.setup.model.EntityField;
import com.rainbow.framework.setup.model.Metadata;
import com.rainbow.framework.setup.sql.MetadataSQL;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.utils.Utils;

public class QueryService implements IQueryService{

	private Date getQuerySelDate(Query query , String fromTo) {
		if(query.isDateExcluded() == true ) return null;
		if ("ABS".equalsIgnoreCase(query.getDateValueType())) {
			if("from".equalsIgnoreCase(fromTo))
				return query.getFromDate() ;
			if("To".equalsIgnoreCase(fromTo))
				return query.getToDate() ;
		}
		if ("REL".equalsIgnoreCase(query.getDateValueType())) {
			if("from".equalsIgnoreCase(fromTo)) {
				return CommonUtil.getRelativeDate(query.getFromCriteria());
			}if("To".equalsIgnoreCase(fromTo))
				return CommonUtil.getRelativeDate(query.getToCriteria());
		}
		return new java.util.Date();
	}
	
	
	private String getSelectedFieldsBlob(String [] selectedField )
	{ 
		StringBuffer buffer = new StringBuffer();
		if (selectedField != null ) { 
			for(int i = 0 ; i < selectedField.length ; i ++) 
			{
				buffer.append(selectedField[i]) ;
				if (i < selectedField.length - 1)
					buffer.append(",");
			}
		}
		return buffer.toString();
	}
	
	private String[] getSelectedFieldArrayFromBlob(String selectedFieldBlob)
	{
		if (Utils.isNullString(selectedFieldBlob)) return  null;
		int ct = StringUtils.countOccurrencesOf(selectedFieldBlob, ",");
		String ans[] = new String[ct+1];
		int  i = 0 ;
		StringTokenizer tokenizer = new StringTokenizer(selectedFieldBlob,",");
		while(tokenizer.hasMoreElements()) {
			ans[i ++] = tokenizer.nextToken();
		}
		return ans ;
	}
	
	@Override
	public Query getQuery(int id) {
		QueryDAO dao = (QueryDAO)getDAO();
		Query query = dao.getQuery(id);
		query.setSelectedFields(getSelectedFieldArrayFromBlob(query.getSelectedFiedsBlob()));
		return query; 
		
	}




	@Override
	public List<Query> getAllQueriesforOwner(CRMContext context) {
		QueryDAO dao = (QueryDAO)getDAO();
		return dao.getAllQueriesforOwner(context.getLoggedinCompany(), context.getLoggedInUser().getUserId());
	}


	private void resetQueryCondition (Query query, CRMContext context)
	{
		Query oldObject = (Query)getQuery(query.getId());
		if (!Utils.isNullSet(query.getConditions())) {
			int  ct = 0;
			Iterator it = oldObject.getConditions().iterator() ;
			for (QueryCondition  line : query.getConditions()) {
				QueryCondition oldLine = null ;
				if (it.hasNext()) {
					oldLine= (QueryCondition) it.next() ;
				}
				line.setQuery(query);
				if (oldLine != null) {
					line.setId(oldLine.getId());
					
				}else {
					int linePK = GeneralSQLs.getNextPKValue( "QUERY_CONDITION") ;
					line.setId(linePK);
				}
			}
			while (it.hasNext()) {
				QueryCondition oldLine= (QueryCondition) it.next() ;
				oldLine.setDeleted(true);
				query.addCondition(oldLine);
			}
		}
		
	}

	@Override
	public Query saveQuery(Query query, CRMContext context) {
		QueryDAO dao = (QueryDAO)getDAO();
		query.setSelectedFiedsBlob(getSelectedFieldsBlob(query.getSelectedFields()));
		if (query.getId()  <= 0 ) {
			if (!Utils.isNullSet(query.getConditions())) {
				int pk = GeneralSQLs.getNextPKValue("Queries") ;
				query.setId(pk);
				for (QueryCondition  line : query.getConditions()) {
					int linePK = GeneralSQLs.getNextPKValue( "Query_Condition") ;
					line.setId(linePK);
					line.setQuery(query);
				}
			}
			dao.create(query);
		}else{
			resetQueryCondition(query,context);
			dao.deleteOrphanedRecords(query);
			dao.update(query);
		}
		return null;
	}



	@Override
	public QueryReport getResult(Query query, CRMContext context) {
		String queryString = "";
		if(query.getResultType().equals("AGGREGATED"))
			 queryString = makeGroupQuery(query);
		else
			queryString = makeQuery(query);
		QueryDAO dao = (QueryDAO)getDAO();
		List lst = dao.getQueryRecord(queryString, context.getLoggedinCompany(),getQuerySelDate(query,"From"),getQuerySelDate(query,"To"));
		return generateReport(query,lst);
	}

	private QueryReport generateReport(Query query, List  list) {
		QueryReport report = new QueryReport();
		if(query.getResultType().equals("AGGREGATED")) {
			 String[] titles = new String[2];
			 titles[0]= query.getAggregationFields().getAggregationType() + "-"+  query.getAggregationFields().getAggredatedField();
			 titles[1] =query.getAggregationFields().getGroupByField();
			 report.setTitles(titles);
		}else 
			report.setTitles(query.getSelectedFields());
		if(getQuerySelDate(query,"From")!=null)  {
		report.setFrom(getQuerySelDate(query,"From").toLocaleString());
		report.setTo(getQuerySelDate(query,"To").toLocaleString());
		}
		if(!Utils.isNullList(list)) {
			for(int i = 0 ; i < list.size() ; i ++ ) {
				QueryRecord record= new QueryRecord();
				Object [] objects  = (Object [])list.get(i);
				String  []actValues = new String[objects.length];
				for (int j = 0 ; j < objects.length ; j ++ ) {
					actValues[j]=String.valueOf(objects[j]);
				}
				record.setFields(actValues);
				record.setFieldCount(objects.length);
				report.addRecord(record);
			}
		}
		return report;
	}
	
	@Override
	public List<RadsError> validate(Query query, CRMContext context) {
	   List<RadsError> errors = new ArrayList< > ();
		QueryValidator validator = new QueryValidator(context);
		return validator.validateforCreate(query);
	}
	
	
	protected ORMDAO getDAO() {
		return (QueryDAO) SpringObjectFactory.INSTANCE.getInstance("QueryDAO");
	}
	private String makeQuery(Query query) {
		StringBuffer selectFields = new StringBuffer (" select ");
		Set<String> joins= new LinkedHashSet<String>();
		Set<String> conditions= new LinkedHashSet<String>();
		for (int i = 0 ; i  < query.getSelectedFields().length ; i ++ )  {
			
			EntityField field = MetadataSQL.getEntityField(query.getEntity(),query.getSelectedFields()[i]);
			if(!Utils.isNullString(field.getHqljoinClause())) { 
				  joins.add(field.getHqljoinClause() + " " );
				  conditions.add( field.getJoinCondition()) ;
			}
			 selectFields.append( " " + query.getSelectedFields()[i] + " ");
			 if (i < query.getSelectedFields().length-1) 
				 selectFields.append(",") ;
		}
		
		Metadata metadata = MetadataSQL.getMetaDataforEntity(query.getEntity());  
		selectFields.append(" from " + metadata.getHqlClass() + " " + metadata.getHqlClass()) ;
		joins.forEach( hqlClause  ->  { 
			selectFields.append(" " +  hqlClause + " ");
		} );
		
		
		if (!Utils.isNullString( metadata.getDateField()))
			selectFields.append(" where " + metadata.getHqlClass()  + "."+ metadata.getDateField()  + " >= :fromDate "+ 
					" and  "   +  metadata.getHqlClass()  + "."+ metadata.getDateField() + " <= :toDate  and  " + metadata.getHqlClass() + ".company.id =:company and ");
		else {
			selectFields.append(" where  " + metadata.getHqlClass()  + ".company.id =:company and ");
			query.setDateExcluded(true);
		}
	
		/*conditions.forEach( condition  ->  { 
			selectFields.append(" " +  condition + " and ");
		} );*/
		if (query.getDivision().getId() > 0 ) {
			selectFields.append(" " + metadata.getHqlClass()  + "."+ "division.id=" + query.getDivision().getId() + " and ");
		}
		
		
		query.getConditions().forEach( condition ->  {  
			selectFields.append( condition.toString() );
		});
		if(!Utils.isNullString(query.getSortField())) {
				selectFields.append(" order by " +query.getSortField() + " "  + query.getSortDesc());
		}
		return selectFields.toString();
	}

	
	private String makeGroupQuery(Query query) {
		StringBuffer selectFields = new StringBuffer (" select ");
		Set<String> joins= new LinkedHashSet<String>();
		Set<String> conditions= new LinkedHashSet<String>();
		EntityField field = MetadataSQL.getEntityField(query.getEntity(),query.getAggregationFields().getAggredatedField());
		if(!Utils.isNullString(field.getHqljoinClause())) { 
			  joins.add(field.getHqljoinClause() + " " );
			  conditions.add( field.getJoinCondition()) ;
		}
		
		query.getConditions().forEach( cond -> {
			EntityField fieldCond = MetadataSQL.getEntityField(query.getEntity(),cond.getField());
			if(!Utils.isNullString(fieldCond.getHqljoinClause())) { 
				  joins.add(fieldCond.getHqljoinClause() + " " );
			}
			
		});
		
		/*for (int i = 0 ; i  < query.getConditions().size() ; i ++ )  {
			QueryCondition cond = (QueryCondition)query.getConditions().get(i);
			EntityField fieldCond = MetadataSQL.getEntityField(query.getEntity(),cond.getField());
			if(!Utils.isNullString(fieldCond.getHqljoinClause())) { 
				  joins.add(fieldCond.getHqljoinClause() + " " );
			}
		}*/
		
		selectFields.append( " " + query.getAggregationFields().getAggregationType() + "(" + query.getAggregationFields().getAggredatedField()  + "), ");
		selectFields.append(query.getAggregationFields().getGroupByField() + " ");
		Metadata metadata = MetadataSQL.getMetaDataforEntity(query.getEntity());
		selectFields.append(" from " + metadata.getHqlClass() + " " + metadata.getHqlClass()) ;
		joins.forEach( hqlClause  ->  { 
			selectFields.append(" " +  hqlClause + " ");
		} );
		
		
		if (!Utils.isNullString( metadata.getDateField()))
			selectFields.append(" where " + metadata.getHqlClass() + "."+ metadata.getDateField()  + " >= :fromDate "+ 
					" and  "   +  metadata.getHqlClass() + "."+ metadata.getDateField() + " <= :toDate  and  " + metadata.getHqlClass() + ".company.id =:company and ");
		else {
			selectFields.append(" where  " + metadata.getHqlClass()  + ".company.id =:company and ");
			query.setDateExcluded(true);
		}
		
		if (query.getDivision().getId() > 0 ) {
			selectFields.append(" " + metadata.getHqlClass() + "."+ "division.id=" + query.getDivision().getId() + " and ");
		}
		query.getConditions().forEach( condition ->  {  
			selectFields.append( condition.toString() );
		});
		
		selectFields.append(" group by  " +  query.getAggregationFields().getGroupByField() );
		
	/*	if(!Utils.isNullString(query.getSortField())) {
				selectFields.append(" order by " +query.getSortField() + " "  + query.getSortDesc());
		}*/
		return selectFields.toString();
	}

	
	@Override
	public String getVelocityConverted(QueryReport report, CRMContext context) {
		Externalize externalize = new Externalize();
        try {
        User user = context.getLoggedInUser();
        Company company= CommonUtil.getCompany(context.getLoggedinCompany());
        //VelocityEngine ve = CommonUtil.getVelocityEngine();
			VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		ve.init();
        Template t = ve.getTemplate("vmtemplates//queryResult.vm" );
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("companyName", company.getName());
        velocityContext.put("title", externalize.externalize(context, "Query_Result"));
        velocityContext.put("runon", externalize.externalize(context, "Run_on"));
        velocityContext.put("FromText", externalize.externalize(context, "From"));
        velocityContext.put("ToText", externalize.externalize(context, "To"));
        velocityContext.put("FromDate", report.getFrom());
        velocityContext.put("ToDate", report.getTo());
        velocityContext.put("runDate", new java.util.Date().toLocaleString());
        
        for(int i = 1 ; i <= 7 ; i ++){
        	if (report.getTitles().length >= i)
        		velocityContext.put("Field"+i,Utils.isNullString(report.getTitles()[i-1])?" ":report.getTitles()[i-1]);
        	else
        		velocityContext.put("Field"+i," ");
        }
        velocityContext.put("records", report.getRecords());
        
        StringWriter writer = new StringWriter();
        t.merge( velocityContext, writer );
        String content=  writer.toString();
        return content;
        
        } catch(Exception ex){
        	Logwriter.INSTANCE.error(ex);
        }

        return "";
	}
	
	
	

}
