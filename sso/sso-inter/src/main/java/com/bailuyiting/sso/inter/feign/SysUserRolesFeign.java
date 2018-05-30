package com.bailuyiting.sso.inter.feign;

import com.bailuyiting.commons.core.entity.sso.SysUserRoles;
import com.bailuyiting.sso.inter.feignfallback.SysUserRolesFeignFallBack;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 *<h1>Description:</h1>
 *<pre><h1>Company:</h1>www.loveichong.com</pre><hr>
 * @version1.0
 * @author SQY
 */
@FeignClient(name="bailuyiting-sso-service",fallback=SysUserRolesFeignFallBack.class)
public interface SysUserRolesFeign {

	@RequestMapping(value="feign/roles/account/{account}",method=RequestMethod.GET)
    SysUserRoles findByAccount(@PathVariable(value = "account") String account);

}
