package com.bailuyiting.sideparking.inter.feignfallback;

import com.bailuyiting.commons.core.entity.sideparking.SideParkingParkSection;
import com.bailuyiting.commons.until.FeignUntils;
import com.bailuyiting.sideparking.inter.feign.SideParkingParkSectionFeign;
import org.springframework.stereotype.Component;

@Component
public class SideParkingParkSectionFeignFallBack implements SideParkingParkSectionFeign {
    private Class c=SideParkingParkSection.class;
    @Override
    public SideParkingParkSection findParkSectionByID(String id) {
        return (SideParkingParkSection) FeignUntils.feignFailure(c);
    }
}
