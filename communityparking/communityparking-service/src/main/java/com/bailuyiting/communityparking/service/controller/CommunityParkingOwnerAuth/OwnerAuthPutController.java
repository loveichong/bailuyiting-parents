package com.bailuyiting.communityparking.service.controller.CommunityParkingOwnerAuth;

import com.bailuyiting.commons.core.entity.communityparking.CommunityDataBase;
import com.bailuyiting.commons.core.entity.communityparking.CommunityParkingOwnerAuth;
import com.bailuyiting.commons.core.entity.communityparking.CommunityParkingParkInfo;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Api(value = "小区停车位出租功能更新-API",description = "小区停车位出租功能更新-API")
@RestController
@RequestMapping(value = "api/communityPark/")
public class OwnerAuthPutController {
    @Autowired
    private CommunityParkingUserAuthService communityParkingUserAuthService;
    @Autowired
    private CommunityDataBaseService communityDataBaseService;
    @Autowired
    private CommunityParkingOwnerAuthService communityParkingOwnerAuthService;
    @Autowired
    private CommunityParkingParkInfoService communityParkingParkInfoService;
    /**
     * 后台确认小区业主车位认证通过
     * @param parkOwnerId
     * @param req
     * @return
     */
    @ApiOperation(value = "后台确认小区业主车位认证通过",httpMethod ="PUT",notes = "停车位信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "parkOwnerId",value = "小区业主车位Id",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @PutMapping (value = "ownerAuth")
    public Map<String,Object> confirmPackOwner(@RequestParam String parkOwnerId,HttpServletRequest req){
        //MVC自动判空
        //确实是否有此车位认证
        CommunityParkingOwnerAuth ownerAuth = this.communityParkingOwnerAuthService.getBaseJapRepository().findOne(parkOwnerId);
        if(ownerAuth==null){
            return ResultUtils.error500("无此车位认证，输入参数有误");
        }
        if(ownerAuth.getAuthStatus()==3){
            return ResultUtils.error500("此车位已经认证通过，请勿重新认证");
        }
        //认证车位
        ownerAuth.setAuthStatus(3);
        //生成停车位基础信息
        CommunityParkingParkInfo base = new CommunityParkingParkInfo();
        base.setCreateTime(DateUtils.formatNow());//设置创建时间
        base.setSysAccount(SQYRequestUntils.getAccount(req));//设置账户
        base.setParkOwnerID(ownerAuth.getId());//设置停车位业务ID
        base.setCommunityId(ownerAuth.getCommunityId());//设置小区ID
        base.setParkNum(ownerAuth.getParkNum());//设置停车位名称
        base.setParkStatus(0);//设置状态 未发布
        //设置停车位具体地址
        CommunityDataBase ba = this.communityDataBaseService.getBaseJapRepository().findOne(ownerAuth.getCommunityId());
        StringBuilder address = new StringBuilder();
        address.append(ba.getAddress())
                .append(ownerAuth.getParkNum());
        base.setParkAddress(address.toString());
        this.communityParkingOwnerAuthService.confirmCommunityParkingOwnerAuthAndSaveCommunityParkingParkInfo(ownerAuth,base);
        return ResultUtils.success();
    }

}
