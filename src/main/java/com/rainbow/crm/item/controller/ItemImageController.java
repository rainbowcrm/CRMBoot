package com.rainbow.crm.item.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;


import com.rainbow.crm.common.CRMAppConfig;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMGeneralController;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.config.service.ConfigurationManager;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.item.dao.ItemImageSQL;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.item.model.ItemImage;
import com.rainbow.crm.item.model.ItemImageSet;
import com.rainbow.crm.item.service.ISkuService;
import com.rainbow.crm.item.service.SkuService;
import com.rainbow.crm.logger.Logwriter;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.GeneralController;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.utils.Utils;

public class ItemImageController extends CRMGeneralController{

	ItemImageSet imageSet ;
	List<ItemImage>  images ;
	ServletContext ctx  = null ;
	@Override
	public PageResult submit(ModelObject object) {
		return new PageResult();
	}
	
	@Override
	public PageResult save(ModelObject object) {
		imageSet = (ItemImageSet) object;
		images = splitImageSet(imageSet,(CRMContext)getContext());
		if (!Utils.isNullList(images)) {
			for(ItemImage image :  images) {
				//saveFile(image.getImage(), image.getFilePath(), image.getFileName());
				if(image.getFileName().charAt(image.getFileName().length()-1) != '.' ) {
				ftpAPFile(image.getImage(), image.getFilePath(), image.getFileName(), (CRMContext)getContext());
				//ftpJCSFile(image.getImage(), image.getFilePath(), image.getFileName(), (CRMContext)getContext());
				}
			}
			saveRecords();
		}
		return new PageResult();
	}
	@Override
	public PageResult delete(ModelObject object) {
		imageSet = (ItemImageSet) object;
		ISkuService service = (ISkuService)SpringObjectFactory.INSTANCE.getInstance("ISkuService") ;
		Sku item = service.getByName(((CRMContext) getContext()).getLoggedinCompany(), imageSet.getSku().getName());
		ItemImageSQL.DeleteAllImagesforItem(item);
		imageSet.setFilewithPath1("");
		imageSet.setFilewithPath2("");
		imageSet.setFilewithPath3("");
		return new PageResult();
	}

	@Override
	public PageResult read(ModelObject object) {
		imageSet = (ItemImageSet) object;
		try {
			ISkuService service = (ISkuService)SpringObjectFactory.INSTANCE.getInstance("ISkuService") ;
			Sku item = service.getByName(((CRMContext) getContext()).getLoggedinCompany(), imageSet.getSku().getName());
			//String filePath = CRMAppConfig.INSTANCE.getProperty("Image_Path");
			String filePath = CRMAppConfig.INSTANCE.getProperty("doc_server");
			String code = ((CRMContext) getContext()).getLoggedinCompanyCode();
			ItemImage dbRecord1 = ItemImageSQL.getItemImage(item.getId(), 'a');
			if (dbRecord1 != null ) {
				imageSet.setFilewithPath1(filePath + "\\" +  code  + "\\itemimages\\" +  dbRecord1.getFileName());
			}
			ItemImage dbRecord2 = ItemImageSQL.getItemImage(item.getId(), 'b');
			if (dbRecord2 != null ) {
				imageSet.setFilewithPath2(filePath + "\\" +  code  + "\\itemimages\\" + dbRecord2.getFileName());
			}
			ItemImage dbRecord3 = ItemImageSQL.getItemImage(item.getId(), 'c');
			if (dbRecord3 != null ) {
				imageSet.setFilewithPath3(filePath + "\\" +  code  + "\\itemimages\\" + dbRecord3.getFileName());
			}
		}catch(Exception ex) {
			
		}
		return new PageResult();

	}



	private void saveRecords() {
		for(ItemImage image :  images) {
			ItemImage dbRecord = ItemImageSQL.getItemImage(image.getSku().getId(), image.getSuffix());
			if(dbRecord == null  ) {
				ItemImageSQL.insertItemImage(image,(CRMContext) getContext());
			}else {
				dbRecord.setFileName(image.getFileName());
				ItemImageSQL.updateItemImage(dbRecord,(CRMContext) getContext());
			}
		
		}
	}

	
	

	private boolean changeFolder(FTPClient ftpClient, String companyCode) throws Exception
	{
		  ftpClient.changeWorkingDirectory(companyCode);
		  int  returnCode = ftpClient.getReplyCode();
		    if (returnCode == 550) {
		    	ftpClient.makeDirectory(companyCode) ;
		    	 ftpClient.changeWorkingDirectory(companyCode);
		    }
		    ftpClient.changeWorkingDirectory("itemimages"); 
		  returnCode = ftpClient.getReplyCode();
		    if (returnCode == 550) {
		    	ftpClient.makeDirectory("itemimages") ;
		    	 ftpClient.changeWorkingDirectory("itemimages");
		    	
		    }
		    return true ;
	}
	
