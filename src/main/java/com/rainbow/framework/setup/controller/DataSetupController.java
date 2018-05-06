package com.rainbow.framework.setup.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rainbow.crm.common.CRMAppConfig;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.crm.user.model.User;
import com.rainbow.framework.setup.dao.DataSetupSQL;
import com.rainbow.framework.setup.model.DataLoader;
import com.rainbow.framework.setup.model.Metadata;
import com.rainbow.framework.setup.service.IDataLoaderService;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.GeneralController;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.ui.abstracts.UIPage;

public class DataSetupController extends GeneralController{

	ServletContext ctx;
	HttpServletResponse resp;
	@Override
	public PageResult submit(ModelObject object) {
		DataLoader dataLoader =(DataLoader) object;
		IDataLoaderService service = getService();
		PageResult result  = new PageResult();
		try {
		if("DownLoad".equalsIgnoreCase(dataLoader.getAction())) {
			Metadata metadata = DataSetupSQL.getMetadataforClass(dataLoader.getExportmetadataClass()) ;
			if("XML".equals(dataLoader.getExportFormat()) ) {
				String xml=	service.exportXML(dataLoader, (CRMContext)getContext());
				//writeIntoFile(xml,(CRMContext)getContext(),dataLoader.getExportmetadataClass(),"xml");
				resp.setHeader("Content-Disposition","attachment; filename=" + metadata.getObjectName()+ ".xml" );
	    		resp.getWriter().write(xml,0,xml.length());
			} else if ("JSON".equals(dataLoader.getExportFormat()) ) {
				String json=	service.exportJSON(dataLoader, (CRMContext)getContext());
				resp.setHeader("Content-Disposition","attachment; filename=" + metadata.getObjectName()+ ".json" );
	    		resp.getWriter().write(json,0,json.length());
			}else if ("XLS".equals(dataLoader.getExportFormat()) ) {
				File file=	service.exportXLS(dataLoader, (CRMContext)getContext());
				resp.setContentType("application/xls");
				resp.setHeader("Content-Disposition","attachment; filename=" + metadata.getObjectName()+ ".xls" );
				FileInputStream fileInputStream = new FileInputStream(file);
	            OutputStream responseOutputStream = resp.getOutputStream();
	            int bytes;
	            while ((bytes = fileInputStream.read()) != -1) {
	                responseOutputStream.write(bytes);
	            }
	            fileInputStream.close();
	            responseOutputStream.close();
			}
			result.setResponseAction(PageResult.ResponseAction.FILEDOWNLOAD);
		}else if("Upload".equalsIgnoreCase(dataLoader.getAction())) {
			Logwriter.INSTANCE.debug("UpLoad" + dataLoader.getImportMetadataClasss());
			if("XML".equals(dataLoader.getImportFormat()) ) {
				System.out.println(dataLoader.getImportData());
				service.importXML(dataLoader, (CRMContext)getContext());
			}else if("XLS".equals(dataLoader.getImportFormat()) ) {
				System.out.println(dataLoader.getImportData());
				service.importXLS(dataLoader, (CRMContext)getContext());
			}else if("JSON".equals(dataLoader.getImportFormat()) ) {
				System.out.println(dataLoader.getImportData());
				service.importJSON(dataLoader, (CRMContext)getContext());
				
			}
		}
		}catch(Exception ex) {
			Logwriter.INSTANCE.error(ex);
		}
		return result;
	}
	
    private void writeIntoFile(String content, CRMContext context,String type,String format) {
    	try {
    		String path = CRMAppConfig.INSTANCE.getProperty("Data_Loader_Path");
    		String folderPath = 	ctx.getRealPath("." ) + "//" + path + "//" + context.getLoggedinCompanyCode() ;
    		File folder = new  File(folderPath);
    		if(!folder.exists()) {
    			boolean b = folder.mkdir();
    			System.out.println("b=" + b);
    			
    		}
    		String fileName = context.getAuthenticationToken() + "_" + type + "."  + format ;
    		FileOutputStream fos = new FileOutputStream(folderPath + "//" + fileName);
    		fos.write(content.getBytes());
    		fos.close();
    		
    	}catch(Exception ex) {
    		
    	}
    }
	
	public IDataLoaderService getService() {
		return (IDataLoaderService) SpringObjectFactory.INSTANCE.getInstance("IDataLoaderService");
	}

	@Override
	public IRadsContext generateContext(HttpServletRequest request,HttpServletResponse response,UIPage page) {
		ctx =  request.getServletContext() ;
		resp = response ;
		return CommonUtil.generateContext(request, response,page);
	}
	
	
	
	@Override
	public IRadsContext generateContext(String authToken,UIPage page) {
		return CommonUtil.generateContext(authToken,page) ;
		//return LoginSQLs.loggedInUser(authToken);
	}

	public String getCompanyName() {
		ICompanyService service = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company =(Company) service.getById(((CRMContext)getContext()).getLoggedinCompany());
		return company.getName();
	}
	
	public Map<String,String> getAllMetaData() {
		List<Metadata> metaDataList =DataSetupSQL.getMetadata();
		Map<String,String> ans = new HashMap<String,String> ();
		for (Metadata metaData : metaDataList) {
			ans.put(metaData.getClassName(),metaData.getObjectName() );
		}
		return ans;
	}
	public Map<String,String> getAllFormats() {
		Map<String,String> ans = new HashMap<String,String> ();
		ans.put("XLS","Excel" );
		ans.put("XML","XML" );
		ans.put("JSON","JSON" );
		return ans;
	}

}
