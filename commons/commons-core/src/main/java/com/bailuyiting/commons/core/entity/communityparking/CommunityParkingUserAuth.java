package com.bailuyiting.commons.core.entity.communityparking;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 小区使用者认证表
 */
@Table
@Entity(name = "community_park_user_auth")
public class CommunityParkingUserAuth extends CommunityAuthBase {

    private static final long serialVersionUID = -7621173396266135528L;

    @Column(name = "user_type",columnDefinition = "int(2)",nullable=false,unique=false)
    private Integer userType;//使用者类型 1：业主 2：租客

    /**
     *
     */
    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }
}
