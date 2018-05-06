package com.rainbow.framework.setup.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.CRMAppConfig;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.ITransactionService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.framework.setup.model.DataLoader;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.utils.Utils;
import com.techtrade.rads.framework.utils.XMLDocument;
import com.techtrade.rads.framework.utils.XMLElement;

public class DataLoaderService implements IDataLoaderService {

	
	@Override
	public void importXLS(DataLoader dataLoader, CRMContext context) {
		try { 
		String title = dataLoader.getImportMetadataClasss();
		String path = CRMAppConfig.INSTANCE.getProperty("Data_Loader_Path");
		String folderPath =path + "//" + context.getLoggedinCompanyCode();
		File folder = new  File(folderPath);
		if(!folder.exists()) {
			boolean b = folder.mkdir();
			System.out.println("b=" + b);
		}
		String fileName = context.getAuthenticationToken() + "_" + title + ".xls" ;
		FileOutputStream fos = new FileOutputStream(folderPath + "//" + fileName);
		fos.write(dataLoader.getImportData());
		fos.close(); 
		
		FileInputStream file = new FileInputStream(new File(folderPath + "//" + fileName));
		String className = dataLoader.getImportMetadataClasss();
		String service = getServiceforClass(className);
		IBusinessService businessService = (IBusinessService)SpringObjectFactory.INSTANCE.getInstance(service) ;
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();
        String titles[] = new String[40] ;
        int rowCount = 0; 
        while (rowIterator.hasNext()) 
        {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            Map objectMap = new HashMap();
            int colIndex = 0 ;
            while (cellIterator.hasNext()) 
            {
            	Cell cell = cellIterator.next();
            	if (rowCount == 0) {
            		titles[colIndex ++ ] = cell.getStringCellValue();
            	}else {
            		cell.setCellType(Cell.CELL_TYPE_STRING);
            		objectMap.put(titles[colIndex ++],cell.getStringCellValue());
/*            		switch (cell.getCellType()) 
                    {
                        case Cell.CELL_TYPE_NUMERIC:
                        	;
                            objectMap.put(titles[colIndex ++],cell.getNumericCellValue());
                            break;
                        case Cell.CELL_TYPE_STRING:
                        	objectMap.put(titles[colIndex ++],cell.getStringCellValue());
                            break;
                    }*/
            	}
            }
            rowCount ++ ;
            if(rowCount > 1) {
            	Map<String,Object> newMap =massageMap(objectMap);
            	CRMBusinessModelObject dataObject =(CRMBusinessModelObject) ModelObject.instantiateObjectfromMap(newMap, className, context);
            	System.out.println(" obj"  + dataObject  );
            	CRMBusinessModelObject object = (CRMBusinessModelObject)businessService.getByBusinessKey(dataObject, context);
				if(object == null) {
					List<RadsError > errors =businessService.validateforCreate(dataObject, context);
					if (Utils.isNullList(errors))
					businessService.create(dataObject, context);
				}else if ( dataLoader.isUpdateEntries()){
					dataObject.setId(object.getId());
					dataObject.setCompany(object.getCompany());
					dataObject.setObjectVersion(object.getObjectVersion());
					List<RadsError > errors =businessService.validateforUpdate(dataObject, context);
					if (Utils.isNullList(errors))
					businessService.update(dataObject, context);
				}
            }
        }
        
		}catch(Exception ex) {
			Logwriter.INSTANCE.error(ex);
		}
	}
	
	private Map massageMap(Map map) {
		Iterator it = map.keySet().iterator() ;
		Map<String,Object> ans = new HashMap<String,Object>();
		while (it.hasNext()) {
			String key = String.valueOf(it.next());
			Object value = map.get(key);
			if (key.contains(".")) {
				String firstPart = key.substring(0, key.indexOf("."));
				String laterPart = key.substring(key.indexOf(".")+1,key.length());
				Object obj = ans.get(firstPart) ;
				if (obj == null) {
					ans.put(firstPart, new HashMap<String,Object>());
				}
				Map<String,Object> subMap = (Map)ans.get(firstPart);
				subMap.put(laterPart, value) ;
				massageMap(subMap);
				ans.put(firstPart, subMap);
			}else
				ans.put(key,value);
		}
		return ans;
	}
	
