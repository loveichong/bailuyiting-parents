package com.bailuyiting.commons.core.entity.sso;


import com.bailuyiting.commons.core.domain.AbstractStringEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;


/**
 * <p>
 * 系统管理_用户信息
 * 更改过，加了实体对应，用于JPA
 * </p>
 *
 * @author ${author}
 * @since 2018-02-07
 */
@Table
@Entity(name = "sys_user")
public class SysUser extends AbstractStringEntity {

    private static final long serialVersionUID = -656308704934964129L;
    /**
     * 头像
     */
    @Column(length =255,nullable=true,unique=false)
	private String avatar;//微信头像
    /**
     * 账号
     */
	@Column(length =45,nullable=false,unique=true)
	private String account;//账户
    /**
     * 密码，格式：密文_盐值
     */
	@Column(length =60,nullable=false,unique=false)
	private String passwd;//密码
    /**
     * 名字
     */
	@Column(length = 60,nullable=true,unique=false)
	private String realName;//微信名字
    /**
     * 性别（1：男 2：女）
     */
	@Column(columnDefinition = "int(2)",nullable=true,unique=false)
	private Integer sex;
    /**
     * 电子邮件
     */
	@Column(length = 200,nullable=true,unique=false)
	private String email;
    /**
     * 生日
     */
	@Column(length = 16,nullable=true,unique=false)
	private String birthday;
    /**
     * 电话
     */
	@Column(length = 16,nullable=true,unique=false)
	private String phone;
	/**
	 *
	 */
	@Column(length = 255,nullable=true,unique=false)
	private String identityCard;
    /**
     * 账号余额
     */
	@Column(columnDefinition = "decimal(10,2)",nullable=true,unique=false)
	private BigDecimal balance;
    /**
     * 状态(1：启用  2：冻结  3：删除 4:正在使用车位 5.没有使用车位 11.管理员账户
     */
	@Column(columnDefinition = "int(2)",nullable=false,unique=false)
	private Integer state;
    /**
     * openId
     */
	@Column(length = 255,nullable=true,unique=true)
	private String openId;
    /**
     * 紧急联系人
     */
	@Column(length = 30,nullable=true,unique=false)
	private String emergencyContact;
    /**
     * 紧急联系人电话
     */
	@Column(length = 30,nullable=true,unique=false)
	private String emergencyPhone;
/**
 *
 */
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIdentityCard() {
		return identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getEmergencyContact() {
		return emergencyContact;
	}

	public void setEmergencyContact(String emergencyContact) {
		this.emergencyContact = emergencyContact;
	}

	public String getEmergencyPhone() {
		return emergencyPhone;
	}

	public void setEmergencyPhone(String emergencyPhone) {
		this.emergencyPhone = emergencyPhone;
	}
}
