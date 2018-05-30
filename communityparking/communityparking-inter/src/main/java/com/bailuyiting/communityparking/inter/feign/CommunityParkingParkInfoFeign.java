package com.bailuyiting.communityparking.inter.feign;

import com.bailuyiting.commons.core.entity.communityparking.CommunityParkingParkInfo;
import com.bailuyiting.communityparking.inter.feignfallback.CommunityParkingParkInfoFeignFallBack;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 *<h1>Description:</h1>
 *<pre><h1>Company:</h1>www.loveichong.com</pre><hr>
 * @version1.0
 * @author SQY
 */
@FeignClient(name="bailuyiting-communityParking-service",fallback=CommunityParkingParkInfoFeignFallBack.class)
public interface CommunityParkingParkInfoFeign {

    @RequestMapping(value="feign/parkInfo/id/{id}",method=RequestMethod.GET)
    CommunityParkingParkInfo findParkInfoByID(@PathVariable(value = "id") String id);

    @RequestMapping(value="feign/parkInfo",method=RequestMethod.POST)
    CommunityParkingParkInfo save(@RequestBody CommunityParkingParkInfo parkInfo);
}
