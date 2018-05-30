package com.bailuyiting.commons.core.entity.sso;

import com.bailuyiting.commons.core.domain.AbstractStringEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Table
@Entity(name = "sys_balance_log")
public class SysBalanceLog extends AbstractStringEntity {
    private static final long serialVersionUID = -1664334870553982967L;

    @Column(columnDefinition = "int(2)",nullable=false,unique=false)
    private Integer balanceType;//记录类型 1.微信充值进钱包  2.支付宝充值 3.其它第三方充值  10.钱包付款

    @Column(length = 32,nullable=true,unique=false)
    private String parkOrder;//如果是付款则记录订单号

    @Column(columnDefinition = "decimal(10,2)",nullable=true,unique=false)
    private BigDecimal oldPrice;//之前金额

    @Column(columnDefinition = "decimal(10,2)",nullable=true,unique=false)
    private BigDecimal  usePrice;//使用金额

    @Column(columnDefinition = "decimal(10,2)",nullable=true,unique=false)
    private BigDecimal currentPrice;//变更之后的金额

    @Column(columnDefinition = "int(2)",nullable=false,unique=false)
    private Integer balanceStatus;//状态 0.申请操作 1.操作成功 2.操作失败

    @Column(length = 200,nullable=true,unique=false)
    private String failureMessage;//操作失败说明

    /**
     *
     */
    public Integer getBalanceType() {
        return balanceType;
    }

    public void setBalanceType(Integer balanceType) {
        this.balanceType = balanceType;
    }

    public String getParkOrder() {
        return parkOrder;
    }

    public void setParkOrder(String parkOrder) {
        this.parkOrder = parkOrder;
    }

    public BigDecimal getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(BigDecimal oldPrice) {
        this.oldPrice = oldPrice;
    }

    public BigDecimal getUsePrice() {
        return usePrice;
    }

    public void setUsePrice(BigDecimal usePrice) {
        this.usePrice = usePrice;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Integer getBalanceStatus() {
        return balanceStatus;
    }

    public void setBalanceStatus(Integer balanceStatus) {
        this.balanceStatus = balanceStatus;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }
}
