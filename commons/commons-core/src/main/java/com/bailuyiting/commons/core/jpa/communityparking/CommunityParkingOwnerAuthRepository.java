package com.bailuyiting.commons.core.jpa.communityparking;


import com.bailuyiting.commons.core.entity.communityparking.CommunityParkingOwnerAuth;
import com.bailuyiting.commons.core.repository.jpa.BaseStringJpaRepository;

import java.util.List;

public interface CommunityParkingOwnerAuthRepository extends BaseStringJpaRepository<CommunityParkingOwnerAuth> {
    List<CommunityParkingOwnerAuth> findByAccount(String account);
    CommunityParkingOwnerAuth findByRealName(String realName);
    CommunityParkingOwnerAuth findByCarNum(String carNum);
}
