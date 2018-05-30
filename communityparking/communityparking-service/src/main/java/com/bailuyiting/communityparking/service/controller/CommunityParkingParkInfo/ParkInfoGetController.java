package com.bailuyiting.communityparking.service.controller.CommunityParkingParkInfo;

import com.bailuyiting.commons.core.entity.communityparking.CommunityParkingParkInfo;
import com.bailuyiting.commons.core.entity.communityparking.CommunityParkingUserAuth;
import com.bailuyiting.commons.until.DateUtils;
import com.bailuyiting.commons.until.ResultUtils;
import com.bailuyiting.commons.until.SQYRequestUntils;
import com.bailuyiting.communityparking.service.service.CommunityDataBaseService;
import com.bailuyiting.communityparking.service.service.CommunityParkingOwnerAuthService;
import com.bailuyiting.communityparking.service.service.CommunityParkingParkInfoService;
import com.bailuyiting.communityparking.service.service.CommunityParkingUserAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Api(value = "小区停车位资源获取-API",description = "小区停车位资源获取-API")
@RestController
@RequestMapping(value = "api/communityPark/")
public class ParkInfoGetController {
    @Autowired
    private CommunityParkingUserAuthService communityParkingUserAuthService;
    @Autowired
    private CommunityDataBaseService communityDataBaseService;
    @Autowired
    private CommunityParkingOwnerAuthService communityParkingOwnerAuthService;
    @Autowired
    private CommunityParkingParkInfoService communityParkingParkInfoService;
    /**
     * 获取用户小区停车位列表
     * @param page
     * @param size
     * @param req
     * @return
     */
    @ApiOperation(value = "获取用户小区正在出租的停车位列表",httpMethod ="GET",notes = "停车位信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "page",value = "页数,第一页是0",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(name = "size",value = "行数",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @GetMapping(value = "parkInfos")
    public Map<String,Object> findpackspace(@RequestParam Integer page,@RequestParam Integer size, HttpServletRequest req) {
        //MVC自动判空
        //判断是否开通小区功能 （前端处理）
        //获取此账户的小区ID
        //查找所有停车位状态是2的停车位
        CommunityParkingUserAuth user = this.communityParkingUserAuthService.getBaseJapRepository().findByAccount(SQYRequestUntils.getAccount(req));
        String id = user.getCommunityId();
        if(user.getCommunityId()==null){
            return ResultUtils.error500("此用户未开通小区停车功能");
        }
        List<CommunityParkingParkInfo> result = this.communityParkingParkInfoService.getBaseJapRepository().findByCommunityIdAndParkStatus(id,2,new PageRequest(page,size));//2  没有被使用
        return ResultUtils.success(result);
    }

}
