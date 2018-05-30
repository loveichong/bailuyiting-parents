package com.bailuyiting.commons.core.entity.order;

import com.bailuyiting.commons.core.domain.AbstractStringEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
/**
 * 停车位订单基础表格
 */
@Table
@Entity(name = "parking_order_base")
public class ParkingOrderBase extends AbstractStringEntity {

    private static final long serialVersionUID = -8046391499630834363L;

    @Column(columnDefinition = "int(2)",nullable=false,unique=false)
    private Integer orderStatus;//1=已预约，2=进场，3=已进场已支付但是未确认，4=出场订单完成

    @Column(columnDefinition = "int(2)",nullable=false,unique=false)
    private Integer orderType;//状态 1.露天停车 2.路边停车 3.小区停车

    @Column(length = 12,nullable=false,unique=false)
    private String carNumber;//使用车辆车牌号

    @Column(name = "order_time",length = 16,nullable=true,unique=false)
    private String orderTime;//下单时间

    @Column(length = 16,nullable=true,unique=false)
    private String finishTime;//出场时间（支付时间）

    @Column(length = 16,nullable=true,unique=false)
    private String keepTime;//停车时间（停车时间总长）

    @Column(columnDefinition = "decimal(10,2)",nullable=true,unique=false)
    private BigDecimal price;//消费金额

    @Column(columnDefinition = "decimal(10,2)",nullable=true,unique=false)
    private BigDecimal discountPrice;//折扣金额

    @Column(columnDefinition = "decimal(10,2)",nullable=true,unique=false)
    private BigDecimal totalPrice;//总共金额

    @Column(length = 200,nullable=true,unique=false)
    private String packInfo;//停车位信息

    @Column(length = 100,nullable=true,unique=false)
    private String address;//停车位地址

    @Column(length = 32,nullable=true,unique=false)
    private String parkId;//停车位Id

    @Transient
    private String currentTime;//系统当前时间不映射数据库
    /**
     *
     */
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getKeepTime() {
        return keepTime;
    }

    public void setKeepTime(String keepTime) {
        this.keepTime = keepTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPackInfo() {
        return packInfo;
    }

    public void setPackInfo(String packInfo) {
        this.packInfo = packInfo;
    }

    public String getParkId() {
        return parkId;
    }

    public void setParkId(String parkId) {
        this.parkId = parkId;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }
}
