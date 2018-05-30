package com.bailuyiting.sideparking.service.controller.parksection;

import com.bailuyiting.sideparking.service.service.SideParkingParkInfoService;
import com.bailuyiting.sideparking.service.service.SideParkingParkingSectionService;
import com.bailuyiting.commons.core.entity.sideparking.SideParkingParkInfo;
import com.bailuyiting.commons.core.entity.sideparking.SideParkingParkSection;
import com.bailuyiting.commons.until.ResultUtils;
import com.bailuyiting.commons.until.SQYMapUntils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Api(value = "路边停车停车位资源获取-API",description = "路边停车停车位资源获取-API")
@RestController
@RequestMapping(value = "api/")
public class ParkSectionGetController {
    @Autowired
    private SideParkingParkInfoService sideParkingParkInfoService;

    @Autowired
    private SideParkingParkingSectionService sideParkingParkingSectionService;
    /**
     *根据定位点和搜索半径获取街道段落
     * @param longitude
     * @param latitude
     * @param radius
     * @return
     */
    @ApiOperation(value = "根据定位点和搜索半径获取街道段落",httpMethod ="GET",notes = "停车位信息")
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
        List<SideParkingParkSection> list = this.sideParkingParkingSectionService.getBaseJapRepository().findByAround(area[0], area[1], area[2], area[3]);
        //根据街道区分停车位数据
        //返回信息
        return ResultUtils.success(list);
    }
    /**
     * 根据区域ID获取停车位信息
     * @param id
     * @return
     */
    @ApiOperation(value = "根据区域ID获取停车位信息",httpMethod ="GET",notes = "停车位信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id",value = "街道区域Id",paramType = "path",dataType = "String",required = true),
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @GetMapping(value = "parkInfo/id/{id}")
    public Map<String,Object> getParkingBaseById(@PathVariable(value = "id") String id){
        //MVC自动判空
        //查找数据库
        List<SideParkingParkInfo> result = this.sideParkingParkInfoService.getBaseJapRepository().findBySectionId(id);
        return ResultUtils.success(result);
    }
    /**
     * 根据路边停车位名称获取此路边停车位信息
     * @param carNum
     * @return
     */
    @ApiOperation(value = "根据路边停车位名称获取此路边停车位信息",httpMethod ="GET",notes = "停车位信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "carNum",value = "停车位编号",paramType = "path",dataType = "String",required = true),
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @GetMapping(value = "parkInfo/carNum/{carNum}")
    public Map<String,Object> findSideParkBaseByNum(@PathVariable(value = "carNum") String carNum) {
        SideParkingParkInfo result = this.sideParkingParkInfoService.getBaseJapRepository().findByParkNum(carNum);
        return ResultUtils.success(result);
    }

}
