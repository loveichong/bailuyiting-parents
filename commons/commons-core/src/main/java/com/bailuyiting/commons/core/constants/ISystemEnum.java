package com.bailuyiting.commons.core.constants;

public interface ISystemEnum<T,V>{
	public T getCode();
	public V getCodeMsg();
	public   enum SystemStringEnum implements ISystemEnum<String,String>{
		SUCESS("0000","成功"),
		SUCESS200("200","处理成功"),
		ERROR400("400","参数有误。"),
		ERROR401("401","身份验证失败。需要重新登录"),
		ERROR402("402","未开通授权"),
		ERROR403("403","拒绝请求（没有相应的操作权限）。"),
		ERROR404("404","对应的数据或资源不存在。"),
		ERROR405("405","请求方法不被允许"),
		ERROR406("406","密钥失效"),
		ERROR407("407","订单已经被其他人接受,抢单失败。"),
		ERROR409("409","数据保存失败，重复的数据"),
		ERROR410("410","文件MD5校验失败"),
		ERROR426("426","数据冲突，服务端已存在版本号更高的数据"),
		ERROR500("500","请求失败，服务器发生异常，未知错误。"),
		ERROR505("505","请求失败，客户端版本需要强制更新。"),
		ERROR601("601","用户名或密码错误"),
		ERROR602("602","验证码失效"),
		ERROR603("603","验证码错误"),
		ERROR604("604","1小时内连续登录失败次数超过5次，1小时内禁止登录");
		private SystemStringEnum(String code,String msg){
			this.code=code;
			this.msg=msg;
		}
		private String code;
		private String msg;
		public String getCode(){
			return code;
		}
		public String getCodeMsg(){
			return msg;
		}
		
		@Override
		public String toString() {
			return super.toString()+"   code="+this.code+"  msg="+this.msg;
		}
	}
	
	
	public   enum SystemIntegerEnum implements ISystemEnum<Integer,String>{
		SUCESS(1,"成功"),
		SUCESS200(200,"处理成功"),
		ERROR400(400,"参数有误。"),
		ERROR401(401,"身份验证失败。需要重新登录"),
		ERROR402(402,"未开通授权"),
		ERROR403(403,"拒绝请求（没有相应的操作权限）。"),
		ERROR404(404,"对应的数据或资源不存在。"),
		ERROR405(405,"请求方法不被允许"),
		ERROR406(406,"密钥失效"),
		ERROR407(407,"订单已经被其他人接受,抢单失败。"),
		ERROR409(409,"数据保存失败，重复的数据"),
		ERROR410(410,"文件MD5校验失败"),
		ERROR426(426,"数据冲突，服务端已存在版本号更高的数据"),
		ERROR500(500,"请求失败，服务器发生异常，未知错误。"),
		ERROR505(505,"请求失败，客户端版本需要强制更新。"),
		ERROR600(600,null);
		private SystemIntegerEnum(Integer code,String msg){
			this.code=code;
			this.msg=msg;
		}
		private Integer code;
		private String msg;
		public Integer getCode(){
			return code;
		}
		public String getCodeMsg(){
			return msg;
		}


		public String getMsg() {
			return msg;
		}


		@Override
		public String toString() {
			return super.toString()+"   code="+this.code+"  msg="+this.msg;
		}
	}
}
