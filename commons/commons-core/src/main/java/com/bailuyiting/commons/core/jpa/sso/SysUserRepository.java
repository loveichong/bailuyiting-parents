package com.bailuyiting.commons.core.jpa.sso;

import com.bailuyiting.commons.core.entity.sso.SysUser;
import com.bailuyiting.commons.core.repository.jpa.BaseStringJpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface SysUserRepository extends BaseStringJpaRepository<SysUser> {
    /**
     * 更改账户状态
     * @param account
     * @param state
     */
    @Query(value = "UPDATE sys_user SET state=?2 WHERE account=?1 ",nativeQuery = true)
    @Modifying
    @Transactional
    void updateStatusByAccount(String account,int state);
    SysUser findByAccount(String account);

}
