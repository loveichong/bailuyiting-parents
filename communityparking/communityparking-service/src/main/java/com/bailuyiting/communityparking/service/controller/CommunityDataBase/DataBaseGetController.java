package com.bailuyiting.communityparking.service.controller.CommunityDataBase;

import com.bailuyiting.commons.core.entity.communityparking.CommunityDataBase;
import com.bailuyiting.commons.until.ResultUtils;
import com.bailuyiting.communityparking.service.service.CommunityDataBaseService;
import com.bailuyiting.communityparking.service.service.CommunityParkingOwnerAuthService;
import com.bailuyiting.communityparking.service.service.CommunityParkingParkInfoService;
import com.bailuyiting.communityparking.service.service.CommunityParkingUserAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@Api(value = "小区信息资源获取-API",description = "小区信息资源获取-API")
@RestController
@RequestMapping(value = "api/community")
public class DataBaseGetController {
    @Autowired
    private CommunityParkingUserAuthService communityParkingUserAuthService;
    @Autowired
    private CommunityDataBaseService communityDataBaseService;
    @Autowired
    private CommunityParkingOwnerAuthService communityParkingOwnerAuthService;
    @Autowired
    private CommunityParkingParkInfoService communityParkingParkInfoService;
    /**
     * 根据关键字模糊查询小区
     * @param communityName
     * @return
     */
    @ApiOperation(value = "根据关键字模糊查询小区",httpMethod ="GET",notes = "停车位信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "communityName",value = "小区名字关键字",paramType = "path",dataType = "String",required = true),
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @GetMapping(value = "name/{communityName}")
    public Map<String,Object> findSideParkBaseByNum(@PathVariable(value = "communityName") String communityName) {
        //MVC自己判空
        //根据关键字模糊查找数据
        if(communityName.length()<2){
            return ResultUtils.errorByUserDefine("600","一个字无法模糊查询");
        }
        List<CommunityDataBase> result = this.communityDataBaseService.getBaseJapRepository().findByCommunityNameLike(communityName);
        return ResultUtils.success(result);
    }

    /**
     * 根据小区Id查询小区信息
     * @param id
     * @return
     */
    @ApiOperation(value = "根据小区Id查询小区信息",httpMethod ="GET",notes = "根据小区Id查询小区信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @GetMapping(value = "id/{id}")
    public Map<String,Object> findSideParkBaseById(@PathVariable(value = "id") String id) {
        CommunityDataBase result = this.communityDataBaseService.getBaseJapRepository().findOne(id);
        return ResultUtils.success(result);
    }
}