	@Override
	public void importXML(DataLoader dataLoader, CRMContext context) {
		try{
			String xmlData = new String(dataLoader.getImportData());
			String className = dataLoader.getImportMetadataClasss();
			XMLDocument document = XMLDocument.parseXMLString(xmlData);
			XMLElement rootElement =document.getRootElement() ;
			String service = getServiceforClass(className);
			IBusinessService businessService = (IBusinessService)SpringObjectFactory.INSTANCE.getInstance(service) ;
			if (rootElement != null )
				for (XMLElement objectElement : rootElement.getAllChildElements()){
					CRMBusinessModelObject obj =(CRMBusinessModelObject)ModelObject.instantiateObjectfromXML(objectElement.toString(), className, context);
					System.out.println("obj=" + obj);
					CRMBusinessModelObject object = (CRMBusinessModelObject)businessService.getByBusinessKey(obj, context);
					if(object == null) {
						if (businessService instanceof ITransactionService) {
							List<RadsError > errors =((ITransactionService) businessService).adaptfromUI( context,obj);
							if (!Utils.isNullList(errors)) {
								break ;
							}
						}
						List<RadsError > errors =businessService.validateforCreate(obj, context);
						if (Utils.isNullList(errors))
						businessService.create(obj, context);
					}else if ( dataLoader.isUpdateEntries()){
						obj.setObjectVersion(object.getObjectVersion());
						obj.setId(object.getId());
						obj.setCompany(object.getCompany());
						List<RadsError > errors =businessService.validateforUpdate(obj, context);
						if (Utils.isNullList(errors))
						businessService.update(obj, context);
					}
				}
			}catch(Exception ex) {
				Logwriter.INSTANCE.error(ex);
			}
	}

	@Override
	public void importJSON(DataLoader dataLoader, CRMContext context) {
		try{
			String jsonData = new String(dataLoader.getImportData());
			String className = dataLoader.getImportMetadataClasss();
			JSONTokener  tokener = new JSONTokener(jsonData);
			JSONObject root = new JSONObject(tokener);
			String key = null;
			Iterator it = root.keys();
			while (it.hasNext()) {
				 key = (String)it.next() ;
			}
			String service = getServiceforClass(className);
			IBusinessService businessService = (IBusinessService)SpringObjectFactory.INSTANCE.getInstance(service) ;
			JSONArray array = root.getJSONArray(key);
			if (array != null )
				for (int i = 0 ; i <  array.length() ; i ++){
					JSONObject json = array.getJSONObject(i);
					CRMBusinessModelObject obj =(CRMBusinessModelObject)ModelObject.instantiateObjectfromJSON(json.toString(), className, context);
					System.out.println("obj=" + obj);
					CRMBusinessModelObject object = (CRMBusinessModelObject)businessService.getByBusinessKey(obj, context);
					if(object == null) {
						if (businessService instanceof ITransactionService) {
							List<RadsError > errors =((ITransactionService) businessService).adaptfromUI( context,obj);
							if (!Utils.isNullList(errors)) {
								break ;
							}
						}
						List<RadsError > errors =businessService.validateforCreate(obj, context);
						if (Utils.isNullList(errors)) {
							businessService.create(obj, context);
						}
					}else if ( dataLoader.isUpdateEntries()){
						obj.setObjectVersion(object.getObjectVersion());
						obj.setId(object.getId());
						obj.setCompany(object.getCompany());
						List<RadsError > errors =businessService.validateforUpdate(obj, context);
						if (Utils.isNullList(errors))
						businessService.update(obj, context);
					}
				}
			}catch(Exception ex) {
				Logwriter.INSTANCE.error(ex);
			}
	}

	private String getServiceforClass(String className) {
		switch(className) {
		case "com.rainbow.crm.division.model.Division" :return "IDivisionService";
		case "com.rainbow.crm.customer.model.Customer" :return "ICustomerService";
		case "com.rainbow.crm.vendor.model.Vendor" :return "IVendorService";
		case "com.rainbow.crm.user.model.User" :return "IUserService";
		case "com.rainbow.crm.product.model.Product" :return "IProductService";
		case "com.rainbow.crm.item.model.Sku" :return "ISkuService";
		case "com.rainbow.crm.item.model.Item" :return "IItemService";
		case "com.rainbow.crm.inventory.model.Inventory" :return "IInventoryService";
		case "com.rainbow.crm.purchase.model.Purchase" :return "IPurchaseService";
		case "com.rainbow.crm.sales.model.Sales" :return "ISalesService";
		case "com.rainbow.crm.saleslead.model.SalesLead" :return "ISalesLeadService";
		case "com.rainbow.crm.wishlist.model.WishList" :return "IWishListService";
		case "com.rainbow.crm.contact.model.Contact" :return "IContactService";
		case "com.rainbow.crm.followup.model.Followup" :return "IFollowupService";
		case "com.rainbow.crm.expensevoucher.model.ExpenseVoucher" :return "IExpenseVoucherService";
		}
		return null;
	}
	
	@Override
	public String exportXML(DataLoader dataLoader, CRMContext context) {
		try {
			String className = dataLoader.getExportmetadataClass();
		ModelObject object = (ModelObject)Class.forName(className).newInstance();
		StringBuffer buffer=  new StringBuffer();
		String title = object.getClass().getSimpleName() + "s";
		String service = getServiceforClass(className);
		if (!Utils.isNullString(service)) {
			String whereCondition = dataLoader.isExcludeDeleted()?" where deleted='false'":null;
			IBusinessService businessService = (IBusinessService)SpringObjectFactory.INSTANCE.getInstance(service) ;
			List<? extends CRMModelObject> objects= businessService.listData(0, 20000, whereCondition, context,null);
			buffer.append("<" + title + ">");
			if (!Utils.isNullList(objects)) {
				for (CRMModelObject modelObject : objects) {
					buffer.append(modelObject.toXML());
				}
			}
			buffer.append("</" + title + ">");
			System.out.println(buffer.toString());
		}
		return buffer.toString();
		}catch(Exception ex) {
			Logwriter.INSTANCE.error(ex);
		}
		return null;
	}
	
	
	

