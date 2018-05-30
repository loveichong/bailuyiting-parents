package com.bailuyiting.publishparking.inter.feignfallback;

import com.bailuyiting.commons.core.entity.publishparking.PublishParkingParkInfo;
import com.bailuyiting.commons.until.FeignUntils;
import com.bailuyiting.publishparking.inter.feign.PublishParkingParkInfoFeign;
import org.springframework.stereotype.Component;

@Component
public class PublishParkingParkInfoFeignFallBack implements PublishParkingParkInfoFeign {
    private Class c=PublishParkingParkInfo.class;
    @Override
    public PublishParkingParkInfo findParkInfoByID(String id) {
        return (PublishParkingParkInfo) FeignUntils.feignFailure(c);
    }

    @Override
    public PublishParkingParkInfo findParkInfoByCarNum(String carNum) {
        return (PublishParkingParkInfo) FeignUntils.feignFailure(c);
    }
}
