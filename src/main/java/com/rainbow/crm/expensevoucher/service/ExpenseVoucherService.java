package com.rainbow.crm.expensevoucher.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.AbstractionTransactionService;
import com.rainbow.crm.common.CRMAppConfig;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMDBException;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.Externalize;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.division.service.IDivisionService;
import com.rainbow.crm.document.model.Document;
import com.rainbow.crm.hibernate.ORMDAO;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.crm.user.model.User;
import com.rainbow.crm.user.service.IUserService;
import com.rainbow.crm.expensehead.model.ExpenseHead;
import com.rainbow.crm.expensehead.service.IExpenseHeadService;
import com.rainbow.crm.expensevoucher.dao.ExpenseVoucherDAO;
import com.rainbow.crm.expensevoucher.model.ExpenseVoucher;
import com.rainbow.crm.expensevoucher.model.ExpenseVoucherLine;
import com.rainbow.crm.expensevoucher.validator.ExpenseVoucherErrorCodes;
import com.rainbow.crm.expensevoucher.validator.ExpenseVoucherValidator;
import com.rainbow.framework.nextup.NextUpGenerator;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.ui.components.SortCriteria;
import com.techtrade.rads.framework.utils.Utils;

@Transactional
public class ExpenseVoucherService extends AbstractionTransactionService implements IExpenseVoucherService{

	
	@Override
	protected String getTableName() {
		return "ExpenseVoucher";
	}
	
	
	private void loadSupplymentoryURL(ExpenseVoucherLine line)
	{
		try { 
			String serverURL = CRMAppConfig.INSTANCE.getProperty("doc_server");
			line.setFileWithLink(serverURL + line.getFilePath());
		
		}catch(Exception ex) 
		{
		  Logwriter.INSTANCE.error(ex);	
		}
	}
	

	@Override
	public Object getById(Object PK) {
		ExpenseVoucher voucher = (ExpenseVoucher) getDAO().getById(PK);
		if(!Utils.isNullSet(voucher.getExpenseVoucherLines()))  {
			for (ExpenseVoucherLine line : voucher.getExpenseVoucherLines()) {
				if(!Utils.isNull(line.getFilePath())) 
					loadSupplymentoryURL(line);
					
			}
		}
		return voucher;
	}

	@Override
	public List<CRMModelObject> listData(int from, int to,
			String whereCondition, CRMContext context, SortCriteria sortCriteria) {
		return super.listData("ExpenseVoucher", from, to, whereCondition, context,sortCriteria);
	}

