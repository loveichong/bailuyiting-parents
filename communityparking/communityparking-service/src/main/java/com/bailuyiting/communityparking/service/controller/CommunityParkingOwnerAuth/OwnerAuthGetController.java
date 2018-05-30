package com.bailuyiting.communityparking.service.controller.CommunityParkingOwnerAuth;

import com.alibaba.fastjson.JSONObject;
import com.bailuyiting.commons.core.entity.communityparking.CommunityDataBase;
import com.bailuyiting.commons.core.entity.communityparking.CommunityParkingOwnerAuth;
import com.bailuyiting.commons.core.entity.communityparking.CommunityParkingParkInfo;
import com.bailuyiting.commons.core.entity.communityparking.CommunityParkingUserAuth;
import com.bailuyiting.commons.until.JSONUtils;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "小区停车位出租功能获取-API",description = "小区停车位出租功能获取-API")
@RestController
@RequestMapping(value = "api/communityPark/")
public class OwnerAuthGetController {
    @Autowired
    private CommunityParkingUserAuthService communityParkingUserAuthService;
    @Autowired
    private CommunityDataBaseService communityDataBaseService;
    @Autowired
    private CommunityParkingOwnerAuthService communityParkingOwnerAuthService;
    @Autowired
    private CommunityParkingParkInfoService communityParkingParkInfoService;

    /**
     * 查看账户是否已开通停车位出租功能
     * 如果没有开通返回此账户小区ID
     * @param req
     * @return
     */
    @ApiOperation(value = "查看账户是否已开通停车位出租功能",httpMethod ="GET",notes = "停车位信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @GetMapping(value = "ownerStatus")
    public Map<String,Object> findCommunityOwnerStatus(HttpServletRequest req) {
        List<CommunityParkingOwnerAuth> account = this.communityParkingOwnerAuthService.getBaseJapRepository().findByAccount(SQYRequestUntils.getAccount(req));
        if(account.isEmpty()){
            CommunityParkingUserAuth user = this.communityParkingUserAuthService.getBaseJapRepository().findByAccount(SQYRequestUntils.getAccount(req));
            if(user==null){
                return ResultUtils.errorByUserDefine("600","未开通小区功能");
            }
            CommunityDataBase dataBase = this.communityDataBaseService.getBaseJapRepository().findOne(user.getCommunityId());
            if(dataBase==null){
                return ResultUtils.errorByUserDefine("600","未开通小区功能");
            }
            return ResultUtils.errorBySQY("600","未开通小区功能",dataBase);
        }
        else {
            HashMap<String, Object> map = new HashMap<>();
            map.put("account", account);
            for(CommunityParkingOwnerAuth owner:account) {
                CommunityDataBase one = this.communityDataBaseService.getBaseJapRepository().findOne(owner.getCommunityId());
                if (one != null) {
                    map.put("community", one);
                }
            }
            return ResultUtils.success(map);
        }
    }
    /**
     * 获取业主所有停车位信息
     * @param req
     * @return
     */
    @ApiOperation(value = "获取业主所有停车位信息,分为两种情况，第一种发布车位没通过，只返回authStatus状态，第二种，发布车位通过，同时返回authStatus和parkStatus,前端自行判断",
            httpMethod ="GET",notes = "停车位信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @GetMapping(value = "ownerAuth/all")
    public Map<String,Object> findParkByOwner(HttpServletRequest req) {
        List<CommunityParkingOwnerAuth> result = this.communityParkingOwnerAuthService.getBaseJapRepository().findByAccount(SQYRequestUntils.getAccount(req));
        if (result.isEmpty()) {
            return ResultUtils.errorByUserDefine("600", "此账户未申请开通出租停车位功能");
        }
        //获取地址
        CommunityDataBase one = this.communityDataBaseService.getBaseJapRepository().findOne(result.get(0).getCommunityId());
        ArrayList<Object> list = new ArrayList<>();
        for (CommunityParkingOwnerAuth auth : result) {
            //
            CommunityParkingParkInfo base = this.communityParkingParkInfoService.getBaseJapRepository().findByParkOwnerID(auth.getId());
            JSONObject object = new JSONObject();
            object.putAll(JSONUtils.objectToMap(auth));
            if (base != null) {
                object.putAll(JSONUtils.objectToMap(base));
            }
            object.put("address", one.getAddress());
            list.add(object);
        }
        return ResultUtils.success(list);
    }
}
