package com.bailuyiting.module.sms.service.impl;

import com.bailuyiting.commons.core.jpa.sms.SMSVerifyCodeRepository;
import com.bailuyiting.module.sms.service.SMSVerifyCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SMSVerifyCodeServiceImpl implements SMSVerifyCodeService {
    @Autowired
    private SMSVerifyCodeRepository smsVertifyCodeRepository;
    @Override
    public SMSVerifyCodeRepository getBaseJapRepository() {
        return this.smsVertifyCodeRepository;
    }
}
