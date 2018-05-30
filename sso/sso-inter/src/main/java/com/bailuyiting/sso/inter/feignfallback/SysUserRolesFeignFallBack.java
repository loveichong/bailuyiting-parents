package com.bailuyiting.sso.inter.feignfallback;

import com.bailuyiting.commons.core.entity.sso.SysUserRoles;
import com.bailuyiting.sso.inter.feign.SysUserRolesFeign;
import org.springframework.stereotype.Component;

@Component
public class SysUserRolesFeignFallBack implements SysUserRolesFeign {

    @Override
    public SysUserRoles findByAccount(String account) {
        SysUserRoles roles = new SysUserRoles();
        roles.setFeignSuccess(false);
        return roles;
    }
}
