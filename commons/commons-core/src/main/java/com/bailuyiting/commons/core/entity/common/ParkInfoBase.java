package com.bailuyiting.commons.core.entity.common;

import com.bailuyiting.commons.core.domain.AbstractStringEntity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;

/**
 * 停车位基础信息表
 *
 */
@MappedSuperclass
public class ParkInfoBase extends AbstractStringEntity {

    private static final long serialVersionUID = -1472171958815561097L;
    /**
     *
     */
    @Column(length = 20,nullable=true,unique=false)
    private String parkNum;//此车位的编号，是唯一

    @Column(columnDefinition = "int(1)",nullable=true,unique=false)
    private Integer parkStatus;//此车位的状态 0.未发布 1.正在被使用 2.没有被使用 3.异常

    @Column(length = 255,nullable=true,unique=false)
    private String parkInfo;//停车位信息说明

    @Column(length = 100,nullable=true,unique=false)
    private String parkAddress;//停车位地址

    @Column(columnDefinition = "decimal(10,2)",nullable=true,unique=false)
    private BigDecimal price;//停车位价格
    /**
     *
     */
    public String getParkNum() {
        return parkNum;
    }

    public void setParkNum(String parkNum) {
        this.parkNum = parkNum;
    }

    public Integer getParkStatus() {
        return parkStatus;
    }

    public void setParkStatus(Integer parkStatus) {
        this.parkStatus = parkStatus;
    }

    public String getParkInfo() {
        return parkInfo;
    }

    public void setParkInfo(String parkInfo) {
        this.parkInfo = parkInfo;
    }

    public String getParkAddress() {
        return parkAddress;
    }

    public void setParkAddress(String parkAddress) {
        this.parkAddress = parkAddress;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
