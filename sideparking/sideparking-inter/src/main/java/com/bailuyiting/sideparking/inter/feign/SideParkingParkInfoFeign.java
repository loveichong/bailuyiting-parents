package com.bailuyiting.sideparking.inter.feign;

import com.bailuyiting.commons.core.entity.sideparking.SideParkingParkInfo;
import com.bailuyiting.sideparking.inter.feignfallback.SideParkingParkInfoFeignFallBack;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 *<h1>Description:</h1>
 *<pre><h1>Company:</h1>www.loveichong.com</pre><hr>
 * @version1.0
 * @author SQY
 */
@FeignClient(name="bailuyiting-sideParking-service",fallback=SideParkingParkInfoFeignFallBack.class)
public interface SideParkingParkInfoFeign {

    @RequestMapping(value="feign/parkInfo/id/{id}",method=RequestMethod.GET)
    SideParkingParkInfo findParkInfoByID(@PathVariable(value = "id")String id);

    @RequestMapping(value="feign/parkInfo",method=RequestMethod.PUT)
    SideParkingParkInfo update(@RequestBody SideParkingParkInfo info);

    @RequestMapping(value="feign/parkInfo",method=RequestMethod.POST)
    SideParkingParkInfo save(@RequestBody SideParkingParkInfo info);

}
