package com.bailuyiting.sso.service.controller.sms;

import com.bailuyiting.commons.until.ResultUtils;
import com.bailuyiting.module.sms.config.sms.SmsVerifyCodePropertyBean;
import com.bailuyiting.module.sms.controller.SmsController;
import com.bailuyiting.module.sms.entity.SmsRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@Api(value = "短信服务-API",description = "账户短信服务-API")
@RestController
@RequestMapping(value = "api/sms/")
public class BlytSmsController {
    @Autowired
    private SmsVerifyCodePropertyBean smsVerifyCodePropertyBean;
    @Autowired
   private SmsController smsController;
    /**
     * 白鹭易停发送登入短信验证码
     * @param tel
     * @param accessCode
     * @return
     */
    @ApiOperation(value = "白鹭易停发送登入短信验证码",httpMethod ="POST",notes = "停车位信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "tel",value = "手机号",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "accessCode",value = "短信发送权限码（www.bailuyiting.com）",paramType = "query",dataType = "String",required = true),
    })
    @PostMapping(value = "verifyCode/login")
    public Map<String,Object> loginByTel( @RequestParam String tel,@RequestParam String accessCode){
        if(!"www.bailuyiting.com".equals(accessCode)){
            return ResultUtils.errorByUserDefine("600","无发送短信验证权限");
        }
        SmsRequest smsRequest = new SmsRequest();
        smsRequest.setTel(tel);
        smsRequest.setTemplateCode(this.smsVerifyCodePropertyBean.getTelLoginTemplateCode());//手机验证模板
        return smsController.SmsVerifyCode(smsRequest);
     }
    /**
     * 白鹭易停发送小区验证短信验证码
     * @param tel
     * @param accessCode
     * @return
     */
    @ApiOperation(value = "白鹭易停发送小区验证短信验证码",httpMethod ="POST",notes = "停车位信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "tel",value = "手机号",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "accessCode",value = "短信发送权限码（www.bailuyiting.com）",paramType = "query",dataType = "String",required = true),
    })
    @PostMapping(value = "verifyCode/community")
    public Map<String,Object> communityVerify( @RequestParam String tel,@RequestParam String accessCode){
        if(!"www.bailuyiting.com".equals(accessCode)){
            return ResultUtils.errorByUserDefine("600","无发送短信验证权限");
        }
        SmsRequest smsRequest = new SmsRequest();
        smsRequest.setTel(tel);
        smsRequest.setTemplateCode(this.smsVerifyCodePropertyBean.getCommunityVerifyTemplateCode());//小区验证验证模板
        return smsController.SmsVerifyCode(smsRequest);
    }
}
