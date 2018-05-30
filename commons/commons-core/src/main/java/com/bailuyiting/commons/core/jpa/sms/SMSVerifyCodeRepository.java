package com.bailuyiting.commons.core.jpa.sms;

import com.bailuyiting.commons.core.entity.module.sms.SMSVerifyCode;
import com.bailuyiting.commons.core.repository.jpa.BaseStringJpaRepository;


public interface SMSVerifyCodeRepository extends BaseStringJpaRepository<SMSVerifyCode> {

    SMSVerifyCode findByPhone(String tel);

}
