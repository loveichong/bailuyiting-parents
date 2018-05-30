package com.bailuyiting.commons.core.entity.communityparking;


import com.bailuyiting.commons.core.domain.AbstractStringEntity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 小区验证基础表
 */
@MappedSuperclass
public class CommunityAuthBase extends AbstractStringEntity {

    private static final long serialVersionUID = -7621173396266135528L;

    @Column(length = 32,nullable=false,unique=false)
    private String account;//账户名

    @Column(length = 32,nullable=false,unique=false)
    private String communityId;//小区ID

    @Column(length = 10,nullable=false,unique=false)
    private String realName;//登记者真实姓名

    @Column(columnDefinition = "int(2)",nullable=true,unique=false)
    private Integer certificateType;//证件类型 1:二代身份证 2：户口本 3：房产证

    @Column(length = 40,nullable=true,unique=false)
    private String certificateNum;//证件号码

    @Column(length = 400,nullable=true,unique=false)
    private String certificateURL;//照片URL

    @Column(columnDefinition = "int(2)",nullable=false,unique=false)
    private Integer authStatus;//s身份验证状态 0：未开启  1：申请审核  2：审核失败  3：审核通过 4.功能冻结
    /**
     *
     */
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(Integer certificateType) {
        this.certificateType = certificateType;
    }

    public String getCertificateNum() {
        return certificateNum;
    }

    public void setCertificateNum(String certificateNum) {
        this.certificateNum = certificateNum;
    }

    public String getCertificateURL() {
        return certificateURL;
    }

    public void setCertificateURL(String certificateURL) {
        this.certificateURL = certificateURL;
    }

    public Integer getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(Integer authStatus) {
        this.authStatus = authStatus;
    }
}
