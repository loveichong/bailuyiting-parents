package com.bailuyiting.commons.core.entity.communityparking;

import com.bailuyiting.commons.core.entity.common.ParkInfoBase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 小区停车位出售明细表
 */
@Table
@Entity(name = "community_parking_park_info")
public class CommunityParkingParkInfo extends ParkInfoBase {

    private static final long serialVersionUID = 8569067600887240294L;

    @Column(length = 32,nullable=false,unique=false)
    private String parkOwnerID;//停车位所有者ID

    @Column(length = 32,nullable=false,unique=false)
    private String communityId;//小区ID

    @Column(name = "price",columnDefinition = "decimal(10,2)",nullable=true,unique=false)
    private BigDecimal price;//出售价格 元每小时

    @Column(name = "start_time",length = 16,nullable=true,unique=false)
    private String startTime;//起始时间

    @Column(name = "finish_time",length = 16,nullable=true,unique=false)
    private String finishTime;//结束时间

    /**
     *
     */
    public String getParkOwnerID() {
        return parkOwnerID;
    }

    public void setParkOwnerID(String parkOwnerID) {
        this.parkOwnerID = parkOwnerID;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }
}
