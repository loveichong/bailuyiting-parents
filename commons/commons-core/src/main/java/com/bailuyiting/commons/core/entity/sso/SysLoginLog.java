package com.bailuyiting.commons.core.entity.sso;

import com.bailuyiting.commons.core.domain.AbstractStringEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <p>
 * 系统管理_登录日志
 * </p>
 *
 * @author ${author}
 * @since 2017-12-05
 */
@Table
@Entity(name = "sys_login_log")
public class SysLoginLog extends AbstractStringEntity {

    private static final long serialVersionUID = -1568839728207069445L;
    /**
     * 用户账号
     */
    @Column(length =50,nullable=false,unique=false)
	private String account;
	/**
	 * 替身
	 */
    @Column(length =50,nullable=true,unique=false)
	private String bodyDouble;
    /**
     * 是否执行成功(0、否 1、是)
     */
	@Column(columnDefinition = "int(2)",nullable=false,unique=false)
	private Integer loginStatus;
    /**
     * 处理消息
     */
	@Column(length =300,nullable=true,unique=false)
	private String message;
    /**
     * 登录ip
     */
    @Column(length =100,nullable=true,unique=false)
	private String ip;
/**
 *
 */
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBodyDouble() {
        return bodyDouble;
    }

    public void setBodyDouble(String bodyDouble) {
        this.bodyDouble = bodyDouble;
    }

    public Integer getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(Integer loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
