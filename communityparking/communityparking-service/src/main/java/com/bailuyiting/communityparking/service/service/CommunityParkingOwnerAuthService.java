package com.bailuyiting.communityparking.service.service;


import com.bailuyiting.commons.core.entity.communityparking.CommunityParkingOwnerAuth;
import com.bailuyiting.commons.core.entity.communityparking.CommunityParkingParkInfo;
import com.bailuyiting.commons.core.service.BaseEntiryService;
import com.bailuyiting.commons.core.jpa.communityparking.CommunityParkingOwnerAuthRepository;

public interface CommunityParkingOwnerAuthService extends BaseEntiryService<CommunityParkingOwnerAuthRepository> {
    /**
     * 确实小区停车位申请通过并且生成小区停车位基础信息表
     * 开启事务
     * @param auth
     * @return
     */
    boolean confirmCommunityParkingOwnerAuthAndSaveCommunityParkingParkInfo(CommunityParkingOwnerAuth auth, CommunityParkingParkInfo parkInfo);
}
