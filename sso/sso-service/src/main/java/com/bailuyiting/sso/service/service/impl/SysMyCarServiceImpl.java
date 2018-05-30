package com.bailuyiting.sso.service.service.impl;

import com.bailuyiting.commons.core.jpa.sso.SysMyCarRepository;
import com.bailuyiting.sso.service.service.SysMyCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysMyCarServiceImpl implements SysMyCarService {
    @Autowired
    private SysMyCarRepository sysMyCarRepository;
    @Override
    public SysMyCarRepository getBaseJapRepository() {
        return this.sysMyCarRepository;
    }
}
