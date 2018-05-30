package com.bailuyiting.commons.until;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONUtils {

	/**
	 *  把json转换成map
	 * @param json
	 * @return
	 */
	public static Map<String, Object> jsonToMap(String json) {
		if(json==null){
			return null;
		}
		JSONObject jobj = (JSONObject) JSON.parse(json);
		Map<String, Object> rmap = new HashMap<String, Object>();
		rmap.putAll(jobj);
		return jobj;
	}
	
	/**
	 *  把json转换成list
	 * @param json
	 * @return
	 */
	public static  <T>  List<T>  jsonToList(String json,Class<T> t) {
		 List<T> jobj =  (List<T> )JSON.parseArray(json, t);
		return jobj;
	}

	/**
	 * 把json转换成对象
	 * @param json
	 * @param t
	 * @return
	 */
	public static <T> T jsonToObject(String json, Class<T> t) {
		T jobj = JSON.parseObject(json, t);
		
 
		return jobj;
	}

	
	
	/**
	 * 把json转换成对象
	 * @param json
	 * @param t
	 * @return
	 */
	public static   <T>  List<T> jsonToLists(String json, Class<T> t) {
		List<T>  jobj = 	JSON.parseArray(json, t);
		return jobj;
	}

	/**
	 * 把json转换成对象
	 * @param json
	 * @param t
	 * @return
	 */
	public static <T> T jsonToObject(Map<String,Object> json, Class<T> t) {
		return jsonToObject(objectToJson(json),t);
	}
	/**
	 * 把对象转换成json
	 * @param obj
	 * @return
	 */
	public static String objectToJson(Object obj) {
		if(obj==null){
			return null;
		}
		return JsonBinder.toJson(obj);
	}
	
	
	/**
	 * 把对象转换成map
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> objectToMap(Object obj) {
		String json =	objectToJson(obj);
		return jsonToMap(json);
	}
	
	
	/**
	 * 把对象转换成list
	 * @param obj
	 * @return
	 */
	public static <T extends Object> List<T> objectToList(Object obj,Class<T> t) {
		String json =	objectToJson(obj);
		return jsonToList(json, t);
	}

	
}
