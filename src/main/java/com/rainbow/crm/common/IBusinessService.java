package com.rainbow.crm.common;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.company.model.Company;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.ui.components.SortCriteria;

public interface IBusinessService {

	
	public long getTotalRecordCount(CRMContext context,String whereCondition);
	
	public Object getById(Object PK) ;
	
	public CRMModelObject getByBusinessKey(CRMModelObject object, CRMContext context);
	
	public List<? extends CRMModelObject> listData(int from , int to , String whereCondition, CRMContext context, SortCriteria sortCriteria );
	
	@Transactional
	public TransactionResult create(CRMModelObject object, CRMContext context) ;
	
	@Transactional
	public TransactionResult createFromScratch(CRMModelObject object, CRMContext context) ;
	
	@Transactional
	public TransactionResult update(CRMModelObject object, CRMContext context) ;
	
	@Transactional
	public TransactionResult batchUpdate(List<CRMModelObject> objects, CRMContext context ) ;
	
	public List<RadsError> validateforCreate(CRMModelObject object, CRMContext context);
	
	public List<RadsError> validateforUpdate(CRMModelObject object, CRMContext context);
	
	public List<? extends CRMModelObject> findAll(String className, String whereCondition, String orderBy, CRMContext context);
	

}
