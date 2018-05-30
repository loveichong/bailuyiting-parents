package com.bailuyiting.commons.core.jpa.sso;


import com.bailuyiting.commons.core.entity.sso.SysUserRoles;
import com.bailuyiting.commons.core.repository.jpa.BaseStringJpaRepository;

public interface SysUserRolesRepository extends BaseStringJpaRepository<SysUserRoles> {

    SysUserRoles findBySysAccount(String account);
}
