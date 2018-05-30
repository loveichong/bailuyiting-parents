package com.bailuyiting.sso.service.service.impl;

import com.bailuyiting.commons.core.entity.order.ParkingOrderBase;
import com.bailuyiting.commons.core.entity.sso.SysBalanceLog;
import com.bailuyiting.commons.core.entity.sso.SysUser;
import com.bailuyiting.commons.core.jpa.sso.SysBalanceLogRepository;
import com.bailuyiting.commons.core.jpa.sso.SysUserRepository;
import com.bailuyiting.commons.until.DateUtils;
import com.bailuyiting.module.wechat.until.WeChatParameterUntils;
import com.bailuyiting.sso.service.service.SysBalanceLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
public class SysBalanceLogServiceImpl implements SysBalanceLogService {
    @Autowired
    private SysBalanceLogRepository sysBalanceLogRepository;
    @Autowired
    private SysUserRepository sysUserRepository;
    @Override
    public SysBalanceLogRepository getBaseJapRepository() {
        return this.sysBalanceLogRepository;
    }

    @Override
    @Transactional
    public void confirmBalanceRechargeAndSaveAccount(String id) {
        SysBalanceLog log = this.sysBalanceLogRepository.findOne(id);
        if(log==null){
            throw new RuntimeException("订单不存在");
        }
        if(log.getBalanceStatus()==2){
            throw new RuntimeException("此钱包操作已经成功，请勿重复操作");
        }
        //查找账户
        SysUser account = this.sysUserRepository.findByAccount(log.getSysAccount());
        //保存当前账户余额
        log.setOldPrice(account.getBalance());
        //计算增加之后的金额
        BigDecimal add=null;
        if(account.getBalance()==null){
            add=log.getUsePrice();
            log.setOldPrice(new BigDecimal(0));
        }
        else{
            add=account.getBalance().add(log.getUsePrice());
            log.setOldPrice(account.getBalance());
        }
        //账户余额增加
        log.setCurrentPrice(add);
        log.setBalanceStatus(1);//成功
        account.setBalance(add);
        //保存钱包记录
        this.sysBalanceLogRepository.save(log);
        //保存账户
        this.sysUserRepository.save(account);
    }

    @Transactional
    @Override
    public void payForOrder(ParkingOrderBase order,SysBalanceLog log) {
        //
        order.getTotalPrice();
        //生成使用记录

    }
}
