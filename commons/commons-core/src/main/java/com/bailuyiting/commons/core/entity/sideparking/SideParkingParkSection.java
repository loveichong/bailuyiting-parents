package com.bailuyiting.commons.core.entity.sideparking;
import com.bailuyiting.commons.core.domain.AbstractStringEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
/**
 * 路边停车街道信息
 * 包含省，市，区
 * 具体到区域
 */
@Table
@Entity(name = "side_parking_park_section")
public class SideParkingParkSection extends AbstractStringEntity {

    private static final long serialVersionUID = 7937538527610636128L;

    @Column(length = 20,nullable=false,unique=false)
    private String street;//街道名字（停车位名字）

    @Column(length = 10,nullable=false,unique=false)
    private String province;//省份

    @Column(length = 10,nullable=false,unique=false)
    private String city;//市

    @Column(length = 10,nullable=false,unique=false)
    private String area;//区，县城

    @Column(length = 10,nullable=false,unique=false)
    private String section;//街道下面的段 A段 B段 C 段,这个字段是这张表的主体

    @Column(columnDefinition = "decimal(10,6)",nullable=false,unique=false)
    private BigDecimal longitude;//此段落的经度

    @Column(columnDefinition = "decimal(10,6)",nullable=false,unique=false)
    private BigDecimal latitude;//此段落的维度

    @Column(columnDefinition = "int(10)",nullable=true,unique=false)
    private Integer num;//这个街道这个段落的车位数量

    @Column(columnDefinition = "decimal(10,2)",nullable=true,unique=false)
    private BigDecimal price;//停车位价格

    @Transient
    private String address;//停车位具体信息，不作为映射字段
    /**
     *
     */
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

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    /**
     * 拼接详细地址
     * @return
     */
    public String getAddress() {
        StringBuilder addrees = new StringBuilder();
        addrees.append(this.getProvince())
                .append(this.getCity())
                .append(this.getArea())
                .append(this.getStreet())
                .append(this.getSection())
                .append("区");
        return addrees.toString();
    }

    @Override
    public String toString() {
        return "SideParkingSection{" +
                "street='" + street + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", section='" + section + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", num=" + num +
                ", price=" + price +
                ", address='" + address + '\'' +
                '}';
    }
}
