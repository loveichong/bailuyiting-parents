package com.bailuyiting.module.sms.config.sms;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SmsVerifyCodePropertyBean {

    //验证码失效时间（单位分钟）
    @Value("${module.sms.verifyCode.expireMinute:15}")
    private int expireMinute;

    //云通信accessKeyId
    @Value("${module.sms.verifyCode.accessKeyId:LTAIpsiQiWIpN831}")
    private String accessKeyId;

    //云通信accessKeySecret
    @Value("${module.sms.verifyCode.accessKeySecret:eebo05VTtkqnXSdZPC2zjWl5GNNWOR}")
    private String accessKeySecret;

    //云通信短信签名
    @Value("${module.sms.verifyCode.signName:LSZBLYT}")
    private String signName;

    //手机登入短信模板码（阿里云设置）
    @Value("${module.sms.verifyCode.telLoginTemplateCode:SMS_135770198}")
    private String telLoginTemplateCode;

    //小区功能验证短信模板码（阿里云设置）
    @Value("${module.sms.verifyCode.communityVerifyTemplateCode:SMS_135770198}")
    private String communityVerifyTemplateCode;

    //发送渠道限制中间用逗号隔开  如:client,server
    @Value("${module.sms.verifyCode.comeFrom:cs}")
    private String comeFrom;

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getComeFrom() {
        return comeFrom;
    }

    public void setComeFrom(String comeFrom) {
        this.comeFrom = comeFrom;
    }

    public int getExpireMinute() {
        return expireMinute;
    }

    public void setExpireMinute(int expireMinute) {
        this.expireMinute = expireMinute;
    }

    public String getTelLoginTemplateCode() {
        return telLoginTemplateCode;
    }
    public void setTelLoginTemplateCode(String telLoginTemplateCode) {
        this.telLoginTemplateCode = telLoginTemplateCode;
    }

    public String getCommunityVerifyTemplateCode() {
        return communityVerifyTemplateCode;
    }

    public void setCommunityVerifyTemplateCode(String communityVerifyTemplateCode) {
        this.communityVerifyTemplateCode = communityVerifyTemplateCode;
    }
}
