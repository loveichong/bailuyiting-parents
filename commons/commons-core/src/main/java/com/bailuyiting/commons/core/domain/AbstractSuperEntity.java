package com.bailuyiting.commons.core.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * 所有实体类的基类
 * 
 * @author  
 * 
 */
@MappedSuperclass
public abstract class AbstractSuperEntity<T> implements Serializable {

  private static final long serialVersionUID = -8731260486753288680L;

  @Column(length = 20,nullable=true,unique=false)
  private String createTime;//创建时间

  @Column(length = 20,nullable=true,unique=false)
  private String modifyTime;//修改时间

  @Column(length = 32,nullable=true,unique=false)
  private String creater;//创建人

  @Column(length = 32,nullable=true,unique=false)
  private String modifyer;//修改修改人

  @Column(length =32,nullable=true,unique=false)
  private String sysAccount;//系统账户

  @Column(length =32,nullable=true,unique=false)
  private String sysId;//系统Id

  @Transient
  private Boolean feignSuccess;//判断是否feign成功 默认都是成功

  public abstract T getId() ;
  public boolean isNew(){
	  return null == getId();
  }

  public String getCreater() {
    return creater;
  }

  public void setCreater(String creater) {
    this.creater = creater;
  }

  public String getModifyer() {
    return modifyer;
  }

  public void setModifyer(String modifyer) {
    this.modifyer = modifyer;
  }

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }

  public String getModifyTime() {
    return modifyTime;
  }

  public void setModifyTime(String modifyTime) {
    this.modifyTime = modifyTime;
  }

  public String getSysAccount() {
    return sysAccount;
  }

  public void setSysAccount(String sysAccount) {
    this.sysAccount = sysAccount;
  }

  public String getSysId() {
    return sysId;
  }

  public void setSysId(String sysId) {
    this.sysId = sysId;
  }

  public Boolean getFeignSuccess() {
    return feignSuccess;
  }

  public void setFeignSuccess(Boolean feignSuccess) {
    this.feignSuccess = feignSuccess;
  }
}
