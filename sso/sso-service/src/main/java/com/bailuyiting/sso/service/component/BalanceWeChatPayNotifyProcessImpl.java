package com.bailuyiting.sso.service.component;

import com.bailuyiting.commons.core.entity.sso.SysBalanceLog;
import com.bailuyiting.module.wechat.inter.WeChatPayNotifyProcess;
import com.bailuyiting.module.wechat.until.WeChatParameterUntils;
import com.bailuyiting.sso.service.service.SysBalanceLogService;
import com.bailuyiting.sso.service.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 钱包充值微信支付回调处理器
 */
@Component
public class BalanceWeChatPayNotifyProcessImpl implements WeChatPayNotifyProcess {
    @Autowired
    private SysBalanceLogService sysBalanceLogService;
    @Autowired
    private SysUserService sysUserService;
    @Override
    public void success(Map<String, String> resultMap) {
        //设置状态操作成功
        sysBalanceLogService.confirmBalanceRechargeAndSaveAccount(WeChatParameterUntils.getOrderIdWhenPayNotifySuccess(resultMap));
    }
    @Override
    public void failure(Map<String, String> resultMap) {
        SysBalanceLog one = this.sysBalanceLogService.getBaseJapRepository().findOne(WeChatParameterUntils.getOrderIdWhenPayNotifySuccess(resultMap));
        one.setBalanceStatus(2);//操作失败
        one.setFailureMessage(WeChatParameterUntils.getResultMessageWhenPayNotify(resultMap));//失败代码
        this.sysBalanceLogService.getBaseJapRepository().save(one);
        //保持不变
    }
}
