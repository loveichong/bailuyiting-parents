package com.bailuyiting.commons.core.entity.sso;

import com.bailuyiting.commons.core.domain.AbstractStringEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table
@Entity(name = "sys_my_car")
public class SysMyCar extends AbstractStringEntity {

    private static final long serialVersionUID = -6932655917971861925L;

    @Column(length =32,nullable=true,unique=false)
    private String sysUserId;//账户ID

    @Column(length =20,nullable=false,unique=false)
    private String carNum;//车牌

    @Column(columnDefinition = "int(2)",nullable=false,unique=false)
    private Integer carStatus;//1.没有使用车位 2.正在使用车位 3.被冻结

    @Column(length = 100,nullable=true,unique=false)
    private String carInfo;//车辆描述
    /**
     *
     *
     */
    public String getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public Integer getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(Integer carStatus) {
        this.carStatus = carStatus;
    }

    public String getCarInfo() {
        return carInfo;
    }

    public void setCarInfo(String carInfo) {
        this.carInfo = carInfo;
    }
}
