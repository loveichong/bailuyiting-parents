package com.bailuyiting.sso.service.service;

import com.bailuyiting.commons.core.entity.order.ParkingOrderBase;
import com.bailuyiting.commons.core.entity.sso.SysBalanceLog;
import com.bailuyiting.commons.core.jpa.sso.SysBalanceLogRepository;
import com.bailuyiting.commons.core.service.BaseEntiryService;

public interface SysBalanceLogService extends BaseEntiryService<SysBalanceLogRepository> {

       void confirmBalanceRechargeAndSaveAccount(String id);
       void payForOrder(ParkingOrderBase order,SysBalanceLog log);
}
