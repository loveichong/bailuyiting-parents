package com.bailuyiting.commons.until;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class DecodeUtils {

	public static final Logger log = Logger.getLogger(DecodeUtils.class);
	public static String encode(String value){
		if(StringUtils.isEmpty(value)){
			return value;
		}
//		 try {
//			 log.info(value=new String(value.getBytes("ISO8859_1"),"utf-8"));
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		 
		 //4
//		try {
//			return new String(Base64.encodeBase64(value.getBytes("utf-8")));
//		} catch (UnsupportedEncodingException e) {
//			 
//			e.printStackTrace();
//		}
		return value;
	}
	 
	
	public static String decode(String value){
		if(StringUtils.isEmpty(value)){
			return value;
		}
		String osName=System.getProperty("os.name") ;
		
		if(StringUtils.isNotEmpty(osName) && (osName.trim().toLowerCase().indexOf("window")==-1)){
			 try {
				  value=new String(value.getBytes("ISO8859_1"),"utf-8");
				  
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
		}
		try {
			value =URLDecoder.decode(value.replace("%", "％"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
//		try {
//			return new String(Base64.decodeBase64(value.getBytes("utf-8")));
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
		return value;
	}
	 public static void main(String[] args) {
		decode("20@#/-. %".replace("%", "％"));
		decode("http%3A%2F%2Fbaidu.com");
	}
}