	private void ftpAPFile(byte[] bytes, String filepath, String fileName,
			CRMContext context) {
		if (Utils.isNullString(fileName))
			return;
		
		FTPClient  ftpClient = new FTPClient ();
		try {
			String host = CRMAppConfig.INSTANCE.getProperty("doc_server_host");
			String user = CRMAppConfig.INSTANCE.getProperty("doc_server_user");
			String pass = CRMAppConfig.INSTANCE.getProperty("doc_server_password");
			String port = CRMAppConfig.INSTANCE.getProperty("doc_server_port");
			ftpClient.setDefaultTimeout(100000);
			if (Integer.parseInt(port) > 0)
				ftpClient.connect(host,Integer.parseInt(port));
			else
				ftpClient.connect(host);
			ftpClient.enterLocalPassiveMode();
			int reply = ftpClient.getReplyCode();
			if (FTPReply.isPositiveCompletion(reply)) {
				
				ftpClient.login(user, pass);
				//FTPFile[] files = ftpClient.listFiles(directory);
				 
				//ftpClient.enterLocalPassiveMode();
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
				ByteArrayInputStream inputStream = new ByteArrayInputStream(
						bytes);
				System.out.println("Start uploading first file");
				changeFolder(ftpClient, context.getLoggedinCompanyCode());
				boolean done = ftpClient.storeFile(fileName, inputStream);
				inputStream.close();
/*				InputStream st2 = ftpClient.retrieveFileStream("/public_html/pics/MP003-a.jpg");
				if (done) {
					System.out
							.println("The first file is uploaded successfully.");
				}
*/			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	private void ftpFile (byte [] bytes , String filepath, String fileName, CRMContext context) {
		String ftpUrl = "ftp://%s:%s@%s;type=i";
		try {
		if (Utils.isNullString(fileName)) return ;
		String host = CRMAppConfig.INSTANCE.getProperty("doc_server_host");
		String user = CRMAppConfig.INSTANCE.getProperty("doc_server_user");
		String pass = CRMAppConfig.INSTANCE.getProperty("doc_server_password");
		String port = CRMAppConfig.INSTANCE.getProperty("doc_server_port");
		String uploadPath = fileName;
		
		ftpUrl = String.format(ftpUrl, user, pass, host);
		System.out.println("Upload URL: " + ftpUrl);

		
		URL url = new URL(ftpUrl);
		URLConnection conn = url.openConnection();
		OutputStream outputStream = conn.getOutputStream();
		outputStream.write(bytes);
		outputStream.close();
		   System.out.println("File uploaded");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
	}
	
	private void saveFile(byte [] bytes , String filepath, String fileName) {
		try {
		String folderPath = 	ctx.getRealPath("." ) + "//" + filepath ;
		File folder = new  File(folderPath);
		if(!folder.exists()) {
			boolean b = folder.mkdir();
			System.out.println("b=" + b);
			
		}
		FileOutputStream fos = new FileOutputStream(folderPath + "//" + fileName);
		fos.write(bytes);
		fos.close(); 
		}catch(Exception ex) {
			Logwriter.INSTANCE.error(ex);
		}
	}
	
	private String getFileExtn(String fullName) {
		if (fullName.contains(".")) {
			String laterPart = fullName.substring(fullName.indexOf(".")+1,fullName.length());
			return laterPart;
		}
		return "";
	}
	
	
	private List<ItemImage> splitImageSet(ItemImageSet set,CRMContext context) {
		
		List<ItemImage> images = new ArrayList<ItemImage> ();
		ISkuService service = (ISkuService)SpringObjectFactory.INSTANCE.getInstance("ISkuService") ;
		Sku item = service.getByName(((CRMContext) getContext()).getLoggedinCompany(), set.getSku().getName());
		try  {
			String filePath = CRMAppConfig.INSTANCE.getProperty("doc_server");
			/*String filePath = ConfigurationManager.getConfig(
					ConfigurationManager.IMAGE_SERVER_URL, context);*/
			String code = context.getLoggedinCompanyCode();
			if(set.getImage1() != null ) {
				ItemImage image = new ItemImage();
				image.setSku(item);
				image.setImage(set.getImage1());
				image.setSuffix('a');
				image.setFilePath(filePath + "/" +  code );
				image.setFileName( set.getSku().getCode() + "-" + image.getSuffix() + "." + getFileExtn(set.getFileName1()) ); 
				images.add(image);
				set.setFilewithPath1(image.getFilePath() + "/" + image.getFileName());
			}
			if(set.getImage2() != null ) {
				ItemImage image = new ItemImage();
				image.setSku(item);
				image.setImage(set.getImage2());
				image.setSuffix('b');
				image.setFilePath(filePath + "/" +  code );
				image.setFileName( set.getSku().getCode() + "-" + image.getSuffix() + "." + getFileExtn(set.getFileName2()) );
				images.add(image);
				set.setFilewithPath2(image.getFilePath() + "/" + image.getFileName());
			}
			if(set.getImage3() != null ) {
				ItemImage image = new ItemImage();
				image.setSku(item);
				image.setImage(set.getImage3());
				image.setSuffix('c');
				image.setFilePath(filePath + "/" +  code );
				image.setFileName( set.getSku().getCode() + "-" + image.getSuffix() + "." + getFileExtn(set.getFileName3()) );
				images.add(image);
				set.setFilewithPath3(image.getFilePath() + "/" + image.getFileName());
			}
		}catch(Exception ex) {
			Logwriter.INSTANCE.error(ex);
		}
		return images;
	}
	
	public String getCompanyName() {
		ICompanyService service = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company =(Company) service.getById(((CRMContext)getContext()).getLoggedinCompany());
		return company.getName();
	}
	
}
