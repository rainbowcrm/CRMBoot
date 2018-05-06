package com.rainbow.crm.customer.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.Base64;


public class MakeBase64 {

	public static void main (String args[])
	{
		try {
		System.out.println("helo base64 string") ;
		File f =  new File("C:/Users/Unnikrishnan/Desktop/Data/pics/ASAL.jpg");
		FileInputStream inpStream = new FileInputStream(f);
		byte[] bytes = new byte[(int)f.length()];
		inpStream.read(bytes);
        String encodedfile =  Base64.getEncoder().encodeToString(bytes);
        System.out.println(encodedfile);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
}
