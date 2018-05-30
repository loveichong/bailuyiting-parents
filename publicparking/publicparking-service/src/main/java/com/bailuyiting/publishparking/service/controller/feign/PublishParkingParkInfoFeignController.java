package com.bailuyiting.publishparking.service.controller.feign;

import com.bailuyiting.commons.core.entity.publishparking.PublishParkingParkInfo;
import com.bailuyiting.commons.until.FeignUntils;
import com.bailuyiting.publishparking.inter.feign.PublishParkingParkInfoFeign;
import com.bailuyiting.publishparking.service.service.PublishParkingParkInfoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "系统内部接口，不对外开放",description = "系统内部接口，不对外开放")
@RestController
public class PublishParkingParkInfoFeignController implements PublishParkingParkInfoFeign {
    @Autowired
    private PublishParkingParkInfoService publishParkingParkInfoService;

    @Override
    @RequestMapping(value="feign/parkInfo/id/{id}",method=RequestMethod.GET)
    public PublishParkingParkInfo findParkInfoByID(@PathVariable(value = "id") String id) {
        PublishParkingParkInfo one = this.publishParkingParkInfoService.getBaseJapRepository().findOne(id);
        return (PublishParkingParkInfo) FeignUntils.feignResult(one,PublishParkingParkInfo.class);
    }

    @Override
    @RequestMapping(value="feign/parkInfo/car/{carNum}",method=RequestMethod.GET)
    public PublishParkingParkInfo findParkInfoByCarNum(@PathVariable(value = "carNum") String carNum) {
        return null;
    }
}
