package com.bailuyiting.module.wechat.jpa;

import com.bailuyiting.commons.core.repository.jpa.BaseStringJpaRepository;
import com.bailuyiting.module.wechat.entity.WeChatPayLog;

public interface WeChatPayLogRepository extends BaseStringJpaRepository<WeChatPayLog> {
    WeChatPayLog findByOrderId(String id);
}
