package com.bailuyiting.sideparking.service.controller.feign;



import com.bailuyiting.commons.core.entity.sideparking.SideParkingParkSection;

import com.bailuyiting.commons.until.FeignUntils;
import com.bailuyiting.sideparking.inter.feign.SideParkingParkSectionFeign;
import com.bailuyiting.sideparking.service.service.SideParkingParkingSectionService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "系统内部接口，不对外开放",description = "系统内部接口，不对外开放")
@RestController
public class SideParkingParkSectionFeignController implements SideParkingParkSectionFeign {
    @Autowired
    private SideParkingParkingSectionService sideParkingParkingSectionService;

    @Override
    @RequestMapping(value="feign/parkSection/id/{id}",method=RequestMethod.GET)
    public SideParkingParkSection findParkSectionByID(@PathVariable(value = "id") String id) {
        SideParkingParkSection result = sideParkingParkingSectionService.getBaseJapRepository().findOne(id);
        return (SideParkingParkSection) FeignUntils.feignResult(result,SideParkingParkSection.class);
    }
}
