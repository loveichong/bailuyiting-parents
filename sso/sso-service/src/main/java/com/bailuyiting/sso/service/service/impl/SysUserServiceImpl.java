package com.bailuyiting.sso.service.service.impl;

import com.bailuyiting.commons.core.entity.sso.SysUser;
import com.bailuyiting.commons.core.entity.sso.SysUserRoles;
import com.bailuyiting.commons.core.jpa.sso.SysUserRepository;
import com.bailuyiting.commons.core.jpa.sso.SysUserRolesRepository;
import com.bailuyiting.commons.until.DateUtils;
import com.bailuyiting.sso.service.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserRepository sysUserRepository;
    @Autowired
    private SysUserRolesRepository sysUserRolesRepository;

    /**
     * 用户相关操作都用事务
     * @return
     */
    @Override
    @Transactional
    public SysUserRepository getBaseJapRepository() {
        return this.sysUserRepository;
    }

    /**
     * 创建账户并且保存角色权限
     * @param user
     * @param userRoles
     * @return
     */
    @Override
    @Transactional
    public boolean saveUserAndRoles(SysUser user, int userRoles) {
        SysUserRoles roles = new SysUserRoles();
        SysUser save = this.sysUserRepository.save(user);
        roles.setCreateTime(DateUtils.formatNow());
        roles.setUserId(save.getId());
        roles.setUserRole(userRoles);
        roles.setSysAccount(user.getAccount());
        this.sysUserRolesRepository.save(roles);
        return true;
    }
}
