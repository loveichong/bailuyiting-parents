package com.bailuyiting.publishparking.service.controller.parkinfo;

import com.bailuyiting.commons.core.entity.publishparking.PublishParkingParkInfo;
import com.bailuyiting.commons.core.entity.sideparking.SideParkingParkInfo;
import com.bailuyiting.commons.core.entity.sideparking.SideParkingParkSection;
import com.bailuyiting.commons.until.ResultUtils;
import com.bailuyiting.commons.until.SQYMapUntils;
import com.bailuyiting.publishparking.service.service.PublishParkingParkInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Api(value = "公共停车停车位资源获取-API",description = "公共停车停车位资源获取-API")
@RestController
@RequestMapping(value = "api/publishParking/")
public class PublishParkingParkInfoGetController {
    @Autowired
    private PublishParkingParkInfoService publishParkingParkInfoService;
    /**
     *根据定位点和搜索半径获取街道段落
     * @param longitude
     * @param latitude
     * @param radius
     * @return
     */
    @ApiOperation(value = "根据定位点和搜索半径获取公共停车停车位",httpMethod ="GET",notes = "停车位信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "longitude",value = "经度",paramType = "query",dataType = "BigDecimal",required = true),
            @ApiImplicitParam(name = "latitude",value = "纬度",paramType = "query",dataType = "BigDecimal",required = true),
            @ApiImplicitParam(name = "radius",value = "查找半径",paramType = "query",dataType = "BigDecimal",required = true),
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @GetMapping(value = "parkSection/around")
    public Map<String,Object> getParkingSectionByAround(@RequestParam BigDecimal latitude, @RequestParam BigDecimal longitude,
                                                        @RequestParam Integer radius){

        //判空 MVC自动判空 无需处理
        //计算查找范围（0：最小纬度，1最小经度，2最大维度，3最大经度）
        double[] area= SQYMapUntils.getAround(latitude.doubleValue(),longitude.doubleValue(),radius);
        //查询数据
        List<PublishParkingParkInfo> list = this.publishParkingParkInfoService.getBaseJapRepository().findByAround(area[0], area[1], area[2], area[3]);
        //根据街道区分停车位数据
        //返回信息
        return ResultUtils.success(list);
    }
    /**
     * 根据公共停车ID获取停车位信息
     * @param id
     * @return
     */
    @ApiOperation(value = "根据公共停车ID获取停车位信息",httpMethod ="GET",notes = "停车位信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id",value = "公共停车位Id",paramType = "path",dataType = "String",required = true),
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @GetMapping(value = "parkInfo/id/{id}")
    public Map<String,Object> getParkingBaseById(@PathVariable(value = "id") String id){
        //MVC自动判空
        //查找数据库
        PublishParkingParkInfo result = this.publishParkingParkInfoService.getBaseJapRepository().findOne(id);
        return ResultUtils.success(result);
    }
}
