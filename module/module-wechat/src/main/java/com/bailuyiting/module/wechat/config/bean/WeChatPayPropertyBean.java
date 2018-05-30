package com.bailuyiting.module.wechat.config.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 微信参数组件  需要yml配置
 * @author SQY
 */
@Component
public class WeChatPayPropertyBean {

    @Value("${module.weChat.pay.mchID}")
    private String mchID; //商户号

    @Value("${module.weChat.pay.key}")
    private String key; //商户支付密钥

    @Value("${module.weChat.pay.appID}")
    private String appID;//商户APPid

    @Value("${module.weChat.pay.appSecret}")
    private String appSecret;//商户APPSecret

    @Value("${module.weChat.pay.spBillCreateIp}")
    private String spBillCreateIp;//商户终端IP

    @Value("${module.weChat.pay.notifyIp}")
    private String notifyIp;//
    /**
     *
     */
    public String getMchID() {
        return mchID;
    }

    public void setMchID(String mchID) {
        this.mchID = mchID;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getSpBillCreateIp() {
        return spBillCreateIp;
    }
    public void setSpBillCreateIp(String spBillCreateIp) {
        this.spBillCreateIp = spBillCreateIp;
    }

    public String getNotifyIp() {
        return notifyIp;
    }

    public void setNotifyIp(String notifyIp) {
        this.notifyIp = notifyIp;
    }
}
