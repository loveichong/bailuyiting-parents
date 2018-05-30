package com.bailuyiting.commons.until;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class ResultMapUtils {

	/**
	 * 
	 * @param data 对象数据
	 * @param rdata map数据
	 * @return
	 */
	public static Map<String, Object> convertMap(Object data,Map<String, Object> rdata) {
		Map<String, Object> rmap = new HashMap<String, Object>();
		Map<String, Object>  jobj =  JSONUtils.objectToMap(data);
		if (jobj != null) {
			rmap.putAll(jobj);
		}
		if (rdata != null) {
			rmap.putAll(rdata);
		}
		return rmap;
	}
	
	 
	
	/**
	 * 
	 * @param data 对象数据
	 * @param rdata map数据
	 * @param excludes 需剔除的数据列表 例如 str1,str2
	 * @return
	 */
	public static Map<String, Object> convertMap(Object data,Map<String, Object> rdata,String excludes) {
		Map<String,Object> result=convertMap(data, rdata);
		if(StringUtils.isNotEmpty(excludes)){
			for(String exclude:excludes.split(",")){
				result.remove(exclude);
			}
		}
		return result;
	}
}
