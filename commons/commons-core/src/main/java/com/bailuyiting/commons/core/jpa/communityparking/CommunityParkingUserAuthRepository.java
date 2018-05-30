package com.bailuyiting.commons.core.jpa.communityparking;


import com.bailuyiting.commons.core.entity.communityparking.CommunityParkingUserAuth;
import com.bailuyiting.commons.core.repository.jpa.BaseStringJpaRepository;

public interface CommunityParkingUserAuthRepository extends BaseStringJpaRepository<CommunityParkingUserAuth> {
    CommunityParkingUserAuth findByRealName(String realName);
    CommunityParkingUserAuth findByCommunityId(String communityId);
    CommunityParkingUserAuth findByAccount(String Account);
    CommunityParkingUserAuth findByCertificateNum(String idNum);
}
