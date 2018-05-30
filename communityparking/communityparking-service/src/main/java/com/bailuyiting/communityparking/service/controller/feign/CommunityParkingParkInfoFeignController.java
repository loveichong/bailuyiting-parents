package com.bailuyiting.communityparking.service.controller.feign;

import com.bailuyiting.commons.core.entity.communityparking.CommunityParkingParkInfo;


import com.bailuyiting.commons.until.FeignUntils;
import com.bailuyiting.communityparking.inter.feign.CommunityParkingParkInfoFeign;
import com.bailuyiting.commons.core.jpa.communityparking.CommunityParkingParkInfoRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@Api(value = "系统内部接口，不对外开放",description = "系统内部接口，不对外开放")
@RestController
public class CommunityParkingParkInfoFeignController implements CommunityParkingParkInfoFeign {
    @Autowired
    private CommunityParkingParkInfoRepository communityParkingParkInfoRepository;
    @Override
    @RequestMapping(value="feign/parkInfo/id/{id}",method=RequestMethod.GET)
    public CommunityParkingParkInfo findParkInfoByID(@PathVariable(value = "id") String id) {
        CommunityParkingParkInfo result = this.communityParkingParkInfoRepository.findOne(id);
        return (CommunityParkingParkInfo) FeignUntils.feignResult(result,CommunityParkingParkInfo.class);
    }

    @Override
    @RequestMapping(value="feign/parkInfo",method=RequestMethod.POST)
    public CommunityParkingParkInfo save(@RequestBody CommunityParkingParkInfo parkInfo) {
        CommunityParkingParkInfo result = this.communityParkingParkInfoRepository.save(parkInfo);
        return (CommunityParkingParkInfo) FeignUntils.feignResult(result,CommunityParkingParkInfo.class);
    }
}
