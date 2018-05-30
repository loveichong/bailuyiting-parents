package com.bailuyiting.commons.core.entity.sideparking;
import com.bailuyiting.commons.core.entity.common.ParkInfoBase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 路边停车基础信息表
 * 存放基础信息
 *
 */
@Table
@Entity(name = "side_parking_park_info")
public class SideParkingParkInfo extends ParkInfoBase {

    private static final long serialVersionUID = -6777872262823911525L;
    /**
     *
     */
    @Column(length = 32,nullable=false,unique=false)
    private String sectionId;//对应街道区域的ID

    @Column(columnDefinition = "decimal(10,6)",nullable=false,unique=false)
    private BigDecimal longitude;//车位精度

    @Column(columnDefinition = "decimal(10,6)",nullable=false,unique=false)
    private BigDecimal latitude;//车位纬度
    /**
     *
     */
    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
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
}
