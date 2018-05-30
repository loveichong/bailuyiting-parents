package com.bailuyiting.sideparking.inter.feignfallback;

import com.bailuyiting.commons.core.entity.sideparking.SideParkingParkInfo;
import com.bailuyiting.commons.until.FeignUntils;
import com.bailuyiting.sideparking.inter.feign.SideParkingParkInfoFeign;
import org.springframework.stereotype.Component;

@Component
public class SideParkingParkInfoFeignFallBack implements SideParkingParkInfoFeign {
    private Class c=SideParkingParkInfo.class;
    @Override
    public SideParkingParkInfo findParkInfoByID(String id) {
        return (SideParkingParkInfo) FeignUntils.feignFailure(c);
    }

    @Override
    public SideParkingParkInfo update(SideParkingParkInfo info) {
        return (SideParkingParkInfo) FeignUntils.feignFailure(c);
    }

    @Override
    public SideParkingParkInfo save(SideParkingParkInfo info) {
        return (SideParkingParkInfo) FeignUntils.feignFailure(c);
    }
}
