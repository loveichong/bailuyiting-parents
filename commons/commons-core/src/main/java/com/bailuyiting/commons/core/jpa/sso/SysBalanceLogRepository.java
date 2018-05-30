package com.bailuyiting.commons.core.jpa.sso;


import com.bailuyiting.commons.core.entity.sso.SysBalanceLog;
import com.bailuyiting.commons.core.repository.jpa.BaseStringJpaRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SysBalanceLogRepository extends BaseStringJpaRepository<SysBalanceLog> {
    List<SysBalanceLog> findBySysAccount(String account, Pageable page);
}
