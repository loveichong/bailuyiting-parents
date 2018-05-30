package com.bailuyiting.commons.core.entity.module.sms;

import com.bailuyiting.commons.core.domain.AbstractStringEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table
@Entity(name = "sms_verify_code_history")
public class SMSVerifyCodeHistory extends AbstractStringEntity {

	private static final long serialVersionUID = -4174355105821874513L;
    /**
     * 用户ID
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
	@Column(length =10,nullable=true,unique=false)
	private String vertifyCode;
    /**
     * 返回结果码 200：成功
     */
	@Column(length =10,nullable=true,unique=false)
	private String code;
    /**
     * 信息 异常错误信息
     */
	@Column(length =100,nullable=true,unique=false)
	private String errMessage;
	/**
	 * 错误码对应信息
	 */
	@Column(length =100,nullable=true,unique=false)
	private String codeMessage;

	@Column(columnDefinition = "int(2)",nullable=true,unique=false)
	private Integer historyStatus;//1.成功发送 2.失败
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

	public String getVertifyCode() {
		return vertifyCode;
	}

	public void setVertifyCode(String vertifyCode) {
		this.vertifyCode = vertifyCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getErrMessage() {
		return errMessage;
	}

	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}

	public String getCodeMessage() {
		return codeMessage;
	}

	public void setCodeMessage(String codeMessage) {
		this.codeMessage = codeMessage;
	}

	public Integer getHistoryStatus() {
		return historyStatus;
	}

	public void setHistoryStatus(Integer historyStatus) {
		this.historyStatus = historyStatus;
	}
}
