package com.bailuyiting.sso.service.service;

import com.bailuyiting.commons.core.entity.sso.SysUser;
import com.bailuyiting.commons.core.jpa.sso.SysUserRepository;
import com.bailuyiting.commons.core.service.BaseEntiryService;

public interface SysUserService extends BaseEntiryService<SysUserRepository> {

    boolean saveUserAndRoles(SysUser user,int userRoles);
}
