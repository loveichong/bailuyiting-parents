package com.bailuyiting.sso.service.service.impl;

import com.bailuyiting.commons.core.jpa.sso.SysLoginLogRepository;
import com.bailuyiting.sso.service.service.SysLoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysLoginLogServiceImpl implements SysLoginLogService {
    @Autowired
    private SysLoginLogRepository sysLoginLogRepository;
    @Override
    public SysLoginLogRepository getBaseJapRepository() {
        return this.sysLoginLogRepository;
    }
}
