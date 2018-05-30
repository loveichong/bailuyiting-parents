package com.bailuyiting.commons.core.entity.communityparking;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 小区停车位拥有者验证表
 */
@Table
@Entity(name = "community_park_owner_auth")
public class CommunityParkingOwnerAuth extends CommunityAuthBase {

    private static final long serialVersionUID = 63166020156487024L;


    @Column(length = 20,nullable=false,unique=true)
    private String parkNum;//车位编号 车位所有者自自定义

    @Column(length = 20,nullable=false,unique=false)
    private String carNum;//登记车牌号

    @Column(length = 20,nullable=true,unique=false)
    private String tel;//手机号码
    /**
     *
     */
    public String getParkNum() {
        return parkNum;
    }

    public void setParkNum(String parkNum) {
        this.parkNum = parkNum;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
