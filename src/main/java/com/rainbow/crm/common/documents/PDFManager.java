package com.rainbow.crm.common.documents;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;


public class PDFManager {

	public PDDocument writeData(PrintDocument printDocument, String fileName) throws IOException
	{
		PDDocument doc = new PDDocument();
        try
        {
            PDPage page = new PDPage();
            doc.addPage(page);
            PDFont font = PDType1Font.COURIER;
            PDPageContentStream contents = new PDPageContentStream(doc, page);
            contents.beginText();
            contents.setFont(font, 12);
            contents.newLine();
            contents.showText(printDocument.getTitle());
            contents.newLine();
            contents.showText(printDocument.getSubTitle());
            printDocument.getHeaderLines().forEach( headerline ->  
            {
            	try {
            	   	contents.newLine();
            	   	contents.showText(headerline.toString());
            	}catch(IOException ex) {
            		ex.printStackTrace();
            	}
            	
            } 
            )  ; 
            int rowNum = 0;
            for(PrintDetail printDetail : printDocument.getPrintDetails()) {
            	printDetailContent(contents,printDetail,++rowNum);
            }
            /*printDocument.getPrintDetails().forEach(printDetail ->
            {
            	try {
            	printDetailContent(contents,printDetail,rowNum);
            	rowNum ++;
            	}catch(Exception ex){
            		ex.printStackTrace();
            	}
            });*/
            
            printDocument.getSummaryLines().forEach( summaryLine ->  
            {
            	try {
            	   	contents.newLine();
            	   	contents.showText(summaryLine.toString());
            	}catch(IOException ex) {
            		ex.printStackTrace();
            	}
            } 
            )  ;
            
            
            contents.endText();
            contents.close();
            doc.save(fileName);
        }
        finally
        {
            doc.close();
        }
		
        return doc ;
	}
	
	private void printDetailContent(PDPageContentStream contents,PrintDetail printDetail, int row)  throws IOException{
		StringBuffer buff =new StringBuffer();
		if (row == 1) {
			printDetail.getColumnHeaders().forEach( header -> { 
				buff.append(header);
			});
		}
		contents.newLine();
		if (buff.length() > 1) {
			contents.showText(buff.toString());
			contents.newLine(); 
		}
		
		printDetail.getPrintLines().forEach(printLine -> { 
			try  {
				contents.showText(printLine.toString());
			}catch(IOException ioex) {
				ioex.printStackTrace();
			}
			
		});
		
		
	}

	
}
