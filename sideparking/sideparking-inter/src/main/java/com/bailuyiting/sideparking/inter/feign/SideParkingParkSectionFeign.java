package com.bailuyiting.sideparking.inter.feign;

import com.bailuyiting.commons.core.entity.sideparking.SideParkingParkSection;
import com.bailuyiting.sideparking.inter.feignfallback.SideParkingParkSectionFeignFallBack;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *<h1>Description:</h1>
 *<pre><h1>Company:</h1>www.loveichong.com</pre><hr>
 * @version1.0
 * @author SQY
 */
@FeignClient(name="bailuyiting-sideParking-service",fallback=SideParkingParkSectionFeignFallBack.class)
public interface SideParkingParkSectionFeign {

    @RequestMapping(value="feign/parkSection/id/{id}",method=RequestMethod.GET)
    SideParkingParkSection findParkSectionByID(@PathVariable(value = "id") String id);

}
