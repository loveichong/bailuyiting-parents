package com.bailuyiting.sideparking.service.controller.feign;

import com.bailuyiting.commons.until.FeignUntils;
import com.bailuyiting.sideparking.service.service.SideParkingParkInfoService;
import com.bailuyiting.commons.core.entity.sideparking.SideParkingParkInfo;
import com.bailuyiting.sideparking.inter.feign.SideParkingParkInfoFeign;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@Api(value = "系统内部接口，不对外开放",description = "系统内部接口，不对外开放")
@RestController
public class SideParkingParkInfoFeignController implements SideParkingParkInfoFeign {
    @Autowired
    private SideParkingParkInfoService sideParkingParkInfoService;
    @Override
    @RequestMapping(value="feign/parkInfo/id/{id}",method=RequestMethod.GET)
    public SideParkingParkInfo findParkInfoByID(@PathVariable(value = "id")String id) {
        SideParkingParkInfo result = this.sideParkingParkInfoService.getBaseJapRepository().findOne(id);
        return (SideParkingParkInfo) FeignUntils.feignResult(result,SideParkingParkInfo.class);
    }

    @Override
    @RequestMapping(value="feign/parkInfo",method=RequestMethod.PUT)
    public SideParkingParkInfo update(@RequestBody SideParkingParkInfo info) {
        SideParkingParkInfo result = this.sideParkingParkInfoService.getBaseJapRepository().save(info);
        return (SideParkingParkInfo) FeignUntils.feignResult(result,SideParkingParkInfo.class);
}

    @Override
    @RequestMapping(value="feign/parkInfo",method=RequestMethod.POST)
    public SideParkingParkInfo save(@RequestBody SideParkingParkInfo info) {
        SideParkingParkInfo result = this.sideParkingParkInfoService.getBaseJapRepository().save(info);
        return (SideParkingParkInfo) FeignUntils.feignResult(result,SideParkingParkInfo.class);
    }
}
