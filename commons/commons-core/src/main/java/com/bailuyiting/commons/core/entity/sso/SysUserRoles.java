package com.bailuyiting.commons.core.entity.sso;



import com.bailuyiting.commons.core.domain.AbstractStringEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 系统管理_用户角色信息
 * </p>
 *
 * @author ${author}
 * @since 2017-12-05
 */
@Table
@Entity(name = "sys_user_roles")
public class SysUserRoles extends AbstractStringEntity {

	private static final long serialVersionUID = -1858773097553563967L;
    /**
     * 用户ID
     */
	@Column(length =32,nullable=true,unique=false)
	private String userId;
    /**
     * 角色ID
     */
	@Column(columnDefinition = "int(4)",nullable=true,unique=false)
	private Integer userRole;//账户类型 1.普通账户  2.后台管理员  3.技术开发人员  4.超级账户
		                     //11.露天停车管理人员 12.路边停车管理人员 13.小区停车管理人员
	/**
	 *停车场ID
	 */
	@Column(length = 32,nullable=true,unique=false)
	private String parkId;//身份绑定的小区Id;有的没有
	/* *
	 *
	 */
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getUserRole() {
		return userRole;
	}

	public void setUserRole(Integer userRole) {
		this.userRole = userRole;
	}

	public String getParkId() {
		return parkId;
	}

	public void setParkId(String parkId) {
		this.parkId = parkId;
	}
}
