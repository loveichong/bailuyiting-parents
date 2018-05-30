package com.bailuyiting.module.wechat.until;

import java.io.UnsupportedEncodingException;


/**
 * 通用工具类
 * @author linshengru
 * @date 2014-04-11
 *
 */
public class CommonUtil {
	//private static Logger log=LoggerFactory.getLogger(CommonUtil.class);
	
	/**
	 * URL编码（utf-8）
	 * @param source
	 * @return
	 */
	public static String urlEncodeUTF8(String source){
		String result=source;
		try{
			result=java.net.URLEncoder.encode(source,"utf-8");
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}
		return result;
	}
	
	
}
