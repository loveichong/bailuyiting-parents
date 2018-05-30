package com.bailuyiting.communityparking.inter.feignfallback;

import com.bailuyiting.commons.core.entity.communityparking.CommunityParkingParkInfo;
import com.bailuyiting.commons.until.FeignUntils;
import com.bailuyiting.communityparking.inter.feign.CommunityParkingParkInfoFeign;
import org.springframework.stereotype.Component;

@Component
public class CommunityParkingParkInfoFeignFallBack implements CommunityParkingParkInfoFeign {
    private Class s=CommunityParkingParkInfoFeignFallBack.class;
    private CommunityParkingParkInfo fallBack(){
        return (CommunityParkingParkInfo) FeignUntils.feignFailure(s);
    }
    @Override
    public CommunityParkingParkInfo findParkInfoByID(String id) {
        return this.fallBack();
    }

    @Override
    public CommunityParkingParkInfo save(CommunityParkingParkInfo parkInfo) {
        return this.fallBack();
    }
}
