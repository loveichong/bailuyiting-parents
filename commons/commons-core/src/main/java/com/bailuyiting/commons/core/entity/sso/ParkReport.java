package com.bailuyiting.commons.core.entity.sso;

import com.bailuyiting.commons.core.domain.AbstractStringEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table
@Entity(name = "sys_park_report")
public class ParkReport extends AbstractStringEntity {

    private static final long serialVersionUID = 6193575237566293676L;

    @Column(columnDefinition = "int(2)",nullable=true,unique=false)
    private Integer reportTpye;//举报类型 1.路边停车

    @Column(length = 400,nullable=true,unique=false)
    private String reportURL;//照片URL

    @Column(length = 200,nullable=true,unique=false)
    private String reportAddress;//违章停车地点

    @Column(length = 20,nullable=true,unique=false)
    private String carNum;//违章停车车牌
    /**
     *
     */
    public Integer getReportTpye() {
        return reportTpye;
    }

    public void setReportTpye(Integer reportTpye) {
        this.reportTpye = reportTpye;
    }

    public String getReportURL() {
        return reportURL;
    }

    public void setReportURL(String reportURL) {
        this.reportURL = reportURL;
    }

    public String getReportAddress() {
        return reportAddress;
    }

    public void setReportAddress(String reportAddress) {
        this.reportAddress = reportAddress;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }
}
