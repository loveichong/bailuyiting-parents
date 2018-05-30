package com.bailuyiting.module.sms.controller;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.bailuyiting.commons.core.entity.module.sms.SMSVerifyCode;
import com.bailuyiting.commons.core.entity.module.sms.SMSVerifyCodeHistory;
import com.bailuyiting.commons.until.DateUtils;
import com.bailuyiting.commons.until.ResultUtils;
import com.bailuyiting.module.sms.config.sms.SmsVerifyCodePropertyBean;
import com.bailuyiting.module.sms.entity.SmsRequest;
import com.bailuyiting.module.sms.service.SMSVerifyCodeHistoryService;
import com.bailuyiting.module.sms.service.SMSVerifyCodeService;
import com.bailuyiting.module.sms.untils.SmsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController
public class SmsController {

    private Logger logger = LoggerFactory.getLogger(SmsController.class);
    @Autowired
    private SmsVerifyCodePropertyBean smsVerifyCodePropertyBean;

    @Autowired
    private SMSVerifyCodeHistoryService smsVerifyCodeHistoryService;

    @Autowired
    private SMSVerifyCodeService smsVerifyCodeService;

    public Map<String,Object> SmsVerifyCode(SmsRequest smsRequest){

        //发送验证码
        int verifyCode=(int)((Math.random()*9+1)*100000);
        SendSmsResponse response=null;
        try {
            logger.info("开始为手机号："+smsRequest.getTel()+"发送短信");//无用
            response = SmsUtil.sendSms(smsVerifyCodePropertyBean.getAccessKeyId(), smsVerifyCodePropertyBean.getAccessKeySecret(), smsVerifyCodePropertyBean.getSignName(), smsRequest.getTemplateCode(), smsRequest.getTel(), "" + verifyCode);
        }
        catch (ClientException e) {
            //发送错误,记录SMS错误
            SMSVerifyCodeHistory history = new SMSVerifyCodeHistory();
            history.setCreateTime(DateUtils.formatNow());
            history.setCode("600");
            history.setCodeMessage("短信发送抛错");
            history.setErrMessage(e.getErrMsg());
            history.setPhone(smsRequest.getTel());
            history.setVertifyCode(""+verifyCode);
            history.setHistoryStatus(2);
            this.smsVerifyCodeHistoryService.getBaseJapRepository().save(history);
            return ResultUtils.errorByUserDefine("600",e.getErrMsg());
        }
        if(response.getCode().equals("OK")){
            //成功 记录验证码 记录history
            //查找手机是否已经发送过验证码
            SMSVerifyCode code = this.smsVerifyCodeService.getBaseJapRepository().findByPhone(smsRequest.getTel());
            Date expireDate = DateUtils.addMinutes(new Date(), this.smsVerifyCodePropertyBean.getExpireMinute());
            if(code==null){
                //新建验证码信息
                SMSVerifyCode newCode = new SMSVerifyCode();
                newCode.setCreateTime(DateUtils.formatNow());//创建时间
                newCode.setComeFrom("s");//客户端
                newCode.setExpireTime(DateUtils.formatDate(expireDate));//过期时间
                newCode.setPhone(smsRequest.getTel());//手机号
                newCode.setVerifyCode(Integer.toString(verifyCode));//验证码
                this.smsVerifyCodeService.getBaseJapRepository().save(newCode);
            }
            else {
                //已经存在的手机修改验证码
                code.setModifyTime(DateUtils.formatNow());//设置修改时间
                code.setVerifyCode(Integer.toString(verifyCode));//设置此次验证码
                code.setExpireTime(DateUtils.formatDate(expireDate));//设置过期时间
                this.smsVerifyCodeService.getBaseJapRepository().save(code);//update
            }
            //记录验证码发送成功信息
            SMSVerifyCodeHistory history = new SMSVerifyCodeHistory();
            history.setCreateTime(DateUtils.formatNow());
            history.setCode("200");
            history.setComeFrom("s");
            history.setCodeMessage("短信发送成功");
            history.setPhone(smsRequest.getTel());
            history.setVertifyCode(""+verifyCode);
            history.setHistoryStatus(1);
            this.smsVerifyCodeHistoryService.getBaseJapRepository().save(history);
            return ResultUtils.success(verifyCode);
        }else{
            //失败
            //Code=isv.BUSINESS_LIMIT_CONTROL
            SMSVerifyCodeHistory history = new SMSVerifyCodeHistory();
            history.setCreateTime(DateUtils.formatNow());
            history.setCode("600");
            history.setComeFrom("s");
            history.setCodeMessage("短信验证码发送失败");
            history.setErrMessage(response.getMessage());
            history.setPhone(smsRequest.getTel());
            history.setVertifyCode(""+verifyCode);
            history.setHistoryStatus(2);
            this.smsVerifyCodeHistoryService.getBaseJapRepository().save(history);
            return ResultUtils.errorByUserDefine("600","短信验证码发送失败，请重试");
        }
    }
}
