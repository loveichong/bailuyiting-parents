package com.bailuyiting.commons.until;



import com.bailuyiting.commons.core.constants.ISystemEnum;
import com.bailuyiting.commons.core.constants.SystemJSONConstants;
import com.bailuyiting.commons.core.domain.AbstractLongEntity;
import com.bailuyiting.commons.core.domain.AbstractStringEntity;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultUtils {
	/**
	 * 
	 * @param codeEnum  返回结果代码和信息
	 * @param data  返回结果数据
	 * @return 
	 */
	public static Map<String,Object> getSCodeAndMsg(ISystemEnum<String, String> codeEnum, Object data){
		Map<String,Object>  result = new HashMap<String,Object>();
		result.put(SystemJSONConstants.RES_CODE_KEY,codeEnum.getCode());
		result.put(SystemJSONConstants.RES_MSG_KEY, codeEnum.getCodeMsg());
		if(data!=null){
		 result.put(SystemJSONConstants.RES_DATA_KEY, data);
		}
		return result;
	}
	
	/**
	 * 
	 * @param codeEnum  返回结果代码和信息
	 * @return 
	 */
	public static Map<String,Object> getICodeAndMsg(ISystemEnum<Integer, String> codeEnum){
		return getICodeAndMsg(codeEnum,null);
	}
	
	
	/**
	 * SUCCESS data
	 * @param data 返回结果代码和信息
	 * @return 
	 */
	public static Map<String,Object> success(Object data){
		return getICodeAndMsg(ISystemEnum.SystemIntegerEnum.SUCESS200,data);
	}

	/**
	 * Error DATA
	 * @param code
	 * @param messsage
	 * @param data
	 * @return
	 */
	public static Map<String,Object> errorBySQY(String code,String messsage ,Object data){
		Map<String,Object>  result = new HashMap<String,Object>();
		result.put(SystemJSONConstants.RES_CODE_KEY,code);
		result.put(SystemJSONConstants.RES_MSG_KEY, messsage);
		if(data!=null){
			if(data instanceof AbstractLongEntity){
				result.put(SystemJSONConstants.RES_DATA_KEY, JSONUtils.objectToMap(data));
			}else if(data instanceof AbstractStringEntity){
				result.put(SystemJSONConstants.RES_DATA_KEY, JSONUtils.objectToMap(data));
			}
			else{
				result.put(SystemJSONConstants.RES_DATA_KEY, data);
			}
		}
		return result;
	}
	/**
	 * 
	 *  返回结果代码和信息
	 * @return 
	 */
	public static Map<String,Object> success(){
		return getICodeAndMsg(ISystemEnum.SystemIntegerEnum.SUCESS200,null);
	}
	
	/**
	 * 
	 *  返回结果代码和信息
	 * @return 
	 */
	public static Map<String,Object> error500(){
		return getICodeAndMsg(ISystemEnum.SystemIntegerEnum.ERROR500,null);
	}
	/**
	 * 返回结果代码和描述
	 * @param data
	 * @return
	 */
	public static Map<String,Object> error500(String data){
		return getICodeAndMsg(ISystemEnum.SystemIntegerEnum.ERROR500,data);
	}
	/**
	 * 参数有误，提交恶意数据。
	 * 
	 * @return
	 */
	public static Map<String,Object> error400(){
		return getICodeAndMsg(ISystemEnum.SystemIntegerEnum.ERROR400,null);
	}
	
	/**
	 * 身份验证失败，无法获取身份，需要重新登录
	 * 
	 * @return
	 */
	public static Map<String,Object> error401(){
		return getICodeAndMsg(ISystemEnum.SystemIntegerEnum.ERROR401,null);
	}
	/**
	 * 接口权限限制，未开通授权
	 *
	 * @return
	 */
	public static Map<String,Object> error402(){
		return getICodeAndMsg(ISystemEnum.SystemIntegerEnum.ERROR402,null);
	}
	/**
	 * 对应的数据或资源不存在，无数据。
	 * 
	 * @return
	 */
	public static Map<String,Object> error404(){
		return getICodeAndMsg(ISystemEnum.SystemIntegerEnum.ERROR404,null);
	}
	/**
	 * 自定义错误信息。
	 * 
	 * @return
	 */
	public static Map<String,Object> errorByUserDefine(String code,String message){
		Map<String,Object>  result = new HashMap<String,Object>();
		result.put(SystemJSONConstants.RES_CODE_KEY,code);
		result.put(SystemJSONConstants.RES_MSG_KEY, message);
		return result;
	}
	
	/**
	 * 
	 * @param codeEnum  返回结果代码和信息
	 * @param data  返回结果数据
	 * @return 
	 */
	public static Map<String,Object> getICodeAndMsg(ISystemEnum<Integer, String> codeEnum,Object data){
		Map<String,Object>  result = new HashMap<String,Object>();
		result.put(SystemJSONConstants.RES_CODE_KEY,codeEnum.getCode());
		result.put(SystemJSONConstants.RES_MSG_KEY, codeEnum.getCodeMsg());
		if(data!=null){
			if(data instanceof AbstractLongEntity){
				result.put(SystemJSONConstants.RES_DATA_KEY, JSONUtils.objectToMap(data));
			}else if(data instanceof AbstractStringEntity){
				result.put(SystemJSONConstants.RES_DATA_KEY, JSONUtils.objectToMap(data));
			}
			else{
				result.put(SystemJSONConstants.RES_DATA_KEY, data);
			}
		}
		return result;
	}
	/**
	 * 
	 * @param codeEnum  返回结果代码和信息
	 * @return 
	 */
	public static String getICodeAndMsgR(ISystemEnum<Integer, String> codeEnum,Object data){
		String result = "";
		result = JSONUtils.objectToJson(getICodeAndMsg(codeEnum, data));
		return result;
	}
	
	
}