	@Override
	public List<RadsError> validateforCreate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((ExpenseVoucher)object).setCompany(company);
		ExpenseVoucherValidator validator = new ExpenseVoucherValidator(context);
		return validator.validateforCreate(object);
	}

	@Override
	public List<RadsError> validateforUpdate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((ExpenseVoucher)object).setCompany(company);
		ExpenseVoucherValidator validator = new ExpenseVoucherValidator(context);
		return validator.validateforUpdate(object);
	}

	@Override
	protected ORMDAO getDAO() {
//	return new ExpenseVoucherDAO();
	return (ExpenseVoucherDAO) SpringObjectFactory.INSTANCE.getInstance("ExpenseVoucherDAO");
	}

	
	@Override
	public List<RadsError> adaptfromUI(CRMContext context,ModelObject obj) {
		ExpenseVoucher object = (ExpenseVoucher) obj;
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
				ans.add(CRMValidator.getErrorforCode(context.getLocale(), ExpenseVoucherErrorCodes.FIELD_NOT_VALID , "Division"));
			}else {
				object.setDivision(division);
			}
		}
		Externalize externalize = new Externalize(); ;
		if(!Utils.isNullSet(object.getExpenseVoucherLines())){
			int lineNo=1;
			for (ExpenseVoucherLine line: object.getExpenseVoucherLines()) {
				line.setCompany(company);
				line.setLineNumber(lineNo ++);
				if(line.getExpenseHead() == null ) {
					ans.add(CRMValidator.getErrorforCode(context.getLocale(), ExpenseVoucherErrorCodes.FIELD_NOT_VALID , externalize.externalize(context, "Item")));
				}else if  (line.getExpenseHead().getId() > 0 ) {
					int id = line.getExpenseHead().getId();
					IExpenseHeadService service = (IExpenseHeadService)SpringObjectFactory.INSTANCE.getInstance("IExpenseHeadService");
					ExpenseHead head = (ExpenseHead)service.getById(id);
					line.setExpenseHead(head);
				}else if  (!Utils.isNullString(line.getExpenseHead().getName())) {
					IExpenseHeadService service = (IExpenseHeadService)SpringObjectFactory.INSTANCE.getInstance("IExpenseHeadService");
					ExpenseHead head = (ExpenseHead) service.getByName(company.getId(), line.getExpenseHead().getName());
					line.setExpenseHead(head);
				}
			}
		}
		if (object.getSalesAssoicate() != null ) {
			IUserService service = (IUserService) SpringObjectFactory.INSTANCE.getInstance("IUserService");
			User user = (User)service.getById(object.getSalesAssoicate().getUserId());
			object.setSalesAssoicate(user);
		}
		if (object.getManager() != null ) {
			IUserService service = (IUserService) SpringObjectFactory.INSTANCE.getInstance("IUserService");
			User user = (User)service.getById(object.getManager().getUserId());
			object.setManager(user);
		}
		if (object.getStatus() == null) {
			object.setStatus(new FiniteValue(CRMConstants.EXP_VOUCHER_STATUS.REQUESTED));
		}else if (object.getStatus().getCode().equals(CRMConstants.EXP_VOUCHER_STATUS.COUNTERED) ) {
			object.setStatus(new FiniteValue(CRMConstants.EXP_VOUCHER_STATUS.REREQUESTED));
		}
		return ans;
	}

	private boolean uploadFile(ExpenseVoucherLine line, String docNo, CRMContext context)
	{
		String fileExtn = CommonUtil.getFileExtn(line.getFile1());
		String fileName =  new String(docNo + line.getLineNumber());
		fileName.replace(" ", "_")    ; 
	//	doc.setDocName(fileName +  "."  + fileExtn);
		line.setFilePath( "//" +  context.getLoggedinCompanyCode() +  "//exps//" + fileName +  "."  + fileExtn );
		CommonUtil.uploadFile(line.getFile1Data(), fileName +  "."  + fileExtn  , context, "exps");
		return true;
	}
	
	@Override
	public TransactionResult create(CRMModelObject object, CRMContext context) {
		ExpenseVoucher expenseVoucher = (ExpenseVoucher)object ;
		if (Utils.isNull(expenseVoucher.getDocNumber())) {
			String bKey = NextUpGenerator.getNextNumber("Expense Voucher", context, expenseVoucher.getDivision());
			expenseVoucher.setDocNumber(bKey);
		}
		if (!Utils.isNullSet(expenseVoucher.getExpenseVoucherLines())) {
			int pk = GeneralSQLs.getNextPKValue("ExpenseVoucher") ;
			expenseVoucher.setId(pk);
			for (ExpenseVoucherLine  line : expenseVoucher.getExpenseVoucherLines()) {
				int linePK = GeneralSQLs.getNextPKValue( "ExpenseVoucher_Lines") ;
				line.setId(linePK);
				line.setExpenseVoucherDoc(expenseVoucher);
				if (line.getFile1Data() != null ) {
					uploadFile(line,expenseVoucher.getDocNumber() ,context);
				}
			}
		}
		TransactionResult result= super.create(object, context);
		return result; 
	}

	  
	
	@Override
	public TransactionResult update(CRMModelObject object, CRMContext context) {
		ExpenseVoucher expenseVoucher = (ExpenseVoucher)object ;
		ExpenseVoucher oldObject = (ExpenseVoucher)getById(expenseVoucher.getPK());
		//ExpenseVoucher oldInvObj = (ExpenseVoucher)oldObject.clone();
		if (!Utils.isNullSet(expenseVoucher.getExpenseVoucherLines())) {
			int  ct = 0;
			Iterator it = oldObject.getExpenseVoucherLines().iterator() ;
			for (ExpenseVoucherLine  line : expenseVoucher.getExpenseVoucherLines()) {
				ExpenseVoucherLine oldLine = null ;
				if (it.hasNext()) {
					oldLine= (ExpenseVoucherLine) it.next() ;
				}
				line.setExpenseVoucherDoc(expenseVoucher);
				if (oldLine != null) {
					line.setId(oldLine.getId());
					line.setObjectVersion(oldLine.getObjectVersion());
				}else {
					int linePK = GeneralSQLs.getNextPKValue( "ExpenseVoucher_Lines") ;
					line.setId(linePK);
				}
				if (line.getFile1Data() != null ) {
					uploadFile(line,expenseVoucher.getDocNumber() ,context);
				}
			}
			while (it.hasNext()) {
				ExpenseVoucherLine oldLine= (ExpenseVoucherLine) it.next() ;
				expenseVoucher.addExpenseVoucherLine(oldLine);
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

	private ExpenseVoucher applyNewChanges(CRMContext context, ExpenseVoucher modifiedVoucher, FiniteValue status){
		ExpenseVoucher oldVoucher= (ExpenseVoucher)getById(modifiedVoucher.getPK());
		oldVoucher.setManager(context.getLoggedInUser());
		oldVoucher.setManagerComments(modifiedVoucher.getManagerComments());
		double counteredTotal= 0 ;
		if (!Utils.isNullSet(oldVoucher.getExpenseVoucherLines())) {
			int  ct = 0;
			Iterator it = oldVoucher.getExpenseVoucherLines().iterator() ;
			for (ExpenseVoucherLine  line : modifiedVoucher.getExpenseVoucherLines()) {
				ExpenseVoucherLine oldLine = null ;
				if (it.hasNext()) {
					oldLine= (ExpenseVoucherLine) it.next() ;
				}
				if (oldLine != null) {
					if (CRMConstants.EXP_VOUCHER_STATUS.COUNTERED.equals(status.getCode())) {
						oldLine.setCorrectedAmount(line.getCorrectedAmount());
						counteredTotal += line.getCorrectedAmount();
					}
					oldLine.setManagerComments(line.getManagerComments());
				}
			}
		}
		if (CRMConstants.EXP_VOUCHER_STATUS.COUNTERED.equals(status.getCode())) {
			oldVoucher.setCorrectedTotal(counteredTotal);
		}
		oldVoucher.setStatus(status);
		return oldVoucher; 
	}
	
	@Override
	public List<RadsError> approve(CRMContext context, ExpenseVoucher voucher) {
		ExpenseVoucher modvoucher = applyNewChanges(context, voucher,new FiniteValue(CRMConstants.EXP_VOUCHER_STATUS.APPROVED));
		super.update(modvoucher, context);
		return null;
	}

	@Override
	public List<RadsError> counter(CRMContext context, ExpenseVoucher voucher) {
		ExpenseVoucher modvoucher = applyNewChanges(context, voucher,new FiniteValue(CRMConstants.EXP_VOUCHER_STATUS.COUNTERED));
		super.update(modvoucher, context);
		return null;
	}

	@Override
	public List<RadsError> reject(CRMContext context, ExpenseVoucher voucher) {
		ExpenseVoucher modvoucher = applyNewChanges(context, voucher,new FiniteValue(CRMConstants.EXP_VOUCHER_STATUS.REJECTED));
		super.update(modvoucher, context);
		return null;

	}

	@Override
	public List<RadsError> hold(CRMContext context, ExpenseVoucher voucher) {
		ExpenseVoucher modvoucher = applyNewChanges(context, voucher,new FiniteValue(CRMConstants.EXP_VOUCHER_STATUS.PENDING));
		super.update(modvoucher, context);
		return null;

	}

	
}
