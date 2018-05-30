package com.bailuyiting.commons.core.entity.communityparking;

import com.bailuyiting.commons.core.domain.AbstractStringEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 使用者申请的小区数据记录表
 */
@Table
@Entity(name = "community_data_base_from_user")
public class CommunityDataBaseFromUser extends AbstractStringEntity {

    private static final long serialVersionUID = -8989176273238201551L;

    @Column(length = 20,nullable=false,unique=true)
    private String communityName;//小区名

    @Column(length = 10,nullable=true,unique=false)
    private String province;//省份

    @Column(length = 10,nullable=true,unique=false)
    private String city;//市

    @Column(length = 10,nullable=true,unique=false)
    private String area;//区，县城

    @Column(length = 10,nullable=true,unique=false)
    private String street;//街道，县城

    @Transient
    private String address;//停车位具体信息，不作为映射字段
    /**
     *
     */
    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
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


    public void setAddress(String address) {
        this.address = address;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }


    public String getAddress() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getProvince())
                .append(this.getCity())
                .append(this.getArea())
                .append(this.getStreet())
                .append(this.getCommunityName());
        return builder.toString();
    }
}
