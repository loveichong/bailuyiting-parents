package com.bailuyiting.module.wechat.entity;

import com.bailuyiting.commons.core.domain.AbstractStringEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 微信支付日志
 */
@Table
@Entity(name = "module_weChat_pay_log")
public class WeChatPayLog extends AbstractStringEntity {

    private static final long serialVersionUID = 7458626408634794313L;

    @Column(length = 32,nullable=false,unique=true)
    private String orderId;//支付订单ID 无法确定型类 外键

    @Column(length = 60,nullable=true,unique=false)
    private String transactionId;//微信支付订单号 成功支付才有

    @Column(columnDefinition = "int(2)",nullable=false,unique=false)
    private Integer PayStatus;//支付状态 1.正在向微信申请支付 2.微信支付成功 3 微信支付失败 4.申请微信支付失败 5.其它异常

    @Column(columnDefinition = "decimal(10,2)",nullable=false,unique=false)
    private BigDecimal orderPrice;//申请支付订单 金额 单位元

    @Column(columnDefinition = "decimal(10,2)",nullable=true,unique=false)
    private BigDecimal payPrice;//支付成功金额 单位元

    @Column(length = 20,nullable=true,unique=false)
    private String  successCode;//支付成功代码

    @Column(length = 200,nullable=true,unique=false)
    private String  successMessage;//支付成功信息

    @Column(length = 20,nullable=true,unique=false)
    private String  failureCode;//支付失败代码

    @Column(length = 200,nullable=true,unique=false)
    private String failureMessage;//支付失败信息

    @Column(length = 32,nullable=true,unique=false)
    private String  notifyTime;//微信回调时间
    /**
     *
     */

    public Integer getPayStatus() {
        return PayStatus;
    }

    public void setPayStatus(Integer payStatus) {
        PayStatus = payStatus;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public BigDecimal getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(BigDecimal payPrice) {
        this.payPrice = payPrice;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }

    public String getNotifyTime() {
        return notifyTime;
    }

    public void setNotifyTime(String notifyTime) {
        this.notifyTime = notifyTime;
    }

    public String getSuccessCode() {
        return successCode;
    }

    public void setSuccessCode(String successCode) {
        this.successCode = successCode;
    }

    public String getFailureCode() {
        return failureCode;
    }

    public void setFailureCode(String failureCode) {
        this.failureCode = failureCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
