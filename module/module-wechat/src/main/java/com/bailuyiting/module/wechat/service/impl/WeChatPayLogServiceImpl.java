package com.bailuyiting.module.wechat.service.impl;

import com.bailuyiting.module.wechat.jpa.WeChatPayLogRepository;
import com.bailuyiting.module.wechat.service.WeChatPayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeChatPayLogServiceImpl implements WeChatPayLogService {
    @Autowired
    private WeChatPayLogRepository weChatPayLogRepository;
    @Override
    public WeChatPayLogRepository getBaseJapRepository() {
        return this.weChatPayLogRepository;
    }
}