	@Override
	public String exportJSON(DataLoader dataLoader, CRMContext context) {
		try {
			String className = dataLoader.getExportmetadataClass();
			ModelObject object = (ModelObject)Class.forName(className).newInstance();
			StringBuffer buffer=  new StringBuffer();
			String title = object.getClass().getSimpleName() ;
			String service = getServiceforClass(className);
			if (!Utils.isNullString(service)) {
				String whereCondition = dataLoader.isExcludeDeleted()?" where deleted='false'":null;
				IBusinessService businessService = (IBusinessService)SpringObjectFactory.INSTANCE.getInstance(service) ;
				List<? extends CRMModelObject> objects= businessService.listData(0, 20000, whereCondition, context,null);
				buffer.append("{ \n ");
				buffer.append("\""+ title +"\":[\n");
				if (!Utils.isNullList(objects)) {
					for (CRMModelObject modelObject : objects) {
						buffer.append(modelObject.toJSON() + ",");
					}
				}
				buffer.setCharAt(buffer.length()-1, ' ');
				buffer.append("]\n");
				buffer.append("}");
				System.out.println(buffer.toString());
			}
			return buffer.toString();
			}catch(Exception ex) {
				Logwriter.INSTANCE.error(ex);
			}
			return null;
	}

	
	private void writeData(Map objectMap,Row row, MutableInt cellnum) {
		 Iterator it = objectMap.keySet().iterator() ;
		 while(it.hasNext()) {
			 String str = String.valueOf(it.next());
			 Object value = objectMap.get(str);
			 if (value instanceof Map) {
				writeData((Map)value,row,cellnum);
			 }else{
				 Cell cell = row.createCell(cellnum.intValue());
				 cellnum.increment();
				 cell.setCellValue(String.valueOf(value));
			 }
		 }
	}
	
	private void writeTitle(Map objectMap,Row row, MutableInt  cellnum,String prefix) {
		 Iterator it = objectMap.keySet().iterator() ;
		 while(it.hasNext()) {
			 String key =String.valueOf(it.next());
			 String str = prefix + key;
			 Object value = objectMap.get(key);
			 if (value instanceof Map) {
				writeTitle((Map)value,row,cellnum,str +".");
			 }else{
				 Cell cell = row.createCell(cellnum.intValue());
				 cellnum.increment();
				 cell.setCellValue(str);
			 }
		 }
	}
	
	@Override
	public File exportXLS(DataLoader dataLoader, CRMContext context) {
		try {
			String className = dataLoader.getExportmetadataClass();
			ModelObject object = (ModelObject)Class.forName(className).newInstance();
			String title = object.getClass().getSimpleName() ;
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet(title);
			String service = getServiceforClass(className);
			if (!Utils.isNullString(service)) {
				String whereCondition = dataLoader.isExcludeDeleted()?" where deleted='false'":null;
				IBusinessService businessService = (IBusinessService)SpringObjectFactory.INSTANCE.getInstance(service) ;
				List<? extends CRMModelObject> objects= businessService.listData(0, 20000, whereCondition, context,null);
				int rowCount= 0;
				Row row= sheet.createRow(rowCount++);
				if (!Utils.isNullList(objects)) {
					for (CRMModelObject model : objects ) {
						MutableInt cellNumber = new MutableInt(0);
						Map objectMap =model.toMap();
						if(rowCount == 1 ){
							writeTitle(objectMap,row,cellNumber,"");
						}
						Row dataRow= sheet.createRow(rowCount++);
						Iterator it = objectMap.keySet().iterator() ;
						MutableInt cellNum =new MutableInt(0);
						writeData(objectMap, dataRow, cellNum); 
					}
				}
			}
		String path = CRMAppConfig.INSTANCE.getProperty("Data_Loader_Path");
		String folderPath =path + "//" + context.getLoggedinCompanyCode();
		File folder = new  File(folderPath);
		if(!folder.exists()) {
			boolean b = folder.mkdir();
			System.out.println("b=" + b);
			
		}
		String fileName = context.getAuthenticationToken() + "_" + title + ".xls" ;
		FileOutputStream fos = new FileOutputStream(folderPath + "//" + fileName);
		workbook.write(fos);
		fos.close();
		File file = new File(folderPath + "//" + fileName);
		return file;
		}catch(Exception ex) {
			Logwriter.INSTANCE.error(ex);
		}
		return null;
	}
	
	
	

}
