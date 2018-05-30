package com.bailuyiting.sso.service.service.impl;

import com.bailuyiting.commons.core.jpa.sso.SysUserRolesRepository;
import com.bailuyiting.sso.service.service.SysUserRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserRolesServiceImpl implements SysUserRolesService{
    @Autowired
    private SysUserRolesRepository sysUserRolesRepository;
    @Override
    public SysUserRolesRepository getBaseJapRepository() {
        return this.sysUserRolesRepository;
    }

}
