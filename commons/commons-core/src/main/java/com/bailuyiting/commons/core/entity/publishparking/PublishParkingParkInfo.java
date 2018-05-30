package com.bailuyiting.commons.core.entity.publishparking;

import com.bailuyiting.commons.core.domain.AbstractStringEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;

/**
 * 公共停车数据记录表
 */
@Table
@Entity(name = "publish_parking_park_info")
public class PublishParkingParkInfo extends AbstractStringEntity {

    private static final long serialVersionUID = -8989176273238201551L;

    @Column(length = 20,nullable=false,unique=true)
    private String publishName;//公共停车区域名字

    @Column(length = 10,nullable=true,unique=false)
    private String province;//省份

    @Column(length = 10,nullable=true,unique=false)
    private String city;//市

    @Column(length = 10,nullable=true,unique=false)
    private String area;//区，县城

    @Column(columnDefinition = "decimal(10,6)",nullable=false,unique=false)
    private BigDecimal longitude;//区域精度

    @Column(columnDefinition = "decimal(10,6)",nullable=false,unique=false)
    private BigDecimal latitude;//区域纬度

    @Column(length = 255,nullable=true,unique=false)
    private String parkInfo;//停车位信息说明

    @Column(columnDefinition = "int(6)",nullable=true,unique=false)
    private Integer parkNum;//停车位数量

    @Column(columnDefinition = "int(6)",nullable=true,unique=false)
    private Integer parkResidualNum;//剩余停车位数量

    @Column(columnDefinition = "int(2)",nullable=true,unique=false)
    private Integer parkStatus;//1.未合作，2.参与合作 3.异常

    @Column(columnDefinition = "decimal(10,2)",nullable=true,unique=false)
    private BigDecimal freeTime;//免费时长

    @Column(columnDefinition = "decimal(10,2)",nullable=true,unique=false)
    private BigDecimal discountTime;//优惠时长/h

    @Column(columnDefinition = "decimal(10,2)",nullable=true,unique=false)
    private BigDecimal discountPrice;//优惠价格

    @Column(columnDefinition = "decimal(10,2)",nullable=true,unique=false)
    private BigDecimal unitPrice;//单价/h

    @Column(columnDefinition = "decimal(10,2)",nullable=true,unique=false)
    private BigDecimal maximumPrice;//24h封顶金额

    @Transient
    private String address;//停车位具体信息，不作为映射字段

    public String getPublishName() {
        return publishName;
    }

    public void setPublishName(String publishName) {
        this.publishName = publishName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getParkInfo() {
        return parkInfo;
    }

    public void setParkInfo(String parkInfo) {
        this.parkInfo = parkInfo;
    }

    public Integer getParkNum() {
        return parkNum;
    }

    public void setParkNum(Integer parkNum) {
        this.parkNum = parkNum;
    }

    public Integer getParkStatus() {
        return parkStatus;
    }

    public void setParkStatus(Integer parkStatus) {
        this.parkStatus = parkStatus;
    }

    public BigDecimal getFreeTime() {
        return freeTime;
    }

    public void setFreeTime(BigDecimal freeTime) {
        this.freeTime = freeTime;
    }

    public BigDecimal getDiscountTime() {
        return discountTime;
    }

    public void setDiscountTime(BigDecimal discountTime) {
        this.discountTime = discountTime;
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getMaximumPrice() {
        return maximumPrice;
    }

    public void setMaximumPrice(BigDecimal maximumPrice) {
        this.maximumPrice = maximumPrice;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public Integer getParkResidualNum() {
        return parkResidualNum;
    }

    public void setParkResidualNum(Integer parkResidualNum) {
        this.parkResidualNum = parkResidualNum;
    }

    /**
     *
     */

    public String getAddress() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getPublishName());
        return builder.toString();
    }
}
