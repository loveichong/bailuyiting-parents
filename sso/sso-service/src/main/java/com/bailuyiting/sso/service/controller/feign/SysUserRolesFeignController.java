package com.bailuyiting.sso.service.controller.feign;

import com.bailuyiting.commons.core.entity.sso.SysUserRoles;

import com.bailuyiting.commons.until.FeignUntils;
import com.bailuyiting.sso.inter.feign.SysUserRolesFeign;
import com.bailuyiting.sso.service.service.SysUserRolesService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
@Api(value = "系统内部接口，不对外开放",description = "系统内部接口，不对外开放")
@RestController
public class SysUserRolesFeignController implements SysUserRolesFeign {
    @Autowired
    private SysUserRolesService sysUserRolesService;

    @Override
    @RequestMapping(value="feign/roles/account/{account}",method=RequestMethod.GET)
    public SysUserRoles findByAccount(@PathVariable(value = "account") String account) {
        SysUserRoles result = this.sysUserRolesService.getBaseJapRepository().findBySysAccount(account);
        return (SysUserRoles) FeignUntils.feignResult(result,SysUserRoles.class);
    }
}
