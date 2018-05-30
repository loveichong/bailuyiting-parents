package com.bailuyiting.communityparking.service.controller.CommunityParkingUserAuth;

import com.bailuyiting.commons.core.entity.communityparking.CommunityParkingUserAuth;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Api(value = "小区停车位功能获取-API",description = "小区停车位功能获取-API")
@RestController
@RequestMapping(value = "api/communityPark/")
public class UserAuthGetController {
    @Autowired
    private CommunityParkingUserAuthService communityParkingUserAuthService;
    @Autowired
    private CommunityDataBaseService communityDataBaseService;
    @Autowired
    private CommunityParkingOwnerAuthService communityParkingOwnerAuthService;
    @Autowired
    private CommunityParkingParkInfoService communityParkingParkInfoService;

    /**
     * 查看用户是否已经开通小区停车功能
     * @param req
     * @return
     */
    @ApiOperation(value = "查看账户是否已开通小区停车功能",httpMethod ="GET",notes = "停车位信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @GetMapping(value = "userStatus")
    public Map<String,Object> findCommunityStatus(HttpServletRequest req) {
        CommunityParkingUserAuth account = this.communityParkingUserAuthService.getBaseJapRepository().findByAccount(SQYRequestUntils.getAccount(req));
        if(account==null){
            return ResultUtils.errorByUserDefine("600","未开通");
        }
        return ResultUtils.success(account);
    }
}
