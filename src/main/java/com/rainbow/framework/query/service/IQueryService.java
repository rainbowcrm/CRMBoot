package com.rainbow.framework.query.service;

import java.util.List;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.framework.query.model.Query;
import com.rainbow.framework.query.model.QueryReport;
import com.techtrade.rads.framework.model.abstracts.RadsError;

public interface IQueryService {
	
	public  QueryReport getResult(Query query , CRMContext context);
	
	public List<RadsError> validate (Query query , CRMContext context);

	public String getVelocityConverted(QueryReport query , CRMContext context);
	
	public Query saveQuery(Query query , CRMContext context);
	
	public Query getQuery(int id);
	
	public List<Query> getAllQueriesforOwner(CRMContext context);

}
