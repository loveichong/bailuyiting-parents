package com.bailuyiting.communityparking.service.controller.CommunityDataBase;

import com.bailuyiting.commons.core.entity.communityparking.CommunityDataBase;
import com.bailuyiting.commons.core.entity.communityparking.CommunityDataBaseFromUser;
import com.bailuyiting.commons.until.DateUtils;
import com.bailuyiting.commons.until.ResultUtils;
import com.bailuyiting.commons.until.SQYRequestUntils;
import com.bailuyiting.communityparking.service.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
@Api(value = "小区信息资源增加-API",description = "小区信息资源增加-API")
@RestController
@RequestMapping(value = "api/community")
public class DataBasePostController {
    @Autowired
    private CommunityParkingUserAuthService communityParkingUserAuthService;
    @Autowired
    private CommunityDataBaseService communityDataBaseService;
    @Autowired
    private CommunityParkingOwnerAuthService communityParkingOwnerAuthService;
    @Autowired
    private CommunityParkingParkInfoService communityParkingParkInfoService;
    @Autowired
    private CommunityDataBaseFromUserService communityDataBaseFromUserService;
    /**
     * 小区信息录入
     * @param communityName
     * @param province
     * @param city
     * @param area
     * @param req
     * @return
     */
    @ApiOperation(value = "小区信息录入",httpMethod ="POST",notes = "停车位信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "communityName",value = "小区名字",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "province",value = "省份",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "city",value = "城市",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "area",value = "区域",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "street",value = "街道",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @PostMapping(value = "baseData")
    public Map<String,Object> saveCommunityData(@RequestParam String communityName,@RequestParam String province,@RequestParam String city,
                                                @RequestParam String area,@RequestParam String street,HttpServletRequest req) {
        //mvc判空
        //写入判断权限
        //判断是否已经有此小区
        CommunityDataBase base = this.communityDataBaseService.getBaseJapRepository().findByCommunityName(communityName);
        if(base!=null){
            return ResultUtils.error500("此小区名字已经录入");
        }
        //小区录入
        CommunityDataBase result = new CommunityDataBase();
        result.setCreateTime(DateUtils.formatNow());//设置创建时间
        result.setCreater(SQYRequestUntils.getAccount(req));//设置创建者
        result.setArea(area);
        result.setCity(city);
        result.setProvince(province);
        result.setStreet(street);//街道
        result.setCommunityName(communityName);
        result.setBaseStatus(1);//未申请合作
        CommunityDataBase save = this.communityDataBaseService.getBaseJapRepository().save(result);
        return ResultUtils.success(save);
    }
    /**
     * 用户申请小区信息录入
     * @param communityName
     * @param province
     * @param city
     * @param area
     * @param req
     * @return
     */
    @ApiOperation(value = "用户申请小区信息录入",httpMethod ="POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "communityName",value = "小区名字",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "province",value = "省份",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "city",value = "城市",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "area",value = "区域",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "street",value = "街道",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @PostMapping(value = "baseData/user")
    public Map<String,Object> saveCommunityDataByUser(@RequestParam String communityName,@RequestParam String province,@RequestParam String city,
                                                @RequestParam String area,@RequestParam String street,HttpServletRequest req) {
        //mvc判空
        //小区录入
        CommunityDataBaseFromUser result = new CommunityDataBaseFromUser();
        result.setCreateTime(DateUtils.formatNow());//设置创建时间
        result.setCreater(SQYRequestUntils.getAccount(req));//设置创建者
        result.setArea(area);
        result.setCity(city);
        result.setProvince(province);
        result.setStreet(street);//街道
        result.setCommunityName(communityName);
        this.communityDataBaseFromUserService.getBaseJapRepository().save(result);
        return ResultUtils.success();
    }
}
