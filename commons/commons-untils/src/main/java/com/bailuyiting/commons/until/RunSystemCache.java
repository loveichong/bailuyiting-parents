package com.bailuyiting.commons.until;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class RunSystemCache {

	private static final Map<String,Object> runMap = new ConcurrentHashMap<String,Object>();
	
	private static final RunSystemCache runSystemCache = new RunSystemCache();
	public static RunSystemCache getInstance(){
		return runSystemCache;
	}
	/**
	 * 保存值
	 * @param key
	 * @param value
	 */
	public static void put(String key,Object value){
		runMap.put(key, value);
	}
	/**
	 * 删除值
	 * @param key
	 * @param value
	 */
	public static void del(String key){
		runMap.remove(key);
	}
	
	
	/**
	 * 得到全部对象
	 * @return
	 */
	public static Map<String,Object> getAll(){
		Map<String,Object> data = new HashMap<String,Object>();
		data.putAll(runMap);
		return data;
	}
	
	/**
	 * 获得对应的值
	 * @param key
	 * @return
	 */
	public static Object get(String key){
		return runMap.get(key);
	}
	
	
//	/**
//	 * 得到系统运行时间
//	 * @return
//	 */
//	public static String getSystemRunTIme(){
//		Object obj=runMap.get(InitRunSystemCacheConstants.SYSTEM_RUN_TIME);
//		if(obj!=null){
//			Long l = (Long)obj;
//			long cl = System.currentTimeMillis();
//			long ll=cl-l;
//			long hour=ll/DateUtils.HOUR_LONG;
//			long yhour=ll%DateUtils.HOUR_LONG;
//			long minutes = yhour/(60*1000l);
//			long yminutes = yhour%(60*1000l);
//			long second =yminutes/1000l;
//			StringBuffer sb = new StringBuffer();
//			if(hour>0){
//				sb.append(hour).append("小时");
//			}
//			
//			if(minutes>0){
//				sb.append(minutes).append("分");
//			}
//			
//			if(second>0){
//				sb.append(second).append("秒");
//			}
//			return sb.toString();
//		}
//		return "";
//	}
}
