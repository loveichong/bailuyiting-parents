package com.bailuyiting.commons.core.entity.module.sms;



import com.bailuyiting.commons.core.domain.AbstractStringEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table
@Entity(name = "sms_verify_code")
public class SMSVerifyCode extends AbstractStringEntity {

	private static final long serialVersionUID = -8977690305734743024L;
    /**
     * 用户手机号（账户）
     */
	@Column(length =45,nullable=true,unique=false)
	private String phone;
    /**
     * 用户类型
     */
	@Column(length =10,nullable=true,unique=false)
	private String comeFrom;
    /**
     * 验证码
     */
	@Column(length =10,nullable=true,unique=true)
	private String verifyCode;
	/**
	 * 验证码过期时间
	 */
	@Column(length =30,nullable=false,unique=false)
	private String expireTime;
	/**
	 *
	 */
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getComeFrom() {
		return comeFrom;
	}

	public void setComeFrom(String comeFrom) {
		this.comeFrom = comeFrom;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public String getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}
}
