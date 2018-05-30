package com.bailuyiting.module.sms.service.impl;

import com.bailuyiting.commons.core.jpa.sms.SMSVerifyCodeHistoryRepository;

import com.bailuyiting.module.sms.service.SMSVerifyCodeHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SMSVerifyCodeHistoryServiceImpl implements SMSVerifyCodeHistoryService {
    @Autowired
    private SMSVerifyCodeHistoryRepository smsVertifyCodeHistoryRepository;
    @Override
    public SMSVerifyCodeHistoryRepository getBaseJapRepository() {
        return this.smsVertifyCodeHistoryRepository;
    }
}
