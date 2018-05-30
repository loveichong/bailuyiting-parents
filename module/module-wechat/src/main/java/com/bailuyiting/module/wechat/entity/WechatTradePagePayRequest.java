package com.bailuyiting.module.wechat.entity;


import java.math.BigDecimal;

/**
 * 下单时要求前端传进来的订单参数
 * @author SQY
 */
public class WechatTradePagePayRequest {
    /**
     * 请求体
     */
    private String body;
    /**
     * 订单号 唯一
     */
    private String tradeNo;
    /**
     * 支付方式
     */
    private String tradeType;
    /**
     * 是否需要微信端再付款
     */
    private boolean needWechatPay;
    /**
     * 支付状态 成功与否
     */
    private boolean payStatus;
    /**
     * 回调地址
     */
    private String notifyUrl;
    /**
     *
     */
    private BigDecimal totalFee;
    /**
     *
     */
    private BigDecimal discountFee;
    /**
     *
     */
    private BigDecimal payDiscountFee;
    /**
     *
     */
    private BigDecimal payBalanceFee;
    /**
     *
     */
    private BigDecimal needWechatFee;
    /**
     *
     */
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public boolean isNeedWechatPay() {
        return needWechatPay;
    }

    public void setNeedWechatPay(boolean needWechatPay) {
        this.needWechatPay = needWechatPay;
    }

    public boolean isPayStatus() {
        return payStatus;
    }

    public void setPayStatus(boolean payStatus) {
        this.payStatus = payStatus;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public BigDecimal getDiscountFee() {
        return discountFee;
    }

    public void setDiscountFee(BigDecimal discountFee) {
        this.discountFee = discountFee;
    }

    public BigDecimal getPayDiscountFee() {
        return payDiscountFee;
    }

    public void setPayDiscountFee(BigDecimal payDiscountFee) {
        this.payDiscountFee = payDiscountFee;
    }

    public BigDecimal getPayBalanceFee() {
        return payBalanceFee;
    }

    public void setPayBalanceFee(BigDecimal payBalanceFee) {
        this.payBalanceFee = payBalanceFee;
    }

    public BigDecimal getNeedWechatFee() {
        return needWechatFee;
    }

    public void setNeedWechatFee(BigDecimal needWechatFee) {
        this.needWechatFee = needWechatFee;
    }

    public String getTradeType() {
        return tradeType;
    }
    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }
}
