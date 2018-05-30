package com.bailuyiting.publishparking.inter.feign;

import com.bailuyiting.commons.core.entity.publishparking.PublishParkingParkInfo;
import com.bailuyiting.publishparking.inter.feignfallback.PublishParkingParkInfoFeignFallBack;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 *<h1>Description:</h1>
 *<pre><h1>Company:</h1>www.loveichong.com</pre><hr>
 * @version1.0
 * @author SQY
 */
@FeignClient(name="bailuyiting-publishParking-service",fallback=PublishParkingParkInfoFeignFallBack.class)
public interface PublishParkingParkInfoFeign {

    @RequestMapping(value="feign/parkInfo/id/{id}",method=RequestMethod.GET)
    PublishParkingParkInfo findParkInfoByID(@PathVariable(value = "id") String id);

    @RequestMapping(value="feign/parkInfo/car/{carNum}",method=RequestMethod.GET)
    PublishParkingParkInfo findParkInfoByCarNum(@PathVariable(value = "carNum") String carNum);


}
